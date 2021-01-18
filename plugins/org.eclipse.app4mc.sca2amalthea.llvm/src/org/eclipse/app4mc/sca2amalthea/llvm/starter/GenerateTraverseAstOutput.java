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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;


/**
 * This class runs the sca.exe to generate the SCAIR from the c process
 */
public class GenerateTraverseAstOutput {

  private final LLVMStarterProperties llvmProperties;

  private final List<String> commandLineList;

  /**
   * @param properties {@link LLVMStarterProperties}
   * @throws IOException IOException
   */
  public GenerateTraverseAstOutput(final LLVMStarterProperties properties) throws IOException {
    super();
    this.llvmProperties = properties;
    this.commandLineList = generateCommandLineList(this.llvmProperties);
  }

  /**
   * @throws IOException IOException
   * @throws InterruptedException InterruptedException
   */
  public void runTraverseAst() throws IOException, InterruptedException {
    String[] cmdLineArray = new String[this.commandLineList.size()];
    cmdLineArray = this.commandLineList.toArray(cmdLineArray);
    ProcessExecutor processExecutor = new ProcessExecutor(this.llvmProperties.getGenDirectory(),
        this.llvmProperties.getLogDirecotry() + "/stdOut.log", this.llvmProperties.getLogDirecotry() + "/stdErr.log",
        cmdLineArray);
    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "start New- " + this.commandLineList, this.getClass(),
        Activator.PLUGIN_ID);
    processExecutor.runProgramm();
    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO, "stop", this.getClass(), Activator.PLUGIN_ID);
  }

  /**
   * @param properties {@link LLVMStarterProperties}
   * @return {@link List}
   * @throws IOException IOException
   */
  private final List<String> generateCommandLineList(final LLVMStarterProperties properties) throws IOException {
    ArrayList<String> cmdList = new ArrayList<String>();
    cmdList.add(properties.getTraverseAstFile());

    if (properties.getNumberOfThreads() > 0) {
      cmdList.add("-n=" + properties.getNumberOfThreads());
    }

    if (properties.getProjectPath() != null) {
      cmdList.add("-pdir=" + properties.getProjectPath());
    }
    if (properties.gethFilesPath() != null) {
      cmdList.add("-hdir=" + properties.gethFilesPath());
    }
    if (properties.getcFilesPath() != null) {
      cmdList.add("-cdir=" + properties.getcFilesPath());
    }
    if ((properties.getcFilesList() != null) && !properties.getcFilesList().isEmpty()) {
      File cFilesFile = generateTextFile(properties.getLogDirecotry(), "cfiles.txt", properties.getcFilesList());
      cmdList.add("-clist=" + cFilesFile.getAbsolutePath());
    }
    if ((properties.gethFilesList() != null) && !properties.gethFilesList().isEmpty()) {
      File hFilesFile = generateTextFile(properties.getLogDirecotry(), "hfiles.txt", properties.gethFilesList());
      cmdList.add("-hlist=" + hFilesFile.getAbsolutePath());
    }
    if ((properties.getHashDefineList() != null) && !properties.getHashDefineList().isEmpty()) {
      File hashFile =
          generateTextFile(properties.getLogDirecotry(), "hashDefineFile.txt", properties.getHashDefineList());
      cmdList.add("-dlist=" + hashFile.getAbsolutePath());
    }

    return cmdList;
  }

  private File generateTextFile(final String outPutPath, final String fileName, final List<String> fileList)
      throws IOException {
    String fileNameHFile = outPutPath + File.separator + fileName;
    File outFile = new File(fileNameHFile);
    FileWriter writer = new FileWriter(outFile);
    for (String str : fileList) {
      // create a file object first to have the windows backslashes in the path what clang will need.
      File cfile = new File(str);
      try {
        writer.write(cfile.getPath());
        writer.write(System.getProperty("line.separator"));
      }
      catch (IOException e) {
        writer.close();
        throw e;
      }
    }
    writer.close();
    return outFile;
  }
}
