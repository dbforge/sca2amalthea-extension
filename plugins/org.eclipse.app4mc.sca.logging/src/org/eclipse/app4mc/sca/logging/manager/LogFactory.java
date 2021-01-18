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
package org.eclipse.app4mc.sca.logging.manager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.app4mc.sca.logging.console.loggers.CommandLineLogger;
import org.eclipse.app4mc.sca.logging.impl.ILogger;
import org.eclipse.app4mc.sca.logging.loggers.TextFileLogger;


/**
 * Factory class that manages the instance of all types of loggers
 *
 */
public class LogFactory {

  private static Map<LoggerType, ILogger> instance;

 
  public enum Severity {
                        /**
                         * Error Severity
                         */
                        ERROR,
                        /**
                         * Warning Severity
                         */
                        WARNING,
                        /**
                         * Info Severity
                         */
                        INFO,
                        /**
                         * Debug Severity
                         */
                        DEBUG,
                        /**
                         * System Severity
                         */
                        SYSTEM,
                        /**
                         * Fatal Severity
                         */
                        FATAL,
                        /**
                         * Metric Severiry
                         */
                        METRIC,
                        /**
                         * UNKNOWN
                         */
                        UNKNOWN
  }


  public enum Priority {
                        /**
                         * Priority Major
                         */
                        MAJOR,
                        /**
                         * Priority Minor
                         */
                        MINOR,
                        /**
                         * Priority Critical
                         */
                        CRITICAL,
                        /**
                         * Priority Blocker
                         */
                        BLOCKER,
                        /**
                         * Priority Trivial
                         */
                        TRIVIAL
  }

  public enum LoggerType {
                          /**
                           * Eclipse console logger
                           */
                          ECLIPSE_CONSOLE,
                          /**
                           * General text logger
                           */
                          TEXT_FILE,
                          /**
                           * Command line logger
                           */
                          CMD_LINE,
                          /**
                           * Error log view
                           */
                          ERROR_LOG,
                          /**
                           * Problems log view
                           */
                          PROBLEMS_LOG,

  }

  /**
   * Return the singleton instance of different type of loggers
   *
   * @param type The type of the logger {@link LoggerType}
   * @return {@link ILogger}
   */
  public static synchronized ILogger getLogger(final LoggerType type) {
    if (instance == null) {
      init();
    }
    return instance.get(type);
  }

  /**
   * Initializes all the non UI loggers which are implemented as part of this plugin
   */
  private static void init() {
    instance = new HashMap<>();
    instance.put(LoggerType.CMD_LINE, CommandLineLogger.getInstance());
    instance.put(LoggerType.TEXT_FILE, TextFileLogger.getInstance());
  }


  /**
   * @return the logFactory
   */
  public static Map<LoggerType, ILogger> getLogFactory() {
    return instance;
  }


  /**
   * @param type The logger type
   * @param logger {@link ILogger}
   */
  public static void addToLogFactory(final LoggerType type, final ILogger logger) {
    if (instance == null) {
      init();
    }
    instance.put(type, logger);
  }
}
