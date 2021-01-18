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
package org.eclipse.app4mc.sca.logging.loggers;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.impl.ITextLogger;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.util.SCALogConstants;
import org.eclipse.app4mc.sca.logging.util.App4mcLogUtil;
import org.eclipse.app4mc.sca.logging.util.LogInformation;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;


/**
 */
public final class TextFileLogger implements ITextLogger {

  private static TextFileLogger instance = null;

  private static Logger logger;

  private FileAppender customAppender;

  private boolean isValidLogFile = false;

  /**
   * Private constructor
   */
  private TextFileLogger() {

  }

  /**
   * Returns the singleton instance of the command line logger
   *
   * @return {@link TextFileLogger}
   */
  public static synchronized TextFileLogger getInstance() {
    if (instance == null) {
      instance = new TextFileLogger();
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void log(final LogInformation logInfo) {
    if (isEnabled(logInfo)) {
      this.isValidLogFile = validate(logInfo);
      configureLogger(logInfo);
      switch (logInfo.getSeverity()) {
        case DEBUG:
          debug(logInfo);
          break;
        case ERROR:
        case FATAL:
          error(logInfo);
          break;
        case INFO:
        case SYSTEM:
        case METRIC:
          info(logInfo);
          break;
        case WARNING:
          warn(logInfo);
          break;
        default:
          break;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void closeLogger() {
    if (logger != null) {
      logger.removeAllAppenders();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    // Nothing right now
  }

  /**
   * Configures the logger with specific property
   */
  private void configureLogger(final LogInformation logInfo) {
    try {
      if (this.isValidLogFile) {
        synchronized (this) {
          logger = Logger.getLogger(logInfo.getLogFilePath());
        }
        if (!logger.getAllAppenders().hasMoreElements()) {
          Logger.getRootLogger().getLoggerRepository().resetConfiguration();
          PatternLayout layout = getLayout(logInfo);
          this.customAppender = new FileAppender(layout, logInfo.getLogFilePath(), true);
          this.customAppender.activateOptions();
          logger.addAppender(this.customAppender);
        }
      }
      else {
        synchronized (this) {
          logger = Logger.getLogger("org.eclipse.app4mc.logging.loggers.TextFileLogger");
        }
        logger.setLevel(Level.OFF);
        URL confURL = Platform.getBundle(SCALogConstants.PLUGIN_ID).getEntry(SCALogConstants.LOGGER_PROPERTIES);
        PropertyConfigurator.configure(FileLocator.toFileURL(confURL).getFile());
      }
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, SCALogConstants.PLUGIN_ID);
    }
  }

  /**
   * Returns the relevant layout for the given file in the log info.
   *
   * @param logInfo {@link LogInformation}
   * @return {@link PatternLayout}
   */
  private PatternLayout getLayout(final LogInformation logInfo) {
    PatternLayout layout = new PatternLayout("%d{ISO8601} - [%-5p] - %c %x - %m%n");
    if (logInfo.getLogFilePath().endsWith(SCALogConstants.CSV_EXTENSION)) {
      layout = new PatternLayout("%m%n");
    }
    return layout;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(final LogInformation logInfo) {
    logger.setLevel(Level.ERROR);
    logger.error(logInfo.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(final LogInformation logInfo) {
    logger.setLevel(Level.DEBUG);
    logger.debug(logInfo.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(final LogInformation logInfo) {
    logger.setLevel(Level.INFO);
    logger.info(logInfo.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(final LogInformation logInfo) {
    logger.setLevel(Level.WARN);
    logger.warn(logInfo.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final LogInformation logInfo) throws App4mcLoggerException {
    if ((logInfo.getLogFilePath() != null) && !logInfo.getLogFilePath().isEmpty() &&
        ((logInfo.getLogFilePath().endsWith(SCALogConstants.LOG_EXTENSION) ||
            logInfo.getLogFilePath().endsWith(SCALogConstants.CSV_EXTENSION)))) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled(final LogInformation logInfo) throws App4mcLoggerException {
    return App4mcLogUtil.isInDebugMode(logInfo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void closeLogger(final String logFile) {
    // TODO Auto-generated method stub

  }
}
