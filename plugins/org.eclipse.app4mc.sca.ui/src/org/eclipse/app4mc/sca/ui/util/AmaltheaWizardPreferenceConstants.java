/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;


/**
 * Constants class for Amalthea wizard preferences
 */
public class AmaltheaWizardPreferenceConstants {

  /**
   * Project path in the file system
   */
  public static final String PROJECT_PATH = "projectPath";

  /**
   * Preference key for build_cmds.log file
   */
  public static final String BUILD_CMDS_LOG_FILE = "buildCmdsLogFile";

  /**
   * Preference key for OS lock function file
   */
  public static final String OS_LOCK_FILE = "lockInfo";
  /**
   * Preference key for Task/Scheduling file
   */
  public static final String TASK_SCHEDULING_FILE = "taskInfo";

  /**
   * Preference key for Path to output directory
   */
  public static final String OUTPUT_DIRECTORY = "outputPath";

  /**
   * Preference key for Generate struct members option.
   */
  public static final String ENABLE_STRUCT_MEMBER_KEY = "enableStructMember";

  /**
   * Preference key for Path to llvm executable option
   */
  public static final String AST_PATH_KEY = "astPath";
  /**
   * Preference key for header directories list option.
   */
  public static final String HDIR_LIST_KEY = "hdirList";

  /**
   * Preference key for build command log option.
   */
  public static final String BLOG_KEY = "blog";

  /**
  *
  */
  private AmaltheaWizardPreferenceConstants() {
    // default constructor
  }
}
