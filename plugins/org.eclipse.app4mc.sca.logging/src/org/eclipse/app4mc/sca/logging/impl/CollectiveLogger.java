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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.app4mc.sca.logging.exception.App4mcLoggerException;
import org.eclipse.app4mc.sca.logging.util.App4mcLogUtil;
import org.eclipse.app4mc.sca.logging.util.LogInformation;


/**
 * Utility class which collects several loggers if user you wants to have multple or use case specific ones
 */
public class CollectiveLogger implements ILogger {

  /**
   * List of loggers to which the given information has to be logged
   */
  protected final List<ILogger> loggers = new ArrayList<ILogger>();

  /**
   * @param logger - The Logger instance to be added
   */
  public void add(final ILogger logger) {
    this.loggers.add(logger);
  }


  /**
   * @param loggerCollection - The logger instances to be added
   */
  public void addAll(final Collection<? extends ILogger> loggerCollection) {
    this.loggers.addAll(loggerCollection);
  }


  /**
   * @return the number of logger instances
   */
  public int size() {
    return this.loggers.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void log(final LogInformation logInfo) {
    this.loggers.forEach(logger -> {
      logger.log(logInfo);
    });
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void closeLogger() {
    // Now nothing
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled(final LogInformation logInfo) throws App4mcLoggerException {
    return App4mcLogUtil.isInDebugMode(logInfo);
  }

}
