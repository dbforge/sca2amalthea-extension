/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

/**
 * Utility to create label, list and the buttons.
 */
public class LabelListButtonCreator {

  /**
   * {@link Label}
   */
  private Label label;
  /**
   * {@link Text}
   */
  private List listBox;

  private final java.util.List<Button> buttons = new ArrayList<>();

  private final Map<String, ButtonInfo> buttonInfoMap = new HashMap<>();
  /**
   * the project base path
   */
  private String basePath;


  /**
   * @param parent the root composite on which the label ,list and buttons are created.
   * @param labelName the name of the label.
   * @param listBoxId the list instance.
   * @param buttonInfoList list of buttoninfo instances
   */
  public LabelListButtonCreator(final Composite parent, final String labelName, final String listBoxId,
      final java.util.Set<ButtonInfo> buttonInfoList) {
    createComponent(parent, labelName, listBoxId, buttonInfoList);
  }

  /**
   * @param parent Composite into which this LabelTextButton composite is to be placed
   * @param labelName label's text
   * @param listBoxId - list Field Id
   * @param buttonInfoList list of buttoninfo instances
   */
  public void createComponent(final Composite parent, final String labelName, final String listBoxId,
      final java.util.Set<ButtonInfo> buttonInfoList) {
    // Create Label
    this.label = new Label(parent, SWT.LEFT);
    this.label.setText(labelName);
    this.label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
    // Create List Field
    this.listBox = new List(parent, SWT.BORDER);
    this.listBox.setData("name", listBoxId);
    this.listBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));


    Composite buttonComposite = new Composite(parent, SWT.NONE);
    buttonComposite.setLayout(new GridLayout(1, false));
    buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, -1));

    // Create buttons
    for (ButtonInfo buttonInfo : buttonInfoList) {
      Button button = new Button(buttonComposite, SWT.PUSH);
      button.setData("name", buttonInfo.getButtonId());
      button.setText(buttonInfo.getButtonName());
      button.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
      this.buttons.add(button);
      this.buttonInfoMap.put(buttonInfo.getButtonName(), buttonInfo);
    }


    GridData listData = (GridData) this.listBox.getLayoutData();
    listData.heightHint = this.listBox.computeTrim(0, 0, 0, this.listBox.getItemHeight() * 4).height;

  }

  /**
   * Adds listeners to the buttons of the list boxes.
   */
  public void addListeners() {
    for (Button b : this.buttons) {
      ButtonInfo buttonInfo = this.buttonInfoMap.get(b.getText());
      if (buttonInfo.getButtonType().equals(ButtonType.BROWSE_FILE)) {
        b.addSelectionListener(new SelectionAdapter() {

          @Override
          public void widgetSelected(final SelectionEvent e) {
            FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell());
            String filePath = dialog.open();
            if (filePath != null) {
              LabelListButtonCreator.this.listBox.add(filePath);
            }
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
              LabelListButtonCreator.this.listBox.add(directoryPath);
            }
          }
        });
      }
      else if (buttonInfo.getButtonType().equals(ButtonType.REMOVE)) {
        this.listBox.remove(this.listBox.getSelectionIndices());
      }
    }
  }


  /**
   * @param pathname
   */
  public void setFilePath(final String pathname) {
    Display.getDefault().syncExec(() -> {

      if ((this.basePath != null) && !this.basePath.isEmpty()) {
        try {
          String path = new File(this.basePath).getCanonicalPath();
          if (pathname.indexOf(path + File.separatorChar) != -1) {
            this.listBox.add(pathname.substring(this.basePath.length() - 1));
          }
          else if (pathname.equalsIgnoreCase(path)) {
            this.listBox.add("");
          }
          else {
            this.listBox.add(pathname);
          }
        }
        catch (IOException e) {
          this.listBox.add(pathname);
        }
      }
      else {
        this.listBox.add(pathname);
      }
    });

  }

  /**
   * @return the labelName
   */
  public Label getLabel() {
    return this.label;
  }

  /**
   * @return
   */
  public java.util.List<Button> getButtonList() {
    return this.buttons;
  }

  /**
   * @return
   */
  public Map<String, ButtonInfo> getButtonInfoMap() {
    return this.buttonInfoMap;
  }

  /**
   * @return
   */
  public List getListBox() {
    return this.listBox;
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

}
