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
package org.eclipse.app4mc.sca.logging.impl;

import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.util.App4mcLogUtil;
import org.eclipse.app4mc.sca.logging.util.LogInformation;


/**
 */
public interface IConsoleLogger extends ILogger {

  /**
   * Logs the exception to the console. This can be command promt or eclipse console
   *
   * @param logInfo {@link LogInformation}
   */
  public void logException(LogInformation logInfo);

  /**
   * Clears the console to which the message has been logged
   */
  public void clear();

  /**
   * {@inheritDoc}
   */
  @Override
  default void closeLogger() {
    // Now nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default boolean isEnabled(final LogInformation logInfo) throws App4mcLoggerException {
    return App4mcLogUtil.isInDebugMode(logInfo);
  }
}
