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
#ifndef CLIENT_DATA_FOR_THREAD_H
#define CLIENT_DATA_FOR_THREAD_H

#include "FunctionAndCallees.h"
#include "GlobalVariable.h"
#include <map>
#include <list>
#include <string>


class ClientDataForThread
{
private:
	std::string currentFileName;
	std::string currentFunctionName;
	std::map <std::string, std::list<FunctionAndCallees*>> structuralCallGraph;
	std::map<std::string, GlobalVariable*> globalVariablesMap;
	std::map<std::string, std::list<GlobalVariable*>> globalVariablesMapProFile;
public:
	void setCurrentFileName(std::string currentFileName);
	void setCurrentFunctionName(std::string currentFunctionName);
	std::string getCurrentFileName();
	std::string getCurrentFunctionName();
	std::list<FunctionAndCallees*>* getListOfFunctionAndCalleesForFile(std::string fileName);
	std::list<GlobalVariable*>* getListOfVariablesProFiles(std::string fileName);
	std::map<std::string, GlobalVariable*>* getGlobalVariablesMap();

};

#endif /* CLIENT_DATA_FOR_THREAD_H */
