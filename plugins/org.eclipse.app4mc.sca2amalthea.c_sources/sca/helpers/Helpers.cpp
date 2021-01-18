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
#include "Helpers.h"
#include <experimental/filesystem>
#include <map>
#include <algorithm>
#include <fstream>
#include <iostream>

using namespace std;
using namespace std::experimental::filesystem;

std::string replaceAll(std::string str, const std::string& from, const std::string& to) {
	string result = str;
	try{
		if (result.size() > 0){
			size_t start_pos = 0;
			while ((start_pos = result.find(from, start_pos)) != std::string::npos) {
				result.replace(start_pos, from.length(), to);
				// Handles case where 'to' is a substring of 'from'
				start_pos += to.length(); 
			}
	
		}
	}
	
	catch (...){
		std::cout << "An error happen in replaceAll() function: str parameter: " << result << endl;
	}
	
	return result;
}

std::string getValidLabelAccessName(std::string labelAccessName, std::set<std::string>* labelSet) {
	string result = labelAccessName ;
	try {
		if (!isVariableStatic(labelAccessName)) {
			if (labelSet->count(labelAccessName) == 0) {
				string structName = labelAccessName.substr(0, labelAccessName.find_first_of("."));
				if (labelSet->count(structName) == 1) {
					result = structName;
				}
				else {
					//cout << "-----" << labelAccessName << endl;
					//result = "#" + structName + "#";
					result = structName;
				}
			}
			if (!isStructMemberAccessActivated && result.find_first_of(".") != string::npos) {
				result = result.substr(0, result.find_first_of("."));
			}
		}
	}
	catch (...) {
		cerr << "getValidLabelAccessName error" << endl;
	}

	return result;
}

vector<std::string>* provideListOfFiles(std::string baseDirectory){

	vector<string> *includes = new vector < string >;

	for (recursive_directory_iterator i(baseDirectory), end; i != end; ++i)
		if (!is_directory(i->path())){

			std::string fileName = i->path().filename().string();

			if (fileName.substr(fileName.length() - 2) == ".c" || fileName.substr(fileName.length() - 2) == ".i"){

				string path = i->path().parent_path().string();
				string fullFileName = path + "/" + fileName;
				fullFileName = replaceAll(fullFileName, "\\", "/");
				fullFileName = replaceAll(fullFileName, "/", "\\");

				if (std::find((*includes).begin(), (*includes).end(), fullFileName) == (*includes).end()){

					(*includes).push_back(fullFileName);
				}
			}
		}
	return includes;
}

void createFilesGroupToAnalyze(vector<std::string>* listOfFiles){
	vector<string>* v = new vector<string>();
	int size = listOfFiles->size();
	int modulo = (size / NumberOfThreads);
	for (int i = 0; i < modulo * NumberOfThreads; i++){
		
		v->push_back(listOfFiles->at(i));

		if ((i + 1) % modulo == 0 ){
			
			filesForThreads.push_back(v);
			v = new vector<string>();
		}
	}
	
	v = filesForThreads.at(0);
	for (int i = modulo * NumberOfThreads; i < size; i++){
		v->push_back(listOfFiles->at(i));
	}
}


list<string>* getHeaderFromList(std::string filename){

	std::ifstream infile(filename);
	std::string line = "";

	list<string>* headers = new list<string>();
	while (std::getline(infile, line))
	{
		line = "-I" + line;
		line = replaceAll(line, "\\", " / ");
		headers->push_back(line);
	}
	headers->unique();
	return headers;
}

list<string>* getHeadersDirectoriesFromProject(string directoryToParse) {

	string localDirectoryToParse = directoryToParse;
	list<string> *includes = new list<string>;

	for (recursive_directory_iterator i(localDirectoryToParse), end; i != end; ++i){
		if (!is_directory(i->path())){
			
			std::string fileName = i->path().filename().string();
			
			if (fileName.substr(fileName.length() - 2) == ".h"){
				string path = i->path().parent_path().string();
				
				path = replaceAll(path, "\\", "/");
				
				path = replaceAll(path, "/", "\\");
				
				includes->push_back("-I" + path);
			}
		}
	}
	includes->unique();
	return includes;
}



list<string>* getHeaderList(string providedList, string directoryToParse){

	std::string line = "";

	list<string>* includePathsfromProject = getHeadersDirectoriesFromProject(directoryToParse);

	if (!providedList.empty()){

		list<string>* headersFromFile = getHeaderFromList(providedList);
		for (list<string>::iterator it = headersFromFile->begin(); it != headersFromFile->end(); ++it){
			includePathsfromProject->push_back(*it);
		}
	}
	//continue to parse even if errors are encoutered
	line = "-ferror-limit=0";
	includePathsfromProject->push_back(line);
	includePathsfromProject->push_back("-w");

	return includePathsfromProject;
}


