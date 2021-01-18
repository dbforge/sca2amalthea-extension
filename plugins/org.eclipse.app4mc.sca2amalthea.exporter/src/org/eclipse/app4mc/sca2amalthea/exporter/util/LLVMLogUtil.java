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
package org.eclipse.app4mc.sca2amalthea.exporter.util;

/**
 * Helper functionality for using the Logmanager
 */
public final class LLVMLogUtil {

  /**
   * The private constructor
   */
  private LLVMLogUtil() {
    // Empty constructor
  }

  /**
   * the llvm log message id
   */
  public static final String LOG_MSG_ID = "LLVM";

  public static final String GEN_FC = "gen_fc";

  public static final String GEN_BC = "gen_bc";

  private static boolean isPVER;

  public static boolean isPVER() {
    return isPVER;
  }

  public static void setIsPVER(final boolean pver) {
    isPVER = pver;
  }

}
