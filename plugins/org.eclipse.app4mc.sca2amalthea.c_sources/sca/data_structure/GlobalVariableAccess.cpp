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
#include "GlobalVariableAccess.h"

GlobalVariableAccess::GlobalVariableAccess(GlobalVariable* globalVariable, std::string accessType, unsigned line, unsigned col, unsigned basicBlockID)
	: CallGraphItem(line, col, basicBlockID, K_GlobalVariableAccess) {
	this->globalVariable = globalVariable;
	this->accessType = accessType;
}

GlobalVariable* GlobalVariableAccess::getGlobalVariable(){
	return this->globalVariable;
}
std::string GlobalVariableAccess::getAccessType(){
	return this->accessType;
}

void GlobalVariableAccess::setGlobalVariable(GlobalVariable* gA){
	this->globalVariable = gA;
}
