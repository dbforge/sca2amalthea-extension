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


//////////////////////// Start TypeDefGeneral ////////////////////////////

TypeDefGeneral::TypeDefGeneral(std::string name, TypeDefType typeDefType, std::string* referencedType){
	this->name = name;
	this->typeDefType = typeDefType;
	this->referencedType = referencedType;
}
std::string TypeDefGeneral::getName(){
	return this->name;
}
TypeDefType TypeDefGeneral::getTypeDefType(){
	return this->typeDefType;
}

std::string* TypeDefGeneral::getReferencedType(){
	return this->referencedType;
}

std::string* TypeDefGeneral::getFileName(){
	return this->fileName;
}
int TypeDefGeneral::getLine(){
	return this->line;
}
int TypeDefGeneral::getCol(){
	return this->col;
}

void TypeDefGeneral::setFileName(std::string* fileName){
	this->fileName = fileName;
}
void TypeDefGeneral::setLine(int line){
	this->line = line;
}
void TypeDefGeneral::setCol(int col){
	this->col = col;
}

void TypeDefGeneral::setName(std::string name){
	this->name = name;
}


//////////////////////// Start TypeDefEnum ////////////////////////////

std::list<std::string>* TypeDefEnum::getElementList(){
	return this->elements;
}
void TypeDefEnum::addElement(string element){
	this->elements->push_back(element);
}

void TypeDefEnum::setElements(std::list<std::string>* elements){
	this->elements = elements;
}

//////////////////////// Start TypeDefSpecialized ////////////////////////////

int TypeDefArray::getSize(){
	return this->size;
}
void TypeDefArray::setSize(int newSize){
	this->size = newSize;
}


//////////////////////// Start TypeDeStructOrUnion ////////////////////////////

std::list<TypeDefGeneral*>* TypeDefStructOrUnion::getMembers(){
	return this->members;
}
void TypeDefStructOrUnion::addMember(TypeDefGeneral* newMember){
	this->members->push_back(newMember);
}

void TypeDefStructOrUnion::setMembers(std::list<TypeDefGeneral*>* newMembers){
	this->members = newMembers;
}

void TypeDefStructOrUnion::printToString(){
	cout << "Struct name: " << this->getName() << endl;
	cout << "Number of members: ";
	if (this->getMembers() != nullptr){
		cout << this->getMembers()->size() << endl;
	}
	else{
		cout << "0" << endl;
	}
}


