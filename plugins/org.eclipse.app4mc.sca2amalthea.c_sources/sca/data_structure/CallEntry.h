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
#ifndef CALLENTRY_H
#define CALLENTRY_H

#include "CallGraphItem.h"
#include "FunctionEntry.h"

class CallEntry : public CallGraphItem {
private:
	FunctionEntry* functionID;
public:
	CallEntry(FunctionEntry* functionID, unsigned callLine, unsigned callColumn, unsigned basicBlockID);
	FunctionEntry* getFunctionID();
	static bool classof(const CallGraphItem *S) {
		return S->getKind() == K_CallEntry;
	}
};

class ProxyVariable{
	int lineNumber;
	std::string globalVariableName;
public:
	ProxyVariable(int lineNumber, std::string globalVariableName);
	std::string getGlobalVariableName();
	int getLineNumber();
};

class SwappingInfo{
	int blockID;
	std::string localVariableName;
	ProxyVariable* proxyVariable;
public:
	SwappingInfo(int blockID, std::string localVariableName, int lineNumber, std::string globalVariableName);
	ProxyVariable* getProxyVariable();
	std::string getLocalVariable();
	int getBlockID();
	void print();
};

#endif /* CALLENTRY_H */
