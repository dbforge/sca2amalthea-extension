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
#include "FunctionEntry.h"

using namespace std;

FunctionEntry::FunctionEntry(std::string* fileName, std::string functionName, unsigned line, unsigned col, std::string returnType, std::vector<FunctionParameter*> parameters){
	this->fileName = fileName;
	this->functionName = functionName;
	this->line = line;
	this->col = col;
	this->returnType = returnType;
	this->parameters = parameters;
}

std::string* FunctionEntry::getFileName(){
	return this->fileName;
}
std::string FunctionEntry::getFunctionName(){
	return this->functionName;
}
unsigned FunctionEntry::getLine(){
	return this->line;
}
unsigned FunctionEntry::getColumn(){
	return this->col;
}
std::string FunctionEntry::getReturnType(){
		return this->returnType;
}

std::vector<FunctionParameter*> FunctionEntry::getParameters(){
	return this->parameters;
}

void FunctionEntry::setLine(unsigned line){
	this->line = line;
}
void FunctionEntry::setColumn(unsigned col){
	this->col = col;
}

void FunctionEntry::setParameters(std::vector<FunctionParameter*> parameters){
	this->parameters = parameters;
}


