/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import org.eclipse.app4mc.sca.ui.messagedialog.App4mcMessgeDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This class to be used by SCA2Amalthea to show any kind of message dialog.
 */
public class SCAPopup implements Runnable {

  private Shell currentShell;
  private final String title;
  private final String message;
  private final int dialogType;
  private boolean dialogStatus;

  /**
   * @param title the title to be displayed in the message dialog
   * @param message the message to be displayed in the message dialog.
   * @param dialogType the type of dailog Eg information,confirmation etc
   */
  public SCAPopup(final String title, final String message, final int dialogType) {
    Display.getDefault().syncExec(() -> {
      SCAPopup.this.currentShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    });
    this.title = title;
    this.message = message;
    this.dialogType = dialogType;
  }

  @Override
  public void run() {
    App4mcMessgeDialog confirmationMessageDialog =
        new App4mcMessgeDialog(this.dialogType, SCAPopup.this.currentShell, this.title, null, this.message);
    this.dialogStatus = (confirmationMessageDialog.open() == 0);
  }

  /**
   * @return the status returning if the dialog was open or not.
   */
  public boolean getDialogStatus() {
    return this.dialogStatus;
  }

}
