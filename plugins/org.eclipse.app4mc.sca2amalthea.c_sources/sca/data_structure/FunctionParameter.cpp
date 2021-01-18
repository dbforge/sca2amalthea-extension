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
#include "FunctionParameter.h"

using namespace std;

FunctionParameter::FunctionParameter(std::string paramName, std::string paramType, bool isPointer){
	this->paramName = paramName;
	this->paramType = paramType;
	this->isPointerType = isPointer;
}
std::string FunctionParameter::getParamName(){
	return this->paramName;
}
std::string FunctionParameter::getParamType(){
	return this->paramType;
}
bool FunctionParameter::isPointer(){
	return this->isPointerType;
}
void FunctionParameter::setParamName(std::string paramName){
	this->paramName = paramName;
}
void FunctionParameter::setParamType(std::string paramType){
	this->paramType = paramType;
}
void FunctionParameter::setIsPointer(bool isPointer){
	this->isPointerType = isPointer;
}