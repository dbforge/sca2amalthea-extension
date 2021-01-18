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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.impl.IConfiguration;
import org.eclipse.app4mc.sca.logging.impl.IConsoleLogger;
import org.eclipse.app4mc.sca.logging.impl.IExternalLogger;
import org.eclipse.app4mc.sca.logging.impl.ILogger;
import org.eclipse.app4mc.sca.logging.impl.ITextLogger;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.LoggerType;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.notification.ILogNotificationListener;
import org.eclipse.app4mc.sca.logging.notification.LogNotificationEvent;
import org.eclipse.app4mc.sca.logging.util.App4mcLogUtil;
import org.eclipse.app4mc.sca.logging.util.LogInformation;
import org.eclipse.app4mc.sca.logging.util.SCALogConstants;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;


/**
 * <p>
 * The class which manages all the logging functionality of the SCA2Amalthea component. The class determines if the
 * product build is a standalone and if so the logging would be handled in house.
 * </p>
 */
public final class Logmanager {

  private static volatile Logmanager instance = null;

  private boolean debugMode;

  private final Map<String, IExternalLogger> externalLoggers = new HashMap<>();

  private final CopyOnWriteArraySet<ILogNotificationListener> notificationListeners =
      new CopyOnWriteArraySet<ILogNotificationListener>();

  private final CopyOnWriteArraySet<IConfiguration> configurations = new CopyOnWriteArraySet<IConfiguration>();

  /**
   * Registration of external UI loggers.
   *
   * @param logger {@link IExternalLogger}
   */
  public void registerAsExternalLogger(final IExternalLogger logger) {
    this.externalLoggers.put(logger.getName(), logger);
  }

  /**
   * Registration of notification listener.
   *
   * @param listener {@link ILogNotificationListener}
   */
  public void registerAsListener(final ILogNotificationListener listener) {
    this.notificationListeners.add(listener);
  }

  /**
   * Registers the given configuration into the list of configurations that the log manager would consider. If there are
   * no configurations registered then the tool would abort if the logging is requested from the locomo tool.
   *
   * @param configuration {@link IConfiguration}
   */
  public void registerAsConfiguration(final IConfiguration configuration) {
    this.configurations.add(configuration);
  }

  /**
   * Private constructor
   */
  private Logmanager() {
    // Private constructor
  }

  /**
   * @param logInfo The log information to be validated
   * @param type The logger type
   * @throws App4mcLoggerException App4mcLoggerException
   */
  private void validateParameters(final LogInformation logInfo, final LoggerType type) throws App4mcLoggerException {
    if (logInfo.getSeverity() == null) {
      throw new App4mcLoggerException(SCALogConstants.SEVERITY_NULL_MSG);
    }
    if ((logInfo.getMessage() == null) || (logInfo.getMessageId() == null)) {
      throw new App4mcLoggerException(SCALogConstants.LOG_MESSAGE_NULL_MSG);
    }
    if (logInfo.getClass() == null) {
      throw new App4mcLoggerException(SCALogConstants.CLASS_INFO_NULL_MSG);
    }
    validateErrorLogParameters(logInfo, type);
    validateProblemsLogParameters(logInfo, type);
  }

  /**
   * Validate parameters for the error log entry
   *
   * @param logInfo {@link LogInformation}
   * @param type {@link LoggerType}
   */
  private void validateErrorLogParameters(final LogInformation logInfo, final LoggerType type) {
    if (type == null) {
      return;
    }
    if ((type.equals(LoggerType.ERROR_LOG)) && ((logInfo.getPluginId() == null) || (logInfo.getException() == null))) {
      throw new App4mcLoggerException(SCALogConstants.ERROR_LOGGER_ERROR_MSG);
    }
  }

