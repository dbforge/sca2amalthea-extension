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
#include "TraversingData.h"

std::vector<std::string>* TraversingData::getUnanalyzedFiles(){
		std::vector<std::string>* v = new std::vector<std::string>();

		for (std::map<std::string, int>::iterator it = analyzedFiles.begin(); it != analyzedFiles.end(); ++it){
			if (it->second == 0){
				v->push_back(it->first);
			}
		}
		return v;
	}