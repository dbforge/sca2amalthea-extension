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

#ifndef OUTPUT_H
#define OUTPUT_H
#include "..\helpers\Helpers.h"
#include "..\data_structure\TraverseASTDataStructure.h"
#include <set>
#include <algorithm>
#include <string>

void addDummyContainerWithNotDefinedFunctions(TraversingData* t);

/*Print the call tree inside a XML file*/
void printCallTreeToXML(std::string directoryToParse, std::string directoryToPrint, TraversingData* t);

/*print the call tree to the console*/
void printCallTree();

/*print the List of all the GlobalVriable of the Program*/
void printGlobalvariableList(std::string directoryToPrint, TraversingData* t);

void printListOfAnalyzedFiles(std::string directoryToPrint, TraversingData* t);

void printCurrentAnalyzedFile(std::string currentAnalyzedFile, TraversingData* t);

void printCFGBlocks(TraversingData* t);

void printCFG(TraversingData* t);

void printCFGPaths(TraversingData* t);

void printTypeDef(TypeDefGeneral* t, std::map<std::string, TypeDefGeneral*>* typeDefsmap);

void printTypeDefToXML(std::ofstream* outputFile, TypeDefGeneral * t, TraversingData* tra);

void outputCompilerArguments(std::list<std::string>* listOfHeaders);

void printCallTreeToXMLAdvanced(std::string directoryToParse, std::string directoryToPrint, TraversingData * t);

void printMemberLabels(TraversingData * t, std::ofstream* outputFile, std::string structName, std::string labelName);

void getLabelList(std::set<std::string>* labelSet, TraversingData* t);


//Returns true if the given variable name is a function parameter of pointer type or a local variable of pointer type referencing a global variable
bool isLocalPointerOrParameter(std::string variableName, TraversingData * traversingData);

#endif /* !OUTPUT_H */
