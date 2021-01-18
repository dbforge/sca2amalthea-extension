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
#include "helpers\TraverseASTMainHelpers.h"

#if defined WIN32
#define __uncaught_exception std::uncaught_exception
#endif

using namespace clang;
using namespace clang::tooling;
using namespace std;
using namespace llvm;



void handle2Arguments(int argc, const char **argv){
	try {
		directoryToParse = projectDirectory;

		if (directoryToParse.size() == 0){
			shouldContinue = false;
			cout << "Wrong arguments: type -help to see how to use the command line options." << endl;
		}
		else{
			shouldContinue = true;
		}
	}
	catch (...) {
		shouldContinue = false;
		cout << "Wrong arguments: Allowed combination 1)-pdir=absolute path of  directorytoparse" << endl;
	}
}

void handle3Arguments(int argc, const char **argv){
	try {

		if (!projectDirectory.empty() && !headerDirectory.empty())
		{
			directoryToParse = projectDirectory;
			headerFileDirectory = headerDirectory;
			shouldContinue = true;
		}
		else if (!projectDirectory.empty() && !htextfile.empty())
		{
			directoryToParse = projectDirectory;
			headerTextFile = htextfile;
			shouldContinue = true;
		}
		else if (!cFilesDirectory.empty() && !projectDirectory.empty())
		{
			directoryToParse = cFilesDirectory;
			headerFileDirectory = projectDirectory;
			shouldContinue = true;
		}
		else if (!ctextfile.empty() && !projectDirectory.empty())
		{
			cText = ctextfile;
			headerFileDirectory = projectDirectory;
			shouldContinue = true;
		}
		else if (!projectDirectory.empty() && numberOfThreads != 0)
		{
			directoryToParse = projectDirectory;
			num = numberOfThreads;
			if (num > 0) {
				shouldContinue = true;
			}
			else {
				cout << "Wrong arguments: The number of threads to start should be a positive number." << endl;
			}
		}
		else
		{
			shouldContinue = false;
			cout << "Wrong arguments: Allowed combinations 1)-pdir= -n= ...2)-pdir= -hdir= ...3)-pdir= -hlist= ...4)-clist= -pdir= ...5)-pdir= -hlist=" << endl;
		}





	}
	catch (...) {
		shouldContinue = false;
		cout << "Wrong arguments: Allowed combinations 1)-pdir= -n= ...2)-pdir= -hdir= ...3)-pdir= -hlist= ...4)-clist= -pdir= ...5)-pdir= -hlist=" << endl;
	}
}

