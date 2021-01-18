/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.llvm.starter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains only the options needed for running the llvm part C-Code executable
 */
public class LLVMStarterProperties {
    /**
     *
     */
    public static final String TRAVERSE_AST_FILE = "sca.exe";

    /**
    *
    */
    public static final String LIB_CLANG_DLL = "libclang.dll";
    private String projectPath;
    private String traverseAstFile;
    private String outPutPath;
    private String genDirectory;
    private String logDirectory;
    private int numberOfThreads;
    private String cFilesPath;
    private String hFilesPath;
    private List<String> cFilesList;
    private List<String> hFilesList;
    private List<String> hashDefineList;

    /**
     * No arg constructor
     */
    public LLVMStarterProperties() {
        // no arg constructor
    }

    /**
     * @param traverseAstPath {@link String}
     * @param outPutPath {@link String}
     * @param numberOfThreads {@link Integer}
     * @param projectPath {@link String}
     * @param cFilePath {@link String}
     * @param hFilePath {@link String}
     * @param cFilesList {@link List}
     * @param hFilesList {@link List}
     * @param hashDefineList {@link List}
     */
    public LLVMStarterProperties(final String traverseAstPath,
        final String outPutPath, final int numberOfThreads,
        final String projectPath, final String cFilePath,
        final String hFilePath, final List<String> cFilesList,
        final List<String> hFilesList, final List<String> hashDefineList) {
        this.traverseAstFile = traverseAstPath;

        if ((this.traverseAstFile != null) && !this.traverseAstFile.isEmpty()) {
            this.traverseAstFile = traverseAstPath + "/" +
                LLVMStarterProperties.TRAVERSE_AST_FILE;
        }

        this.outPutPath = outPutPath;
        this.numberOfThreads = numberOfThreads;
        this.projectPath = projectPath;
        this.cFilesPath = cFilePath;
        this.hFilesPath = hFilePath;
        this.cFilesList = new ArrayList<String>(cFilesList);
        this.hFilesList = new ArrayList<String>(hFilesList);
        this.hashDefineList = new ArrayList<String>(hashDefineList);
    }

    /**
     * @param traverseAstFile the traverseAstFile to set
     */
    public void setTraverseAstFile(final String traverseAstFile) {
        this.traverseAstFile = traverseAstFile;

        if ((this.traverseAstFile != null) && !this.traverseAstFile.isEmpty()) {
            this.traverseAstFile = this.traverseAstFile + "/" +
                LLVMStarterProperties.TRAVERSE_AST_FILE;
        }
    }

    /**
     * @param outPutPath the outPutPath to set
     */
    public void setOutPutPath(final String outPutPath) {
        this.outPutPath = outPutPath;
    }

    /**
     * @param cFilesList the cFilesList to set
     */
    public void setcFilesList(final List<String> cFilesList) {
        this.cFilesList = new ArrayList<String>(cFilesList);
    }

    /**
     * @param hFilesList the hFilesList to set
     */
    public void sethFilesList(final List<String> hFilesList) {
        this.hFilesList = new ArrayList<String>(hFilesList);
    }

    /**
     * @param hashDefineList the hashDefineList to set
     */
    public void setHashDefineList(final List<String> hashDefineList) {
        this.hashDefineList = new ArrayList<String>(hashDefineList);
    }

    /**
     * @param projectPath the projectPath to set
     */
    public void setProjectPath(final String projectPath) {
        this.projectPath = projectPath;
    }

    /**
     * @return LLVMStarterProperties
     */
    public String getProjectPath() {
        return this.projectPath;
    }

    /**
     * @return the cFilesPath
     */
    public String getcFilesPath() {
        return this.cFilesPath;
    }

    /**
     * @return the traverseAstFile
     */
    public String getTraverseAstFile() {
        return this.traverseAstFile;
    }

    /**
     * @return the outPutPath
     */
    public String getOutPutPath() {
        return this.outPutPath;
    }

    /**
     * @return the numberOfThreads
     */
    public int getNumberOfThreads() {
        return this.numberOfThreads;
    }

    /**
     * @return the hFilesPath
     */
    public String gethFilesPath() {
        return this.hFilesPath;
    }

    /**
     * @return the cFilesList
     */
    public List<String> getcFilesList() {
        return this.cFilesList;
    }

    /**
     * @return the hFilesList
     */
    public List<String> gethFilesList() {
        return this.hFilesList;
    }

    /**
     * @return the hashDefineList
     */
    public List<String> getHashDefineList() {
        return this.hashDefineList;
    }

    /**
     * @return the genDirectory
     */
    public String getGenDirectory() {
        return this.genDirectory;
    }

    /**
     * @param genDirectory the genDirectory to set
     */
    public void setGenDirectory(final String genDirectory) {
        this.genDirectory = genDirectory;
    }

    /**
     * @return the logDirecotry
     */
    public String getLogDirecotry() {
        return this.logDirectory;
    }

    /**
     * @param logDirecotry the logDirecotry to set
     */
    public void setLogDirecotry(final String logDirecotry) {
        this.logDirectory = logDirecotry;
    }
}
