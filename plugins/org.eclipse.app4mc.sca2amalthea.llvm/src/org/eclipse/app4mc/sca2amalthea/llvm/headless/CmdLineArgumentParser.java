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

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;
import org.eclipse.app4mc.sca2amalthea.llvm.util.LLVMConstants;

/**
 * Command line parser class. Creates a wrapper out of the arguments passed in the headless command line prompt.
 */
public class CmdLineArgumentParser {


  /**
   * Method that parses the given set of command line arguments. The arguments are then used to create a wrapper
   * property
   *
   * @param args {@link String}
   * @return {@link SCA2AMALTHEAStarterProperties}
   */
  public SCA2AMALTHEAStarterProperties parseCommandLineParameters(final String[] args) {
    CommandLineParser parser = new BasicParser();
    CommandLine cmd = null;

    Options helpOptions = new Options();
    helpOptions.addOption("h", "help", false, "show help");

    Options options = new Options();
    addOptions(options);

    try {
      cmd = parser.parse(helpOptions, args, true);
      if (cmd.hasOption("h")) {
        HelpFormatter helpformatter = new HelpFormatter();
        helpformatter.printHelp("GenerateAmaltheaModelFromLLVM", options);
        return null;
      }

      // now parse main options
      cmd = parser.parse(options, args);
      String cProjectPath = cmd.getOptionValue(LLVMConstants.PDIR);
      String hFilePathList = null;
      if (cmd.hasOption(LLVMConstants.HDIR_LIST)) {
        hFilePathList = cmd.getOptionValue(LLVMConstants.HDIR_LIST);
      }
      String buildLogFilePath = null;
      if (cmd.hasOption(LLVMConstants.BLOG)) {
        buildLogFilePath = cmd.getOptionValue(LLVMConstants.BLOG);
      }
      String astpPath = null;
      if (cmd.hasOption(LLVMConstants.ASTP)) {
        astpPath = cmd.getOptionValue(LLVMConstants.ASTP);
      }
      String outdir = null;
      if (cmd.hasOption(LLVMConstants.OUTDIR)) {
        outdir = cmd.getOptionValue(LLVMConstants.OUTDIR);
      }
      String taskinfo = null;
      if (cmd.hasOption(LLVMConstants.TASKINFO)) {
        taskinfo = cmd.getOptionValue(LLVMConstants.TASKINFO);
      }
      String lockinfo = null;
      if (cmd.hasOption(LLVMConstants.LOCKINFO)) {
        lockinfo = cmd.getOptionValue(LLVMConstants.LOCKINFO);
      }
      
      String enableStructMembers=null;
      if(cmd.hasOption(LLVMConstants.ENABLE_STRUCT_MEMBER)){
    	  enableStructMembers=cmd.getOptionValue(LLVMConstants.ENABLE_STRUCT_MEMBER);
      }
      return new SCA2AMALTHEAStarterProperties(astpPath, outdir, taskinfo, 10, cProjectPath, hFilePathList,
          buildLogFilePath, lockinfo, Boolean.valueOf(enableStructMembers));
    }
    catch (ParseException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      HelpFormatter helpformatter = new HelpFormatter();
      helpformatter.printHelp("GenerateAmaltheaModelFromLLVM", options);
      return null;
    }
  }

  /**
   * @param options {@link Option}
   */
  private void addOptions(final Options options) {
    options.addOption("h", "help", false, "show help");
    Option astOption = new Option(LLVMConstants.ASTP, "ASTParser", true,
        "Path to the LLVM ASTParser executable");
    astOption.setRequired(true);
    options.addOption(astOption);
   
    Option outDirOption = new Option(LLVMConstants.OUTDIR, "output_directory", true, "Path to the output directory");
    outDirOption.setRequired(false);
    options.addOption(outDirOption);

    Option pDirOption = new Option(LLVMConstants.PDIR, "cproject_directory", true,
        "Path of the root directory of the c project (PVER)");
    pDirOption.setRequired(true);
    options.addOption(pDirOption);

    Option hDirOption = new Option(LLVMConstants.HDIR_LIST, "hdir_directories", true,
        "List of paths to directories that contain h source files seperated by the path seperator ;");
    hDirOption.setRequired(false);
    options.addOption(hDirOption);

    Option taskInfoOption =
        new Option(LLVMConstants.TASKINFO, "task_info_file", true, "Path to the task/isr information file");
    taskInfoOption.setRequired(false);
    options.addOption(taskInfoOption);

    Option buildLogOption = new Option(LLVMConstants.BLOG, "build_log_file", true, "Path to the build_cmd.log file");
    buildLogOption.setRequired(false);
    options.addOption(buildLogOption);

    Option hashDefineOption =
        new Option(LLVMConstants.DLIST, "hashDefines.txt", true, "Path to the hashDefines.txt file");
    hashDefineOption.setRequired(false);
    options.addOption(hashDefineOption);

    Option lockinfoOption =
        new Option(LLVMConstants.LOCKINFO, "bsw_lock_functions.csv", true, "Path to the lock_functions.csv file");
    lockinfoOption.setRequired(false);
    options.addOption(lockinfoOption);
    
    Option enableStructMember =
            new Option(LLVMConstants.ENABLE_STRUCT_MEMBER, "enableStructMember", true, "Boolean value for enabling struct members in amalthea file");
    enableStructMember.setRequired(false);
        options.addOption(enableStructMember);
  }

}