  /**
   * Validate parameters for the problems log entry
   *
   * @param logInfo {@link LogInformation}
   * @param type {@link LoggerType}
   */
  private void validateProblemsLogParameters(final LogInformation logInfo, final LoggerType type) {
    if (type == null) {
      return;
    }
    if ((type.equals(LoggerType.PROBLEMS_LOG)) &&
        ((logInfo.getProject() == null) || (logInfo.getPriority() == null) || (logInfo.getLineNumberStart() == null))) {
      throw new App4mcLoggerException(SCALogConstants.PROBLEMS_LOGGER_ERROR_MSG);
    }
  }

  /**
   * The singleton instance of the log manager. This has to be syncronized to make sure that there is no race condition.
   *
   * @return the singleton instance of the sca2amalthea log manager
   */
  public static synchronized Logmanager getInstance() {
    if (instance == null) {
      instance = new Logmanager();
    }
    return instance;
  }

  /**
   * Logs the given log information in a custom text log file created in the given absolute path.
   *
   * @param logInfo The information to be logged
   * @param logFilePath The absolute path where the custom log file would be created
   * @param toCommandLine Flag whether log msg should additionally be logged to command line or not
   * @throws App4mcLoggerException if something goes wrong
   */
  public void logCustomTextLogger(final LogInformation logInfo, final String logFilePath,
      final boolean... toCommandLine) throws App4mcLoggerException {
    logInfo.setLogFilePath(logFilePath);
    if ((logFilePath == null) || (!logFilePath.endsWith(SCALogConstants.LOG_EXTENSION) &&
        !logFilePath.endsWith(SCALogConstants.CSV_EXTENSION))) {
      throw new App4mcLoggerException(SCALogConstants.TXT_LOGGER_ERROR_MSG);
    }
    ILogger logger = LogFactory.getLogger(LoggerType.TEXT_FILE);
    logger.log(logInfo);
    if (logInfo.getSeverity().equals(Severity.FATAL)) {
      notifyListeners(getNotificationEvent(logInfo));
    }
    if ((toCommandLine == null) || (toCommandLine.length != 1) || (toCommandLine[0])) {
      logOnCommandLine(logInfo);
    }
  }

  /**
   * Logs the given log information to the given external logger
   *
   * @param logInformation The information to be logged
   * @param loggerName The name of the logger
   * @throws App4mcLoggerException if something goes wrong
   */
  public void logInExternalLogger(final LogInformation logInformation, final String loggerName)
      throws App4mcLoggerException {
    IExternalLogger externalLogger = this.externalLoggers.get(loggerName);
    if (externalLogger == null) {
      throw new App4mcLoggerException(new NullPointerException("No such exernal logger available"));
    }
    externalLogger.validate(logInformation);
    externalLogger.log(logInformation);

    defaultSystemLogging(logInformation);
  }

  /**
   * In all provisions of logging, the system has to make sure that the information is logged into the text file and
   * also logged on to the console for user understanding
   *
   * @param logInfo {@link LogInformation}
   */
  private void defaultSystemLogging(final LogInformation logInfo) throws App4mcLoggerException {
    validateParameters(logInfo, LoggerType.TEXT_FILE);
    logOnCommandLine(logInfo);
    if (logInfo.getLogClass() == null) {
      logInfo.setLogClass(this.getClass());
    }
    // Changing the log file path to the default
    logInfo.setLogFilePath(loadTextLoggerPreferences());
    LogFactory.getLogger(LoggerType.TEXT_FILE).log(logInfo);
  }

 
  /**
   * Returns the current text logger being handled by the log manager.
   *
   * @return {@link ITextLogger}
   */
  public ILogger getTextLogger() {
    ILogger logger = LogFactory.getLogger(LoggerType.TEXT_FILE);
    return logger;
  }


