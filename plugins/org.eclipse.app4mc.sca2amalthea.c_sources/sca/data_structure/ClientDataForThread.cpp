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
#include "ClientDataForThread.h"

void ClientDataForThread::setCurrentFileName(std::string currentFileName){
	this->currentFileName = currentFileName;
}
void ClientDataForThread::setCurrentFunctionName(std::string currentFunctionName){
	this->currentFunctionName = currentFunctionName;
}
std::string ClientDataForThread::getCurrentFileName(){
	return this->currentFileName;
}
std::string ClientDataForThread::getCurrentFunctionName(){
	return this->currentFunctionName;
}

std::list<FunctionAndCallees*>* ClientDataForThread::getListOfFunctionAndCalleesForFile(std::string fileName){
	return &structuralCallGraph.at(fileName);
}

std::list<GlobalVariable*>* ClientDataForThread::getListOfVariablesProFiles(std::string fileName){
	return &globalVariablesMapProFile.at(fileName);
}

std::map<std::string, GlobalVariable*>* ClientDataForThread::getGlobalVariablesMap(){
	return &globalVariablesMap;
}
