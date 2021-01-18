/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

/**
 * This is constants class for common Wizard dialog for Elf2Amalthea/SCA2Amalthea
 */
public class WizardConstants {

  /**
   * Export Options button name
   */
  public static final String EXPORT_OPTIONS = "Export Options";
  /**
   * Default file name used during the export options button
   */
  public static final String DEFAULT_EX_OPT_FILE_NAME = "OptionsExport.opt";

  /**
   * Browse button name
   */
  public static final String BROWSE = "Browse";
  /**
   * Output
   */
  public static final String OUTPUT = "Output";

  /**
   * SW Data (Labels/Runnables)
   */
  public static final String SW_DATA = "SW Data (Labels/Runnables)";

  /**
   * Scheduling/OS Data
   */
  public static final String SCHEDULING_OS_DATA = "Scheduling/OS Data";
  /**
   * OS lock function file (optional)
   */
  public static final String OS_LOCK_FUNC_FILE = "OS lock function file (optional)";
  /**
   * Task/Scheduling file (optional)
   */
  public static final String TASK_SCHED_FILE = "Task/Scheduling file (optional)";

  /**
   * Path to output directory (optional)
   */
  public static final String OUTPUT_DIRECTORY_PATH = "Output directory (optional)";
  /**
   * Amalthea Generation
   */
  public static final String AMALTHEA_GENERATION = "Amalthea Generation";
  /**
   * Group Name used in sca2amalthea widget
   */
  public static final String GROUP_SCA2AMALTHEA = "SCA2AMALTHEA";
  /**
   * Generate struct member check box text.
   */
  public static final String GENERATE_STRUCT_MEMBER = "Generate Structure members";
  /**
   * LLVM Executable path label text
   */
  public static final String LLVM_PATH_TEXT = "LLVM Executable directory (optional)";
  /**
   * Header directories label text.
   */
  public static final String HEADER_DIRECTORIES = "Header directories (optional)";
  /**
   * New Button text.
   */
  public static final String NEW_BUTTON = "New";
  /**
   * Remove Button text.
   */
  public static final String REMOVE_BUTTON = "Remove";
  /**
   * Build cmd file label text.
   */
  public static final String BUILD_CMD_FILE = "Build command log file (optional)";

  /**
   * Error message to be shown when the path of the llvm executable is not existing.
   */
  public static final String LLVM_PATH_ERROR_MESSAGE = "Path to the llvm does not exist";
  
  /**
   * Error message to be shown when the path of the llvm executable is not existing.
   */
  public static final String LLVM_PATH_DIR_MESSAGE = "Path to the llvm should be a directory";

  /**
   * Error message to be shown when the path of the llvm executable is empty.
   */
  public static final String LLVM_PATH_EMPTY_ERROR_MESSAGE = "Path to the llvm is mandatory";
  /**
   * Error message to be shown when the path of the build cmd file does not exit.
   */
  public static final String BUILD_CMD_FILE_ERROR_MESSAGE = "Path of the build command log file does not exist";
  /**
   * Error message to be shown when the path of the build cmd file does not end with .log.
   */
  public static final String BUILD_CMD_FILE_EXTENSION_ERROR_MESSAGE =
      "Path of the build command log file shoud end with .log extension";

  /**
   * Error message to be shown when the header directory paths do not exist.
   */
  public static final String HEADER_LIST_ERROR_MESSAGE = "At least one of the header directory paths does not exist";


  /**
   * Private Constructor
   */
  private WizardConstants() {
    // Private Constructor
  }

}
