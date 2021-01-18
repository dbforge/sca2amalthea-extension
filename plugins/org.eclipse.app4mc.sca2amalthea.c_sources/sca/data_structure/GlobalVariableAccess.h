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
#ifndef GLOBAL_VARIABLE_ACCESS_H
#define GLOBAL_VARIABLE_ACCESS_H

#include "CallGraphItem.h"
#include "GlobalVariable.h"
#include <string>

class GlobalVariableAccess : public CallGraphItem
{
private:
	GlobalVariable* globalVariable;
	std::string accessType;
	
public:
	GlobalVariableAccess(GlobalVariable* globalVariable, std::string accessType, unsigned line, unsigned col, unsigned basicBlockID);

	GlobalVariable* getGlobalVariable();
	std::string getAccessType();
	void setGlobalVariable(GlobalVariable* gA);

	static bool classof(const CallGraphItem *S) {
		return S->getKind() == K_GlobalVariableAccess;
	}
};

#endif /* GLOBAL_VARIABLE_ACCESS_H */
