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
#ifndef TRAVERSE_AST_MAIN_HELPERS_H
#define TRAVERSE_AST_MAIN_HELPERS_H


#include "clang/AST/ASTConsumer.h"
#include "clang/AST/RecursiveASTVisitor.h"
#include "clang/Frontend/CompilerInstance.h"
#include "clang/Frontend/FrontendAction.h"
#include "clang/Tooling/Tooling.h"
#include "clang/Tooling/CommonOptionsParser.h"
#include "llvm/Support/CommandLine.h"
#include <fstream>
#include <sys/types.h>
#include <sys/stat.h>

#include "algorithms\TraverseASTClassAction.h"
#include "algorithms\TraverseASTWorker.h"
#include "Helpers.h"
#include "output\Output.h"
#include <stdio.h>
#include <time.h>
#include <algorithm>
#include <filesystem>
#include <fstream>
#include <sstream>
#include <iostream>
#include<exception>
#include <string>
#include <set>
#include <thread>

extern llvm::cl::OptionCategory MyToolCategory;

extern bool shouldContinue;

extern std::vector<std::string> *fileList;
extern std::list<std::string>* headerList;
extern std::list<std::string> *headerList1;

extern std::string directoryToParse;
extern std::string headerFileDirectory;
extern std::string headerTextFile;
extern std::string cText;
extern int num;
extern llvm::cl::list<std::string> IgnoredParams;

extern llvm::cl::opt<std::string> cFilesDirectory;
extern llvm::cl::opt<std::string> headerDirectory;
extern llvm::cl::opt<int> numberOfThreads;
extern llvm::cl::opt<std::string> ctextfile;
extern llvm::cl::opt<std::string> htextfile;
extern llvm::cl::opt<std::string> projectDirectory;
extern llvm::cl::opt<std::string> hashDefineTextfile;

extern bool isCFGActivated;

void handle2Arguments(int argc, const char **argv);

void handle3Arguments(int argc, const char **argv);

void handle4Arguments(int argc, const char **argv);

void handle5Arguments(int argc, const char **argv);

void handleCommandLineArguments(int argc, const char **argv);

void createListOfFilesToAnalyse();

void createListOfFileForEachThread();

void createHeaderDirectoryList(const char **argv);

void createAndStartJobs();

void mergeDataFromDifferentThreadsToFirst();

void printIRInformation();

void printCFGInformation();

std::string getFileNameWithoutFileAndPackage(std::string line);

void centrallyStoreFile_PackageInformation(std::string filename, std::string fName, std::string packageName);

void swappLocalVariableWhenPossible(FunctionAndCallees* functionAndCallees, TraversingData* t);

void addLocalVariableToGlobalVariableMap(TraversingData* t);

#endif TRAVERSE_AST_MAIN_HELPERS_H