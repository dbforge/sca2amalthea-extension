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
#include "Output.h"
#include <data_structure/ModeSwitch.h>


using namespace std;

std::set<std::string> labelSet;

void printCurrentAnalyzedFile(std::string currentAnalyzedFile,
                              TraversingData *t) {

  if (currentAnalyzedFile.size() > 0) {
    lock_console.lock();
    cout << "Analysing file " << to_string(currentNumberOfFiles++) << " von " <<
        totalNumberOfFiles << " in thread " << t->workerId << ": " <<
        currentAnalyzedFile << endl;
    lock_console.unlock();
  }
}

void addDummyContainerWithNotDefinedFunctions(TraversingData *t) {
  set<string> calledFunctions;
  set<string> definedFunctions;
  set<string> undefinedFunctions;

  for (map<std::string, std::list<FunctionAndCallees*>*>::iterator iterator = t
                                                                              ->
                                                                              internalStructuralCallGraphs
                                                                              .begin()
       ; iterator != t->internalStructuralCallGraphs.end(); ++iterator) {
    list<FunctionAndCallees*> *functionsList = iterator->second;
    for (FunctionAndCallees *fc : *functionsList) {
      definedFunctions.insert(fc->getFunctionEntry()->getFunctionName());
      list<CallEntry*> *callees = fc->getCallees();
      if (callees->size() > 0) {
        for (CallEntry *fE : *callees) {
          calledFunctions.insert(fE->getFunctionID()->getFunctionName());
        }
      }
    }
  }
  set_difference(calledFunctions.begin(), calledFunctions.end(),
                 definedFunctions.begin(), definedFunctions.end(),
                 inserter(undefinedFunctions, undefinedFunctions.begin()));

  if (undefinedFunctions.size() > 0) {
    string dummyContainer = "function_definition_not_found.c";
    string *fileName = new string(dummyContainer);
    t->filesMap[dummyContainer] = fileName;
    fileName = t->filesMap[dummyContainer];

    list<FunctionAndCallees*> *listFac = new list<FunctionAndCallees*>();
    t->internalStructuralCallGraphs[*fileName] = listFac;
    for (auto undefFunction : undefinedFunctions) {
      FunctionAndCallees *fac = new FunctionAndCallees();
      std::vector<FunctionParameter*> dummyList;
      fac->setFunctionID(
        new FunctionEntry(fileName, undefFunction, 0, 0, "", dummyList));
      t->internalStructuralCallGraphs[*fileName]->push_back(fac);
    }
  }
}


string getRelativePathFilename(string directoryToParse, std::string fileName) {
  size_t found = fileName.find(directoryToParse);
  if (found == 0) {
    if (directoryToParse.size() < fileName.size()) {
      return "." + fileName.substr(directoryToParse.size(), fileName.size());
    }
  }
  return fileName;
}

string removeAnonymousOrConst(string *str) {
  string result = "";
  if (str && str != nullptr) {
    result = *str;
    if (str->find("(anonymous") != string::npos) {
      result = str->substr(0, str->find("(anonymous") - 1);
    }

    if (str->find("const") == 0) {
      result = str->substr(6);
    }
    if (str->find("volatile") == 0) {
      result = str->substr(9);
    }
    if (str->find_last_of(" ") != string::npos) {
      result = str->substr(str->find_last_of(" ") + 1);
    }
  }
  return result;
}

string removeTrailingPoint(string variableName) {
  if (!isVariableStatic(variableName)) {
    if (variableName.find(".") != string::npos) {
      int n = variableName.find_last_of(".");
      if (n == variableName.length() - 1) {
        return variableName.substr(0, n);
      }
    }

  }
  return variableName;
}

void printIndentation(int indentationLevel, ofstream *outputFile)
{
  for (int i = 0; i < indentationLevel; ++i)
  {
    *outputFile << "  ";
  }
}

