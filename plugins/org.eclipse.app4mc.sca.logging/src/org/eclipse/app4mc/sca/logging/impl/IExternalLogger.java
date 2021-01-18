package org.eclipse.app4mc.sca.logging.impl;

import java.util.List;

import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.LoggerType;
import org.eclipse.app4mc.sca.logging.util.App4mcLogUtil;
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
 * The interface for the UI specific logging mechanism
 */
public interface IExternalLogger extends ILogger {

  /**
   * Logs the given list of log information
   *
   * @param logInfoList {@link LogInformation}
   */
  public void log(final List<LogInformation> logInfoList);

  /**
   * Mentions the type of the logger
   *
   * @return {@link LoggerType}
   */
  public String getName();

  /**
   * Validates the given log information and returns the state
   *
   * @param logInfo The log information to be validated
   * @return true if the log info contains relevant data to log into the this external logger
   * @throws App4mcLoggerException If the validation fails
   */
  public boolean validate(LogInformation logInfo) throws App4mcLoggerException;

  /**
   * {@inheritDoc}
   */
  @Override
  default boolean isEnabled(final LogInformation logInfo) throws App4mcLoggerException {
    return App4mcLogUtil.isInDebugMode(logInfo);
  }
}
