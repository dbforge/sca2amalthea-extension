/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import java.util.Map;
import java.util.Properties;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.util.IAmaltheaGenerator;
import org.eclipse.app4mc.sca.ui.util.SCAToolsUIUtil;
import org.eclipse.app4mc.sca.util.App4mcToolConstants;
import org.eclipse.app4mc.sca2amalthea.llvm.headless.GenerateAmaltheaModelFromLLVM;
import org.eclipse.app4mc.sca2amalthea.llvm.headless.SCA2AMALTHEAStarterProperties;
import org.eclipse.app4mc.sca2amalthea.ui.Activator;
import org.eclipse.app4mc.sca2amalthea.utils.UtilityForProcessHandling;
import org.eclipse.app4mc.sca2amalthea.utils.constants.SCA2AmaltheaConstants;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The sca2Amalthea execution starts with this class.
 */
public class SCA2AmaltheaExecutor implements IAmaltheaGenerator {

  private Properties properties;


  @Override
  public IStatus execute(final IProgressMonitor iProgressMonitor, final Properties properties) {
    IStatus status = Status.OK_STATUS;
    this.properties = properties;
    storeUserSelectionToProjectPreferences();
    SCAProgressMonitor scaProgressMonitor = new SCAProgressMonitor(Job.getJobManager().currentJob());
    Thread progressMonitorObserverThread = scaProgressMonitor.createInfiniteProgressMonitor(iProgressMonitor);
    try {
      progressMonitorObserverThread.start();
      Display.getDefault().syncExec(new SCAPopup(App4mcToolConstants.SCA2AMALTHEA_TOOL_ID,
          SCA2AmaltheaConstants.AMALTHEA_GENERATION__PROJECT_SIZE_MESSAGE, MessageDialog.INFORMATION));
      SCA2AMALTHEAStarterProperties sca2AmaltheaStarterProperties = new SCA2AMALTHEAStarterProperties();
      sca2AmaltheaStarterProperties
          .setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));
      sca2AmaltheaStarterProperties
          .setOutPutPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY));
      sca2AmaltheaStarterProperties.getLlvmStarterProperties()
          .setTraverseAstFile(this.properties.getProperty(AmaltheaWizardPreferenceConstants.AST_PATH_KEY));
      sca2AmaltheaStarterProperties
          .setTaskListFile(this.properties.getProperty(AmaltheaWizardPreferenceConstants.TASK_SCHEDULING_FILE));
      sca2AmaltheaStarterProperties
          .setHDirFilePath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY));
      sca2AmaltheaStarterProperties
          .setBuildLogFile(this.properties.getProperty(AmaltheaWizardPreferenceConstants.BUILD_CMDS_LOG_FILE));
      sca2AmaltheaStarterProperties
          .setLockinfoFile(this.properties.getProperty(AmaltheaWizardPreferenceConstants.OS_LOCK_FILE));
      sca2AmaltheaStarterProperties.setIsStructMemberEnabled(
          Boolean.parseBoolean(this.properties.getProperty(AmaltheaWizardPreferenceConstants.ENABLE_STRUCT_MEMBER_KEY)));

      GenerateAmaltheaModelFromLLVM generator = new GenerateAmaltheaModelFromLLVM(sca2AmaltheaStarterProperties);
      SCA2AmaltheaTimer scaTimer = new SCA2AmaltheaTimer();
      scaTimer.startTimer();
      status = generator.run();
      scaProgressMonitor.setGenerationDone(true);

      if ((UtilityForProcessHandling.getCurrentRunningProcess().exitValue() != 0) &&
          !UtilityForProcessHandling.isModelGenerationcancelled()) {
        Display.getDefault().syncExec(new SCAPopup(App4mcToolConstants.SCA2AMALTHEA_TOOL_ID,
            SCA2AmaltheaConstants.PARSING_ERROR, MessageDialog.INFORMATION));
        return Status.CANCEL_STATUS;
      }
    }
    catch (Exception e) {
    	LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
            "It crashed where you expected it second." + e, this.getClass(), Activator.PLUGIN_ID);
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      if (progressMonitorObserverThread != null) {
        waitForThread(progressMonitorObserverThread);
      }
      scaProgressMonitor.getSubMonitor().done();
      return Status.CANCEL_STATUS;
    }

    waitForThread(progressMonitorObserverThread);
    scaProgressMonitor.getSubMonitor().done();
    UtilityForProcessHandling.setCurrentRunningProcess(null);
    return status;
  }

  private void storeUserSelectionToProjectPreferences() {
    Display.getDefault().syncExec(() -> {
      IEclipsePreferences projectPreferences = SCAToolsUIUtil
          .loadProjectScopePrefs(SCAToolsUIUtil.getCurrentProjectResource(), App4mcToolConstants.SCA2AMALTHEA_TOOL_ID);
      try {
        projectPreferences.clear();
        for (Map.Entry<Object, Object> e : this.properties.entrySet()) {
          projectPreferences.put(e.getKey().toString(), e.getValue().toString());
        }
        projectPreferences.flush();
      }
      catch (BackingStoreException e) {
        Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      }
    });
  }

  private void waitForThread(final Thread thread) {
    try {
      if (thread != null) {
        thread.join();
      }
    }
    catch (InterruptedException e) {
      Logmanager.getInstance().log(e.getMessage());
    }
  }

}