void printAccessAdvanced(GlobalVariableAccess *gvE, ofstream *outputFile,
                         ofstream *errorsFile, string &filename, int indentationLevel) {
  try {
    if (gvE && gvE != nullptr && gvE->getGlobalVariable() && gvE->
        getGlobalVariable() != nullptr) {
      string name = removeTrailingPoint(gvE->getGlobalVariable()->getName());
      string variableName = gvE->getGlobalVariable()->getName();
      string type = "Label";
      string cleanedName = getValidLabelAccessName(name, &labelSet);
      if (!isLocalPointerOrParameter(variableName, allTraversingData.at(0)) &&
          isGlobalVariableDeclared(gvE, allTraversingData.at(0))) {
        //string cleanedName = getValidLabelAccessName(name, &labelSet);
        if (cleanedName.compare(name) == 0 || (
              name.find(cleanedName) >= 0 && cleanedName.find("#") == string::
              npos)) {
          printIndentation(indentationLevel, outputFile);
          *outputFile <<
              "<stmtseq xsi:type=\"scair:LabelAccess\" label=\"" <<
              cleanedName;
          //*outputFile << "?type=" << type << "\" Access=\"" << gvE->getAccessType() << "\" srcline=\"" << gvE->getLine() << "\"" << " blockID=\"" << gvE->getBasicBlockID() << "\"" << " srccol=\"" << gvE->getColumn() << "\"/>" << endl;
          *outputFile << "?type=" << type << "\" Access=\"" << gvE->
              getAccessType() << "\" srcline=\"" << gvE->getLine() << "\"" <<
              " srccol=\"" << gvE->getColumn() << "\"/>" << endl;
          if (cleanedName.compare(name) != 0) {
            *errorsFile << "<stmtseq xsi:type=\"scair:LabelAccess\"" <<
                " labelWritten=\"" << cleanedName << "\" labelExpected=\"" <<
                name;
            *errorsFile << "?type=" << type << "\" Access=\"" << gvE->
                getAccessType() << "\" srcline=\"" << gvE->getLine() <<
                "\" srccol=\"" << gvE->getColumn() << "\" filename=\"" <<
                filename << "\"/>" << endl;
          }
        }
      } else if (
        isLocalPointerOrParameter(variableName, allTraversingData.at(0)) || (
          name.find_first_of("-FPTR") == name.length() - 5)) {
        printIndentation(indentationLevel, outputFile);
        *outputFile <<
            "<stmtseq xsi:type=\"scair:LabelAccess\" label=\"" <<
            variableName;
        //*outputFile << "?type=" << type << "\" Access=\"" << gvE->getAccessType() << "\" srcline=\"" << gvE->getLine() << "\"" << " blockID=\"" << gvE->getBasicBlockID() << "\"" << " srccol=\"" << gvE->getColumn() << "\"/>" << endl;
        *outputFile << "?type=" << type << "\" Access=\"" << gvE->
            getAccessType() << "\" srcline=\"" << gvE->getLine() << "\"" <<
            " srccol=\"" << gvE->getColumn() << "\"/>" << endl;
        if (variableName.find("#") != string::npos) {
          cout << "error2: " << variableName << endl;
        }
        if (allTraversingData.at(0)->internalGlobalVariablesMap.count(
              variableName) == 0) {
          allTraversingData.at(0)->internalGlobalVariablesMap[variableName] =
              allTraversingData.at(0)->mapOfAllLocalAndParameter[variableName];
        }
      } else {
        *errorsFile <<
            "<stmtseq xsi:type=\"scair:LabelAccess\" label2=\"" << gvE
                                                                           ->
                                                                           getGlobalVariable()
                                                                           ->
                                                                           getName();
        *errorsFile << "?type=" << type << "\" Access=\"" << gvE->
            getAccessType() << "\" srcline=\"" << gvE->getLine() <<
            "\" srccol=\"" << gvE->getColumn() << "\"/>" << endl;
      }
    } else {
      //cerr << gvE->getGlobalVariable() << endl;
    }
  } catch (...) {

    //cout << gvE->getGlobalVariable()->getName() << endl;
    cerr << "An error occured in printAccessAdvanced" << endl;
  }
}

void printCallAdvanced(CallEntry *fE, ofstream *outputFile, int indentationLevel) {
  printIndentation(indentationLevel, outputFile);
  *outputFile << "<stmtseq xsi:type=\"scair:FunctionCall\"";
  *outputFile << " calls=\"" << fE->getFunctionID()->getFunctionName() <<
      "?type=Function\"" << " srcline = \"" << fE->getLine() << "\" srccol=\""
      << fE->getColumn() << "\"/>" << endl;
}

void printModeSwitchBegin(int indentationLevel, ofstream *outputFile) {
    printIndentation(indentationLevel, outputFile);
    *outputFile << "<stmtseq xsi:type=\"scair:ModeSwitch\">" << endl;
}

void printModeSwitchEnd(int indentationLevel, ofstream *outputFile) {
    printIndentation(indentationLevel, outputFile);
    *outputFile << "</stmtseq>" << endl;
}

void printModeEntryBegin(int indentationLevel, ofstream *outputFile) {
    printIndentation(indentationLevel, outputFile);
    *outputFile << "<modeentry>" << endl;
}

void printModeEntryEnd(int indentationLevel, ofstream *outputFile) {
    printIndentation(indentationLevel, outputFile);
    *outputFile << "</modeentry>" << endl;
}

void printCallGraphItem(CallGraphItem *callGraphItem, int indentationLevel,
                        ofstream *outputFile, ofstream *errorsFile,
                        string &filename) {

  CallEntry *itemAsCallEntry = llvm::dyn_cast<CallEntry>(callGraphItem);
  GlobalVariableAccess *itemAsGlobalVariableAccess = llvm::dyn_cast<
    GlobalVariableAccess>(callGraphItem);
  ModeSwitch *itemAsModeSwitch = llvm::dyn_cast<ModeSwitch>(callGraphItem);

  if (itemAsCallEntry) {
    printCallAdvanced(itemAsCallEntry, outputFile, indentationLevel);
  } else if (itemAsGlobalVariableAccess) {
    printAccessAdvanced(itemAsGlobalVariableAccess, outputFile, errorsFile,
                        filename, indentationLevel);
  } else if (itemAsModeSwitch) {
    printModeSwitchBegin(indentationLevel, outputFile);
    for (auto modeEntry : *itemAsModeSwitch->getModeEntries()) {
      printModeEntryBegin(indentationLevel + 1, outputFile);
      for (auto item : modeEntry->getChildren()) {
        printCallGraphItem(item, indentationLevel + 2, outputFile, errorsFile, filename);
      }
      printModeEntryEnd(indentationLevel + 1, outputFile);
    }
    printModeSwitchEnd(indentationLevel, outputFile);
  } else {
    cout << "Unknown call graph item occured." << endl;
  }
}