void handle4Arguments(int argc, const char **argv){
	try
	{

		if (!cFilesDirectory.empty() && !headerDirectory.empty())
		{
			directoryToParse = cFilesDirectory;
			headerFileDirectory = headerDirectory;
			shouldContinue = true;

		}
		else if (!cFilesDirectory.empty() && !htextfile.empty())
		{
			directoryToParse = cFilesDirectory;
			headerTextFile = htextfile;
			shouldContinue = true;

		}
		else if (!ctextfile.empty() && !headerDirectory.empty())
		{
			cText = ctextfile;
			headerFileDirectory = headerDirectory;
			shouldContinue = true;
		}
		else if (!ctextfile.empty() && !htextfile.empty())
		{
			cText = ctextfile;
			headerTextFile = htextfile;
			shouldContinue = true;

		}
		else if (!cFilesDirectory.empty() && !ctextfile.empty())
		{
			cText = ctextfile;
			headerFileDirectory = projectDirectory;
			shouldContinue = true;
		}
		else if (!headerDirectory.empty() && !htextfile.empty())
		{
			headerTextFile = htextfile;
			directoryToParse = projectDirectory;
			shouldContinue = true;
		}
		else if (!projectDirectory.empty() && !headerDirectory.empty() && numberOfThreads != 0)
		{
			directoryToParse = projectDirectory;
			headerFileDirectory = headerDirectory;
			num = numberOfThreads;
		}
		else if (!projectDirectory.empty() && !htextfile.empty() && numberOfThreads != 0)
		{
			directoryToParse = projectDirectory;
			headerTextFile = htextfile;
			num = numberOfThreads;
		}
		else if (!cFilesDirectory.empty() && !projectDirectory.empty() && numberOfThreads != 0)
		{
			directoryToParse = cFilesDirectory;
			headerFileDirectory = projectDirectory;
			num = numberOfThreads;
		}
		else if (!ctextfile.empty() && !projectDirectory.empty() && numberOfThreads != 0)
		{
			cText = ctextfile;
			headerFileDirectory = projectDirectory;
			num = numberOfThreads;
		}
		else
		{
			shouldContinue = false;
			cout << "Wrong arguments : Allowed combinations 1) -pdir= -cdir = -hdir = ...2) -pdir= -cdir = -hlist =  ... 3) -pdir= -clist = -hdir =  ...4) -pdir= -clist = -hlist = ...5)-pdir= -cdir= -clist= ...6)-pdir= -hdir= -hlist= ...7)-pdir= -hdir= -n= ...8)-pdir= -hlist= -n= ...9)-clist= -pdir= -n=...10)-pdir= -hlist= -n=...." << endl;
		}
		//headerFileirectory = headerDirectory;
		if (numberOfThreads != 0)
		{
			if (num > 0) {
				shouldContinue = true;
			}
			else {
				cout << "Wrong arguments: The number of threads to start should be a positive number." << endl;
			}
		}

	}
	catch (...) {
		shouldContinue = false;
		cout << "Wrong arguments : Allowed combinations 1) -pdir= -cdir = -hdir = ...2) -pdir= -cdir = -hlist =  ... 3) -pdir= -clist = -hdir =  ...4) -pdir= -clist = -hlist = ...5)-pdir= -cdir= -clist= ...6)-pdir= -hdir= -hlist= ...7)-pdir= -hdir= -n= ...8)-pdir= -hlist= -n= ...9)-clist= -pdir= -n=...10)-pdir= -hlist= -n=...." << endl;
	}
}



void handle5Arguments(int argc, const char **argv){
	try
	{
		//cl::ParseCommandLineOptions(argc, argv);
		if (!cFilesDirectory.empty() && !headerDirectory.empty() && numberOfThreads != 0)
		{
			directoryToParse = cFilesDirectory;
			headerFileDirectory = headerDirectory;
			num = numberOfThreads;


		}
		else if (!cFilesDirectory.empty() && !htextfile.empty() && numberOfThreads != 0)
		{
			directoryToParse = cFilesDirectory;
			headerTextFile = htextfile;
			num = numberOfThreads;


		}
		else if (!ctextfile.empty() && !headerDirectory.empty() && numberOfThreads != 0)
		{
			cText = ctextfile;
			headerFileDirectory = headerDirectory;
			num = numberOfThreads;

		}
		else if (!ctextfile.empty() && !htextfile.empty() && numberOfThreads != 0)
		{
			cText = ctextfile;
			headerTextFile = htextfile;
			num = numberOfThreads;


		}
		else if (!cFilesDirectory.empty() && !ctextfile.empty() && numberOfThreads != 0)
		{
			cText = ctextfile;
			headerFileDirectory = projectDirectory;
			num = numberOfThreads;

		}
		else if (!headerDirectory.empty() && !htextfile.empty() && numberOfThreads != 0)
		{
			headerTextFile = htextfile;
			directoryToParse = projectDirectory;
			num = numberOfThreads;

		}
		else
		{
			shouldContinue = false;
			cout << "Wrong arguments : Allowed combinations 1) -pdir= -cdir = -hdir = -n= ...2) -pdir= -cdir = -hlist = -n= ... 3) -pdir= -clist = -hdir =  -n=...4) -pdir= -clist = -hlist = -n=...5)-pdir= -cdir= -clist= -n=...6)-pdir= -hdir= -hlist= -n=...." << endl;
		}

		if (num > 0) {
			shouldContinue = true;
		}
		else {
			cout << "Wrong arguments: The number of threads to start should be a positive number." << endl;
		}


	}
	catch (...) {
		shouldContinue = false;
		cout << "Wrong arguments : Allowed combinations 1) -pdir= -cdir = -hdir = ...2) -pdir= -cdir = -hlist =  ... 3) -pdir= -clist = -hdir =  ...4) -pdir= -clist = -hlist = ...5)-pdir= -cdir= -clist= ...6)-pdir= -hdir= -hlist= ...." << endl;
	}



}

