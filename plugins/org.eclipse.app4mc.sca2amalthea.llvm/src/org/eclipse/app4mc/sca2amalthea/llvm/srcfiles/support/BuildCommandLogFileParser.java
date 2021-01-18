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
package org.eclipse.app4mc.sca2amalthea.llvm.srcfiles.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;


/**
 * This class parses the build_cmd.log file which is typically located in _log/swb/module/build/build_cmds.log One
 * functionality is to get the list of compiled c-files. It could also be used to parse #define options. It is not the
 * best way since the logging format is not standardized. But for an intermediate solution it is a fast way.
 *
 */
public class BuildCommandLogFileParser implements ISourceFileListProvider {

  private final String buildCommandLogfilePath;
  private final String pverPath;

  /**
   * Constructor
   *
   * @param buildCommandLogfilePath path to the build_cmds.log file
   * @param projectPath path to the root directory of the PVER (project)
   */
  public BuildCommandLogFileParser(final String buildCommandLogfilePath, final String projectPath) {
    super();
    this.buildCommandLogfilePath = buildCommandLogfilePath;
    this.pverPath = projectPath;
  }

  /**
   * This method generates the list of c-Files from the build_cmds.log {@inheritDoc}
   */
  @Override
  public List<String> generateSourceFileList() {
    Set<String> cfileList = new LinkedHashSet<String>();

    try {
      BufferedReader inFileBuffer = new BufferedReader(new FileReader(this.buildCommandLogfilePath));
      String line;
      while ((line = inFileBuffer.readLine()) != null) {
        String cFileName = parseCFileName(line);
        if (cFileName != null) {
          // add c-File to the list, in the build_cmds.log the relative path is given, but we need the absolut path
          cfileList.add(this.pverPath + "/" + cFileName);
        }
      }
      inFileBuffer.close();
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      return new ArrayList<String>();
    }
    return new ArrayList<String>(cfileList);
  }

  private String parseCFileName(final String textLine) {
    // # Compiling source file [EnvSw/Startup/src/RBSYS/src/RBSYS_TimerSetup.c] (2/1.921)
    String patternString = ".*Compiling source file\\s\\[(.*c)\\].*";
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(textLine);
    if (matcher.matches()) {
      return matcher.group(1);
    }
    return null;
  }

  /**
   * This method parses special #defines from the build options (e.g. -DSYSTEM_H="system.h")
   *
   * @return a list of -Doptions from the build log
   */
  public List<String> generateHashDefineList() {
    List<String> definesList = new ArrayList<String>();

    String patternString = "\\s+(-D\\w+=\\S+)+";
    Pattern hDefinePattern = Pattern.compile(patternString);
    String firstCompilePatternString = ".*Compiling source file.*";
    Pattern firstCompilePattern = Pattern.compile(firstCompilePatternString);

    try {
      BufferedReader inFileBuffer = new BufferedReader(new FileReader(this.buildCommandLogfilePath));
      String line;
      boolean firstCompileStatementFound = false;
      while ((line = inFileBuffer.readLine()) != null) {
        // Scan until the first compile line is found
        Matcher matcher = firstCompilePattern.matcher(line);
        if (!firstCompileStatementFound && matcher.matches()) {
          firstCompileStatementFound = true;
        }
        if (!firstCompileStatementFound) {
          continue;
        }

        matcher = hDefinePattern.matcher(line);
        while (matcher.find()) {
          definesList.add(matcher.group(1));
        }
        // just take the hash defines from the first occurence in the build_cmd.log
        if (!definesList.isEmpty()) {
          break;
        }
      }
      inFileBuffer.close();
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
    }
    return definesList;
  }


}
