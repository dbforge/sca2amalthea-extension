
/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.messagedialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * This is a message dialog class to be used to show the pop-ups(information,error,warning) as and when required.
 */
public class App4mcMessgeDialog {


  private final Image shellImg;
  private final int kind;
  private final Shell shell;
  private final String title;
  private final String msg;

  /**
   * @param kind the kind of dialog to open Eg ERROR,INFORMATION,QUESTION, WARNING,CONFIRM,orQUESTION_WITH_CANCEL.
   * @param shell the parent shell of the dialog
   * @param title the dialog's title
   * @param shellImg the image to be shown on the left top corner of the pop-up
   * @param msg the message to be shown in the pop up.
   */
  public App4mcMessgeDialog(final int kind, final Shell shell, final String title, final Image shellImg,
      final String msg) {
    this.kind = kind;
    this.shell = shell;
    this.title = title;
    this.shellImg = shellImg;
    this.msg = msg;

  }

  private String[] getApp4mcButtonLabels() {
    String[] app4mcDialogButtonLabels;

    if ((this.kind == MessageDialog.ERROR) || (this.kind == MessageDialog.INFORMATION) ||
        (this.kind == MessageDialog.WARNING)) {
      app4mcDialogButtonLabels = new String[] { IDialogConstants.OK_LABEL };
    }
    else if (this.kind == MessageDialog.CONFIRM) {
      app4mcDialogButtonLabels = new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL };
    }
    else if (this.kind == MessageDialog.QUESTION) {
      app4mcDialogButtonLabels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL };
    }
    else if (this.kind == MessageDialog.QUESTION_WITH_CANCEL) {
      app4mcDialogButtonLabels =
          new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
    }
    else {
      throw new IllegalArgumentException("Illegal value for kind in MessageDialog.open()");
    }
    return app4mcDialogButtonLabels;
  }

  /**
   * Opens the message dialog.
   *
   * @return int indicating if the message dialog was opened successfully.
   */
  public int open() {
    MessageDialog d =
        new MessageDialog(this.shell, this.title, this.shellImg, this.msg, this.kind, 0, getApp4mcButtonLabels());
    return d.open();
  }
}
