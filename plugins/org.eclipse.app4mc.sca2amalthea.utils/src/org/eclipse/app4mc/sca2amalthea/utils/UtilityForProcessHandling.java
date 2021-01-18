/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.utils;

public final class UtilityForProcessHandling {

  private static Process currentRunningProcess = null;

  private static boolean isModelGenCancelled = false;

  /**
   * Private constructor
   */
  private UtilityForProcessHandling() {
    // Private constructor
  }

  /**
   * @return the currentRunningProcess
   */
  public synchronized static Process getCurrentRunningProcess() {
    return UtilityForProcessHandling.currentRunningProcess;
  }


  /**
   * @param currentRunningProcess the currentRunningProcess to set
   */
  public synchronized static void setCurrentRunningProcess(final Process currentRunningProcess) {
    UtilityForProcessHandling.currentRunningProcess = currentRunningProcess;
  }


  /**
   * @return the isModelGenerationcancelled
   */
  public synchronized static boolean isModelGenerationcancelled() {
    return isModelGenCancelled;
  }


  /**
   * @param isModelGenerationcancelled the isModelGenerationcancelled to set
   */
  public synchronized static void setModelGenerationcancelled(final boolean isModelGenerationcancelled) {
    UtilityForProcessHandling.isModelGenCancelled = isModelGenerationcancelled;
  }


}
