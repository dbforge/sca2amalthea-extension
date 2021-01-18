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
package org.eclipse.app4mc.sca.util.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.util.Activator;

public class OptionsFileParser {

  /**
   * Loads the properties
   *
   * @param filePath the absoulute path of the options file
   * @param defaults ??
   * @return Returns the properties
   */
  public Properties load(final String filePath, final Properties defaults) {
    File file = new File(filePath);
    if (!file.exists()) {
      return null;
    }
    try (FileInputStream inputStream = new FileInputStream(file)) {
      Properties properties = new Properties(defaults);
      properties.load(inputStream);
      return properties;
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
    }
    return null;
  }
}