char** getStringArrayFromStringList(list<string>* stringList){

	std::string line = "";
	char** listOfIncludes = new char*[stringList->size()];
	int a = 0;

	for (list<string>::iterator it = stringList->begin(); it != stringList->end(); ++it){
		line = *it;
		
		listOfIncludes[a] = (char *)malloc((line.size() + 1) * sizeof(char));
		strcpy(listOfIncludes[a++], line.c_str());
	}

	return listOfIncludes;
}

// Get current date/time, format is YYYY-MM-DD.HH:mm:ss
const std::string currentDateTime() {
	time_t     now = time(0);
	struct tm  tstruct;
	char       buf[80];
	tstruct = *localtime(&now);
	// Visit http://en.cppreference.com/w/cpp/chrono/c/strftime
	// for more information about date/time format
	strftime(buf, sizeof(buf), "%Y-%m-%d.%X", &tstruct);

	return buf;
}


list<unsigned int>* copyList(list<unsigned int>* listToCopy){
	list<unsigned int>* myList = new list<unsigned int>();
	for (unsigned int i : *listToCopy){
		myList->push_back(i);
	}
	return myList;
}

void findAllPaths(list<unsigned int>* nodes, BasicBlock* newNode, list<BasicBlock>* completeList, FunctionAndCallees* functionAndCallees){

	if (nodes->size() == 0 || (nodes->size() > 0 && std::find(nodes->begin(), nodes->end(), newNode->getID()) == nodes->end())){
		//new node is not already in the list or the first need to be inserted
		nodes->push_back(newNode->getID());
		int i = 0;
		for (unsigned int succ : *newNode->getSuccs()){

			for (BasicBlock bb : *completeList){
				if (bb.getID() == succ){
					i++;
					//cout << i << endl;
					findAllPaths(copyList(nodes), &bb, completeList, functionAndCallees);
				}
			}
		}
		if (newNode->getSuccs()->size() == 0){
			int n = 0;
			list<unsigned int>* myPath = new list<unsigned int>();
			for (unsigned int j : *nodes){
				if (++n < nodes->size()){
					myPath->push_back(j);
				}
				else{
					myPath->push_back(j);
				}
			}
			functionAndCallees->addCFGPath(myPath);
		}
	}
	else if (nodes->size() > 0 && std::find(nodes->begin(), nodes->end(), newNode->getID()) != nodes->end()){

		bool elementFound = false;
		list<unsigned int>* myCycle = new list<unsigned int>();
		for (unsigned int j : *nodes){
			if (j == newNode->getID()){
				elementFound = true;
			}
			if (elementFound){
				myCycle->push_back(j);
			}
		}
		myCycle->push_back(newNode->getID());
		functionAndCallees->addCFGCycle(myCycle);
	}

}
string trim(string& str)
{
	size_t first = str.find_first_of(' ');
	return str.substr(0, first);
}
string extractType(string& str)
{	
	string result = str;
	size_t firstSymbol = -1;
	size_t firstParen = -1;
	firstSymbol = str.find_first_of('*');
	firstParen = str.find_first_of('[');
	bool needExtraction = false;
	if (firstSymbol != -1 || firstParen != -1){
		needExtraction = true;
	}
	if (!needExtraction){
		return str;
	}
	size_t pos = -1;
	if (firstParen > 0){
		if (firstSymbol > 0){
			firstParen > firstSymbol ? pos = firstSymbol : pos = firstParen;
		}
		else{
			pos = firstParen;
		}
	}
	else{//firstParen <= 0
		if (firstSymbol > 0){
			pos = firstSymbol;
		}
	}
	string strWihoutArray = str.substr(0, pos - 1);
	return strWihoutArray;
}

//return true if the given referenced string is a typedef and false if it is a primitive dataType
varType isTypeDef(std::string referencedType, std::map<std::string, TypeDefGeneral*>* typeDefsmap){

	varType result;
	string referencedTypetemp = referencedType;
	referencedTypetemp = extractType(referencedTypetemp);
	//lock_TypedefsMap.lock();
	std::size_t constFound = referencedTypetemp.find("const");
	result.isConst = constFound != std::string::npos;
	bool isConst = result.isConst;
	
	if (isConst){
		do{//remove const keyword
			referencedTypetemp = referencedTypetemp.substr(constFound + 6, referencedTypetemp.length() - constFound - 6);
			constFound = referencedTypetemp.find("const");
			isConst = referencedTypetemp.find("const") != std::string::npos;
		} while (isConst);
	}
	std::size_t volatileFound = referencedTypetemp.find("volatile");
	result.isVolatile = volatileFound != std::string::npos;
	bool isVolatile = result.isVolatile;
	if (isVolatile){
		do{//remove volatile keyword
			referencedTypetemp = referencedTypetemp.substr(volatileFound + 9, referencedTypetemp.length() - constFound - 9);
			volatileFound = referencedTypetemp.find("volatile");
			isVolatile = referencedTypetemp.find("volatile") != std::string::npos;
		} while (isVolatile);
	}
	result.isTypeDef = typeDefsmap->count(referencedTypetemp) == 1;
	return result;
}

