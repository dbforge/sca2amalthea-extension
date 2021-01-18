/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.util.ButtonInfo;
import org.eclipse.app4mc.sca.ui.util.ButtonType;
import org.eclipse.app4mc.sca.ui.util.LabelListButtonCreator;
import org.eclipse.app4mc.sca.ui.util.LabelTextButtonCreator;
import org.eclipse.app4mc.sca.ui.util.WizardConstants;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.app4mc.sca2amalthea.ui.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * This class is responsible to create UI controls for SCA2Amalthea widget.
 */
public class SCA2AmaltheaWidget extends Widget {

  private LabelTextButtonCreator astPath;
  private LabelTextButtonCreator buildCmmandlogPath;
  private final Properties properties;
  private Button generateStructMember;
  private LabelListButtonCreator headerList;

  /**
   * @param parent Composite instance
   * @param properties Properties instance
   */
  public SCA2AmaltheaWidget(final Composite parent, final Properties properties) {
    this.properties = properties;
    createComponent(parent);
  }

  /**
   * The methods creates the UI elements using the SWT Widgets. The UI consists of a)Check-box for enabling struct
   * members b)Path to LLVM Executable text box c)Header Directories d)Build cmd log file text box.
   *
   * @param parent Composite instance.
   */
  private void createComponent(final Composite parent) {
    Composite compositeForCheckBoxes = new Composite(parent, SWT.NONE);
    compositeForCheckBoxes.setLayout(new GridLayout(1, true));

    this.generateStructMember = new Button(compositeForCheckBoxes, SWT.CHECK);
    this.generateStructMember.setText(WizardConstants.GENERATE_STRUCT_MEMBER);
    this.generateStructMember.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, true, false));

    Composite composite = new Composite(parent, SWT.NONE);
    GridDataFactory.defaultsFor(composite).grab(true, false).span(3, 1).applyTo(composite);
    composite.setLayout(new GridLayout(3, false));

    this.astPath = new LabelTextButtonCreator(composite, WizardConstants.LLVM_PATH_TEXT, "ast_path",
        WizardConstants.BROWSE, "ast_path_browse");
    this.astPath.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));

    this.buildCmmandlogPath = new LabelTextButtonCreator(composite, WizardConstants.BUILD_CMD_FILE, "build_cmd_log",
        WizardConstants.BROWSE, "build_cmd_log_browse");
    this.buildCmmandlogPath.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));

    java.util.Set<ButtonInfo> buttonInfoList = new HashSet<ButtonInfo>();
    ButtonInfo newButton = new ButtonInfo(WizardConstants.NEW_BUTTON, "new", ButtonType.BROWSE_FOLDER);
    ButtonInfo removeButton = new ButtonInfo(WizardConstants.REMOVE_BUTTON, "remove", ButtonType.REMOVE);
    buttonInfoList.add(newButton);
    buttonInfoList.add(removeButton);

    this.headerList = new LabelListButtonCreator(composite, WizardConstants.HEADER_DIRECTORIES,
        "header_directories_list", buttonInfoList);
    this.headerList.setProjectPath(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH));

    addListeners();
    initialiseOptions();
  }


  /**
   * This method returns the properties instance.
   */
  @Override
  public Properties getOptions() {
    return this.properties;
  }


  /**
   * This method basically validates the values in the UI elements and set appropriate error message.
   */
  @Override
  public IStatus validate() {
    if (this.astPath.getTextField().getText().isEmpty()) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, WizardConstants.LLVM_PATH_EMPTY_ERROR_MESSAGE);
    }
    else if (!this.astPath.getTextField().getText().isEmpty() &&
        !new File(this.astPath.getTextField().getText()).exists() &&
        !new File(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH) +
            this.astPath.getTextField().getText()).exists()) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, WizardConstants.LLVM_PATH_ERROR_MESSAGE);
    }
    else if(new File(this.astPath.getTextField().getText()).isFile()||new File(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH) +
            this.astPath.getTextField().getText()).isFile()) {
    	return new Status(IStatus.ERROR,Activator.PLUGIN_ID,WizardConstants.LLVM_PATH_DIR_MESSAGE);
    }
    else if (!this.buildCmmandlogPath.getTextField().getText().isEmpty() &&
        !new File(this.buildCmmandlogPath.getTextField().getText()).exists() &&
        !new File(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH) +
            this.buildCmmandlogPath.getTextField().getText()).exists()) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, WizardConstants.BUILD_CMD_FILE_ERROR_MESSAGE);
    }
    else if (!this.buildCmmandlogPath.getTextField().getText().isEmpty() &&
        (!(this.buildCmmandlogPath.getTextField().getText().endsWith(".log")))) {
      return new Status(IStatus.ERROR, Activator.PLUGIN_ID, WizardConstants.BUILD_CMD_FILE_EXTENSION_ERROR_MESSAGE);
    }
    else if (this.headerList.getListBox().getItemCount() > 0) {
      for (String hDir : this.headerList.getListBox().getItems()) {
        if (!new File(hDir).exists() &&
            !new File(this.properties.getProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH) + hDir).exists()) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, WizardConstants.HEADER_LIST_ERROR_MESSAGE);
        }
      }
    }
    return Status.OK_STATUS;

  }


  /**
   * This method adds the listeners to the UI elemets of this widget.
   */
  private void addListeners() {
    this.generateStructMember.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        SCA2AmaltheaWidget.this.properties.setProperty(AmaltheaWizardPreferenceConstants.ENABLE_STRUCT_MEMBER_KEY,
            Boolean.toString(SCA2AmaltheaWidget.this.generateStructMember.getSelection()));
      }
    });

    this.astPath.getButton().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
        dialog.setFilterPath(SCA2AmaltheaWidget.this.astPath.provideDialogLocation());
        String ast_dir_Path = dialog.open();
        if (ast_dir_Path != null) {
          SCA2AmaltheaWidget.this.astPath.setFilePath(ast_dir_Path);
        }
      }
    });

    this.astPath.getTextField().addModifyListener(event -> {
      this.properties.setProperty(AmaltheaWizardPreferenceConstants.AST_PATH_KEY,
          this.astPath.getAbsoluteFilePath());
      setChanged();
      notifyObservers();

    });

    this.buildCmmandlogPath.getButton().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(final SelectionEvent e) {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell());
        dialog.setFilterPath(SCA2AmaltheaWidget.this.buildCmmandlogPath.provideDialogLocation());
        String build_cmd_file_path = dialog.open();
        if (build_cmd_file_path != null) {
          SCA2AmaltheaWidget.this.buildCmmandlogPath.setFilePath(build_cmd_file_path);
        }
      }
    });

    this.buildCmmandlogPath.getTextField().addModifyListener(event -> {
      this.properties.setProperty(AmaltheaWizardPreferenceConstants.BUILD_CMDS_LOG_FILE,
          this.buildCmmandlogPath.getAbsoluteFilePath());
      setChanged();
      notifyObservers();

    });

    for (Button b : this.headerList.getButtonList()) {
      ButtonInfo buttonInfo = this.headerList.getButtonInfoMap().get(b.getText());
      if (buttonInfo.getButtonType().equals(ButtonType.BROWSE_FILE)) {
        b.addSelectionListener(new SelectionAdapter() {

          @Override
          public void widgetSelected(final SelectionEvent e) {
            FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell());
            String filePath = dialog.open();
            if (filePath != null) {
              SCA2AmaltheaWidget.this.headerList.setFilePath(filePath);
            }
            setChanged();
            notifyObservers();
          }
        });
      }
      else if (buttonInfo.getButtonType().equals(ButtonType.BROWSE_FOLDER)) {
        b.addSelectionListener(new SelectionAdapter() {

          @Override
          public void widgetSelected(final SelectionEvent e) {
            DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
            String directoryPath = dialog.open();
            if (directoryPath != null) {
              SCA2AmaltheaWidget.this.headerList.setFilePath(directoryPath);
            }
            setChanged();
            notifyObservers();
            SCA2AmaltheaWidget.this.properties.setProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY,
                Joiner.on(File.pathSeparator).join(getHeaderDirList()));
          }
        });
      }
      else if (buttonInfo.getButtonType().equals(ButtonType.REMOVE)) {
        b.addSelectionListener(new SelectionAdapter() {

          @Override
          public void widgetSelected(final SelectionEvent e) {
            SCA2AmaltheaWidget.this.headerList.getListBox()
                .remove(SCA2AmaltheaWidget.this.headerList.getListBox().getSelectionIndices());
            SCA2AmaltheaWidget.this.properties.setProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY,
                Joiner.on(File.pathSeparator).join(getHeaderDirList()));
          }
        });
      }
    }
  }

  /**
   * @return the headerDirList
   */
  public java.util.List<String> getHeaderDirList() {
    java.util.List<String> list = Lists.newArrayList();
    for (int i = 0; i < this.headerList.getListBox().getItemCount(); i++) {
      list.add(this.headerList.getListBox().getItem(i));
    }
    return list;
  }


  /**
   * This method initializes the UI elements to the values provided in the properties instance.
   */
  private void initialiseOptions() {
    this.generateStructMember.setSelection(
        Boolean.parseBoolean(this.properties.getProperty(AmaltheaWizardPreferenceConstants.ENABLE_STRUCT_MEMBER_KEY)));
    if (this.properties.getProperty(AmaltheaWizardPreferenceConstants.AST_PATH_KEY) != null) {
      this.astPath.getTextField()
          .setText(this.properties.getProperty(AmaltheaWizardPreferenceConstants.AST_PATH_KEY));
    }
    if ((this.properties.getProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY) != null) &&
        !this.properties.getProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY).isEmpty()) {
      this.headerList.getListBox()
          .setItems(Splitter.on(File.pathSeparator)
              .splitToList(this.properties.getProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY))
              .toArray(new String[] {}));
    }
    if (this.properties.getProperty(AmaltheaWizardPreferenceConstants.BLOG_KEY) != null) {
      this.buildCmmandlogPath.getTextField().setText(this.properties.getProperty(AmaltheaWizardPreferenceConstants.BLOG_KEY));
    }

  }
}