  /**
   * Logs the given logInformtion to the default logger set by the user in the preferences
   *
   * @param logInfo {@link LogInformation}
   * @throws App4mcLoggerException Exception if something goes wrong. This can be handled by the caller
   */
  public void log(final LogInformation logInfo) throws App4mcLoggerException {
    asserLogInfoNotNull(logInfo);
    validateParameters(logInfo, null);
    IEclipsePreferences node = InstanceScope.INSTANCE.getNode("org.eclipse.app4mc.ui");
    String preference = node.get("SCA_LOGGER", LoggerType.TEXT_FILE.toString());
    String[] selectedLoggers = preference.split(";");
    for (String selectedLogger : selectedLoggers) {
      if (selectedLogger == null) {
        throw new App4mcLoggerException(new NullPointerException("Default logger cannot be null"));
      }
     
        if (LoggerType.TEXT_FILE.toString().equals(selectedLogger)) {
          String logFileName = node.get("LOG_FILE_NAME", SCALogConstants.DEFAULT_LOG_FILE);
          String logFilePath = node.get("LOG_FILE_DIRECTORY", SCALogConstants.USER_HOME);
          logInfo.setLogFilePath(logFilePath + File.separator + logFileName);
        }
        ILogger logger = LogFactory.getLogger(LoggerType.valueOf(selectedLogger));
        logger.log(logInfo);
        logOnCommandLine(logInfo);
      
      if (logInfo.getSeverity().equals(Severity.FATAL)) {
        notifyListeners(getNotificationEvent(logInfo));
      }
    }
  }

  /**
   * Loads the text file logger preferences and returns the path
   */
  private String loadTextLoggerPreferences() {
    IEclipsePreferences node = InstanceScope.INSTANCE.getNode("org.eclipse.app4mc.ui");
    String preference = node.get("SCA_LOGGER", LoggerType.TEXT_FILE.toString());
    String[] selectedLoggers = preference.split(";");
    for (String selectedLogger : selectedLoggers) {
      if (selectedLogger == null) {
        throw new App4mcLoggerException(new NullPointerException("Default logger cannot be null"));
      }
      if (LoggerType.TEXT_FILE.toString().equals(selectedLogger)) {
        String logFileName = node.get("LOG_FILE_NAME", SCALogConstants.DEFAULT_LOG_FILE);
        String logFilePath = node.get("LOG_FILE_DIRECTORY", SCALogConstants.USER_HOME);
        return logFilePath + File.separator + logFileName;
      }
    }
    return SCALogConstants.USER_HOME + File.separator + SCALogConstants.DEFAULT_LOG_FILE;
  }

  /**
   * Logs the given logInformation to the list of provided loggers
   *
   * @param logInfo The log information to be logged
   * @param toCommandLine flag to turn off the default logging
   * @param loggerTypes The types of loggers to which the information has to be logged
   * @throws App4mcLoggerException Exception if something goes wrong
   */
  public void log(final LogInformation logInfo, final boolean toCommandLine, final LoggerType... loggerTypes)
      throws App4mcLoggerException {
    asserLogInfoNotNull(logInfo);
    for (LoggerType loggerType : loggerTypes) {
      validateParameters(logInfo, loggerType);
      if (isStandalone()) {
        ILogger logger = LogFactory.getLogger(loggerType);
        logger.log(logInfo);
        if (logInfo.getSeverity().equals(Severity.FATAL)) {
          notifyListeners(getNotificationEvent(logInfo));
        }
      }
      if (toCommandLine) {
        defaultSystemLogging(logInfo);
      }
    }
  }

  /**
   * Logs the corresponding message from the contributed specificaiton file
   *
   * @param messageId the id of the message record that needs to be logged
   * @param loggerType The type of the logger to be used for loggin the given log information
   * @throws App4mcLoggerException If in case the log specification is not found of the given message id
   */
  public void logFromSpecification(final String messageId, final LoggerType loggerType) throws App4mcLoggerException {
    LogInformation logInfo = null;
    for (IConfiguration configuration : this.configurations) {
      logInfo = configuration.getLogMetaData(messageId);
      if (logInfo != null) {
        break;
      }
    }
    if (logInfo == null) {
      throw new App4mcLoggerException("No log specification found for the given message id");
    }
    if (isStandalone()) {
      ILogger logger = LogFactory.getLogger(loggerType);
      logger.log(logInfo);
    }
    // For logging from specification the
    notifyListeners(getNotificationEvent(logInfo));
    defaultSystemLogging(logInfo);
  }

