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
package org.eclipse.app4mc.sca.util.preferences;

import org.eclipse.app4mc.sca.util.Activator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;

public class DefaultSCAToolsPreferences {

  /**
   * Returns the value from the default preference.ini file for the given key
   *
   * @param key the key for which the value has to be retreived
   * @return returns the value represented for the given key
   */
  public static String getValue(final String key) {
    IPreferencesService service = Platform.getPreferencesService();
    String value = service.getString(Activator.PLUGIN_ID, key, null, null);
    return value;
  }
}
