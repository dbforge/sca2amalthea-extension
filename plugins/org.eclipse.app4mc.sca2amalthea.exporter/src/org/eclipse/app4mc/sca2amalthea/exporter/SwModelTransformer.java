/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.exporter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.app4mc.amalthea.model.ActivityGraph;
import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.Group;
import org.eclipse.app4mc.amalthea.model.ISR;
import org.eclipse.app4mc.amalthea.model.LabelAccessEnum;
import org.eclipse.app4mc.amalthea.model.ModeSwitchEntry;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableCall;
import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.amalthea.model.Semaphore;
import org.eclipse.app4mc.amalthea.model.SemaphoreAccess;
import org.eclipse.app4mc.amalthea.model.SemaphoreAccessEnum;
import org.eclipse.app4mc.amalthea.model.StringObject;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockFunction.LockType;
import org.eclipse.app4mc.sca2amalthea.exporter.util.CustomPropertiesAdder;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Container;
import org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Function;
import org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Label;
import org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess;
import org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry;
import org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Project;
import org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.emf.common.util.EList;

/**
 * This class transforms all SwModel related part and sub-models to the AMALTHEA model
 */
public class SwModelTransformer {

    private final TransformerDataStore data;

    private final ComponentModelTransformer componentTransformer;

    private final SwModelTypeDefTransformer typeExporter = new SwModelTypeDefTransformer();

    /**
     * @param dataStore
     * @param componentTransformer
     */
    public SwModelTransformer(final TransformerDataStore dataStore,
        final ComponentModelTransformer componentTransformer) {
        super();
        this.data = dataStore;
        this.componentTransformer = componentTransformer;
    }


