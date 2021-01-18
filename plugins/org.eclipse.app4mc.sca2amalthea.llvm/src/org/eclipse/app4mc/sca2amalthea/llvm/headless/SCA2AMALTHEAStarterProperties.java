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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.app4mc.sca2amalthea.llvm.starter.LLVMStarterProperties;

import com.google.common.base.Joiner;


/**
 * These class contains only the data of all options needed for running the SCA2AMALTHEA tool
 */
public class SCA2AMALTHEAStarterProperties {

  private String projectPath;
  private String outPutPath;
  private String binDirectory;
  private String taskListFile;
  private String hDirFilePath;
  private String buildLogFile;
  private String lockinfoFile;
  private boolean isStructMemberEnabled;

  private LLVMStarterProperties llvmStarterProperties = new LLVMStarterProperties();

  /**
   *
   */
  public static final String LOCK_DEFINITION_FILE = "default_bsw_lock_functions.csv";


  public SCA2AMALTHEAStarterProperties() {
    // no -arg constructor
  }

  /**
   * @param traverseAstPath {@link String}
   * @param outPutPath {@link String}
   * @param taskListFile {@link String}
   * @param numberOfThreads {@link Integer}
   * @param projectPath {@link String}
   * @param hDirFilePath {@link String}
   * @param buildLogFile {@link String}
   * @param lockinfoFile {@link String}
   * @param isStructMemberEnabled {@link Boolean}
   */


  public SCA2AMALTHEAStarterProperties(final String traverseAstPath, final String outPutPath, final String taskListFile,
      final int numberOfThreads, final String projectPath, final String hDirFilePath, final String buildLogFile,
      final String lockinfoFile, final boolean isStructMemberEnabled) {

    this.llvmStarterProperties = new LLVMStarterProperties(traverseAstPath, outPutPath, numberOfThreads, projectPath,
        null, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

    this.taskListFile = taskListFile;
    this.outPutPath = outPutPath;
    this.projectPath = projectPath;
    this.hDirFilePath = hDirFilePath;
    this.buildLogFile = buildLogFile;
    this.lockinfoFile = lockinfoFile;
    this.isStructMemberEnabled = isStructMemberEnabled;
  }

  /**
   * @return the llvmStarterProperties
   */
  public LLVMStarterProperties getLlvmStarterProperties() {
    return this.llvmStarterProperties;
  }


  /**
   * @param projectPath the cProjectPath to set
   */
  public void setProjectPath(final String projectPath) {
    this.projectPath = projectPath;
    this.llvmStarterProperties.setProjectPath(projectPath);
  }

  /**
   * @param outPutPath the outPutPath to set
   */
  public void setOutPutPath(final String outPutPath) {
    if ((outPutPath == null) || outPutPath.isEmpty() || new File(outPutPath).isAbsolute()) {
      this.outPutPath = outPutPath;
    }
    else {
      this.outPutPath = this.projectPath + outPutPath;
    }
    this.llvmStarterProperties.setOutPutPath(outPutPath);
  }

  /**
   * @param buildLogFile the build command log file path
   */
  public void setBuildLogFile(final String buildLogFile) {
    if ((buildLogFile == null) || buildLogFile.isEmpty() || new File(buildLogFile).isAbsolute()) {
      this.buildLogFile = buildLogFile;
    }
    else {
      this.buildLogFile = this.projectPath + buildLogFile;
    }
  }


  /**
   * @param cFilesList the cFilesList to set
   */
  public void setcFilesList(final List<String> cFilesList) {
    this.llvmStarterProperties.setcFilesList(cFilesList);
  }


  /**
   * @param hFilesList the hFilesList to set
   */
  public void sethFilesList(final List<String> hFilesList) {
    this.llvmStarterProperties.sethFilesList(hFilesList);
  }

  /**
   * @param hDirFilePath the header directories.
   */
  public void setHDirFilePath(final String hDirFilePath) {
    List<String> hdirList = new ArrayList<>();
    if ((hDirFilePath != null) && !hDirFilePath.isEmpty()) {
      for (String s : hDirFilePath.split(File.pathSeparator)) {
        if (new File(s).isAbsolute()) {
          hdirList.add(s);
        }
        else {
          hdirList.add(this.projectPath + s);
        }
      }
    }
    this.hDirFilePath = Joiner.on(File.pathSeparator).join(hdirList);
  }

  /**
   * @param taskListFile the taskListFile to set
   */
  public void setTaskListFile(final String taskListFile) {
    if ((taskListFile == null) || taskListFile.isEmpty() || new File(taskListFile).isAbsolute()) {
      this.taskListFile = taskListFile;
    }
    else {
      this.taskListFile = this.projectPath + taskListFile;
    }
  }

  /**
   * @param isStructMemberEnabled enable structure members option.
   */
  public void setIsStructMemberEnabled(final boolean isStructMemberEnabled) {
    this.isStructMemberEnabled = isStructMemberEnabled;
  }


  /**
   * @return the list of C files contained in the project or variant
   */
  public List<String> getCFilesList() {
    return this.llvmStarterProperties.getcFilesList();
  }

  /**
   * @return the list of header files contained in the project or variant
   */
  public List<String> getHFilesList() {
    return this.llvmStarterProperties.gethFilesList();
  }

  /**
   * @param hashDefineList the hashDefineList to set
   */
  public void setHashDefineList(final List<String> hashDefineList) {
    this.llvmStarterProperties.setHashDefineList(hashDefineList);
  }

  /**
   * @return the taskListFile
   */
  public String getTaskListFile() {
    return this.taskListFile;
  }


  /**
   * @return the cProjectPath
   */
  public String getProjectPath() {
    return this.projectPath;
  }

  /**
   * @return the traverseAstFile
   */
  public String getTraverseAstFile() {
    return this.llvmStarterProperties.getTraverseAstFile();
  }

  /**
   * @return the outPutPath
   */
  public String getOutPutPath() {
    return this.outPutPath;
  }

  /**
   * @return the hDirFilePath
   */
  public String gethDirFilePath() {
    return this.hDirFilePath;
  }


  /**
   * @return the buildLogFile
   */
  public String getBuildLogFile() {
    return this.buildLogFile;
  }

  /**
   * @return the lockinfoFile
   */
  public String getLockinfoFile() {
    return this.lockinfoFile;
  }


  /**
   * @param lockinfoFile the lockinfoFile to set
   */
  public void setLockinfoFile(final String lockinfoFile) {
    if ((lockinfoFile == null) || lockinfoFile.isEmpty() || new File(lockinfoFile).isAbsolute()) {
      this.lockinfoFile = lockinfoFile;
    }
    else {
      this.lockinfoFile = this.projectPath + lockinfoFile;
    }
  }


  /**
   * @return the isStructDisabled
   */
  public boolean isStructMemberEnabled() {
    return this.isStructMemberEnabled;
  }

  /**
   * @return the binDirecotry
   */
  public String getBinDirectory() {
    return this.binDirectory;
  }


  /**
   * @param binDirecotry the binDirecotry to set
   */
  public void setBinDirectory(final String binDirecotry) {
    this.binDirectory = binDirecotry;
  }


}
