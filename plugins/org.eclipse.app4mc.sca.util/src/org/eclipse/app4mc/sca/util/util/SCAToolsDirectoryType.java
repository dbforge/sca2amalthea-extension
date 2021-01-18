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
package org.eclipse.app4mc.sca.util.util;


/**
 * This is an enum class for creating a common sca tools directory under _bin, _log, _out , _gen directories.
 */
public enum SCAToolsDirectoryType {
                                  /**
                                   * _bin directory under the project
                                   */
                                  SCA_BIN("_bin"),
                                  /**
                                   * _gen directory under the project
                                   */
                                  SCA_GEN("_gen"),
                                  /**
                                   * _log directory under the project
                                   */
                                  SCA_LOG("_log"),
                                  /**
                                   * _out directory under the project
                                   */
                                  SCA_OUT("_out");

  private String directoryName;

  /**
   * @return the directoryName
   */
  public String getDirectoryName() {
    return this.directoryName;
  }

  SCAToolsDirectoryType(final String dirName) {
    this.directoryName = dirName;
  }

}
