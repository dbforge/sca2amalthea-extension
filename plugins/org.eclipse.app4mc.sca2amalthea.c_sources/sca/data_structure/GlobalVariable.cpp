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
#include "GlobalVariable.h"

GlobalVariable::GlobalVariable(std::string name, std::string type, std::string* fileName, unsigned line, unsigned col, std::string cat, std::string istypeDef, std::string isConstString, std::string isVolatile){
	this->name = name;
	this->type = type;
	this->fileName = fileName;
	this->line = line;
	this->col = col;
	this->blockID = 0;
	this->cat = cat;
	this->istypeDef = istypeDef;
	this->isConstString = isConstString;
	this->isVolatileString = isVolatile;
	this->proxy = nullptr;
	this->pointeeType = "";
}
std::string GlobalVariable::getName(){
	return name;
}

std::string GlobalVariable::getType(){
	return type;
}
std::string* GlobalVariable::getFileName(){
	return this->fileName;
}
unsigned GlobalVariable::getLine(){
	return line;
}
unsigned GlobalVariable::getColumn(){
	return col;
}

std::string GlobalVariable::getCat(){
	return this->cat;
}
std::string GlobalVariable::getIsConst(){
	return this->isConstString;
}
std::string GlobalVariable::getIsVolatile(){
	return this->isVolatileString;
}
std::string GlobalVariable::getIstypeDef(){
	return this->istypeDef;
}


GlobalVariable* GlobalVariable::getProxy(){
	return this->proxy;
}

unsigned GlobalVariable::getBlockID(){
	return this->blockID;
}

void GlobalVariable::setProxy(GlobalVariable* proxy){
	this->proxy = proxy;
}

std::string GlobalVariable::getPointeeType(){
	return this->pointeeType;
}

void GlobalVariable::setPointeeType(std::string pointeeType){
	this->pointeeType = pointeeType;
}

void GlobalVariable::setBlockID(unsigned blockID){
	this->blockID = blockID;
}


