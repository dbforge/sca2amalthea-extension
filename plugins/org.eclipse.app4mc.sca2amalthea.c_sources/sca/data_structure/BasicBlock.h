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
#ifndef BASICBLOCK_H
#define BASICBLOCK_H

#include <list>

class BasicBlock{
private:
	unsigned int id;
	std::list<unsigned int> preds;
	std::list<unsigned int> succs;

public:
	BasicBlock(unsigned int id);
	unsigned int getID();
	std::list<unsigned int>* getPreds();
	std::list<unsigned int>* getSuccs();
	void addPred(unsigned int pred);
	void addSucc(unsigned int succ);
};

#endif /* BASICBLOCK_H */
