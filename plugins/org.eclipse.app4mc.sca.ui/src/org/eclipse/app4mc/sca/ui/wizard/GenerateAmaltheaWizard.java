/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.wizard;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.util.IAmaltheaGenerator;
import org.eclipse.app4mc.sca.ui.util.IOptionsProvider;
import org.eclipse.app4mc.sca.ui.util.WizardConstants;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;


/**
 * This is a common wizard class to Generate Amalthea
 */
public class GenerateAmaltheaWizard extends Wizard implements IOptionsProvider, Observer {

  private final List<GenerateAmaltheaWizardPage> wizardPages;
  private final IAmaltheaGenerator amaltheaExecutor;
  private final Properties properties;

  /**
   * @param asList - List<GenerateAmaltheaWizardPage>
   * @param properties Properties
   * @param amaltheaExecutor IAmaltheaExecutor - for amalthea generation
   */
  public GenerateAmaltheaWizard(final List<GenerateAmaltheaWizardPage> asList, final Properties properties,
      final IAmaltheaGenerator amaltheaExecutor) {
    setNeedsProgressMonitor(true);
    this.wizardPages = asList;
    this.properties = properties;
    this.amaltheaExecutor = amaltheaExecutor;
    setWindowTitle("Generate Amalthea");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addPages() {
    for (GenerateAmaltheaWizardPage amaltheaWizardPage : this.wizardPages) {
      addPage(amaltheaWizardPage);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean performFinish() {
    Job job = new Job("Amalthea Generation") {

      @Override
      protected IStatus run(final IProgressMonitor monitor) {
        IStatus status = GenerateAmaltheaWizard.this.amaltheaExecutor.execute(monitor, getOptions());
        String outputDirProperty =
            GenerateAmaltheaWizard.this.properties.getProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY, "");
        String ouputDirPath =
            ((outputDirProperty != null) && !outputDirProperty.isEmpty()) ? " in " + outputDirProperty + "." : ".";
        openResultDialog(status, ouputDirPath);
        return status;
      }
    };
    job.schedule();
    return true;
  }


  private void openResultDialog(final IStatus status, final String ouputDirPath) {
    Display.getDefault().asyncExec(() -> {

      if (status.getSeverity() == IStatus.WARNING) {
        MessageDialog.openInformation(Display.getDefault().getActiveShell(), WizardConstants.AMALTHEA_GENERATION,
            "Amalthea model successfully generated" + ouputDirPath +
                "\n\nWarnings have been found - Please check log files for more details.");
      }
      else if (status.getSeverity() == IStatus.OK) {
        MessageDialog.openInformation(Display.getDefault().getActiveShell(), WizardConstants.AMALTHEA_GENERATION,
            "Amalthea model successfully generated" + ouputDirPath);
      }
      else {
        MessageDialog.openError(Display.getDefault().getActiveShell(), WizardConstants.AMALTHEA_GENERATION,
            "Amlathea model generation failed. Please check the logs for more details.");
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Properties getOptions() {
    for (GenerateAmaltheaWizardPage amaltheaWizardPage : this.wizardPages) {
      this.properties.putAll(amaltheaWizardPage.getOptions());
    }
    return this.properties;
  }


  @Override
  public boolean canFinish() {
    for (GenerateAmaltheaWizardPage amaltheaWizardPage : this.wizardPages) {
      if (!amaltheaWizardPage.isPageComplete()) {
        return false;
      }
    }
    return getContainer().getCurrentPage().getNextPage() == null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable o, final Object arg) {
    for (GenerateAmaltheaWizardPage amaltheaWizardPage : this.wizardPages) {
      amaltheaWizardPage.setPageComplete(amaltheaWizardPage.isPageComplete());
    }
  }


}
