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
package org.eclipse.app4mc.sca.logging.console.loggers;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.app4mc.sca.logging.impl.IConsoleLogger;
import org.eclipse.app4mc.sca.logging.util.LogInformation;


/**
 * Logs the information into the command line.
 */
public final class CommandLineLogger implements IConsoleLogger {

  /**
   * Logger instance
   */
  private final Logger logger;

  private static CommandLineLogger instance = null;

  /**
   * Private constructor
   */
  private CommandLineLogger() {
    this.logger = Logger.getLogger("org.eclipse.app4mc.logging.console.loggers.CommandLineLogger");
  }

  /**
   * Returns the singleton instance of the command line logger
   *
   * @return {@link CommandLineLogger}
   */
  public static synchronized CommandLineLogger getInstance() {
    if (instance == null) {
      instance = new CommandLineLogger();
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void log(final LogInformation logInfo) {
    if (isEnabled(logInfo)) {
      configureLogger();
      switch (logInfo.getSeverity()) {
        case DEBUG:
          this.logger.debug(logInfo.getMessage());
          break;
        case ERROR:
        case FATAL:
          this.logger.error(logInfo.getMessage());
          break;
        case WARNING:
          this.logger.warn(logInfo.getMessage());
          break;
        case INFO:
          this.logger.info(logInfo.getMessage());
          break;
        default:
          break;
      }
    }
  }

  /**
   * Configures the logger with specific property
   */
  private void configureLogger() {
    if (!this.logger.getAllAppenders().hasMoreElements()) {
      PatternLayout layout = new PatternLayout("%5p %d{dd MMM yyyy HH:mm:ss,SSS} [%t] (%F:%L) - %m%n");
      ConsoleAppender consoleAppender = new ConsoleAppender(layout, "System.out");
      consoleAppender.activateOptions();
      this.logger.addAppender(consoleAppender);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    // Nothing to implement now
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void logException(final LogInformation logInfo) {
    configureLogger();
    switch (logInfo.getSeverity()) {
      case DEBUG:
        this.logger.debug(logInfo.getException().getMessage(), logInfo.getException());
        break;
      case ERROR:
        this.logger.error(logInfo.getException().getMessage(), logInfo.getException());
        break;
      case INFO:
        this.logger.info(logInfo.getException().getMessage(), logInfo.getException());
        break;
      default:
        break;
    }

  }

  /**
   * @return the logger
   */
  public Logger getLogger() {
    return this.logger;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void closeLogger() {
    // Now nothing
  }
}
