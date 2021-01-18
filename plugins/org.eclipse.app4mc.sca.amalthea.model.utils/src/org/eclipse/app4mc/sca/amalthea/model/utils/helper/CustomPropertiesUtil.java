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
package org.eclipse.app4mc.sca.amalthea.model.utils.helper;

import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.IAnnotatable;
import org.eclipse.app4mc.amalthea.model.StringObject;

/**
 * This class contains Source Code to add custom properties to the AMALTHEA Model
 */
public final class CustomPropertiesUtil {

  /**
   * Constant for srcline property
   */
  public static final String SRC_LINE = "srcline";
  /**
   * Constant for srccol property
   */
  public static final String SRC_COL = "srccol";

  /**
   * Private constructor
   */
  private CustomPropertiesUtil() {
    // Intentionally left blank
  }

  /**
   * Creates string objects out of the value provided and returns
   *
   * @param value The value for which the string object has to be created
   * @return {@link StringObject}
   */
  private static StringObject getCustomStringObject(final String value) {
    StringObject srcValue = AmaltheaFactory.eINSTANCE.createStringObject();
    srcValue.setValue(value);
    return srcValue;
  }

  /**
   * @param amBaseObject
   * @param key
   * @param value
   */
  public static void addToCustomProperties(final IAnnotatable amBaseObject, final String key, final String value) {
    amBaseObject.getCustomProperties().put(key, getCustomStringObject(value));
  }

  /**
   * @param amBaseObject
   * @param srcLine
   * @param srcCol
   */
  public static void addSourceLineInformation(final IAnnotatable amBaseObject, final String srcLine,
      final String srcCol) {
    if ((srcLine != null) && !srcLine.isEmpty()) {
      addToCustomProperties(amBaseObject, CustomPropertiesUtil.SRC_LINE, srcLine);
    }
    if ((srcCol != null) && !srcCol.isEmpty()) {
      addToCustomProperties(amBaseObject, CustomPropertiesUtil.SRC_COL, srcCol);
    }
  }
}