void handleCommandLineArguments(int argc, const char **argv){
	if (!hashDefineTextfile.empty())
	{
		argc--;
	}
	switch (argc)
	{
	case 2:
		handle2Arguments(argc, argv);
		break;
	case 3:
		handle3Arguments(argc, argv);
		break;

	case 4:
		handle4Arguments(argc, argv);
		break;

	case 5:
		handle5Arguments(argc, argv);
		break;
	default:
		cout << "wrong number of arguments, at least 1 and maximum 3 arguments schould be povided" << endl;
	}
}

void createListOfFilesToAnalyse(){
	if (num != 0)
	{
		NumberOfThreads = num;
	}
	if (!cText.empty())
	{

		vector<string> *fileList1 = new vector<string>;
		fileList = new vector<string>;
		struct stat statbuf;
		std::ifstream input(cText);
		for (std::string line; getline(input, line);)
		{
			line = getFileNameWithoutFileAndPackage(line);
			//checking if the path is a directory
			stat(line.c_str(), &statbuf);
			if (statbuf.st_mode & S_IFDIR)
			{
				fileList1 = provideListOfFiles(line);
				fileList->insert(fileList->end(), fileList1->begin(), fileList1->end());
			}
			else
			{
				fileList->push_back(line);
			}
		}
	}
	else if (!cFilesDirectory.empty())
	{

		fileList = provideListOfFiles(directoryToParse);
	}
	else
	{
		fileList = provideListOfFiles(directoryToParse);
	}

	if (fileList && fileList->size() < NumberOfThreads){
		NumberOfThreads = fileList->size();
	}
}

void createListOfFileForEachThread(){
	int workerID = 0;
	for (std::vector<std::string>* v : filesForThreads) {

		TraversingData* t = new TraversingData();
		t->workerId = workerID++;
		allTraversingData.push_back(t);

		for (string str : *v) {
			traversingDatas[str] = t;
		}
	}
}

void createHeaderDirectoryList(const char **argv){

	if (!headerFileDirectory.empty())
	{
		headerList = getHeaderList("", headerFileDirectory);
	}
	else if (!headerTextFile.empty())
	{
		struct stat statbuf;
		headerList1 = new list<string>;

		std::ifstream input(headerTextFile);
		for (std::string line; getline(input, line);)
		{
			line = getFileNameWithoutFileAndPackage(line);

			stat(line.c_str(), &statbuf);
			if (!(statbuf.st_mode & S_IFDIR))
			{
				line = line.substr(0, line.find_last_of("\\"));

			}
			headerList1->push_back("-I" + line);
			headerList1->unique();

		}

		headerList1->push_back("-ferror-limit=0");
		headerList1->push_back("-w");
		headerList = headerList1;

	}
	else
	{
		headerList = getHeaderList("", directoryToParse);

	}

	if (!hashDefineTextfile.empty())
	{
		std::ifstream input(hashDefineTextfile);
		for (std::string line; getline(input, line);)
		{
			line = replaceAll(line, "\\\"", "");
			headerList->push_back(line);

		}
	}


	headerList->push_front("--");
	headerList->push_front(directoryToParse);

	headerList->push_front(string(argv[0]));

	totalNumberOfFiles = fileList->size();
}

