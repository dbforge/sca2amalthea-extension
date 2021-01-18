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
package org.eclipse.app4mc.sca2amalthea.llvm.starter;

import java.io.File;
import java.io.IOException;

import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;
import org.eclipse.app4mc.sca2amalthea.utils.UtilityForProcessHandling;


/**
 */
public class ProcessExecutor {

  private final String workingDirectory;
  private final String standardOutFile;
  private final String standardErrFile;
  private final String[] commandLine;

  /**
   * @param workingDirectory {@link String}
   * @param commandLine Command line arguments
   * @param standardOutFile {@link String}
   * @param standardErrFile {@link String}
   */
  public ProcessExecutor(final String workingDirectory, final String standardOutFile, final String standardErrFile,
      final String... commandLine) {
    super();
    this.commandLine = commandLine.clone();
    this.workingDirectory = workingDirectory;
    this.standardOutFile = standardOutFile;
    this.standardErrFile = standardErrFile;
  }

  /**
   * @throws IOException IOException
   * @throws InterruptedException InterruptedException
   */
  public void runProgramm() throws IOException, InterruptedException {

    ProcessBuilder b = new ProcessBuilder();

    b.command(this.commandLine);

    for (String str : this.commandLine) {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, str, this.getClass(), Activator.PLUGIN_ID);
    }

    b.directory(new File(this.workingDirectory));
    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "working directory: " + this.workingDirectory, this.getClass(),
        Activator.PLUGIN_ID);

    // Redirect output to files
    b.redirectOutput(new File(this.standardOutFile));
    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "Output directory: " + this.standardOutFile, this.getClass(),
        Activator.PLUGIN_ID);
    b.redirectError(new File(this.standardErrFile));

    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "Parsing the PVER to generate the intermediate representation",
        this.getClass(), Activator.PLUGIN_ID);
    Process p = b.start();
    UtilityForProcessHandling.setCurrentRunningProcess(p);

    p.waitFor();

    if (UtilityForProcessHandling.isModelGenerationcancelled()) {
      throw new InterruptedException("Programm has been cancelled.");
    }

    if (UtilityForProcessHandling.getCurrentRunningProcess().exitValue() == 0) {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "Intermediate representation generated", this.getClass(),
          Activator.PLUGIN_ID);
    }
    else {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
          "Failed to generate intermediate representation....There was some error while parsing c files....Please check the stdErr.log for details.",
          this.getClass(), Activator.PLUGIN_ID);
    }
  }

}
