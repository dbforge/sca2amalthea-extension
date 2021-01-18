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
#include "BasicBlock.h"


using namespace std;

BasicBlock::BasicBlock(unsigned int id){
	this->id = id;
}

unsigned int BasicBlock::getID(){
	return this->id;
}

std::list<unsigned int>* BasicBlock::getPreds(){
	return &this->preds;
}

std::list<unsigned int>* BasicBlock::getSuccs(){
	return &this->succs;
}
void BasicBlock::addPred(unsigned int pred){
	this->preds.push_back(pred);
}
void BasicBlock::addSucc(unsigned int succ){
	this->succs.push_back(succ);
}