/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


/**
 * This class creates the often used combination of label, text and button
 */
public class LabelTextButtonCreator {

  /**
   * {@link Label}
   */
  private Label label;
  /**
   * {@link Text}
   */
  private Text textField;
  /**
   * {@link Button}
   */
  private Button button;
  /**
   * the project base path
   */
  private String basePath;
  /**
   * the absolute path to the given file
   */
  private String absolutePath = "";
  private String optionalButtonTitle;
  private Button optionalButton;
  private int optionalButtonStyle;
  private final LinkedHashMap<String, String> textHistory;

  /**
   * Constructor used for LabelTextButton composite.
   *
   * @param parent -Parent Composite
   * @param labelName - Label Name
   * @param textFieldId - Text Field Id
   * @param buttonName - Button Name
   * @param buttonId - Button Id
   */
  public LabelTextButtonCreator(final Composite parent, final String labelName, final String textFieldId,
      final String buttonName, final String buttonId) {
    createComponent(parent, labelName, textFieldId, buttonName, buttonId);
    this.textHistory = new LinkedHashMap<>();
  }


  /**
   * Constructor used for LabelTextButton composite with optional button
   *
   * @param parent {@link Composite}
   * @param labelName Label Name
   * @param textFieldId Text Field id
   * @param buttonName Button name
   * @param buttonId Button id
   * @param optButtonStyle Optional button Style
   * @param optButtonTitle Optional Button title
   */
  public LabelTextButtonCreator(final Composite parent, final String labelName, final String textFieldId,
      final String buttonName, final String buttonId, final int optButtonStyle, final String optButtonTitle) {
    this.optionalButtonStyle = optButtonStyle;
    this.optionalButtonTitle = optButtonTitle;
    createComponent(parent, labelName, textFieldId, buttonName, buttonId);
    this.textHistory = new LinkedHashMap<>();
  }


  /**
   * @return children items.
   */
  public Control[] getChildren() {
    List<Control> children = new ArrayList<>();
    Control[] mandatoryControls = new Control[] { this.label, this.textField, this.button };
    children.addAll(Arrays.asList(mandatoryControls));
    // Check if Optional Button has been provided
    if (hasOptionalButton()) {
      children.add(this.optionalButton);
    }
    return children.toArray(new Control[children.size()]);
  }

