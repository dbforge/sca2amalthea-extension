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
#pragma once

#include "clang/AST/ASTConsumer.h"
#include "clang/AST/RecursiveASTVisitor.h"
#include "clang/Frontend/CompilerInstance.h"
#include "clang/Frontend/FrontendAction.h"
#include "clang/Tooling/Tooling.h"
#include "clang/Tooling/CommonOptionsParser.h"
#include "llvm/Support/CommandLine.h"
#include "algorithms\TraverseASTClassAction.h"
#include "algorithms\TraverseASTWorker.h"

#if defined WIN32
#define __uncaught_exception std::uncaught_exception
#endif

#include "helpers/Helpers.h"
#include "output\Output.h"
#include <stdio.h>
#include <time.h>
#include <filesystem>
#include <fstream>
#include <sstream>
#include <iostream>
#include<exception>
#include <string>
#include <set>
#include <thread>

class TraverseASTWorker
{

public:
	clang::tooling::CommonOptionsParser* OptionsParser;
	std::vector<std::string>* fileList;
	std::string directoryToParse;
	int workerId;

	TraverseASTWorker(clang::tooling::CommonOptionsParser* OptionsParser, std::vector<std::string>* fileList, std::string directoryToParse, int workerId);

	void executeWorker();

	~TraverseASTWorker();
};

