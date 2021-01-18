/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.llvm.headless;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.sca.amalthea.serializer.AMALTHEAResourceSerializer;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import java.io.StringWriter;
import java.io.PrintWriter;
import org.eclipse.app4mc.sca.scheduling.loader.model.OSConfModel;
import org.eclipse.app4mc.sca.scheduling.loader.model.SchedulingInformationLoader;
import org.eclipse.app4mc.sca.util.App4mcToolConstants;
import org.eclipse.app4mc.sca.util.util.SCAToolsDirectoryType;
import org.eclipse.app4mc.sca.util.util.SCAUtils;
import org.eclipse.app4mc.sca.util.workspaces.WorkspaceOperations;
import org.eclipse.app4mc.sca2amalthea.exporter.SCAToAmaltheaExporter;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockDefinition;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca2amalthea.ir.loader.SCAIRLoader;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;
import org.eclipse.app4mc.sca2amalthea.llvm.srcfiles.support.BuildCommandLogFileParser;
import org.eclipse.app4mc.sca2amalthea.llvm.srcfiles.support.PathsToListConverter;
import org.eclipse.app4mc.sca2amalthea.llvm.starter.GenerateTraverseAstOutput;
import org.eclipse.app4mc.sca2amalthea.llvm.starter.LLVMStarterProperties;
import org.eclipse.app4mc.sca2amalthea.llvm.util.ManipulateAmalthea;
import org.eclipse.app4mc.sca2amalthea.scairmodelenricher.SCAIRModelEnrichmentUtils;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.app4mc.sca2amalthea.utils.UtilityForProcessHandling;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.emf.common.util.URI;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Locale;


/**
 */
public class GenerateAmaltheaModelFromLLVM {
    private final SCA2AMALTHEAStarterProperties sca2amProperties;

    /**
     * Generates amalthea model from the given properties. The given properties are used to populate the ast model and
     * from the ast model the intermediate representation. The intermdediate representation is inturn used to generate the
     * amalthea model
     *
     * @param llvmProperties {@link LLVMStarterProperties}
     */
    public GenerateAmaltheaModelFromLLVM(
        final SCA2AMALTHEAStarterProperties llvmProperties) {
        super();
        this.sca2amProperties = llvmProperties;
    }

    /**
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     * @return int status of the amalthea model generation
     */
    public IStatus run() throws IOException, InterruptedException {
        IStatus status = Status.OK_STATUS;
        long startTime = 0;

        try {
            validateConsolidatedLLVMOptions();
        } catch (IllegalArgumentException e) {
            Logmanager.getInstance()
                      .logException(getClass(), e, Activator.PLUGIN_ID);
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.ERROR,
                "Aborting the amalthea generation, see exception log: " +
                e.getMessage(), this.getClass(), Activator.PLUGIN_ID);
            status = Status.CANCEL_STATUS;
        }