void printFunctionContentInOrderAdvanced(
  CallGraphItemContainer *callGraphItemContainer, ofstream *outputFile,
  ofstream *errorsFile, string &filename) {

  list<CallGraphItem*> callGraphItems = callGraphItemContainer->getChildren();
  for (list<CallGraphItem*>::iterator callGraphItemIterator = callGraphItems.
           begin(); callGraphItemIterator != callGraphItems.end(); ++
       callGraphItemIterator) {
    printCallGraphItem(*(callGraphItemIterator), 4, outputFile, errorsFile,
                       filename);
  }
}

void addAnonymousStruct(
  map<string, pair<string, string>> *currentTypeDefMembers, string currentLabel,
  string prefix) {
  if (currentLabel.find(".") != string::npos) {
    int n = currentLabel.find(".");
    string name = currentLabel.substr(0, n);
    if (prefix.size() > 0) {
      name = prefix + "." + name;
    }
    if (currentTypeDefMembers->count(name) == 0) {
      pair<string, string> p("ANONYMOUS", "STRUCT");
      (*currentTypeDefMembers)[name] = p;
    }
  }
}

string removeImplicitField(string fieldMember) {
  int startPos = 0;
  int length = 0;
  if (fieldMember.find(".implicit_field#.") != string::npos) {
    startPos = fieldMember.find(".implicit_field#.");
    length = 17;
  } else if (fieldMember.find("implicit_field#.") != string::npos) {
    startPos = fieldMember.find("implicit_field#.");
    length = 16;
  }
  return fieldMember.erase(startPos, length);
}

map<string, pair<string, string>> *getTypeDefMembers(
  TraversingData *t, map<string, pair<string, string>> *currentTypeDefMembers,
  TypeDefStructOrUnion *typeDefinition, string prefix) {
  list<TypeDefGeneral*> *listOfMembers = nullptr;
  TypeDefStructOrUnion *referencedStruct = nullptr;
  string currentLabel = "";
  listOfMembers = typeDefinition->getMembers();
  if (listOfMembers == nullptr || (
        listOfMembers != nullptr && listOfMembers->size() == 0)) {
    string referencedTypeName = removeAnonymousOrConst(
      typeDefinition->getReferencedType());
    TypeDefGeneral *typeDef = t->typeDefsmap[referencedTypeName];
    if (referencedTypeName.compare("") != 0 && typeDef != nullptr) {
      referencedStruct = (TypeDefStructOrUnion*)typeDef;
      if (referencedStruct != typeDefinition)
        getTypeDefMembers(t, currentTypeDefMembers, referencedStruct, prefix);
    }
  } else {
    for (TypeDefGeneral *tMember : *listOfMembers) {
      try {
        currentLabel = tMember->getName();
        addAnonymousStruct(currentTypeDefMembers, currentLabel, prefix);
        if (prefix.size() > 0) {
          currentLabel = prefix + "." + tMember->getName();
        }

        string cat = enumToString(tMember->getTypeDefType());
        string type = *tMember->getReferencedType();
        //Commenting to allow const voltile and other keywords to be present in the type
        //	type=removeAnonymousOrConst(tMember->getReferencedType());

        pair<string, string> p(type, cat);
        if (currentLabel.compare("implicit_field#") != 0 && currentLabel.
            find("implicit_field#") == string::npos) {
          (*currentTypeDefMembers)[currentLabel] = p;
        } else if (currentLabel.find("implicit_field#") != string::npos) {
          (*currentTypeDefMembers)[removeImplicitField(currentLabel)] = p;
        }

        if (tMember->getTypeDefType() == STRUCT || tMember->getTypeDefType() ==
            UNION) {
          getTypeDefMembers(t, currentTypeDefMembers,
                            (TypeDefStructOrUnion*)tMember, currentLabel);
        }
      } catch (...) {
        cout << "a problem happened when getting type def members of " <<
            typeDefinition->getName() << endl;
      }

    }
  }
  return currentTypeDefMembers;
}