std::string enumToString(TypeDefType t){
	switch (t)
	{
	case PRIMITIVE:
		return "PRIMITIVE";
		break;
	case POINTER:
		return "POINTER";
		break;
	case ENUM:
		return "ENUM";
		break;
	case STRUCT:
		return "STRUCT";
		break;
	case UNION:
		return "UNION";
		break;
	case ARRAY:
		return "ARRAY";
		break;
	default:
		break;
	}
}

void concurrentCout(std::string str){
	if (isErrorSearchActivated){
		lock_console.lock();
		cout << str << endl;
		lock_console.unlock();
	}
}

bool isGlobalVariableDeclared(GlobalVariableAccess* g, TraversingData* t){
		std::map<std::string, GlobalVariable*>* internalGlobalVariablesMap = &t->internalGlobalVariablesMap;
		if (g->getGlobalVariable() != nullptr){
			string variableName = g->getGlobalVariable()->getName();
			variableName = g->getGlobalVariable()->getName();
			if (!isVariableStatic(variableName) && variableName.find_first_of(".") != string::npos){
				variableName = variableName.substr(0, variableName.find_first_of("."));
			}
			if (!isVariableStatic(variableName) && variableName.find_first_of("->") != string::npos){
				variableName = variableName.substr(0, variableName.find_first_of("->"));
			}
			if (internalGlobalVariablesMap->count(variableName) < 1){
				return false;
			}
		}
	return true;
}


//Two functions are considered to be same when they have the same name ,same return type and same type of the input parameters.This function removes such duplicate entries. It is called from printCallTreeToXMLAdvanced while writing to XMLCallTree.xml.
//Added for the task 152993.
void removeDuplicates(std::list<FunctionAndCallees*>* functionsList, std::map<std::string, FunctionEntry*>functionAndPrintedStatus){	
	FunctionAndCallees* fc;
	try{
		for (std::list<FunctionAndCallees*>::iterator it = functionsList->begin(); it != functionsList->end();){
			 fc = *it;
			if (functionAndPrintedStatus.count(convertToLowerCase(fc->getFunctionEntry()->getFunctionName())) == 1){
					FunctionEntry* f = functionAndPrintedStatus[convertToLowerCase(fc->getFunctionEntry()->getFunctionName())];
					if ((f->getReturnType().compare(fc->getFunctionEntry()->getReturnType()) == 0) && (getListOfFunctionParameterTypes(f->getParameters()) == getListOfFunctionParameterTypes(fc->getFunctionEntry()->getParameters()))){
						it = functionsList->erase(it);
					}
					else{
						++it;
					}
			}
			else{
				++it;
			}

		}	
	}
	catch (...){
		string* file=fc->getFunctionEntry()->getFileName();
		cerr << "An error happened during remove duplicates: " << fc->getFunctionEntry()->getFunctionName() << " inside  the file: " << *file << endl;
	}
}
std::string getStructName(std::string referencedStructName){
	if (referencedStructName.find("struct ") != string::npos){
		return referencedStructName.substr(7);
	}
	return referencedStructName;
}


bool isVariableStatic(std::string variableName){
		std::size_t pos = variableName.find("APP4MC_CS");
		std::size_t pos1 = variableName.find("APP4MC_FS");
		if (pos != std::string::npos || pos1 != std::string::npos){
			return true;
		}
		else{
			return false;
		}
}

std::string convertToLowerCase(std::string functionName){
	for (unsigned int i = 0; i < functionName.length(); i++){
		functionName.at(i) = tolower(functionName.at(i));
	}
	return functionName;
}

std::string getFileNameWithoutPath(std::string filename) {
	string f = filename.substr(filename.find_last_of("/\\") + 1);
	return f;
}

std::vector<std::string> getListOfFunctionParameterTypes(std::vector<FunctionParameter*>function){
	std::vector<std::string> paramTypes;
	for (std::vector<FunctionParameter*>::iterator it = function.begin(); it != function.end();++it){
		FunctionParameter* fp = *it;
		paramTypes.push_back(fp->getParamType());
	}
	return paramTypes;
}