  /**
   * @param parent Composite into which this LabelTextButton composite is to be placed
   * @param labelName label's text
   * @param textFieldId - Text Field Id
   * @param buttonName button's text
   * @param buttonId - Button Id
   */
  public void createComponent(final Composite parent, final String labelName, final String textFieldId,
      final String buttonName, final String buttonId) {
    // Create Label
    this.label = new Label(parent, SWT.LEFT);
    this.label.setText(labelName);
    this.label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
    // Create Text Field
    this.textField = new Text(parent, SWT.BORDER);
    this.textField.setData("name", textFieldId);
    this.textField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    this.textField.addVerifyListener(e -> {
      // text field content before change
      String currentText = ((Text) e.widget).getText();
      // text field content after change
      String changedText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
      validateTextEntry(changedText);
    });


    // Create button
    this.button = new Button(parent, SWT.PUSH);
    this.button.setData("name", buttonId);
    this.button.setText(buttonName);
    this.button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, -1));

    // Create Optional Button if needed
    if (hasOptionalButton()) {
      // depending on page it can be check box or push button
      this.optionalButton = new Button(parent, this.optionalButtonStyle);
      this.optionalButton.setText(this.optionalButtonTitle);
      this.optionalButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
    }
  }


  /**
   * @return true if this LabelTextButtonCreator component has the optional Label + Button, false otherwise
   */
  private boolean hasOptionalButton() {
    return !((this.optionalButtonTitle == null) || this.optionalButtonTitle.isEmpty());
  }


  /**
   * Enable / Disable the control
   *
   * @param enabled Indicates if the control should be enabled or disabled
   */

  public void setEnabled(final boolean enabled) {
    if (this.label != null) {
      this.label.setEnabled(enabled);
    }
    if (this.textField != null) {
      this.textField.setEnabled(enabled);
    }
    if (this.button != null) {
      this.button.setEnabled(enabled);
    }
  }

  /**
   * method to retain the relative path to project level
   *
   * @param pathname : set path from UI
   */
  public void setFilePath(final String pathname) {

    Display.getDefault().syncExec(() -> {

      if ((this.basePath != null) && !this.basePath.isEmpty()) {
        try {
          String path = new File(this.basePath).getCanonicalPath();
          if (pathname.indexOf(path + File.separatorChar) != -1) {
            this.textField.setText(pathname.substring(this.basePath.length() - 1));
          }
          else if (pathname.equalsIgnoreCase(path)) {
            this.textField.setText("");
          }
          else {
            this.textField.setText(pathname);
          }
        }
        catch (IOException e) {
          this.textField.setText(pathname);
        }
      }
      else {
        this.textField.setText(pathname);
      }
    });
  }


  /**
   * Validates the provided path inside this LabelTextButtonCreator's text field and triggers respective notifications
   * to be displayed in this LabelTextButtonCreator's notification component.
   *
   * @param filePath to be validated
   */
  public void validateTextEntry(final String filePath) {
    if (filePath != null) {
      String tempFilePath = filePath.trim();
      if (!tempFilePath.isEmpty() &&
          (new File(tempFilePath).exists() || new File(this.basePath + tempFilePath).exists())) {
        setAbsolutePath(tempFilePath);
      }
      else if (tempFilePath.isEmpty()) {
        this.absolutePath = "";
      }
    }
  }


  /**
   * @param filePath
   */
  private void setAbsolutePath(final String filePath) {
    if (new File(filePath).exists()) {
      this.absolutePath = filePath;
    }
    else {
      if (new File(filePath).isAbsolute()) {
        this.absolutePath = filePath;
      }
      else if (new File(this.basePath + filePath).exists()) {
        this.absolutePath = new File(this.basePath + filePath).getAbsolutePath();
      }
      else {
        this.absolutePath = this.basePath + filePath;
      }
    }
    this.textHistory.put(this.label.getText(), this.absolutePath);
  }


  /**
   * @return the labelName
   */
  public Label getLabel() {
    return this.label;
  }


  /**
   * @return the textFieldName
   */

  public Text getTextField() {
    return this.textField;
  }


  /**
   * @return the buttonName
   */

  public Button getButton() {
    return this.button;
  }

  /**
   * @return the Optional Button
   */
  public Button getOptionalButton() {
    return this.optionalButton;
  }

  /**
   * @param projectLocation the project base path to set
   */
  public void setProjectPath(final String projectLocation) {
    if (projectLocation.substring(projectLocation.length() - 1).equals(File.separator)) {
      this.basePath = projectLocation;
    }
    this.basePath = projectLocation + File.separator;
  }

  /**
   * @return the project base path
   */
  public String getProjectPath() {
    return this.basePath;
  }


  /**
   * @return the absolutePath of the file/directory given in text field
   */

  public String getAbsoluteFilePath() {
    return this.absolutePath;
  }


  /**
   * @return the text history
   */
  public Map<String, String> getTextHistory() {
    return this.textHistory;
  }


  /**
   * @return the location for the Dialog to open in when pressing "Browse" button
   */
  public String provideDialogLocation() {
    String location = getProjectPath();
    String textEntry = getTextField().getText();

    File file = new File(textEntry);
    // dialog per default shall open either in location provided in text field or in project base path
    if (!textEntry.isEmpty()) {
      // try to set location to provided path of text field
      if (file.exists()) {
        // as is (full path)
        if (file.isDirectory()) {
          location = file.getPath();
        }
        else {
          location = file.getParent();
        }
      }
      else if (new File(getProjectPath() + textEntry).exists()) {
        // base path + text field entry (full path = base path + relative path)
        location = new File(getProjectPath() + textEntry).getParent();
      }
    }
    return location;
  }

}