void printMemberLabels(TraversingData *t, ofstream *outputFile,
                       string structName, string labelName) {
  list<TypeDefGeneral*> *listOfMembers = nullptr;
  TypeDefGeneral *typeDefinition = nullptr;
  typeDefinition = t->typeDefsmap[structName];
  if (typeDefinition != nullptr && (
        typeDefinition->getTypeDefType() == STRUCT || typeDefinition->
        getTypeDefType() == UNION)) {
    listOfMembers = ((TypeDefStructOrUnion*)typeDefinition)->getMembers();
    if (listOfMembers != nullptr && listOfMembers->size() > 0) {
      set<string> possibleAnonymousStructs;
      for (TypeDefGeneral *tMember : *listOfMembers) {
        string memberName = labelName + "." + tMember->getName();
        *outputFile << "      <label name=\"" << memberName << "\" type=\"" <<
            removeAnonymousOrConst(tMember->getReferencedType());
        *outputFile << "\" cat=\"" << enumToString(tMember->getTypeDefType());
        *outputFile << "\"></label>" << endl;
        int n = tMember->getName().find(".");
        if (n != string::npos) {
          possibleAnonymousStructs.insert(tMember->getName().substr(0, n));
        }
      }
      if (possibleAnonymousStructs.size() > 0) {
        for (set<string>::iterator it = possibleAnonymousStructs.begin();
             it != possibleAnonymousStructs.end(); ++it) {
          string memberName = labelName + "." + *it;
          *outputFile << "      <label name=\"" << memberName <<
              "\" type=\"anonymous";
          *outputFile << "\" cat=\"STRUCT";
          *outputFile << "\"></label>" << endl;
        }
      }
    }
  }
}

void printfileAndPackageInfo(string fileName, ofstream *outputFile) {
  string fName = "";
  string pName = "";
  string fileNameWithoutPath = getFileNameWithoutPath(fileName);
  if (filePackageMap.count(fileNameWithoutPath) > 0) {
    pair<string, string> filePackagePair = filePackageMap[fileNameWithoutPath];
    fName = filePackagePair.first;
    pName = filePackagePair.second;
  }

  *outputFile << "\" File=\"" << fName << "\" Package=\"" << pName;
}

void printMemberLabelsAdvanced(TraversingData *t, ofstream *outputFile,
                               string structName, string labelName,
                               string *fileName, bool isPointerOnStruct) {
  string memberConnector = ".";
  if (isPointerOnStruct) {
    memberConnector = POINTS2;
  }
  TypeDefGeneral *typeDefinition = nullptr;
  typeDefinition = t->typeDefsmap[structName];
  map<string, pair<string, string>> *mapOfMembers = new map<
    string, pair<string, string>>();
  if (!isPointerOnStruct && typeDefinition != nullptr && (
        typeDefinition->getTypeDefType() == STRUCT || typeDefinition->
        getTypeDefType() == UNION)) {
    TypeDefStructOrUnion *typeDefStructOrUnion = (TypeDefStructOrUnion*)
        typeDefinition;
    mapOfMembers = getTypeDefMembers(t, mapOfMembers, typeDefStructOrUnion, "");
    if (mapOfMembers != nullptr && mapOfMembers->size() > 0) {
      for (map<string, pair<string, string>>::iterator it = mapOfMembers->
               begin(); it != mapOfMembers->end(); ++it) {
        string memberName = labelName + memberConnector + it->first;
        *outputFile << "      <label name=\"" << memberName << "\" type=\"" <<
            it->second.first;
        *outputFile << "\" cat=\"" << it->second.second;
        printfileAndPackageInfo(*fileName, outputFile);
        *outputFile << "\"/>" << endl;
      }
    }
  }
}

