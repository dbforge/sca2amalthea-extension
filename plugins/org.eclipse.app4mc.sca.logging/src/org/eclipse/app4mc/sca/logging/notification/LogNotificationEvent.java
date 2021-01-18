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
package org.eclipse.app4mc.sca.logging.notification;

import org.eclipse.app4mc.sca.logging.util.LogInformation;


/**
 * The log notification event object.
 *
 */
public class LogNotificationEvent {

  private String source;

  private String loggerType;

  private LogInformation logInformation;
  /**
   * Constructor
   * 
   * @param logInfo {@link LogInformation}
   */
  public LogNotificationEvent(LogInformation logInfo) {
    this.logInformation = logInfo;
  }
  /**
   * Constructor
   */
  public LogNotificationEvent() {
    //Default
  }
  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }


  /**
   * @param source the source to set
   */
  public void setSource(String source) {
    this.source = source;
  }


  /**
   * @return the loggerType
   */
  public String getLoggerType() {
    return loggerType;
  }


  /**
   * @param loggerType the loggerType to set
   */
  public void setLoggerType(String loggerType) {
    this.loggerType = loggerType;
  }


  /**
   * @return the logInformation
   */
  public LogInformation getLogInformation() {
    return logInformation;
  }


  /**
   * @param logInformation the logInformation to set
   */
  public void setLogInformation(LogInformation logInformation) {
    this.logInformation = logInformation;
  }


}
