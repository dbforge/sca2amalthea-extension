package org.eclipse.app4mc.sca.logging.impl;

import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.util.LogInformation;

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


/**
 */
public interface ITextLogger extends ILogger {

  /**
   * @param logInfo to be logged
   */
  default void error(final LogInformation logInfo) {
    log(logInfo);
  }

  /**
   * @param logInfo to be logged
   */
  default void debug(final LogInformation logInfo) {
    log(logInfo);
  }

  /**
   * @param logInfo to be logged
   */
  default void info(final LogInformation logInfo) {
    log(logInfo);
  }

  /**
   * @param logInfo to be logged
   */
  default void warn(final LogInformation logInfo) {
    log(logInfo);
  }

  /**
   * Clears the logger
   */
  public void clear();

  /**
   * Validates the given log information and returns the state
   *
   * @param logInfo The log information to be validated
   * @return true if the log info contains relevant data to log into the this external logger
   * @throws App4mcLoggerException If the validation fails
   */
  public boolean validate(LogInformation logInfo) throws App4mcLoggerException;

  /**
   * Closes the logger associated with the given log file. The log file parameter should be the abosulute path of the
   * log file. The abosulute path is used to store the loggers created for a given file
   *
   * @param logFile the absolute path of the log file whose logger has to be closed
   */
  public void closeLogger(final String logFile);
}
