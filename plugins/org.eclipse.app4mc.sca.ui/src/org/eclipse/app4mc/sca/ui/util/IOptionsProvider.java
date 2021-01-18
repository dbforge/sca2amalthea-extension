/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

import java.util.Properties;

/**
 * This is an interface to handle input/output Properties/Options.
 */
public interface IOptionsProvider {

  /**
   * @return - Contains all properties (UI and Tool properties)
   */
  public Properties getOptions();


}