  /**
   * Creates a notification event out of the log information. THe notifiation event would then be sent to all the
   * listeners
   *
   * @param logInfo {@link LogInformation}
   * @return Returns the log notification event
   */
  private LogNotificationEvent getNotificationEvent(final LogInformation logInfo) {
    LogNotificationEvent event = new LogNotificationEvent(logInfo);
    return event;
  }

  /**
   * Validates the log information and throws exception in case of anomalies
   *
   * @param logInfo {@link LogInformation}
   * @throws App4mcLoggerException
   */
  private void asserLogInfoNotNull(final LogInformation logInfo) throws App4mcLoggerException {
    if (logInfo == null) {
      throw new App4mcLoggerException("Error :: LogInformation cannot be null.");
    }
  }

  /**
   * Uses the given logger for logging the message. This method should be used only if the logging has to be carried out
   * using the inhouse logger. If the given logger is not found, the framework would try to use the default logger. If
   * not successfull an exception would be thrown to the caller. The exception should be handled by the caller.
   *
   * @param logInfo The message to be logged
   * @param loggerType the logger to be used for the logging the given message
   * @param toCommandLine the flag to swtich of the default logging to the command line and the default text log file
   * @throws App4mcLoggerException Exception
   */
  public void log(final LogInformation logInfo, final LoggerType loggerType, final boolean... toCommandLine)
      throws App4mcLoggerException {
    asserLogInfoNotNull(logInfo);
    validateParameters(logInfo, loggerType);
    if (isStandalone()) {
      ILogger logger = LogFactory.getLogger(loggerType);
      logger.log(logInfo);
      if (logInfo.getSeverity().equals(Severity.FATAL)) {
        notifyListeners(getNotificationEvent(logInfo));
      }
    }
    if ((toCommandLine == null) || (toCommandLine.length != 1) || (toCommandLine[0])) {
      defaultSystemLogging(logInfo);
    }
  }

  /**
   * Logs the information into the eclipse console of the runtime tool
   *
   * @param logInfo {@link LogInformation} The information to be logged to the eclipse console
   * @throws App4mcLoggerException Exception
   */
  public void logOnEclipseConsole(final LogInformation logInfo) throws App4mcLoggerException {
    validateParameters(logInfo, LoggerType.ECLIPSE_CONSOLE);
    if (!isStandalone()) {
      ILogger eclipseConsoleLogger = LogFactory.getLogger(LoggerType.ECLIPSE_CONSOLE);
      eclipseConsoleLogger.log(logInfo);
    }
    else {
      throw new App4mcLoggerException();
    }
    defaultSystemLogging(logInfo);
  }

  /**
   * Logs the error with the give information
   *
   * @param logClass The class attribute
   * @param pluginId The plugin that the class belongs
   * @param msg the message to be logged
   */
  public void logError(final Class<?> logClass, final String pluginId, final String msg) {
    logInErrorLog(logClass, pluginId, msg, null);
  }

  /**
   * Logs the given details in the eclipse error log view and in the development console. Used for the development phase
   *
   * @param logClass The class which logs the message or exception
   * @param pluginId The plugin id of the caller
   * @param msg The message to be logged. In case of an exception the message from the exception is considered
   * @param exception The exception that has to be logged in the error log view
   * @throws App4mcLoggerException
   */
  private void logInErrorLog(final Class<?> logClass, final String pluginId, final String msg,
      final Exception exception) {
    LogInformation logInfo = App4mcLogUtil.prepareLogInfo(logClass, pluginId, msg, exception, null);
    if (App4mcLogUtil.isEclipseProduct()) {

      IConsoleLogger errorLogger = (IConsoleLogger) LogFactory.getLogger(LoggerType.ERROR_LOG);
      if (errorLogger != null) {
        if ((exception != null)) {
          errorLogger.logException(logInfo);
        }
        else {
          errorLogger.log(logInfo);
        }
      }
    }
    logErrorOnCommandLine(logClass, pluginId, msg, exception, "");
  }