/*Print the call tree inside a XML file*/
void printCallTreeToXMLAdvanced(string directoryToParse,
                                string directoryToPrint, TraversingData *t) {
  getLabelList(&labelSet, t);
  ofstream outputFile;
  outputFile.open(directoryToPrint + "/XMLCalltree.xml");
  ofstream outputFileGloballAccessErrors;
  outputFileGloballAccessErrors.open(
    directoryToPrint + "/outputFileGloballAccessErrors.txt");
  //map of function name and its corrosponding function entry object..The entries are those that are already printed in the IR.. added for the task 152993
  std::map<std::string, FunctionEntry*> functionAndPrintedStatus;

  outputFile << "<?xml version=\"1.0\" encoding=\"ASCII\"?>" << endl;
  outputFile <<
      "<scair:Project xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:scair=\"http://org.eclipse.app4mc.scair.core.model\" location=\""
      << directoryToParse << "\">" << endl;
  cout << "Starting to write the containers" << endl;
  try {
    outputFile << " <containers>" << endl;
    for (map<std::string, std::list<FunctionAndCallees*>*>::iterator iterator =
             t->internalStructuralCallGraphs.begin();
         iterator != t->internalStructuralCallGraphs.end(); ++iterator) {
      string fileName = iterator->first;
      //string headerFileName = fileName.substr(fileName.find_last_of("/\\") + 1);
      removeDuplicates(iterator->second, functionAndPrintedStatus);
      if (iterator->second->size() != 0) {
        outputFile << "  <container name=\"" << getRelativePathFilename(
          directoryToParse, fileName) << "\">" << endl;
        list<FunctionAndCallees*> *functionsList = iterator->second;
        outputFile << "    <functions>" << endl;
        for (FunctionAndCallees *fc : *functionsList) {
          functionAndPrintedStatus[convertToLowerCase(
                fc->getFunctionEntry()->getFunctionName())] = fc->
              getFunctionEntry();
          outputFile << "      <function srcline=\"" << fc
                                                        ->getFunctionEntry()->
                                                        getLine() <<
              "\" srccol=\"" << fc->getFunctionEntry()->getColumn();
          printfileAndPackageInfo(fileName, &outputFile);
          outputFile << "\">" << endl;
          outputFile << "        <name>" << fc
                                            ->getFunctionEntry()->
                                            getFunctionName() << "</name>" <<
              endl;
          printFunctionContentInOrderAdvanced(fc->getCurrentContainer(),
                                              &outputFile,
                                              &outputFileGloballAccessErrors,
                                              fileName);

          outputFile << "      </function>" << endl;
        }
        outputFile << "    </functions>" << endl;
        outputFile << "  </container>" << endl;
      }
    }
    outputFile << " </containers>" << endl;
  } catch (...) {
    cout << "Problem creating the container list" << endl;
  }
  cout << "Starting to write the labels" << endl;

  try {
    outputFile << " <labels>" << endl;
    for (map<string, GlobalVariable*>::iterator it = t
                                                     ->
                                                     internalGlobalVariablesMap.
                                                     begin();
         it != t->internalGlobalVariablesMap.end(); ++it) {
      GlobalVariable *globalVariableTemp = it->second;
      string type = "";
      try {
        if (globalVariableTemp != nullptr) {
          string str = globalVariableTemp->getType();
          string fileName = *globalVariableTemp->getFileName();
          string globalVariableName = it->first;
          //Commenting to allow const voltile and other keywords to be present in the type
          //	str = removeAnonymousOrConst(&str);
          outputFile << "      <label name=\"" << globalVariableName <<
              "\" type=\"" << str;
          outputFile << "\" cat=\"" << globalVariableTemp->getCat();
          printfileAndPackageInfo(fileName, &outputFile);
          outputFile << "\"/>" << endl;
          if (isStructMemberAccessActivated && (
                globalVariableTemp->getCat().compare("STRUCT") == 0
                || globalVariableTemp->getCat().compare("UNION") == 0)
              || globalVariableTemp->getCat().compare("POINTER_ON_STRUCT") == 0
          ) {
            //adding to allow const voltile and other keywords to be present in the type
            str = removeAnonymousOrConst(&str);
            type = globalVariableTemp->getCat();
            //printMemberLabelsAdvanced(t, &outputFile, str, globalVariableTemp->getName());
            bool isPointerOnStruct = false;
            if (globalVariableTemp->getCat().compare("POINTER_ON_STRUCT") == 0
            ) {
              str = globalVariableTemp->getPointeeType();
              isPointerOnStruct = true;
            }
            printMemberLabelsAdvanced(t, &outputFile, str, globalVariableName,
                                      globalVariableTemp->getFileName(),
                                      isPointerOnStruct);
          }
        }
      } catch (...) {
        cout << "Problem with label " << globalVariableTemp->getName() << " - "
            << type << endl;
        TypeDefGeneral *typeDefinition = nullptr;
        string str = globalVariableTemp->getType();
        str = removeAnonymousOrConst(&str);
        typeDefinition = t->typeDefsmap[str];
        if (typeDefinition != nullptr)
          if (typeDefinition->getTypeDefType() == STRUCT || typeDefinition->
              getTypeDefType() == UNION)
            ((TypeDefStructOrUnion*)typeDefinition)->printToString();
      }
    }
    outputFile << " </labels>" << endl;
  } catch (...) {
    cout << "problem creating the label list" << endl;
  }
  cout << "Starting to write the typedefs" << endl;

  try {
    if (t->typeDefsmap.size() > 0) {
      outputFile << " <typeDefs>" << endl;
      for (map<string, TypeDefGeneral *>::iterator it = t->typeDefsmap.begin();
           it != t->typeDefsmap.end(); ++it) {

        try {
          printTypeDefToXML(&outputFile, it->second, t);
        } catch (...) {
          cout << "Problem with typedef " << it->first << " - " << enumToString(
            it->second->getTypeDefType()) << endl;

        }
      }
      outputFile << " </typeDefs>" << endl;
    }
  } catch (...) {
    cout << "Problem with typedefs" << endl;
  }
  cout << "Finishing to write the typedefs" << endl;
  outputFile << "</scair:Project>" << endl;
  outputFile.close();
  outputFileGloballAccessErrors.close();
}

