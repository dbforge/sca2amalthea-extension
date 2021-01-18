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
#include "TraverseASTDataStructure.h"

using namespace std;

CallEntry::CallEntry(FunctionEntry* functionID, unsigned callLine, unsigned callColumn, unsigned basicBlockID)
	: CallGraphItem(callLine, callColumn, basicBlockID, K_CallEntry) {
	this->functionID = functionID;
}
FunctionEntry* CallEntry::getFunctionID(){
	return this->functionID;
}
SwappingInfo::SwappingInfo(int blockID, std::string localVariableName, int lineNumber, std::string globalVariableName){
	this->blockID = blockID;
	this->localVariableName = localVariableName;
	this->proxyVariable = new ProxyVariable(lineNumber, globalVariableName);
}
ProxyVariable* SwappingInfo::getProxyVariable(){
	return this->proxyVariable;
}
std::string SwappingInfo::getLocalVariable(){
	return this->localVariableName;
}
int SwappingInfo::getBlockID(){
	return this->blockID;
}

void SwappingInfo::print(){
	cout << "At line " << this->proxyVariable->getLineNumber() << " in the basic block " << this->blockID << " the variable: " << this->proxyVariable->getGlobalVariableName() << " will replace " << this->localVariableName << endl;
}



ProxyVariable::ProxyVariable(int lineNumber, std::string globalVariableName){
	this->lineNumber = lineNumber;
	this->globalVariableName = globalVariableName;
}
std::string ProxyVariable::getGlobalVariableName(){
	return globalVariableName;
}
int ProxyVariable::getLineNumber(){
	return lineNumber;
}

