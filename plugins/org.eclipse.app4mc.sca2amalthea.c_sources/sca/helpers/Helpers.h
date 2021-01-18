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
#ifndef HELPERS_H
#define HELPERS_H

#include <string>
#include <vector>
#include <list>

#include <iostream>
#include <fstream>
#include <direct.h>
#include "data_structure\TraverseASTDataStructure.h"

typedef struct varType{
	bool isTypeDef = false;
	bool isConst = false;
	bool isVolatile = false;
};


/*function to replace all occurences of a string with another one within a given string*/
std::string replaceAll(std::string str, const std::string& from, const std::string& to);

/*Provide the list of files to be analyzed in the project*/
std::vector<std::string>* provideListOfFiles(std::string baseDirectory);

/*Return the list of headers necessary for the analysis of the project*/
std::list<std::string>* getHeaderList(std::string providedList, std::string directoryToParse);

char** getStringArrayFromStringList(std::list<std::string>* stringList);

// Get current date/time, format is YYYY-MM-DD.HH:mm:ss
const std::string currentDateTime();

void createFilesGroupToAnalyze(std::vector<std::string>* listOfFiles);

void findAllPaths(std::list<unsigned int>* nodes, BasicBlock* newNode, std::list<BasicBlock>* completeList, FunctionAndCallees* functionAndCallees);

std::list<unsigned int>* copyList(std::list<unsigned int>* listToCopy);

//Parse the command line that the user enters and handle the different scenarios
void handleCommandLineArguments(int argc);

/* Give the type of the referenced string given in parameter (ARRAY, STRUCT, UNION, ENUM, POINTER, PRIMITIVE)*/
std::string getType(std::string referencedType, std::map<std::string, TypeDefGeneral*>* typeDefsmap);

//return true if the given referenced string is a typedef and false if it is a primitive dataType
varType isTypeDef(std::string referencedType, std::map<std::string, TypeDefGeneral*>* typeDefsmap);

std::string enumToString(TypeDefType t);

void concurrentCout(std::string str);

std::list<GlobalVariableAccess*>* checkInconsistentGlobalVariableAccess(TraversingData* t);
std::list<CallEntry*>* checkInconsistentCallEntry(TraversingData* t);

bool isGlobalVariableDeclared(GlobalVariableAccess* g, TraversingData* t);
bool isGlobalVariableAFunctionName(TraversingData* t, std::string &filename, std::string name);
void removeDuplicates(std::list<FunctionAndCallees*>* functionsList, std::map<std::string, FunctionEntry*>functionAndPrintedStatus);
std::string getStructName(std::string referencedStructName);

std::string getValidLabelAccessName(std::string labelAccessName, std::set<std::string>* labelSet);

bool isVariableStatic(std::string);

std::string convertToLowerCase(std::string functionName);

std::string getFileNameWithoutPath(std::string filename);

std::vector<std::string> getListOfFunctionParameterTypes(std::vector<FunctionParameter*>function);
#endif /* !HELPERS_H */
