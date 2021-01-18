/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.util.util;

import java.io.File;

/**
 * Utilty class
 */
public class SCAUtils {


  /**
   * @param projectPath - Absolute path of the project
   * @param directoryType - Enum value SCAToolsDirectoryType
   * @param componentDirectoryName - Optional directory name of the component that needs to be creatred under "SCA"
   *          directory. This directory will not be created if the value is null or empty
   * @return - SCA directory path
   */
  public static String getSCARootDirectory(final String projectPath, final SCAToolsDirectoryType directoryType,
      final String componentDirectoryName) {
    return createSCADirectory(projectPath, directoryType.getDirectoryName(), componentDirectoryName);
  }


  private static String createSCADirectory(final String projectPath, final String dirType,
      final String componentDirectoryName) {
    File scaDir;
    if ((componentDirectoryName != null) && !componentDirectoryName.isEmpty()) {
      scaDir = new File(projectPath + File.separator + dirType + File.separator + componentDirectoryName);
    }
    else {
      scaDir = new File(projectPath + File.separator + dirType + File.separator);
    }
    if (scaDir.exists()) {
      return scaDir.getAbsolutePath();
    }
    scaDir.mkdirs();
    return scaDir.getAbsolutePath();
  }


}
