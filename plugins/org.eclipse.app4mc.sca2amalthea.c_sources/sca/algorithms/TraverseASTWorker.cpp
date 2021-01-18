
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
#include "algorithms\TraverseASTWorker.h"

using namespace clang;
using namespace std;
using namespace clang::tooling;
using namespace llvm;

TraverseASTWorker::TraverseASTWorker(clang::tooling::CommonOptionsParser* OptionsParser, std::vector<std::string>* fileList, string directoryToParse, int workerId)
{
	this->OptionsParser = OptionsParser;
	this->fileList = fileList;
	this->directoryToParse = directoryToParse;
	this->workerId = workerId;
	lock_allTraversingDataMap.lock();
	if (allTraversingData.at(workerId)->analyzedFiles.size() == 0){
		for (string str : *this->fileList){
			allTraversingData.at(workerId)->analyzedFiles[str] = 0;
		}
	}
	lock_allTraversingDataMap.unlock();
}

void handle_eptr(std::exception_ptr eptr) // passing by value is ok
{
	try {
		if (eptr) {
			std::rethrow_exception(eptr);
		}
	}
	catch (const std::exception& e) {
		lock_console.lock();
		std::cout << "Caught exception \"" << e.what() << "\"\n";
		lock_console.unlock();
	}
	catch (...){

	}
}

void TraverseASTWorker::executeWorker() {

		try {
			if (this->fileList->size() > 0){
				/*for (string str : *this->fileList) {
					std::vector<std::string>* fileListlocal = new std::vector<std::string>();
					fileListlocal->push_back(str);
					ClangTool Tool(this->OptionsParser->getCompilations(), *fileListlocal);

					Tool.run(newFrontendActionFactory<FindNamedClassAction>().get());
					
				}*/
				ClangTool Tool(this->OptionsParser->getCompilations(), *this->fileList);

				Tool.run(newFrontendActionFactory<FindNamedClassAction>().get());
			}
		}
		catch (exception &e) {
			lock_console.lock();
			cout << "1- An error happened during the Parsing of the source files: " << endl;
			cout << e.what() << endl;
			lock_console.unlock();
		}
		catch (...) {
			/*std::exception_ptr eptr = std::current_exception(); // capture
			if (eptr){
				handle_eptr(eptr);
			}
			else{
				lock_console.lock();
				std::cout << "Caught exception null" << endl;
				lock_console.unlock();
			}*/
			lock_console.lock();
			cerr << "Thread " << this->workerId << ": An error happened during the Parsing of the source files: " << allTraversingData.at(this->workerId)->currentlyAnalyzedFile << endl;
			allTraversingData.at(this->workerId)->analyzedFiles[allTraversingData.at(this->workerId)->currentlyAnalyzedFile] = -1;
			
			lock_console.unlock();
		}
	//}
	}

TraverseASTWorker::~TraverseASTWorker()
{
}
