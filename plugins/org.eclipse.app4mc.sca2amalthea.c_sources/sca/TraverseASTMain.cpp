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
#include "helpers/TraverseASTMainHelpers.h"
#include "data_structure\TraverseASTDataStructure.h"

/*#define WAIT_FOR_CONNECT true
#define OVERRIDE_NEW_DELETE
#include "C:\Program Files\PureDevSoftware\MemPro\MemProLib\src\MemPro.cpp"*/


using namespace std;

std::mutex lock_console;
std::mutex lock_allTraversingDataMap;

std::vector<TraversingData*> allTraversingData;

std::vector<std::vector<std::string>*> filesForThreads;

llvm::cl::OptionCategory MyToolCategory("Traverse AST With Cpp API");

std::map<std::string, TraversingData*> traversingDatas;

std::map<std::string, std::pair<std::string, std::string>> filePackageMap;

/*
std::mutex lock_TypedefsMap;
std::mutex lock_filesMap;
std::mutex lock_typesMap;

std::map<std::string, TypeDefGeneral*> typeDefsmap;
std::map<std::string, std::string*> typesMap;
std::map<std::string, std::string*> filesMap;*/

//llvm::cl::opt<std::string> directory("directorytoparse", llvm::cl::desc("Specify directory to parse"), llvm::cl::value_desc("directory to parse"));

llvm::cl::opt<std::string> cFilesDirectory("cdir", llvm::cl::desc("Specify c files directory"), llvm::cl::value_desc("c files directory"), llvm::cl::cat(MyToolCategory));
llvm::cl::opt<std::string> headerDirectory("hdir", llvm::cl::desc("Specify header file directory"), llvm::cl::value_desc("header file directory"), llvm::cl::cat(MyToolCategory));
llvm::cl::opt<int> numberOfThreads("n", llvm::cl::desc("Number of threads"), llvm::cl::value_desc("No of threads"), llvm::cl::cat(MyToolCategory));
llvm::cl::opt<std::string> ctextfile("clist", llvm::cl::desc("Specify absolute path of text file containing c files"), llvm::cl::value_desc("text file containing list of c files"), llvm::cl::cat(MyToolCategory));
llvm::cl::opt<std::string> htextfile("hlist", llvm::cl::desc("Specify absolute path of text file containing header files"), llvm::cl::value_desc("text file containing list of header files"), llvm::cl::cat(MyToolCategory));
llvm::cl::opt<std::string> projectDirectory("pdir", llvm::cl::desc("Mandatory option ...Specify absolute path of project directory"), llvm::cl::value_desc("Project directory"), llvm::cl::cat(MyToolCategory),llvm::cl::Required);
llvm::cl::opt<std::string> hashDefineTextfile("dlist", llvm::cl::desc("Specify absolute path of the text file containing the #defines"), llvm::cl::value_desc("text file containing list of #defines"), llvm::cl::cat(MyToolCategory));
llvm::cl::list<std::string> IgnoredParams(llvm::cl::Sink);


std::string directoryToParse;
std::string headerFileDirectory;
std::string headerTextFile;
std::string cText;
std::string helpOption;
int num;

int totalNumberOfFiles = 0;
int currentNumberOfFiles = 1;
int NumberOfThreads = 8;
bool shouldContinue = false;

bool isCFGActivated = false;
bool isErrorSearchActivated = false;
bool isStructMemberAccessActivated = true;

std::vector<std::string> *fileList = nullptr;
std::list<std::string>* headerList = nullptr;
std::list<std::string> *headerList1 = nullptr;

int main(int argc, const char **argv) {
	
	string startTime = currentDateTime();
	std::cout << "Start Time()=" << startTime << std::endl;
		llvm::cl::HideUnrelatedOptions(MyToolCategory);
		llvm::cl::ParseCommandLineOptions(argc, argv);
		if (!IgnoredParams.empty()){
			for (llvm::cl::list<string>::iterator it = IgnoredParams.begin(); it != IgnoredParams.end(); it++){

				std::cerr << "option " << *it << " is invalid"<<std::endl;
			}
		
			exit(EXIT_FAILURE);
			
		}
		

		handleCommandLineArguments(argc, argv);

		if (shouldContinue) {
			
			try{
				createListOfFilesToAnalyse();
			}
			catch (...){
				cout << "passed this point 1" << endl;
			}

			try{
				createFilesGroupToAnalyze(fileList);
			}
			catch (...){
				cout << "passed this point 2" << endl;
			}
			
			try{
				createListOfFileForEachThread();
			}
			catch (...){
				cout << "passed this point 3" << endl;
			}

			
			createHeaderDirectoryList(argv);

			outputCompilerArguments(headerList);

			createAndStartJobs();
			cout << "Parsing done " << endl;

			mergeDataFromDifferentThreadsToFirst();
			cout << "Merging done " << endl;
			printIRInformation();

			//printCFGInformation();

		}
		string endTime = currentDateTime();
		std::cout << "End Time()=" << endTime << std::endl;
		cout << "From " << startTime << " to " << endTime << endl;


		return 0;
	
}
