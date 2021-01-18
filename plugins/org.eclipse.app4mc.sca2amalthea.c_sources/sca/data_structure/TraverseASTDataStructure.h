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
#ifndef TRAVERSEASTDATASTRUCTURE_H
#define TRAVERSEASTDATASTRUCTURE_H

extern "C" {
#include "clang-c/Index.h"
}
#include "llvm/Support/CommandLine.h"
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <vector>
#include <filesystem>
#include <algorithm>
#include <fstream>
#include <mutex>

#include "BasicBlock.h"
#include "CallEntry.h"
#include "ClientDataForThread.h"
#include "FunctionAndCallees.h"
#include "FunctionEntry.h"
#include "GlobalVariable.h"
#include "GlobalVariableAccess.h"
#include "TraversingData.h"



#define READ "Read"
#define WRITE "Write"
#define POINTS2 "."
#define CONCAT_F_IA "_IA-" //Indirect Access struct member of a local variable
#define CONCAT_F "-" //normal local variable(not struct member)

enum TypeDefType
{
	PRIMITIVE, POINTER, ENUM, ARRAY, STRUCT, UNION
};

class TypeDefGeneral{
	//Name of the typedef
	std::string name;
	//Type of the Typedef (@TypeDefType enum)
	TypeDefType typeDefType;
	//referenced type within the typedef
	std::string* referencedType;

	int line;
	int col;
	std::string* fileName;

public:
	TypeDefGeneral(std::string name, TypeDefType typeDefType, std::string* referencedType);
	virtual std::string getName();
	virtual TypeDefType getTypeDefType();
	virtual std::string* getReferencedType();

	virtual std::string* getFileName();
	virtual int getLine();
	virtual int getCol();

	virtual void setFileName(std::string* fileName);
	virtual void setLine(int line);
	virtual void setCol(int col);
	virtual void setName(std::string name);
};

class TypeDefEnum : public TypeDefGeneral{
	std::list<std::string>* elements = nullptr;
public:
	TypeDefEnum(std::string name, TypeDefType typeDefType) :TypeDefGeneral(name, typeDefType, 0){}
	virtual std::list<std::string>* getElementList();
	virtual void addElement(std::string element);
	virtual void setElements(std::list<std::string>* elements);
};


class TypeDefArray : public TypeDefGeneral{
	int size = -1;
public:
	TypeDefArray(std::string name, TypeDefType typeDefType, std::string* referencedType) :TypeDefGeneral(name, typeDefType, referencedType){}
	TypeDefArray(std::string name, TypeDefType typeDefType, std::string* referencedType, int size) :TypeDefGeneral(name, typeDefType, referencedType){
		this->size = size;
	}
	virtual int getSize();
	virtual void setSize(int newSize);
};

class TypeDefStructOrUnion : public TypeDefGeneral{
	std::list<TypeDefGeneral*>* members = nullptr;
public:
	TypeDefStructOrUnion(std::string name, TypeDefType typeDefType, std::string* referencedType) :TypeDefGeneral(name, typeDefType, referencedType){}
	virtual std::list<TypeDefGeneral*>* getMembers();
	virtual void addMember(TypeDefGeneral* newMember);
	virtual void setMembers(std::list<TypeDefGeneral*>* newMembers);
	virtual void printToString();
};






extern int totalNumberOfFiles;
extern int NumberOfThreads;
extern int currentNumberOfFiles;

extern std::vector<std::vector<std::string>*> filesForThreads;
extern std::map<std::string, TraversingData*> traversingDatas;
extern std::vector<TraversingData*> allTraversingData;
extern std::map<std::string, std::pair<std::string, std::string>> filePackageMap;

extern std::mutex lock_console;
extern std::mutex lock_allTraversingDataMap;

extern bool isCFGActivated;
extern bool isErrorSearchActivated;
extern bool isStructMemberAccessActivated;


#endif /* TRAVERSEASTDATASTRUCTURE_H */
