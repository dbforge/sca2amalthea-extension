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
#ifndef GLOBALVARIABLE_H
#define GLOBALVARIABLE_H


#include <string>

class GlobalVariable
{
private:
	std::string name;
	std::string type;
	std::string* fileName;
	std::string cat;
	std::string istypeDef;
	std::string isConstString;
	std::string isVolatileString;
	unsigned line;
	unsigned col;
	//for local variable it is important to know their basic block ID
	unsigned blockID;
	GlobalVariable* proxy;
	std::string pointeeType;
public:
	GlobalVariable(std::string name, std::string type, std::string* fileName, unsigned line, unsigned col, std::string cat, std::string istypeDef, std::string isConstString, std::string isVolatile);
	std::string getName();
	std::string getType();
	std::string* getFileName();
	std::string getCat();
	std::string getIstypeDef();
	std::string getIsConst();
	std::string getIsVolatile();
	unsigned getLine();
	unsigned getColumn();
	unsigned getBlockID();
	GlobalVariable* getProxy();
	void setProxy(GlobalVariable* proxy);
	std::string getPointeeType();
	void setPointeeType(std::string pointeeType); 
	void setBlockID(unsigned blockID);

};

#endif /* GLOBALVARIABLE_H */