void createAndStartJobs(){

	char ** args = getStringArrayFromStringList(headerList);

	//all the header directories + program name + directory to parse + -- symbol for the comming includes
	int val = headerList->size();
	cl::ResetCommandLineParser();
	CommonOptionsParser OptionsParser(val, (const char **)args, MyToolCategory);

	vector<thread> jobs;


	for (unsigned i = 0; i < allTraversingData.size(); i++) {

		TraverseASTWorker t(&OptionsParser, filesForThreads.at(i), directoryToParse, i);

		jobs.push_back(thread(&TraverseASTWorker::executeWorker, t));
	}

	for (unsigned i = 0; i < jobs.size(); i++) {

		jobs.at(i).join();
	}
}

string* getStructMemberType(string memberName, string structTypeName, TraversingData* t2){
	TypeDefGeneral* t = t2->typeDefsmap[structTypeName];
	list<TypeDefGeneral*>* listOfMembers = nullptr;
	string categorie = "";
	string referencedType = "";
	string localMemberName = "";
	static string result[2] = { "STRUCT", "ANONYMOUS" };
	if (t != nullptr){
		listOfMembers = ((TypeDefStructOrUnion*)t)->getMembers();
		if (listOfMembers != nullptr  && listOfMembers->size() > 0){
			set<string> anonymousStructs;
			for (TypeDefGeneral* tMember : *listOfMembers){
				if (tMember->getName().compare(memberName) == 0){
					result[0] = *(tMember->getReferencedType());
					result[1] = enumToString(tMember->getTypeDefType());
					return result;
				}

				int n = localMemberName.find_first_of(".");
				if (n != string::npos){
					anonymousStructs.insert(tMember->getName().substr(0, n));
				}
			}
			if (anonymousStructs.size() > 0){
				for (set<string>::iterator it = anonymousStructs.begin(); it != anonymousStructs.end(); ++it){
					if (it->compare("memberName") == 0){
						return result;
					}
				}
			}
		}
	}
	return nullptr;
}

/*
Update the 
*/
void UpdateGlobalVariableFromLocal(string variableName, string functionName, TraversingData* t, GlobalVariableAccess* gA){
	int endStructVarName = -1;
	int startMemberName = -1;
	GlobalVariable* g = nullptr;
	string referencedType = "UNKNOW_TYPE";
	string category = "UNKNOW_CAT";

	if (variableName.find_first_of(".") != string::npos){
		endStructVarName = variableName.find_first_of(".");
		startMemberName = endStructVarName + 1;
	}
	if (endStructVarName > 0){
		string structVariableName = variableName.substr(0, endStructVarName);
		structVariableName = structVariableName + CONCAT_F + functionName;
		if (t->mapOfAllLocalAndParameter.count(structVariableName) == 1){

			g = t->mapOfAllLocalAndParameter[structVariableName];
			string structTypeName = g->getPointeeType();
			int functionNameStart = variableName.length() - functionName.length() - 1;
			string memberName = variableName.substr(startMemberName, functionNameStart - startMemberName);
			if (variableName.find_last_of(CONCAT_F_IA) != string::npos){
				memberName = memberName.substr(0, memberName.length() - 3);
			}
			string* memberTypeInfo = getStructMemberType(memberName, structTypeName, t);
			if (memberTypeInfo != nullptr){
				referencedType = memberTypeInfo[0];
				category = memberTypeInfo[1];
			}
			
			GlobalVariable* globalVariable = new GlobalVariable(variableName, referencedType, g->getFileName(), g->getLine(), g->getColumn(), category, "", "", "");
			gA->setGlobalVariable(globalVariable);
			t->internalGlobalVariablesMap[variableName] = globalVariable;
			
			if (t->internalGlobalVariablesMap.count(structVariableName) == 0){
				t->internalGlobalVariablesMap[structVariableName] = g;
			}
		}
	}
	else{
		if (t->mapOfAllLocalAndParameter.count(variableName) == 1){
			g = t->mapOfAllLocalAndParameter[variableName];
			gA->setGlobalVariable(g);
			t->internalGlobalVariablesMap[variableName] = g;
		}
	}
}