void printTypeDefToXML(std::ofstream *outputFile, TypeDefGeneral *t,
                       TraversingData *tra) {
  if (t != nullptr) {
    //list<string>* listOfElements = nullptr;
    list<TypeDefGeneral*> *listOfMembers = nullptr;
    switch (t->getTypeDefType()) {
    case PRIMITIVE:
      *outputFile << "    <typedef name=\"" << t->getName() <<
          "\" cat=\"PRIMITIVE\" type=\"" << *t->getReferencedType() << "\"/>" <<
          endl;
      break;
    case POINTER:
      *outputFile << "    <typedef name=\"" << t->getName() <<
          "\" cat=\"POINTER\" type=\"" << *t->getReferencedType() << "\"/>" <<
          endl;
      break;
    case ARRAY:
      *outputFile << "    <typedef name=\"" << t->getName() <<
          "\" cat=\"ARRAY\" type=\"" << *t->getReferencedType() << "\"/>" <<
          endl;
      break;
    case ENUM:
      *outputFile << "    <typedef name=\"" << t->getName() <<
          "\" cat=\"PRIMITIVE\" type=\"enum\"/>" << endl;

      break;
    case UNION:
    case STRUCT:

      listOfMembers = ((TypeDefStructOrUnion*)t)->getMembers();
      if (listOfMembers != nullptr && listOfMembers->size() > 0) {
        *outputFile << "    <typedef name=\"" << t->getName() <<
            "\" cat=\"STRUCT\">" << endl;
        set<string> anonymousStructs;
        for (TypeDefGeneral *tMember : *listOfMembers) {
          *outputFile << "		<member name=\"" << tMember->getName() <<
              "\" cat=\"" << enumToString(tMember->getTypeDefType());
          *outputFile << "\" type=\"" << *(tMember->getReferencedType()) <<
              "\"/>" << endl;
          //removed removeAnonymousOrConst(tMember->getReferencedType()) and incluced only tMember->getReferencedType() to have const and volatile and other attributes
          int n = tMember->getName().find_first_of(".");
          if (n != string::npos) {
            anonymousStructs.insert(tMember->getName().substr(0, n));
          }
        }
        if (anonymousStructs.size() > 0) {
          for (set<string>::iterator it = anonymousStructs.begin();
               it != anonymousStructs.end(); ++it) {
            *outputFile << "		<member name=\"" << *it << "\" cat=\"STRUCT";
            *outputFile << "\" type=\"ANONYMOUS\"/>" << endl;
          }
        }
        *outputFile << "    </typedef>" << endl;
      } else {
        if (t->getReferencedType() != nullptr) {
          *outputFile << "    <typedef name=\"" << t->getName() <<
              "\" cat=\"STRUCT\" type=\"" << *t->getReferencedType() << "\"/>"
              << endl;
        }
        //*outputFile << "    <typedef name=\"" << t->getName() << "\" cat=\"STRUCT\" type=\"null" << "\"/>" << endl;
      }

      break;
    }

  }
}

void printGlobalvariableList(string directoryToPrint, TraversingData *t) {
  ofstream outputFile;
  outputFile.open(directoryToPrint + "/globalVariables.txt");

  ofstream outputFile2;
  outputFile2.open(directoryToPrint + "/globalVariablesToFiles.txt");

  for (map<string, GlobalVariable*>::iterator it = t
                                                   ->internalGlobalVariablesMap.
                                                   begin();
       it != t->internalGlobalVariablesMap.end(); ++it) {
    GlobalVariable *globalVariable = it->second;
    if (globalVariable) {
      string globalVariableName = it->first;
      outputFile << globalVariableName << endl;
      outputFile2 << replaceAll(*globalVariable->getFileName(), "/", "\\") <<
          ":" << globalVariableName << ":" << globalVariable->getType() << ":"
          << globalVariable->getLine() << ":" << globalVariable->getColumn() <<
          endl;
    }

  }
}

void printListOfAnalyzedFiles(string directoryToPrint, TraversingData *t) {
  ofstream outputFile;
  string directoryName = directoryToPrint + "/analyzedFiles" +
                         to_string(t->workerId) + ".txt";
  outputFile.open(directoryName);

  ofstream outputFile2;
  outputFile2.open(
    directoryToPrint + "/analyzedFilesContainingErrors" + to_string(t->workerId)
    + ".txt");

  ofstream outputFile3;
  outputFile3.open(
    directoryToPrint + "/UnanalyzedFiles" + to_string(t->workerId) + ".txt");

  for (std::map<std::string, int>::iterator it = t->analyzedFiles.begin();
       it != t->analyzedFiles.end(); ++it) {
    if (it->second == 1) {
      outputFile << it->first << endl;
    } else if (it->second == -1) {
      outputFile2 << it->first << endl;
    } else if (it->second == 0) {
      outputFile3 << it->first << endl;
    }
  }
  outputFile.close();
  outputFile2.close();
  outputFile3.close();
}

void printIDList(list<list<unsigned int>*> *listOfListOfID) {
  if (listOfListOfID) {
    for (list<unsigned int> *l : *listOfListOfID) {
      if (l) {
        int i = 0;
        for (unsigned int id : *l) {
          if (++i < l->size()) {
            cout << id << " -> ";
          } else {
            cout << id;
          }
        }
        cout << endl;
      }
    }
  }
}

