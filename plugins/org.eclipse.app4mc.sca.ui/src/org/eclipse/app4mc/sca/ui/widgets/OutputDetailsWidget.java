/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widgets;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.eclipse.app4mc.sca.ui.Activator;
import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.util.LabelTextButtonCreator;
import org.eclipse.app4mc.sca.ui.util.SCAToolsUIUtil;
import org.eclipse.app4mc.sca.ui.util.WizardConstants;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;


/**
 * This is the class for Output folder location in common wizard
 */
public class OutputDetailsWidget extends Widget {


  private final Properties properties;
  private LabelTextButtonCreator outputDirectory;

  /**
   * @param parent Composite
   * @param properties Properties
   */
  public OutputDetailsWidget(final Composite parent, final Properties properties) {
    this.properties = properties;
    createComponent(parent);
  }


  /**
   * @param parent
   */
  private void createComponent(final Composite parent) {
    Composite compositeForGroup = new Composite(parent, SWT.NONE);
    compositeForGroup.setLayout(new GridLayout());
    GridDataFactory.defaultsFor(compositeForGroup).grab(true, false).span(1, 1).applyTo(compositeForGroup);

    Group group = new Group(compositeForGroup, SWT.NONE);
    group.setLayout(new GridLayout());
    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    group.setText(WizardConstants.OUTPUT);

    Composite composite = new Composite(group, SWT.NONE);
    composite.setLayout(new GridLayout(3, false));
    GridDataFactory.defaultsFor(composite).grab(true, false).span(3, 1).applyTo(composite);


    this.outputDirectory = new LabelTextButtonCreator(composite, WizardConstants.OUTPUT_DIRECTORY_PATH, "outputTextField",
        WizardConstants.BROWSE, "outputBrowseButton");
    this.outputDirectory.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));
    this.outputDirectory.getButton().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        DirectoryDialog directoryDialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
        directoryDialog.setFilterPath(OutputDetailsWidget.this.outputDirectory.provideDialogLocation());
        String outputPath = directoryDialog.open();
        if (outputPath != null) {
          OutputDetailsWidget.this.outputDirectory.setFilePath(outputPath);
        }
      }
    });

    this.outputDirectory.getTextField().addModifyListener(event -> {
      this.properties.setProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY,
          this.outputDirectory.getAbsoluteFilePath());
      setChanged();
      notifyObservers();

    });

    initializeOptions();
  }


  /**
   *
   */
  private void initializeOptions() {
    this.outputDirectory.setFilePath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY, ""));
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Properties getOptions() {
    return this.properties;
  }


  /**
   * @return - validate and return Status
   */
  @Override
  public IStatus validate() {
    String projectPath = this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH);
    String outputText = this.outputDirectory.getTextField().getText();
    File absoluteFile = new File(SCAToolsUIUtil.getProjectAbsoluteFilePath(projectPath, outputText)).getAbsoluteFile();
    if (!outputText.isEmpty() && !SCAToolsUIUtil.validatePath(projectPath, outputText, false, "")) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid output directory path");
    }
    if (!Files.isWritable(Paths.get(absoluteFile.getAbsolutePath())) || SCAToolsUIUtil.isDrive(outputText)) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Output Path cannot be a drive.");
    }
    return Status.OK_STATUS;
  }

}
