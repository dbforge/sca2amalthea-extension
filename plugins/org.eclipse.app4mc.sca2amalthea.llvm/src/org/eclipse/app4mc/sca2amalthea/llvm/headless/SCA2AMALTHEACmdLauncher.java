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
package org.eclipse.app4mc.sca2amalthea.llvm.headless;

import java.io.IOException;


/**
 * Main method for sca2amalthea exporter. Used while the product is exported as a jar
 */
public final class SCA2AMALTHEACmdLauncher {

  /**
   * Private constructor
   */
  private SCA2AMALTHEACmdLauncher() {
    // Private constructor
  }

  /**
   * Main mthod to start the sca2amalthea exporter
   *
   * @param args {@link String}
   * @throws IOException IOException
   * @throws InterruptedException InterruptedException
   */
  public static void main(final String[] args) throws IOException, InterruptedException {
    SCA2AMALTHEAStarterProperties properties = new CmdLineArgumentParser().parseCommandLineParameters(args);
    if (properties != null) {
      new GenerateAmaltheaModelFromLLVM(properties).run();
    }
  }

}
