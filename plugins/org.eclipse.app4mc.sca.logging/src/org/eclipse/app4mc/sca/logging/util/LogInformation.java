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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.app4mc.sca.logging.manager.LogFactory.Priority;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;


/**
 * Container class that holds the information regarding one single record of log from the specification file
 */
public class LogInformation {

  private Severity severity;

  private Priority priority;

  private IProject resource;

  private String lineNumberStart;

  private String lineNumberEnd;

  private boolean abort;

  private String messageId;

  private String message;

  private Class<?> logClass;

  private Exception exception;

  private String pluginId;

  private String logFilePath;

  private String toolVersion;

  private String helpURL;

  private String incidentURL;

  private String toolId = SCALogConstants.TOOL_ID;


  /**
   * @return the resource
   */
  public IProject getProject() {
    return this.resource;
  }

  /**
   * @param resource the resource to set
   */
  public void setProject(final IProject resource) {
    this.resource = resource;
  }

  /**
   * @return the className
   */
  public Class<?> getLogClass() {
    return this.logClass;
  }

  private final Map<String, String> customProperties = new HashMap<String, String>();

  private static final String ASSERTION_MESSAGE = "This field cannot be null";

  /**
   * Default constructor
   */
  public LogInformation() {
    // Default constructor
  }

  /**
   * Constructor
   *
   * @param message {@link String} The message to be logged
   */
  public LogInformation(final String message) {
    this.message = message;
  }

  /**
   * Constructor
   *
   * @param messageid {@link String} The message id
   * @param severity {@link Severity} Severity of the message to be logged
   * @param message {@link String} The message to be logged
   * @param logClass The class information of the logger
   */
  public LogInformation(final String messageid, final Severity severity, final String message,
      final Class<?> logClass) {
    Assert.isNotNull(messageid, LogInformation.ASSERTION_MESSAGE);
    Assert.isNotNull(message, LogInformation.ASSERTION_MESSAGE);
    Assert.isNotNull(severity, LogInformation.ASSERTION_MESSAGE);
    Assert.isNotNull(logClass, LogInformation.ASSERTION_MESSAGE);
    this.messageId = messageid;
    this.severity = severity;
    this.message = message;
    this.logClass = logClass;
  }

  /**
   * @return the severity
   */
  public Severity getSeverity() {
    return this.severity;
  }


  /**
   * @param severity the severity to set
   */
  public void setSeverity(final Severity severity) {
    this.severity = severity;
  }


  /**
   * @return the abort
   */
  public boolean isAbort() {
    return this.abort;
  }


  /**
   * @param abort the abort to set
   */
  public void setAbort(final boolean abort) {
    this.abort = abort;
  }


  /**
   * @return the messageId
   */
  public String getMessageId() {
    return this.messageId;
  }


  /**
   * @param messageId the messageId to set
   */
  public void setMessageId(final String messageId) {
    this.messageId = messageId;
  }


  /**
   * @return the message
   */
  public String getMessage() {
    return this.message;
  }


  /**
   * @param message the message to set
   */
  public void setMessage(final String message) {
    this.message = message;
  }


  /**
   * @return the customProperties
   */
  public Map<String, String> getCustomProperties() {
    return this.customProperties;
  }


  /**
   * @return the exception
   */
  public Exception getException() {
    return this.exception;
  }


  /**
   * @param exception the exception to set
   */
  public void setException(final Exception exception) {
    this.exception = exception;
  }


  /**
   * @return the priority
   */
  public Priority getPriority() {
    return this.priority;
  }


  /**
   * @param priority the priority to set
   */
  public void setPriority(final Priority priority) {
    this.priority = priority;
  }

  /**
   * @return the pluginId
   */
  public String getPluginId() {
    return this.pluginId;
  }


  /**
   * @param pluginId the pluginId to set
   */
  public void setPluginId(final String pluginId) {
    this.pluginId = pluginId;
  }


  /**
   * @param logClass the logClass to set
   */
  public void setLogClass(final Class<?> logClass) {
    this.logClass = logClass;
  }


  /**
   * @return the logFilePath
   */
  public String getLogFilePath() {
    return this.logFilePath;
  }


  /**
   * @param logFilePath the logFilePath to set
   */
  public void setLogFilePath(final String logFilePath) {
    this.logFilePath = logFilePath;
  }


  /**
   * @return the toolVersion
   */
  public String getToolVersion() {
    return this.toolVersion;
  }


  /**
   * @param toolVersion the toolVersion to set
   */
  public void setToolVersion(final String toolVersion) {
    this.toolVersion = toolVersion;
  }


  /**
   * @return the helpURL
   */
  public String getHelpURL() {
    return this.helpURL;
  }


  /**
   * @param helpURL the helpURL to set
   */
  public void setHelpURL(final String helpURL) {
    this.helpURL = helpURL;
  }


  /**
   * @return the incidentURL
   */
  public String getIncidentURL() {
    return this.incidentURL;
  }


  /**
   * @param incidentURL the incidentURL to set
   */
  public void setIncidentURL(final String incidentURL) {
    this.incidentURL = incidentURL;
  }


  /**
   * @return the lineNumberStart
   */
  public String getLineNumberStart() {
    return this.lineNumberStart;
  }


  /**
   * @param lineNumberStart the lineNumberStart to set
   */
  public void setLineNumberStart(final String lineNumberStart) {
    this.lineNumberStart = lineNumberStart;
  }


  /**
   * @return the lineNumberEnd
   */
  public String getLineNumberEnd() {
    return this.lineNumberEnd;
  }


  /**
   * @param lineNumberEnd the lineNumberEnd to set
   */
  public void setLineNumberEnd(final String lineNumberEnd) {
    this.lineNumberEnd = lineNumberEnd;
  }


  /**
   * @return the toolId
   */
  public String getToolId() {
    return this.toolId;
  }


  /**
   * @param toolId the toolId to set
   */
  public void setToolId(final String toolId) {
    this.toolId = toolId;
  }
}
