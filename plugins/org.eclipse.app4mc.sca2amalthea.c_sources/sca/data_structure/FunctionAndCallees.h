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
#ifndef FUNCTION_AND_CALLEES_H
#define FUNCTION_AND_CALLEES_H

#include "BasicBlock.h"
#include "CallEntry.h"
#include "GlobalVariableAccess.h"
#include "FunctionEntry.h"
#include "CallGraphItemContainer.h"

#include<list>
#include<map>


/* Data structure to hold the informations concerning a function and it callees*/
class FunctionAndCallees {
private:
	FunctionEntry* functionID;
	std::list<CallEntry*> callees;
	std::list<GlobalVariableAccess*> globalVariableAccesses;
	CallGraphItemContainer *currentContainer;
	std::list<BasicBlock>* basicBlockList;
	std::list<std::list<unsigned int>*>* CFGPaths = nullptr;
	std::list<std::list<unsigned int>*>* CFGCycles = nullptr;
	std::list<GlobalVariable*>* localVariableList = nullptr;
	std::list<SwappingInfo*>* listOfSwappingInfos = nullptr;

public:
	FunctionAndCallees();
	FunctionEntry* getFunctionEntry();
	CallGraphItemContainer* getCurrentContainer();
	std::list<CallEntry*>* getCallees();
	void addCallee(CallEntry *callEntry);
	std::list<GlobalVariableAccess*>* getGlobalVariableAccesses();
	void addGlobalVariableAccess(GlobalVariableAccess *globalVariableAccess);
	void setCurrentContainer(CallGraphItemContainer *callGraphItemContainer);
	void setFunctionID(FunctionEntry* functionID);
	void setBasicBlockList(std::list<BasicBlock>* basicBlockList);
	std::list<BasicBlock>* getbasicBlockList();
	void addCFGPath(std::list<unsigned int>* CFGPath);
	void addCFGCycle(std::list<unsigned int>* CFGCycle);
	std::list<std::list<unsigned int>*>* getCFGCycles();
	std::list<std::list<unsigned int>*>* getCFGPaths();
	void addLocalVariable(GlobalVariable* variable);
	std::list<GlobalVariable*>* getLocalVariableList();
	std::list<SwappingInfo*>* getListOfSwappingInfos();
	void addSwappingInfo(SwappingInfo* swappingInfo);
};
#endif /* FUNCTION_AND_CALLEES_H */
