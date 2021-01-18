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
#ifndef TRAVERSING_DATA_H
#define TRAVERSING_DATA_H

#include "FunctionAndCallees.h"
#include "GlobalVariable.h"
//#include "data_structure\TraverseASTDataStructure.h"

#include <vector>
#include<map>
#include<string>

class TypeDefGeneral;

class TraversingData {
public:
	/*Data structure which holds the call graph*/
	std::map <std::string, std::list<FunctionAndCallees*>*> internalStructuralCallGraphs;

	/*Data structure containing a map of all global variables including their types and location*/
	std::map<std::string, GlobalVariable*> internalGlobalVariablesMap;
	//Data structure containing the list of functions found as well as their parameters of pointer type 
	std::map<std::string, std::map<std::string, GlobalVariable*>*> internalFunctionsParametersMap;
	//Data structure containing the list of functions found as well as the local variables inside them referencing global variables
	std::map<std::string, std::map<std::string, GlobalVariable*>*> internalFunctionLocalVariablesMap;
	//map containing all the variables/functions' parameters of pointer type referencing global variables
	std::map<std::string, GlobalVariable*> mapOfAllLocalAndParameter;

	std::string currentlyAnalyzedFile;

	std::map<std::string, int> analyzedFiles; //0-unanalyzed, 1-Analyzed, -1=Analyzed with errors

	int workerId;

	std::vector<std::string>* getUnanalyzedFiles();

	std::map<std::string, TypeDefGeneral*> typeDefsmap;
	std::map<std::string, std::string*> typesMap;
	std::map<std::string, std::string*> filesMap;

};

#endif /* TRAVERSING_DATA_H */