        try {
            if (status == Status.OK_STATUS) {
                applyFallBackStrategy();
                parseBlogAndHDirListOptions();

                startTime = System.currentTimeMillis();
                LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                    "Starting sca2amalthea export... [start time " + startTime +
                    "]", this.getClass(), Activator.PLUGIN_ID);

                GenerateTraverseAstOutput llvmStarter = new GenerateTraverseAstOutput(this.sca2amProperties.getLlvmStarterProperties());

                llvmStarter.runTraverseAst();
            }
        } catch (Exception possibleException) {
            LogUtil.logException(LLVMLogUtil.LOG_MSG_ID,
                possibleException.getClass(), possibleException,
                Activator.PLUGIN_ID);
            possibleException.printStackTrace();
            status = Status.CANCEL_STATUS;
        }

        if ((status == Status.OK_STATUS) &&
                (UtilityForProcessHandling.getCurrentRunningProcess().exitValue() == 0)) {
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                "Transforming the intermediate representation to amalthea model",
                this.getClass(), Activator.PLUGIN_ID);

            try {
                convertXMLToAmaltheaModel();
            } catch (Exception possibleException) {
            	StringWriter sw = new StringWriter();
            	PrintWriter pw = new PrintWriter(sw);
            	possibleException.printStackTrace(pw);
            	String sStackTrace = sw.toString();
            	LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                        "It crashed where you expected." + possibleException.getMessage() + sStackTrace, this.getClass(), Activator.PLUGIN_ID);
                LogUtil.logException(LLVMLogUtil.LOG_MSG_ID,
                    possibleException.getClass(), possibleException,
                    Activator.PLUGIN_ID);
                status = Status.CANCEL_STATUS;
            }

            long duration = (System.currentTimeMillis() - startTime) / 1000; // in seconds
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                "Export of AMALTHEA model completed. [duration=" + duration +
                " seconds]", this.getClass(), Activator.PLUGIN_ID);

            WorkspaceOperations.refreshWorkspace(this.sca2amProperties.getProjectPath(),
                "_bin/sca2amalthea");
            WorkspaceOperations.refreshWorkspace(this.sca2amProperties.getProjectPath(),
                "_log/sca2amalthea");
            WorkspaceOperations.refreshWorkspace(this.sca2amProperties.getProjectPath(),
                "_gen/sca2amalthea");
        } else {
            status = Status.CANCEL_STATUS;
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
                "AMALTHEA model generation aborted.", this.getClass(),
                Activator.PLUGIN_ID);
        }

        return status;
    }

    /**
     * @throws IllegalArgumentException if a given path of the properties does not exist
     */
    private void validateConsolidatedLLVMOptions()
        throws IllegalArgumentException {
        if (!new File(this.sca2amProperties.getProjectPath()).exists()) {
            throw new IllegalArgumentException(
                "The project path provided is not valid: " +
                this.sca2amProperties.getProjectPath());
        }

        if ((this.sca2amProperties.getTraverseAstFile() != null) &&
                !this.sca2amProperties.getTraverseAstFile().isEmpty() &&
                !new File(this.sca2amProperties.getTraverseAstFile()).exists()) {
            throw new IllegalArgumentException(
                "The traverse ast path provided is not valid: " +
                this.sca2amProperties.getTraverseAstFile());
        }

        if ((this.sca2amProperties.getOutPutPath() != null) &&
                !this.sca2amProperties.getOutPutPath().isEmpty()) {
            checkFilePathExists(this.sca2amProperties.getOutPutPath(),
                "output path");
        }

        checkFilePathExists(this.sca2amProperties.getTaskListFile(),
            "tasklist file path");
        checkFilePathExists(this.sca2amProperties.getBuildLogFile(),
            "build log file path");

        if ((this.sca2amProperties.gethDirFilePath() != null) &&
                !this.sca2amProperties.gethDirFilePath().isEmpty()) {
            String[] paths = this.sca2amProperties.gethDirFilePath().split(";");

            for (String s : paths) {
                checkFilePathExists(s, "h dir path");
            }
        }

        checkFilePathExists(this.sca2amProperties.getLockinfoFile(),
            "lock info file path");
    }

    /**
     * @param filePath to be checked
     * @param filePathName description
     */
    private void checkFilePathExists(final String filePath,
        final String filePathName) {
        if ((filePath != null) && !filePath.isEmpty() &&
                !new File(filePath).exists()) {
            throw new IllegalArgumentException("The " + filePathName +
                " provided is not valid: " + filePath);
        }
    }

    private void convertXMLToAmaltheaModel() {
        OSConfModel osConfModel = null;
        URI createURI = URI.createFileURI(this.sca2amProperties.getLlvmStarterProperties()
                                                               .getGenDirectory() +
                "/XMLCalltree.xml");
        SCAResource resource = new SCAIRLoader().load(createURI);

        if ((this.sca2amProperties.getTaskListFile() != null) &&
                !this.sca2amProperties.getTaskListFile().isEmpty()) {
            SchedulingInformationLoader schedInfoLoader = new SchedulingInformationLoader();
            osConfModel = schedInfoLoader.getTasksInformation(this.sca2amProperties.getTaskListFile());

            if (osConfModel != null) {
                SCAIRModelEnrichmentUtils.markTasksIsrsRunnablesInModel(resource,
                    osConfModel.getTaskISRInfoAsMap());
            }
        }

        LockDefinition lockDefinition = null;

        if ((this.sca2amProperties.getLockinfoFile() != null) &&
                !this.sca2amProperties.getLockinfoFile().isEmpty()) {
            lockDefinition = new LockDefinition(this.sca2amProperties.getLockinfoFile());
            lockDefinition.readLockDefinitionFromFile();
        }

        Amalthea amaltheaModel = new SCAToAmaltheaExporter().amaltheaTransformation(resource,
                lockDefinition, osConfModel);

        ManipulateAmalthea.markIniTasksInAmaltheaModel(amaltheaModel,
            osConfModel);

        ManipulateAmalthea manipulatorCaller = new ManipulateAmalthea();
        manipulatorCaller.manipulates(this.sca2amProperties, amaltheaModel);

        AMALTHEAResourceSerializer amResSerializer = new AMALTHEAResourceSerializer();
        amResSerializer.saveAmaltheaModel(this.sca2amProperties.getBinDirectory() +
            "/amaltheaModelFromLLVM", amaltheaModel);
    }

    /**
     * Method that provides the fall back strategy if the user has not provided the necessary parameters
     */
    private void applyFallBackStrategy() {
        if ((this.sca2amProperties.getOutPutPath() == null) ||
                this.sca2amProperties.getOutPutPath().isEmpty()) {
            this.sca2amProperties.setBinDirectory(SCAUtils.getSCARootDirectory(
                    this.sca2amProperties.getProjectPath(),
                    SCAToolsDirectoryType.SCA_BIN,
                    App4mcToolConstants.SCA2AMALTHEA_TOOL_ID.toLowerCase(
                        Locale.getDefault())));
            this.sca2amProperties.getLlvmStarterProperties()
                                 .setGenDirectory(SCAUtils.getSCARootDirectory(
                    this.sca2amProperties.getProjectPath(),
                    SCAToolsDirectoryType.SCA_GEN,
                    App4mcToolConstants.SCA2AMALTHEA_TOOL_ID.toLowerCase(
                        Locale.getDefault())));
            this.sca2amProperties.getLlvmStarterProperties()
                                 .setLogDirecotry(SCAUtils.getSCARootDirectory(
                    this.sca2amProperties.getProjectPath(),
                    SCAToolsDirectoryType.SCA_LOG,
                    App4mcToolConstants.SCA2AMALTHEA_TOOL_ID.toLowerCase(
                        Locale.getDefault())));
        } else {
            this.sca2amProperties.setBinDirectory(this.sca2amProperties.getOutPutPath());
            this.sca2amProperties.getLlvmStarterProperties()
                                 .setGenDirectory(this.sca2amProperties.getOutPutPath());
            this.sca2amProperties.getLlvmStarterProperties()
                                 .setLogDirecotry(this.sca2amProperties.getOutPutPath());
        }
    }

    /**
     * This method checks if the user has provided the build_cmd.log file and the header directories. If so it reads them
     * and generates the clist and hlist.
     */
    private void parseBlogAndHDirListOptions() {
        List<String> hdList;
        List<String> cList;

        if ((this.sca2amProperties.getBuildLogFile() != null) &&
                !this.sca2amProperties.getBuildLogFile().isEmpty()) {
            BuildCommandLogFileParser cListProvider = new BuildCommandLogFileParser(this.sca2amProperties.getBuildLogFile(),
                    this.sca2amProperties.getProjectPath());
            cList = cListProvider.generateSourceFileList();
            hdList = cListProvider.generateHashDefineList();

            this.sca2amProperties.setcFilesList(cList);
            this.sca2amProperties.setHashDefineList(hdList);
        }

        List<String> hList;

        if ((this.sca2amProperties.gethDirFilePath() != null) &&
                !this.sca2amProperties.gethDirFilePath().isEmpty()) {
            String hFilePathList = this.sca2amProperties.gethDirFilePath();
            // create h file list
            hList = new PathsToListConverter(hFilePathList, ".h").generateSourceFileList();
            this.sca2amProperties.sethFilesList(hList);
        }
    }
}
