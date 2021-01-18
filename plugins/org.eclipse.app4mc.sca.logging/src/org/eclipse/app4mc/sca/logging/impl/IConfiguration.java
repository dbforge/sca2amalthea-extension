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

import org.eclipse.app4mc.sca.logging.util.LogInformation;


/**
 * Interface to be implemented by the configurators. Every class registering as a configurator to the log manager should
 * implement this inferface. The configurators should make sure that log manager can get the relevant log information on
 * passing the message id. The information would be returns as {@link LogInformation}
 */
public interface IConfiguration {

  /**
   * Returns the log information corresponding to the given message id
   *
   * @param messageId The id of the log record
   * @return Returns the log information @LogInformation
   */
  public LogInformation getLogMetaData(String messageId);
}