void printCFGPaths(TraversingData *t) {
  std::map<std::string, std::list<FunctionAndCallees*>*> myMap = t->
      internalStructuralCallGraphs;
  for (std::map<std::string, std::list<FunctionAndCallees*>*>::iterator it =
           myMap.begin(); it != myMap.end(); ++it) {
    cout << "FileName: " << it->first << "-------------" << endl;
    if (it->second) {
      for (FunctionAndCallees *functionAndCallees : *(it->second)) {
        cout << "    FunctionName: " << functionAndCallees
                                        ->getFunctionEntry()->getFunctionName()
            << "-------------" << endl;
        cout << "Paths: -------------" << endl;
        printIDList(functionAndCallees->getCFGPaths());
        cout << "Cycles: -------------" << endl;
        printIDList(functionAndCallees->getCFGCycles());

      }
    }
  }
}

void printCFG(TraversingData *t) {
  std::map<std::string, std::list<FunctionAndCallees*>*> myMap = t->
      internalStructuralCallGraphs;
  for (std::map<std::string, std::list<FunctionAndCallees*>*>::iterator it =
           myMap.begin(); it != myMap.end(); ++it) {
    cout << "FileName: " << it->first << "-------------" << endl;
    for (FunctionAndCallees *functionAndCallees : *(it->second)) {
      cout << "    FunctionName: " << functionAndCallees
                                      ->getFunctionEntry()->getFunctionName() <<
          "-------------" << endl;
      list<BasicBlock> *listOfbasicBlocks = functionAndCallees->
          getbasicBlockList();
      if (listOfbasicBlocks) {
        for (BasicBlock b : *listOfbasicBlocks) {
          int id = b.getID();
          cout << "        Block ID: " << id << "-------------" << endl;

          for (CallEntry *callEntry : *(functionAndCallees->getCallees())) {
            if (callEntry->getBasicBlockID() == id) {
              cout << "           Call to: " << callEntry
                                                ->getFunctionID()->
                                                getFunctionName() << endl;
            }
          }

          for (GlobalVariableAccess *globalVariableAccess : *(functionAndCallees
                 ->getGlobalVariableAccesses())) {
            if (globalVariableAccess->getBasicBlockID() == id) {
              cout << "           Access to: " << globalVariableAccess
                                                  ->getGlobalVariable()->
                                                  getName() <<
                  "    --> access type: " << globalVariableAccess->
                  getAccessType() << "   Line= " << globalVariableAccess->
                  getLine() << endl;
            }
          }
        }
      }
    }
  }
}

void printCFGBlocks(TraversingData *t) {
  std::map<std::string, std::list<FunctionAndCallees*>*> myMap = t->
      internalStructuralCallGraphs;
  for (std::map<std::string, std::list<FunctionAndCallees*>*>::iterator it =
           myMap.begin(); it != myMap.end(); ++it) {
    cout << "FileName: " << it->first << "-------------" << endl;
    for (FunctionAndCallees *functionAndCallees : *(it->second)) {
      cout << "    FunctionName: " << functionAndCallees
                                      ->getFunctionEntry()->getFunctionName() <<
          "-------------" << endl;
      list<BasicBlock> *listOfbasicBlocks = functionAndCallees->
          getbasicBlockList();
      if (listOfbasicBlocks) {
        for (BasicBlock b : *listOfbasicBlocks) {
          int id = b.getID();
          cout << "        Block ID: " << id << "    Succs: ----->   ";
          for (unsigned int succ : *b.getSuccs()) {
            cout << succ << ", ";
          }
          cout << endl;

        }
      }
    }
  }
}