  /**
   * Logs the given exception into the command line or the available developer eclipse console
   *
   * @param logClass The class which logs the message or exception
   * @param pluginId The plugin id of the caller
   * @param msg The message to be logged. In case of an exception the message from the exception is considered
   * @param exception The exception that has to be logged in the cmd prompt or the eclipse console
   * @param messageId The message id for logging
   * @throws App4mcLoggerException If the validation fails
   */
  public void logErrorOnCommandLine(final Class<?> logClass, final String pluginId, final String msg,
      final Exception exception, final String messageId) throws App4mcLoggerException {
    LogInformation logInfo = App4mcLogUtil.prepareLogInfo(logClass, pluginId, msg, exception, messageId);
    validateParameters(logInfo, LoggerType.CMD_LINE);
    IConsoleLogger errorLogger = (IConsoleLogger) LogFactory.getLogger(LoggerType.CMD_LINE);
    if (exception != null) {
      errorLogger.logException(logInfo);
    }
    else {
      errorLogger.log(logInfo);
    }
  }

  /**
   * Logs the given exception into the command line or the available developer eclipse console
   *
   * @param logInfo LogInformation
   * @throws App4mcLoggerException If the validation fails
   */
  public void logOnCommandLine(final LogInformation logInfo) throws App4mcLoggerException {
    validateParameters(logInfo, LoggerType.CMD_LINE);
    IConsoleLogger errorLogger = (IConsoleLogger) LogFactory.getLogger(LoggerType.CMD_LINE);
    errorLogger.log(logInfo);
  }

  /**
   * Logs exception in the eclipse error log view
   *
   * @param logClass The class that logs the exception
   * @param exception @Exception
   * @param pluginId The plugin id
   */
  public void logException(final Class<?> logClass, final Exception exception, final String pluginId) {
    String exceptionMessage = "";
    if (exception.getMessage() != null) {
      exceptionMessage = exception.getMessage();
    }
    logInErrorLog(logClass, pluginId, exceptionMessage, exception);
  }


  /**
   * Creates a log information with some default parameters and returns it. Used when the user supplies only the message
   * to log.
   *
   * @return {@link LogInformation}
   */
  private LogInformation getDefaultLogInfo() {
    LogInformation logInfo = new LogInformation();
    logInfo.setMessageId("DEFAULT_ID");
    logInfo.setSeverity(Severity.INFO);
    return logInfo;
  }

  /**
   * @param message The message to be logged.
   */
  public void log(final String message) {
    LogInformation logInfo = getDefaultLogInfo();
    logInfo.setMessage(message);
    logInfo.setLogClass(this.getClass());
    if (isStandalone()) {
      ILogger logger = LogFactory.getLogger(LoggerType.CMD_LINE);
      logger.log(logInfo);
    }
    defaultSystemLogging(logInfo);
  }

  /**
   * Determines if the product build is a standalone
   *
   * @return true if the product build is a standalone else false
   */
  private boolean isStandalone() {
    if (App4mcLogUtil.isTestMode()) {
      return App4mcLogUtil.isStandalone();
    }
    return true;
  }

  /**
   * Notifies all the listeners registered with the log manager. The listeners can further take appropriate action based
   * on the event information
   *
   * @param event {@link LogNotificationEvent}
   */
  private void notifyListeners(final LogNotificationEvent event) {
    for (ILogNotificationListener listener : this.notificationListeners) {
      listener.receiveNotification(event);
    }
  }


  /**
   * @return the debugMode
   */
  public boolean isDebugMode() {
    return this.debugMode;
  }


  /**
   * @param debugMode the debugMode to set
   */
  public void setDebugMode(final boolean debugMode) {
    this.debugMode = debugMode;
  }

}
