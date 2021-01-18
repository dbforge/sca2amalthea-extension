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
#ifndef FUNCTION_ENTRY_H
#define FUNCTION_ENTRY_H

#include<string>
#include <vector>
#include "FunctionParameter.h"
using namespace std;
/* data structure to hold the information concerning a function*/

class FunctionEntry
{
private:
	std::string* fileName;
	std::string functionName;
	unsigned line;
	unsigned col;
	std::string returnType;
	std::vector<FunctionParameter*> parameters;
public:
	FunctionEntry(std::string* fileName, std::string functionName, unsigned line, unsigned col, std::string returnType, std::vector<FunctionParameter*> parameters);
	std::string* getFileName();
	std::string getFunctionName();
	unsigned getLine();
	unsigned getColumn();
	void setLine(unsigned line);
	void setColumn(unsigned col);
	std::string getReturnType();
	std::vector<FunctionParameter*> getParameters();
	void setParameters(std::vector<FunctionParameter*> paramTypes);
};




#endif /* FUNCTION_ENTRY_H */
