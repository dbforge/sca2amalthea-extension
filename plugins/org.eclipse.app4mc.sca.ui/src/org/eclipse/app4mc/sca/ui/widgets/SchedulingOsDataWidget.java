/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widgets;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;


/**
 * This class provides the UI elements for Scheduling/OS Data model in Amalthea generation wizard
 */
public class SchedulingOsDataWidget extends Widget {

  private static final String CSV = "*.csv";
  private LabelTextButtonCreator osLockFuncFile;
  private LabelTextButtonCreator taskSchedFile;
  private final Properties properties;

  /**
   * @param parent - Composite
   * @param options - Properties
   */
  public SchedulingOsDataWidget(final Composite parent, final Properties options) {
    this.properties = options;
    createComponent(parent);
  }


  /**
   *
   */
  private void createComponent(final Composite parent) {
    Composite compositeForGroup = new Composite(parent, SWT.NONE);
    compositeForGroup.setLayout(new GridLayout());
    GridDataFactory.defaultsFor(compositeForGroup).grab(true, false).span(1, 1).applyTo(compositeForGroup);

    Group group = new Group(compositeForGroup, SWT.NONE);
    group.setLayout(new GridLayout());
    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    group.setText(WizardConstants.SCHEDULING_OS_DATA);

    Composite composite = new Composite(group, SWT.NONE);
    composite.setLayout(new GridLayout(3, false));
    GridDataFactory.defaultsFor(composite).grab(true, false).span(3, 1).applyTo(composite);

    this.osLockFuncFile = new LabelTextButtonCreator(composite, WizardConstants.OS_LOCK_FUNC_FILE, "osLockTextField",
        WizardConstants.BROWSE, "osLockBrowseButton");
    this.osLockFuncFile.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));
    this.osLockFuncFile.getButton().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell());
        fileDialog.setFilterExtensions(new String[] { CSV });
        fileDialog.setFilterPath(SchedulingOsDataWidget.this.osLockFuncFile.provideDialogLocation());
        String outsetPropertyPath = fileDialog.open();
        if (outsetPropertyPath != null) {
          SchedulingOsDataWidget.this.osLockFuncFile.setFilePath(outsetPropertyPath);
        }
      }
    });

    String[] extensions = new String[] { CSV };
    this.taskSchedFile = new LabelTextButtonCreator(composite, WizardConstants.TASK_SCHED_FILE, "taskSchedTextField",
        WizardConstants.BROWSE, "taskSchedBrowseButton");
    this.taskSchedFile.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));
    this.taskSchedFile.getButton().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell());
        fileDialog.setFilterExtensions(extensions);
        fileDialog.setFilterPath(SchedulingOsDataWidget.this.taskSchedFile.provideDialogLocation());
        String outsetPropertyPath = fileDialog.open();
        if (outsetPropertyPath != null) {
          SchedulingOsDataWidget.this.taskSchedFile.setFilePath(outsetPropertyPath);
        }
      }
    });


    this.taskSchedFile.getTextField().addModifyListener(e -> {
      this.properties.setProperty(AmaltheaWizardPreferenceConstants.TASK_SCHEDULING_FILE,
          this.taskSchedFile.getAbsoluteFilePath());
      setChanged();
      notifyObservers();

    });

    this.osLockFuncFile.getTextField().addModifyListener(event -> {
      this.properties.setProperty(AmaltheaWizardPreferenceConstants.OS_LOCK_FILE, this.osLockFuncFile.getAbsoluteFilePath());
      setChanged();
      notifyObservers();
    });

    initializeOptions();
  }


  /**
   *
   */
  private void initializeOptions() {
    this.osLockFuncFile.setFilePath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.OS_LOCK_FILE, ""));
    this.taskSchedFile.setFilePath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.TASK_SCHEDULING_FILE, ""));
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
    String osLockFilePath = this.osLockFuncFile.getTextField().getText();
    if (!osLockFilePath.isEmpty() && !SCAToolsUIUtil.validatePath(projectPath, osLockFilePath, true, "csv")) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid OS lock function file");
    }
    String taskSchedFilePath = this.taskSchedFile.getTextField().getText();
    if (!taskSchedFilePath.isEmpty() && !SCAToolsUIUtil.validatePath(projectPath, taskSchedFilePath, true, "csv")) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid Task/Scheduling file");
    }

    return Status.OK_STATUS;
  }

}