void addLocalVariableToGlobalVariableMap(TraversingData* t) {
	for (std::map<std::string, GlobalVariable*>::iterator it = t->mapOfAllLocalAndParameter.begin(); it != t->mapOfAllLocalAndParameter.end(); ++it) {
		if (it->second->getCat().compare("POINTER_ON_STRUCT") == 0) {
			t->internalGlobalVariablesMap[it->first] = it->second;
		}
	}
}

void upadateLocalVariableAccessInformation(TraversingData* t){
	for (std::map <std::string, std::list<FunctionAndCallees*>*>::iterator it = t->internalStructuralCallGraphs.begin(); it != t->internalStructuralCallGraphs.end(); ++it){
		std::list<FunctionAndCallees*>* listOfFunctionsAndCallees = it->second;
		for (FunctionAndCallees* f : *listOfFunctionsAndCallees){
			swappLocalVariableWhenPossible(f, t);
			std::list<GlobalVariableAccess*>* listOfGlobalVariableAccesses = f->getGlobalVariableAccesses();
			for (GlobalVariableAccess* gA : *listOfGlobalVariableAccesses){
				GlobalVariable* g = gA->getGlobalVariable();
				if (g && g != nullptr){
					UpdateGlobalVariableFromLocal(g->getName(), f->getFunctionEntry()->getFunctionName(), t, gA);
				}
			}
		}
	}
}

/* Swapp the local variables with the global one whenever possible*/
void swappLocalVariableWhenPossible(FunctionAndCallees* functionAndCallees, TraversingData* t){

	list<SwappingInfo*>* listOfSwappingInfos = functionAndCallees->getListOfSwappingInfos();
	list<GlobalVariableAccess*>* variableAccesses = functionAndCallees->getGlobalVariableAccesses();
	if (variableAccesses && variableAccesses != nullptr && variableAccesses->size() > 0){
		for (GlobalVariableAccess* varAccess : *variableAccesses){
			int varAccessBlockID = varAccess->getBasicBlockID();
			string globalLabelToReplaceWith = "";
			if (listOfSwappingInfos != nullptr && listOfSwappingInfos->size() > 0){
				for (SwappingInfo* swappingInfo : *listOfSwappingInfos){
					if (swappingInfo->getBlockID() == varAccessBlockID && varAccess->getLine() > swappingInfo->getProxyVariable()->getLineNumber()){
						globalLabelToReplaceWith = swappingInfo->getProxyVariable()->getGlobalVariableName();
						break;
					}
				}
			}
			if (globalLabelToReplaceWith.compare("") != 0 && t->internalGlobalVariablesMap.count(globalLabelToReplaceWith) > 0){
				GlobalVariable* globalVar = t->internalGlobalVariablesMap[globalLabelToReplaceWith];
				varAccess->setGlobalVariable(globalVar);
			}
		}
	}
}

