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

import org.eclipse.app4mc.amalthea.model.IAnnotatable;
import org.eclipse.app4mc.sca.amalthea.model.utils.helper.CustomPropertiesUtil;

/**
 * This class contains the constants for Source Code to AMALTHEA Exporter
 */
public final class CustomPropertiesAdder {

  /**
   * Constant for FC property
   */
  public static final String FILE_NAME = "File";
  /**
   * Constant for BC property
   */
  public static final String PACKAGE_NAME = "Package";
  /**
   * Constant for get_lock_function Name
   */
  public static final String GET_LOCK_FUNC_NAME = "Get_Lock_Function";

  /**
   * Constant for release_lock_function Name
   */
  public static final String RELEASE_LOCK_FUNC_NAME = "Release_Lock_Function";

  /**
   * Constant for lock type of a semaphore
   */
  public static final String LOCK_TYPE = "Lock_Type";

  /**
   * Private constructor
   */
  private CustomPropertiesAdder() {
    // Intentionally left blank
  }

  /**
   * @param amBaseObject
   * @param srcLine
   * @param srcCol
   */
  public static void addSourceLineInformation(final IAnnotatable amBaseObject, final String srcLine,
      final String srcCol) {
    if ((srcLine != null) && !srcLine.isEmpty()) {
      CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesUtil.SRC_LINE, srcLine);
    }
    if ((srcCol != null) && !srcCol.isEmpty()) {
      CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesUtil.SRC_COL, srcCol);
    }
  }


  /**
   * @param amBaseObject
   * @param fcName
   * @param bcName
   */
  public static void addFilePackageInformation(final IAnnotatable amBaseObject, final String fcName, final String bcName) {
    if ((fcName != null) && !fcName.isEmpty()) {
      CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesAdder.FILE_NAME, fcName);
    }
    if ((bcName != null) && !bcName.isEmpty()) {
      CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesAdder.PACKAGE_NAME, bcName);
    }
  }
}