    /**
     * @param amaltheaModel resulting AMALTHEA model
     * @param resource SCAIR to be transformed
     */
    public void transform(final Amalthea amaltheaModel, final SCAResource resource) {
        SWModel swModel = AmaltheaFactory.eINSTANCE.createSWModel();
        amaltheaModel.setSwModel(swModel);

        this.typeExporter.transform(resource, swModel);

        transformLabels(resource, swModel);

        Set < Function > allFunctions = getAllfunctions(resource);
        Set < Function > ignoreList = prepareIgnoreList(allFunctions);
        allFunctions.removeAll(ignoreList);

        EList < Runnable > runnables = swModel.getRunnables();
        EList < Task > tasks = swModel.getTasks();
        EList < ISR > isrs = swModel.getIsrs();
        transformFirstLevel(allFunctions, runnables, tasks, isrs);

        for (Function func: allFunctions) {
            try {
                if (func.getType().getLiteral().equalsIgnoreCase(EFunctionTypeEnum.RUNNABLE.getLiteral())) {
                    transformRunnableInternals(func);
                } else if (func.getType().getLiteral().equalsIgnoreCase(EFunctionTypeEnum.TASK.getLiteral())) {
                    transformTaskInternals(func);
                } else if (func.getType().getLiteral().equalsIgnoreCase(EFunctionTypeEnum.ISR.getLiteral())) {
                    transformISRInternals(func);
                }
            } catch (Exception e) {
                LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "**Function transformation problem" + func.getName(),
                    this.getClass(), Activator.PLUGIN_ID);
                LogUtil.logException(LLVMLogUtil.LOG_MSG_ID, e.getClass(), e, Activator.PLUGIN_ID);
            }
        }

    }


    /**
     * @param resource
     * @param swModel
     */
    private void transformLabels(final SCAResource resource, final SWModel swModel) {
        EList < Label > labels = ((Project) resource.getContents().get(0)).getLabels();
        EList < org.eclipse.app4mc.amalthea.model.Label > amlabels = swModel.getLabels();

        for (Label label: labels) {
            org.eclipse.app4mc.amalthea.model.Label amaltheaLabel = AmaltheaFactory.eINSTANCE.createLabel();
            amaltheaLabel.setName(label.getName());
            this.data.getLabelMap().put(label.getName(), amaltheaLabel);
            CustomPropertiesAdder.addFilePackageInformation(amaltheaLabel, label.getFile(), label.getPackage());
            amlabels.add(amaltheaLabel);
            this.typeExporter.transformTypeDefitionToLabel(amaltheaLabel, label);
        }
    }


    /**
     * This function collects all functions which should not be transformed as runnables because either they are semaphore
     * calls or included in a semaphore function
     *
     * @param allFunctions
     * @return
     */
    private Set < Function > prepareIgnoreList(final Set < Function > allFunctions) {
        Set < String > keySet = this.data.getSemaMap().keySet();
        Set < Function > ignorelist = new HashSet < Function > ();

        for (Function f: allFunctions) {
            if (keySet.contains(f.getName())) {
                ignorelist.add(f);
                ignorelist.addAll(extractCalledFunctions(f));
            }
        }
        return ignorelist;
    }

    /**
     * This function collects the function calls from statements sequence of {@link function}
     */
    private List < Function > extractCalledFunctions(final Function
        function) {
        List < Function > calls = (function.getStmtseq()).stream().filter(stmt -> stmt instanceof FunctionCall)
            .map(stmt -> ((FunctionCall) stmt).getCalls()).collect(Collectors.toList());
        List < Function > calledinCalls = new ArrayList < Function > ();
        for (Function f: calls) {
            calledinCalls.addAll(extractCalledFunctions(f));
        }
        calls.addAll(calledinCalls);
        return calls;
    }

    /**
     * @param containers
     * @return
     */
    private Set < Function > getAllfunctions(final SCAResource resource) {
        EList < Container > containers = ((Project) resource.getContents().get(0)).getContainers();
        Set < Function > allFunctions = new LinkedHashSet < Function > ();
        for (Container c: containers) {
            allFunctions.addAll(c.getFunctions());
        }
        return allFunctions;
    }

    /**
     * @param function
     */
    private void transformISRInternals(final Function
        function) {
        String name = function.getName();
        ISR isr = this.data.getIsrMap().get(name);
        ActivityGraph activityGraph = AmaltheaFactory.eINSTANCE.createActivityGraph();
        Group group = AmaltheaFactory.eINSTANCE.createGroup();
        group.setOrdered(true);
        transformStatements(function, activityGraph, group);
        isr.setActivityGraph(activityGraph);
    }

    /**
     * @param runnablesMap
     * @param function
     * @param callGraph
     * @param callSequence
     */
    private void transformStatements(final Function
        function, final ActivityGraph activityGraph,
        final Group group) {
        EList < StmtCall > stmtseq = function.getStmtseq();
        for (StmtCall stmtCall: stmtseq) {
            if (stmtCall instanceof FunctionCall) {
                FunctionCall fc = (FunctionCall) stmtCall;
                Runnable runnable = this.data.getRunnableMap().get(fc.getCalls().getName());
                if (runnable != null) {
                    RunnableCall runnableCall = AmaltheaFactory.eINSTANCE.createRunnableCall();
                    runnableCall.setRunnable(runnable);
                    group.getItems().add(runnableCall);
                    CustomPropertiesAdder.addSourceLineInformation(runnableCall, fc.getSrcline(), fc.getSrccol());
                }
            }
        }
        activityGraph.getItems().add(group);
    }

    /**
     * @param function
     */
    private void transformTaskInternals(final Function
        function) {
        String name = function.getName();
        Task task = this.data.getTaskMap().get(name);

        ActivityGraph activityGraph = AmaltheaFactory.eINSTANCE.createActivityGraph();
        Group group = AmaltheaFactory.eINSTANCE.createGroup();
        group.setOrdered(true);
        transformStatements(function, activityGraph, group);
        task.setActivityGraph(activityGraph);
    }

    /**
     * @param runnables
     * @param labelsMap
     * @param runnablesMap
     * @param function
     * @param calls
     * @param fname
     */
    private void transformRunnableInternals(final Function
        function) {
        String name = function.getName();
        Runnable runnable = this.data.getRunnableMap().get(name);

        EList < StmtCall > stmtseq = function.getStmtseq();
        for (StmtCall stmtCall: stmtseq) {
            if (stmtCall instanceof FunctionCall) {
                FunctionCall fc = (FunctionCall) stmtCall;
                String calledFunc = fc.getCalls().getName();


                if (this.data.getSemaMap().keySet().contains(fc.getCalls().getName())) {
                    String srcLine = fc.getSrcline();
                    String srcCol = fc.getSrccol();
                    checkForSemaphoreCall(runnable, calledFunc, srcLine, srcCol);
                } else {
                    handleRunnableCall(name, runnable, fc);
                }
            } else if (stmtCall instanceof LabelAccess) {
                handleLabelAccess(runnable, stmtCall);
            } else if (stmtCall instanceof ModeSwitch) {
                handleModeSwitch(name, runnable, stmtCall);
            }
        }
    }

    private org.eclipse.app4mc.amalthea.model.RunnableCall buildRunnableCall(final Runnable calledRunnable, final FunctionCall fc) {
        org.eclipse.app4mc.amalthea.model.RunnableCall runnableCall = AmaltheaFactory.eINSTANCE.createRunnableCall();
        runnableCall.setRunnable(calledRunnable);
        CustomPropertiesAdder.addSourceLineInformation(runnableCall, fc.getSrcline(), fc.getSrccol());
        return runnableCall;
    }

    /**
     * @param name
     * @param runnable
     * @param fc
     */
    private void handleRunnableCall(final String name, final Runnable runnable, final FunctionCall fc) {
    	String calledFunc = fc.getCalls().getName();
        Runnable calledRunnable = this.data.getRunnableMap().get(calledFunc);
        if (calledRunnable != null) {
            runnable.getRunnableItems().add(buildRunnableCall(calledRunnable, fc));
        } else {
            LogUtil.log(
                LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Called Runnable do not exist for ***" + calledFunc + "*** in *" +
                name + "*Possibly it appeared in ignored hierarchy of Lock Functions",
                this.getClass(), Activator.PLUGIN_ID);
        }
    }

    private void handleRunnableCallOnModeSwitch(final String name, final ModeSwitchEntry modeSwitchEntry, final FunctionCall fc) {
    	String calledFunc = fc.getCalls().getName();
        Runnable calledRunnable = this.data.getRunnableMap().get(calledFunc);
        if (calledRunnable != null) {
            modeSwitchEntry.getItems().add(buildRunnableCall(calledRunnable, fc));
        } else {
            LogUtil.log(
                LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Called Runnable do not exist for ***" + calledFunc + "*** in *" +
                name + "*Possibly it appeared in ignored hierarchy of Lock Functions",
                this.getClass(), Activator.PLUGIN_ID);
        }
    }

    private org.eclipse.app4mc.amalthea.model.LabelAccess buildLabelAccess(LabelAccess labelAccess) {
        org.eclipse.app4mc.amalthea.model.LabelAccess amLabelAccess = AmaltheaFactory.eINSTANCE.createLabelAccess();
        amLabelAccess.setData(this.data.getLabelMap().get(labelAccess.getLabel().getName()));
        if ("Read".equalsIgnoreCase(labelAccess.getAccess().getLiteral())) {
            amLabelAccess.setAccess(LabelAccessEnum.READ);
        } else {
            amLabelAccess.setAccess(LabelAccessEnum.WRITE);
        }

        CustomPropertiesAdder.addSourceLineInformation(amLabelAccess, labelAccess.getSrcline(), labelAccess.getSrccol());
        return amLabelAccess;
    }

    /**
     * transform SCAIR labelAccess to AMALTHEA Label Access in a runnable
     *
     * @param runnable
     * @param stmtCall
     */
    private void handleLabelAccess(final Runnable runnable, final StmtCall stmtCall) {
        LabelAccess labelAccess = (LabelAccess) stmtCall;
        if ((labelAccess != null) && (labelAccess.getLabel() != null)) {
            runnable.getRunnableItems().add(buildLabelAccess(labelAccess));
        } else {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
                "**LabelAccess transformation problem in function " + runnable.getName(), this.getClass(),
                Activator.PLUGIN_ID);
        }
    }

    private void handleLabelAccessOnModeSwitch(final ModeSwitchEntry modeSwitchEntry, final StmtCall stmtCall) {
        LabelAccess labelAccess = (LabelAccess) stmtCall;
        if ((labelAccess != null) && (labelAccess.getLabel() != null)) {
            modeSwitchEntry.getItems().add(buildLabelAccess(labelAccess));
        } else {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
                    "**LabelAccess transformation problem in mode switch entry", this.getClass(),
                Activator.PLUGIN_ID);
        }
    }
        
    private org.eclipse.app4mc.amalthea.model.ModeSwitch buildModeSwitch(final String name, final ModeSwitch modeSwitch) {
        org.eclipse.app4mc.amalthea.model.ModeSwitch amModeSwitch = AmaltheaFactory.eINSTANCE.createModeSwitch();
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                "*Amount of mode entries: " + modeSwitch.getModeentry().size(), this.getClass(),
            Activator.PLUGIN_ID);
        for (ModeEntry me: modeSwitch.getModeentry()) {
            org.eclipse.app4mc.amalthea.model.ModeSwitchEntry amModeEntry = AmaltheaFactory.eINSTANCE.createModeSwitchEntry();
            EList < StmtCall > modeEntryStmtseq = me.getStmtseq();
            for (StmtCall stmtCall: modeEntryStmtseq) {
                if (stmtCall instanceof FunctionCall) {
                    FunctionCall fc = (FunctionCall) stmtCall;
                    String calledFunc = fc.getCalls().getName();
                    if (this.data.getSemaMap().keySet().contains(fc.getCalls().getName())) {
                        String srcLine = fc.getSrcline();
                        String srcCol = fc.getSrccol();
                        checkForSemaphoreCallOnModeSwitch(amModeEntry, calledFunc, srcLine, srcCol);
                    } else {
                        handleRunnableCallOnModeSwitch(name, amModeEntry, fc);
                    }
                } else if (stmtCall instanceof LabelAccess) {
                    handleLabelAccessOnModeSwitch(amModeEntry, stmtCall);
                } else if (stmtCall instanceof ModeSwitch) {
                	handleModeSwitchOnModeSwitch(name, amModeEntry, stmtCall);
                }
            }
            
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                    "*ModeEntry added to mode switch.", this.getClass(),
                Activator.PLUGIN_ID);
            amModeSwitch.getEntries().add(amModeEntry);
        }
        return amModeSwitch;
    }

    private void handleModeSwitch(final String name, final Runnable runnable, final StmtCall stmtCall) {
    	ModeSwitch modeSwitch = (ModeSwitch) stmtCall;
        if ((modeSwitch != null)) {
        	runnable.getRunnableItems().add(buildModeSwitch(name, modeSwitch));
        } else {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
                "**ModeSwitch transformation problem in function " + runnable.getName(), this.getClass(),
                Activator.PLUGIN_ID);
        }
    }
    
    private void handleModeSwitchOnModeSwitch(final String name, final ModeSwitchEntry modeSwitchEntry, final StmtCall stmtCall) {
    	ModeSwitch modeSwitch = (ModeSwitch) stmtCall;
        if ((modeSwitch != null)) {
        	modeSwitchEntry.getItems().add(buildModeSwitch(name, modeSwitch));
        } else {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
                "**ModeSwitch transformation problem in mode switch", this.getClass(),
                Activator.PLUGIN_ID);
        }
    }

    private SemaphoreAccess buildSemaphoreAccess(final String calledFunc, final String srcLine,
        final String srcCol) {
        Semaphore semaphore = this.data.getSemaMap().get(calledFunc);
        SemaphoreAccess semaAcc = AmaltheaFactory.eINSTANCE.createSemaphoreAccess();
        if (((StringObject) semaphore.getCustomProperties().get(CustomPropertiesAdder.GET_LOCK_FUNC_NAME)).getValue()
            .equalsIgnoreCase(calledFunc)) {

            if (((StringObject) semaphore.getCustomProperties().get(CustomPropertiesAdder.LOCK_TYPE)).getValue()
                .equalsIgnoreCase(LockType.EXCLUSIVE_LOCK.name())) {
                semaAcc.setAccess(SemaphoreAccessEnum.EXCLUSIVE);
            } else {
                semaAcc.setAccess(SemaphoreAccessEnum.REQUEST);
            }
        } else if (((StringObject) semaphore.getCustomProperties().get(CustomPropertiesAdder.RELEASE_LOCK_FUNC_NAME))
            .getValue().equalsIgnoreCase(calledFunc)) {
            semaAcc.setAccess(SemaphoreAccessEnum.RELEASE);
        }
        semaAcc.setSemaphore(semaphore);
        CustomPropertiesAdder.addSourceLineInformation(semaAcc, srcLine, srcCol);
        return semaAcc;
    }

    private void checkForSemaphoreCallOnModeSwitch(final ModeSwitchEntry modeSwitchEntry, final String calledFunc, final String srcLine,
        final String srcCol) {
        modeSwitchEntry.getItems().add(buildSemaphoreAccess(calledFunc, srcLine, srcCol));
    }

    /**
     * @param runnable
     * @param calledFunc
     */
    private void checkForSemaphoreCall(final Runnable runnable, final String calledFunc, final String srcLine,
        final String srcCol) {
        runnable.getRunnableItems().add(buildSemaphoreAccess(calledFunc, srcLine, srcCol));
    }


    /**
     * @param allFunctions
     * @param runnables
     * @param tasks
     * @param isrs
     */
    private void transformFirstLevel(final Set < Function > allFunctions, final EList < Runnable > runnables,
        final EList < Task > tasks, final EList < ISR > isrs) {

        for (Function
            function: allFunctions) {
            switch (function.getType().getValue()) {
                case EFunctionTypeEnum.ISR_VALUE:
                    createISR(function, isrs);
                    break;
                case EFunctionTypeEnum.TASK_VALUE:
                    createTask(function, tasks);
                    break;
                case EFunctionTypeEnum.RUNNABLE_VALUE:
                    createRunnable(function, runnables);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * This function creates the Task and also initializes the global {@link #data.getRunnableMap()}
     *
     * @param function
     * @param runnables
     */
    private void createRunnable(final Function
        function, final EList < Runnable > runnables) {
        String fname = function.getName();
        if (this.data.getRunnableMap().get(fname) != null) {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Duplicate function definition:" + fname, this.getClass(),
                Activator.PLUGIN_ID);
        }

        if (this.data.getLabelMap().get(fname) != null) {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Function name is already defined as label:" + fname,
                this.getClass(), Activator.PLUGIN_ID);
        }

        Runnable r = AmaltheaFactory.eINSTANCE.createRunnable();
        r.setName(fname);
        this.data.getRunnableMap().put(fname, r);
        CustomPropertiesAdder.addSourceLineInformation(r, function.getSrcline(), function.getSrccol());
        CustomPropertiesAdder.addFilePackageInformation(r, function.getFile(), function.getPackage());
        this.componentTransformer.addRunnableToComponent(function, r);
        runnables.add(r);
    }

    /**
     * This function creates the Task and also initializes the global {@link #data.getTaskMap()}
     *
     * @param function
     * @param tasks
     */
    private void createTask(final Function
        function, final EList < Task > tasks) {
        String fname = function.getName();
        Task task = AmaltheaFactory.eINSTANCE.createTask();
        task.setName(fname);
        this.data.getTaskMap().put(fname, task);
        CustomPropertiesAdder.addSourceLineInformation(task, function.getSrcline(), function.getSrccol());
        this.componentTransformer.addTaskToComponent(function, task);
        tasks.add(task);
    }

    /**
     * This function creates the ISR and also initializes the global {@link #data.getIsrMap()}
     *
     * @param function
     * @param isrs
     */
    private void createISR(final Function
        function, final EList < ISR > isrs) {
        String fname = function.getName();
        ISR isr = AmaltheaFactory.eINSTANCE.createISR();
        isr.setName(fname);
        this.data.getIsrMap().put(fname, isr);
        CustomPropertiesAdder.addSourceLineInformation(isr, function.getSrcline(), function.getSrccol());
        this.componentTransformer.addTaskToComponent(function, isr);
        isrs.add(isr);
    }

}