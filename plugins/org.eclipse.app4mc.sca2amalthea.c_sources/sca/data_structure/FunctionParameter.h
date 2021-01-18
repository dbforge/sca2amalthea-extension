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
#ifndef FUNCTION_PARAMETER_H
#define FUNCTION_PARAMETER_H

#include<string>
using namespace std;

class FunctionParameter{
private:
	std::string paramName;
	std::string paramType;
	bool isPointerType;
public:
	FunctionParameter(std::string paramName, std::string paramType, bool isPointer);
	std::string getParamName();
	std::string getParamType();
	bool isPointer();
	void setParamName(std::string paramName);
	void setParamType(std::string paramType);
	void setIsPointer(bool isPointer);
};
#endif /* FUNCTION_PARAMETER_H */