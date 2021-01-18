/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;


/**
 * Class for storing swt button information.
 */
public class ButtonInfo {

  private final String buttonName;
  private final String buttonId;
  private final ButtonType buttonType;

  /**
   * @param name
   * @param id
   */
  public ButtonInfo(final String name, final String id, final ButtonType buttonType) {
    this.buttonName = name;
    this.buttonId = id;
    this.buttonType = buttonType;
  }


  /**
   * @return the buttonName
   */
  public String getButtonName() {
    return this.buttonName;
  }


  /**
   * @return the buttonId
   */
  public String getButtonId() {
    return this.buttonId;
  }


  /**
   * @return the buttonType
   */
  public ButtonType getButtonType() {
    return this.buttonType;
  }

  @Override
  public boolean equals(final Object buttonInfo) {
    if (buttonInfo instanceof ButtonInfo) {
      return this.buttonName.equals(((ButtonInfo) buttonInfo).getButtonName());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.buttonName.hashCode();
  }
}