/*
Merge All the Data to the first traverse Data
*/
void mergeDataFromDifferentThreadsToFirst(){
	for (int i = 1; i < allTraversingData.size(); i++){

		TraversingData* t1 = allTraversingData.at(i);

		for (std::map<std::string, GlobalVariable*>::iterator it = t1->internalGlobalVariablesMap.begin(); it != t1->internalGlobalVariablesMap.end(); ++it){
			allTraversingData.at(0)->internalGlobalVariablesMap[it->first] = it->second;
		}
		cout << i << " - internalGlobalVariablesMap" << endl;
		for (std::map <std::string, std::list<FunctionAndCallees*>*>::iterator it = t1->internalStructuralCallGraphs.begin(); it != t1->internalStructuralCallGraphs.end(); ++it){
			if (it->first.compare("function_definition_not_found.c") == 0 || it->first.empty()){
				string filename = it->first;
				std::list<FunctionAndCallees*>* l = t1->internalStructuralCallGraphs[filename];

				for (FunctionAndCallees* f : *l){
					allTraversingData.at(0)->internalStructuralCallGraphs[filename]->push_back(f);
				}
			}
			else{
				allTraversingData.at(0)->internalStructuralCallGraphs[it->first] = it->second;
			}
		}
		cout << i << " - internalStructuralCallGraphs" << endl;
		for (std::map<std::string, TypeDefGeneral*>::iterator it = t1->typeDefsmap.begin(); it != t1->typeDefsmap.end(); ++it){
			allTraversingData.at(0)->typeDefsmap[it->first] = it->second;
		}
		cout << i << " - typeDefsmap" << endl;
		// consolidate function parameter map
		for (std::map<std::string, GlobalVariable*>::iterator it = t1->mapOfAllLocalAndParameter.begin(); it != t1->mapOfAllLocalAndParameter.end(); ++it){
			allTraversingData.at(0)->mapOfAllLocalAndParameter[it->first] = it->second;
		}
		for (std::map<std::string, std::map<std::string, GlobalVariable*>*>::iterator it = t1->internalFunctionsParametersMap.begin(); it != t1->internalFunctionsParametersMap.end(); ++it){
			if (it->second && it->second != nullptr){
				for (std::map<std::string, GlobalVariable*>::iterator it2 = it->second->begin(); it2 != it->second->end(); ++it2){
					allTraversingData.at(0)->mapOfAllLocalAndParameter[it2->first] = it2->second;
				}
			}
		}
		cout << i << " - local and parameter map" << endl;
	}
	addLocalVariableToGlobalVariableMap(allTraversingData.at(0));
	if (isCFGActivated) {
		upadateLocalVariableAccessInformation(allTraversingData.at(0));
	}
	
}

void printIRInformation(){
	TraversingData* t = allTraversingData.at(0);

	try {
		addDummyContainerWithNotDefinedFunctions(t);

		printCallTreeToXMLAdvanced(replaceAll(projectDirectory, "/", "\\"), ".", t);
	}
	catch (...) {
		cout << "An error happened during the generation of the XML Calltree" << endl;
	}

	try {
		printGlobalvariableList(".", t);

		for (std::map<std::string, GlobalVariable*>::iterator it = allTraversingData.at(0)->mapOfAllLocalAndParameter.begin(); 
			it != allTraversingData.at(0)->mapOfAllLocalAndParameter.end(); ++it){
		}
	}

	catch (...) {
		cout << "An error happened during the generation of the Global variable list" << endl;
	}

}

void printCFGInformation(){
	try {

		if (isCFGActivated){
			TraversingData* t = allTraversingData.at(0);
			printCFG(t);
			printCFGBlocks(t);
			printCFGPaths(t);
		}
	}

	catch (...) {
		cout << "An error happened during the display of CFG related data" << endl;
	}
}

string getFileNameWithoutFileAndPackage(string line){
	int posFile = line.find_first_of(":", 3);
	int posPackage = line.find_last_of(":");
	if (posFile != string::npos && posFile != string::npos){
		string* filename = new string(line.substr(0, posFile));
		string fName = "";
		string pName = "";
		stringstream ss(line.substr(3));
		string tok;
		int i = 0;
		while (getline(ss, tok, ':')) {
			if (i == 1){
				fName = tok;
			}
			else if (i == 2){
				pName = tok;
			}
			i++;
		}
		centrallyStoreFile_PackageInformation(*filename, fName, pName);
		return *filename;
	}
	else{
		return line;
	}
}

void centrallyStoreFile_PackageInformation(std::string filename, std::string fName, std::string packageName){
	pair<string, string> filePackage(fName, packageName);
	filePackageMap[getFileNameWithoutPath(filename)] = filePackage;
}