void printTypeDef(TypeDefGeneral *t,
                  std::map<std::string, TypeDefGeneral*> *typeDefsmap) {
  if (t != nullptr) {
    switch (t->getTypeDefType()) {
    case PRIMITIVE:
      //cout << "Name: " << t->getName() << " -> referenced Type: " << *t->getReferencedType() << endl;
      cout << "<typedef name=\"" << t->getName() <<
          "\" cat=\"PRIMITIVE\" refType=\"" << *t->getReferencedType() << "\"/>"
          << endl;
      break;
    case POINTER:
      //cout << "Name: " << t->getName() << " -> referenced Type: " << *t->getReferencedType() << " (POINTER)" << endl;
      cout << "<typedef name=\"" << t->getName() <<
          "\" cat=\"POINTER\" refType=\"" << *t->getReferencedType() << "\"/>"
          << endl;
      break;
    case ARRAY:
      //cout << "Name: " << t->getName() << " -> referenced Type: " << *t->getReferencedType() << " (ARRAY)" << endl;
      cout << "<typedef name=\"" << t->getName() <<
          "\" cat=\"ARRAY\" refType=\"" << *t->getReferencedType() << "\"/>" <<
          endl;
      break;
    case ENUM:
      //cout << "Name: " << t->getName() << " -> (ENUM)" << endl;
      cout << "<typedef name=\"" << t->getName() << "\" cat=\"ENUM\">" << endl;
      for (string str : *((TypeDefEnum*)t)->getElementList()) {
        //cout <<"	"<< str << endl;
        cout << "	<member name=\"" << str << "\"/>" << endl;
      }
      cout << "</typedef>" << endl;
      break;
    case UNION:
      cout << "<typedef name=\"" << t->getName() << "\" cat=\"UNION\">" << endl;
      //cout << "Name: " << t->getName() << " -> (STRUCT OR UNION)" << endl;
      for (TypeDefGeneral *tMember : *((TypeDefStructOrUnion*)t)->getMembers()
      ) {
        string typeDefNote = isTypeDef(*tMember->getReferencedType(),
                                       typeDefsmap).isTypeDef
                               ? "true"
                               : "false";
        cout << "	<member name=\"" << tMember->getName() << " cat=\"" <<
            enumToString(tMember->getTypeDefType());
        cout << "\" typeDef=\"" << typeDefNote << "\" refType=\"" << *tMember->
            getReferencedType() << "\"/>" << endl;
      }
      cout << "</typedef>" << endl;
      break;
    case STRUCT:
      cout << "<typedef name=\"" << t->getName() << "\" cat=\"STRUCT\">" <<
          endl;
      //cout << "Name: " << t->getName() << " -> (STRUCT OR UNION)" << endl;
      for (TypeDefGeneral *tMember : *((TypeDefStructOrUnion*)t)->getMembers()
      ) {
        string typeDefNote = isTypeDef(*tMember->getReferencedType(),
                                       typeDefsmap).isTypeDef
                               ? "true"
                               : "false";
        cout << "	<member name=\"" << tMember->getName() << " cat=\"" <<
            enumToString(tMember->getTypeDefType());
        cout << "\" typeDef=\"" << typeDefNote << "\" refType=\"" << *tMember->
            getReferencedType() << "\"/>" << endl;
      }
      cout << "</typedef>" << endl;
      break;
    default:
      break;
    }
  }
}


void outputCompilerArguments(list<string> *listOfHeaders) {
  cout << endl;
  cout <<
      "-----------------------Start list of compiler arguments -------------------------------"
      << endl;
  if (listOfHeaders != nullptr) {
    for (list<string>::iterator it = listOfHeaders->begin();
         it != listOfHeaders->end(); ++it) {
      cout << *it << endl;
    }
  }
  cout <<
      "-----------------------End compiler arguments -------------------------------"
      << endl;
  cout << endl;
}

void getMemberLabelsAdvanced(TraversingData *t, string structName,
                             string labelName, std::set<std::string> *labelSet,
                             bool isPointerOnStruct) {
  if (labelName.find_first_of(CONCAT_F_IA) == string::npos || labelName.
      find_first_of(CONCAT_F) == string::npos) {
    return;
  }
  string memberConnector = ".";
  if (isPointerOnStruct) {
    memberConnector = POINTS2;
  }
  TypeDefGeneral *typeDefinition = nullptr;
  typeDefinition = t->typeDefsmap[structName];
  map<string, pair<string, string>> *mapOfMembers = new map<
    string, pair<string, string>>();
  if (typeDefinition != nullptr && (
        typeDefinition->getTypeDefType() == STRUCT || typeDefinition->
        getTypeDefType() == UNION)) {
    TypeDefStructOrUnion *typeDefStructOrUnion = (TypeDefStructOrUnion*)
        typeDefinition;
    mapOfMembers = getTypeDefMembers(t, mapOfMembers, typeDefStructOrUnion, "");
    if (mapOfMembers != nullptr && mapOfMembers->size() > 0) {
      for (map<string, pair<string, string>>::iterator it = mapOfMembers->
               begin(); it != mapOfMembers->end(); ++it) {
        string memberName = labelName + memberConnector + it->first;
        labelSet->insert(memberName);
      }
    }
  }
}

void getLabelList(std::set<std::string> *labelSet, TraversingData *t) {
  for (map<string, GlobalVariable*>::iterator it = t
                                                   ->internalGlobalVariablesMap.
                                                   begin();
       it != t->internalGlobalVariablesMap.end(); ++it) {
    GlobalVariable *globalVariableTemp = it->second;
    string type = "";
    try {
      if (globalVariableTemp != nullptr) {
        string str = globalVariableTemp->getType();
        str = removeAnonymousOrConst(&str);

        labelSet->insert(globalVariableTemp->getName());

        if (globalVariableTemp->getCat().compare("STRUCT") == 0 ||
            globalVariableTemp->getCat().compare("UNION") == 0
            || globalVariableTemp->getCat().compare("POINTER_ON_STRUCT") == 0) {

          bool isPointerOnStruct = false;
          if (globalVariableTemp->getCat().compare("POINTER_ON_STRUCT") == 0) {
            str = globalVariableTemp->getPointeeType();
            isPointerOnStruct = true;
          }
          //type = globalVariableTemp->getCat();
          getMemberLabelsAdvanced(t, str, globalVariableTemp->getName(),
                                  labelSet, isPointerOnStruct);
        }
      }
    } catch (...) {

    }
  }
}

bool isLocalPointerOrParameter(std::string variableName,
                               TraversingData *traversingData) {
  return traversingData->mapOfAllLocalAndParameter.count(variableName) > 0;
}
