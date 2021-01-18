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
package org.eclipse.app4mc.sca2amalthea.exporter.locks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.exporter.Activator;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockFunction.LockType;


/**
 * This class contains a list of lock defintions. These defintions are read from a given csv file.
 */
public class LockDefinition {

  private final File lockDefinitionFile;
  private final List<LockFunction> lockFunctions;

  /**
   * Constructor. The csv file path must be given for later parsing of the lock definitions
   *
   * @param lockDefinitionFilePath {@link File}
   */
  public LockDefinition(final String lockDefinitionFilePath) {
    super();
    this.lockDefinitionFile = new File(lockDefinitionFilePath);
    this.lockFunctions = new ArrayList<LockFunction>();
  }

  /**
   * Parses the csv file that contains the locking funtion defintions
   */
  public void readLockDefinitionFromFile() {
    try {
      BufferedReader inFileBuffer = new BufferedReader(new FileReader(this.lockDefinitionFile));
      String line;
      while ((line = inFileBuffer.readLine()) != null) {
        if (line.startsWith("#")) {
          continue;
        }
        String[] csvLineArray = line.split(",");
        parseCsvLineArray(csvLineArray);
      }
      inFileBuffer.close();
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
    }
  }

  /**
   * @return the lockDefinitionFile
   */
  public File getLockDefinitionFile() {
    return this.lockDefinitionFile;
  }

  /**
   * @return the lockFunctions
   */
  public List<LockFunction> getLockFunctions() {
    return this.lockFunctions;
  }

  private void parseCsvLineArray(final String[] csvLineArray) {
    if (csvLineArray.length > 2) {
      String lockName = csvLineArray[0];
      String getLock = csvLineArray[1];
      String releaseLock = csvLineArray[2];
      String lockTypeStr = LockType.SPIN_LOCK.name();
      if (csvLineArray.length > 3) {
        lockTypeStr = csvLineArray[3];
      }
      this.lockFunctions.add(new LockFunction(lockName, getLock, releaseLock, LockType.valueOf(lockTypeStr)));
    }
  }

}
