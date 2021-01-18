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
package org.eclipse.app4mc.sca.logging.util;


/**
 */
public final class SCALogConstants {

  /**
   * Private constructor
   */
  private SCALogConstants() {
    // Private constructor
  }

  /**
   * Default log file
   */
  public static final String DEFAULT_LOG_FILE = "sca.log";
  /**
   * User home directory
   */
  public static final String USER_HOME = System.getProperty("user.home");

  /**
   * Log extension
   */
  public static final String LOG_EXTENSION = ".log";
  /**
   * CSV extension
   */
  public static final String CSV_EXTENSION = ".csv";
  /**
   * The tool id
   */
  public static final String TOOL_ID = "SCA";
  /**
   * The logger properties file name
   */
  public static final String LOGGER_PROPERTIES = "logger.properties";
  /**
   * The CMD logger properties file name
   */
  public static final String CMD_LOGGER_PROPERTIES = "cmdlogger.properties";

  /**
   * Plugin id
   */
  public static final String PLUGIN_ID = "org.eclipse.app4mc.logging";
  /**
   *
   */
  public static final String ERROR_LOGGER_ERROR_MSG = "Missing mandatory attributes-Plug-in ID or Exception object";
  /**
   *
   */
  public static final String PROBLEMS_LOGGER_ERROR_MSG =
      "Missing mandatory attributes-Resource, Line number or priority";

  /**
   *
   */
  public static final String TXT_LOGGER_ERROR_MSG =
      "Log File path cannot be empty or null and should end with .log or .csv extension";
  /**
   *
   */
  public static final String LOG_MESSAGE_NULL_MSG = "Message or message id cannot be null.";
  /**
   *
   */
  public static final String CLASS_INFO_NULL_MSG = "The class information cannot be null.";
  /**
   *
   */
  public static final String SEVERITY_NULL_MSG = "Severity cannot be null.";
  /**
   *
   */
  public static final String IGNORE_LOG_MSG = "Constraint check failed, Logging has been ignored";
}
