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
#include "FunctionAndCallees.h"

using namespace std;

FunctionAndCallees::FunctionAndCallees() {
  this->currentContainer = new CallGraphItemContainer();
}


FunctionEntry *FunctionAndCallees::getFunctionEntry() {
  return functionID;
}

CallGraphItemContainer *FunctionAndCallees::getCurrentContainer() {
  return currentContainer;
}

std::list<CallEntry*> *FunctionAndCallees::getCallees() {
  return &callees;
}

void FunctionAndCallees::addCallee(CallEntry *callEntry) {
  callees.push_back(callEntry);
}

std::list<GlobalVariableAccess*> *FunctionAndCallees::
getGlobalVariableAccesses() {
  return &globalVariableAccesses;
}

void FunctionAndCallees::addGlobalVariableAccess(
  GlobalVariableAccess *globalVariableAccess) {
  globalVariableAccesses.push_back(globalVariableAccess);
}

void FunctionAndCallees::setCurrentContainer(
  CallGraphItemContainer *callGraphItemContainer) {
  this->currentContainer = callGraphItemContainer;
}


void FunctionAndCallees::setFunctionID(FunctionEntry *functionID) {
  this->functionID = functionID;
}

void FunctionAndCallees::setBasicBlockList(
  std::list<BasicBlock> *basicBlockList) {
  this->basicBlockList = basicBlockList;
}

std::list<BasicBlock> *FunctionAndCallees::getbasicBlockList() {
  return this->basicBlockList;
}

void FunctionAndCallees::addCFGPath(list<unsigned int> *CFGPath) {
  if (this->CFGPaths == nullptr) {
    this->CFGPaths = new std::list<std::list<unsigned int>*>();
  }
  this->CFGPaths->push_back(CFGPath);
}

void FunctionAndCallees::addCFGCycle(list<unsigned int> *CFGCycle) {
  if (this->CFGCycles == nullptr) {
    this->CFGCycles = new std::list<std::list<unsigned int>*>();
  }
  bool alreadyInserted = false;

  for (list<list<unsigned int>*>::iterator it = this->CFGCycles->begin();
       it != CFGCycles->end(); ++it) {
    if (CFGCycle->size() > 0 && (*it)->size() == CFGCycle->size()) {
      list<unsigned int>::iterator it3;
      list<unsigned int>::iterator it2;
      bool allElementsAreEqual = true;
      for (it2 = (*it)->begin(), it3 = CFGCycle->begin();
           it2 != (*it)->end(), it3 != CFGCycle->end(); ++it2, ++it3) {
        if (*it2 != *it3) {
          allElementsAreEqual = false;
          break;
        }
      }
      if (allElementsAreEqual) {
        alreadyInserted = true;
        break;
      }
    }
  }
  if (!alreadyInserted) {
    this->CFGCycles->push_back(CFGCycle);
  }
}

std::list<std::list<unsigned int>*> *FunctionAndCallees::getCFGCycles() {
  return this->CFGCycles;
}

std::list<std::list<unsigned int>*> *FunctionAndCallees::getCFGPaths() {
  return this->CFGPaths;
}

void FunctionAndCallees::addLocalVariable(GlobalVariable *variable) {
  if (this->localVariableList == nullptr) {
    this->localVariableList = new list<GlobalVariable*>();
  }
  this->localVariableList->push_back(variable);
}

std::list<GlobalVariable*> *FunctionAndCallees::getLocalVariableList() {
  return this->localVariableList;
}

std::list<SwappingInfo*> *FunctionAndCallees::getListOfSwappingInfos() {
  if (this->listOfSwappingInfos == nullptr) {
    this->listOfSwappingInfos = new list<SwappingInfo*>();
  }
  return this->listOfSwappingInfos;
}

void FunctionAndCallees::addSwappingInfo(SwappingInfo *swappingInfo) {
  if (this->listOfSwappingInfos == nullptr) {
    this->listOfSwappingInfos = new list<SwappingInfo*>();
  }
  this->listOfSwappingInfos->push_back(swappingInfo);
}
