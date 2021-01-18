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
#include "algorithms/TraverseASTClassAction.h"
#include <windows.h>

#ifndef UNICODE 
typedef std::string String;
#else
typedef std::wstring String;
#endif

using namespace clang;
using namespace tooling;
using namespace std;
using namespace llvm;

std::string FindInformationClassVisitor::getUniqueVariableName(
  VarDecl *variable, string oldVariableName) {
  if (variable->hasLinkage() || variable->isStaticLocal()) {
    return oldVariableName;
  }
  string currentFuncName = currentAnalyzedFunction
                           ->getFunctionEntry()->getFunctionName();
  if (oldVariableName.find_first_of(POINTS2) != string::npos) {
    return oldVariableName + CONCAT_F_IA + currentFuncName;
  }
  return oldVariableName + CONCAT_F + currentFuncName;

}

bool FindInformationClassVisitor::isVariableOrParameterToBeConsideredAsGlobal(
  VarDecl *variable) {

  if (variable->hasLinkage() || variable->isStaticLocal()) {
    return false;
  }
  return true;
}

DeclRefExpr *FindInformationClassVisitor::getDeclRefExprForSwapping(
  Expr *initialisationExpression) {
  string unaryOperatorStr = "";
  DeclRefExpr *declRef = nullptr;
  UnaryOperator *unaryOperatorElement = nullptr;
  ImplicitCastExpr *statement = nullptr;
  switch (initialisationExpression->getStmtClass()) {
  case Stmt::UnaryOperatorClass:
    unaryOperatorElement = llvm::cast<UnaryOperator>(initialisationExpression);
    unaryOperatorStr = unaryOperatorElement->getOpcodeStr(
      unaryOperatorElement->
      getOpcode()).str();

    if (unaryOperatorStr.compare("&") == 0) {

      for (Stmt::child_iterator I = unaryOperatorElement->child_begin(), E =
                                    unaryOperatorElement->child_end(); I != E;
           ++I) {
        Stmt *child = *I;
        switch (child->getStmtClass()) {
        case Stmt::DeclRefExprClass:
          declRef = llvm::cast<DeclRefExpr>(child);
          break;
        default:
          break;
        }
      }
    }
    break;
  case Stmt::ImplicitCastExprClass:
    statement = llvm::cast<ImplicitCastExpr>(initialisationExpression);
    for (Stmt::child_iterator I = statement->child_begin(), E = statement->
                                  child_end(); I != E; ++I) {
      Stmt *child = *I;
      switch (child->getStmtClass()) {
      case Stmt::DeclRefExprClass:
        declRef = llvm::cast<DeclRefExpr>(child);
        break;
      default:
        break;
      }
    }
    break;
  default:
    //cout << "not of that types " << initialisationExpression->getStmtClass() << endl;
    break;

    // we are only interested with unaryOperator and Implicitcast expressions
  }
  return declRef;
}

void FindInformationClassVisitor::getGlobalVariableAsProxy(VarDecl *variable) {
  if (isCFGActivated && variable->hasInit()) {
    Expr *initialisationExpression = variable->getInit();

    DeclRefExpr *declRef = getDeclRefExprForSwapping(initialisationExpression);

    if (declRef != nullptr) {
      if (isCFGActivated && currentAnalyzedFunction && currentAnalyzedFunction
          != nullptr) {
        string localVarName = getUniqueVariableName(
          variable, variable->getNameAsString());
        int blockID = varDeclToBlockID[variable];
        addSwappingInformation(localVarName, declRef, blockID);
      }
    }
  }
}

list<BasicBlock> *FindInformationClassVisitor::buildCFGModel(
  FunctionDecl *decl, Stmt *stmt) {

  list<BasicBlock> *basicBlockList = new list<BasicBlock>();

  if (decl && stmt) {
    std::unique_ptr<CFG> srcCFG = CFG::buildCFG(decl, stmt, Context,
                                                CFG::BuildOptions());

    CFG *otherCFG = srcCFG.release();
    parentMap = new ParentMap(decl->getBody());
    mapOfCFGStmts = CFGStmtMap::Build(otherCFG, parentMap);

    for (CFG::const_reverse_iterator it = otherCFG->rbegin();
         it != otherCFG->rend(); ++it) {
      const CFGBlock *CB = *it;

      BasicBlock bb(CB->getBlockID());

      for (CFGBlock::const_pred_iterator it_pred = CB->pred_begin();
           it_pred != CB->pred_end(); ++it_pred) {
        bb.addPred(it_pred->getReachableBlock()->getBlockID());
      }
      for (CFGBlock::const_succ_iterator it_succ = CB->succ_begin();
           it_succ != CB->succ_end(); ++it_succ) {
        bb.addSucc(it_succ->getReachableBlock()->getBlockID());
      }
      basicBlockList->push_back(bb);
    }
    delete otherCFG;
  }
  return basicBlockList;
}


void FindInformationClassVisitor::addParameterToGlobalMap(
  ParmVarDecl *variable, string functionName) {

  string typeName = variable->getType().getAsString();

  string paramName = variable->getNameAsString() + "-" + functionName;

  varType varTypeResult = isTypeDef(getStructName(typeName),
                                    &this->traversingData->typeDefsmap);

  string isTypedefString = varTypeResult.isTypeDef ? "true" : "false";
  string isConstString = varTypeResult.isConst ? "true" : "false";
  string isVolatileString = varTypeResult.isVolatile ? "true" : "false";
  string str = parseQualTypeSimple(variable->getType(), this->traversingData);

  GlobalVariable *g = new GlobalVariable(paramName, getStructName(typeName),
                                         getSourceFileName(variable),
                                         getLineNumberDecl(variable),
                                         getColumnNumberDecl(variable), str,
                                         isTypedefString, isConstString,
                                         isVolatileString);

  if (str.compare("POINTER_ON_STRUCT") == 0) {
    string pointeeType = variable->getType()->getPointeeType().getAsString();
    if (pointeeType.find_first_of("struct ") == 0) {
      pointeeType = pointeeType.substr(7);
    }
    g->setPointeeType(pointeeType);
  }

  //cout <<"to be added: "<< paramName << endl;

  if (this->traversingData->internalFunctionsParametersMap.count(functionName)
      == 0) {
    std::map<std::string, GlobalVariable*> *paramMap = new map<
      std::string, GlobalVariable*>();
    (*paramMap)[paramName] = g;
    this->traversingData->mapOfAllLocalAndParameter[paramName] = g;
    this->traversingData->internalFunctionsParametersMap[functionName] =
        paramMap;
  } else {
    std::map<std::string, GlobalVariable*> *paramMap = this
                                                       ->traversingData->
                                                       internalFunctionsParametersMap
        [functionName];
    (*paramMap)[paramName] = g;
    this->traversingData->mapOfAllLocalAndParameter[paramName] = g;
  }
}

void FindInformationClassVisitor::addLocalValiableToGlobalMap(
  VarDecl *variable, string functionName, GlobalVariable *proxy) {

  string typeName = variable->getType().getAsString();
  //string paramName = variable->getNameAsString(); 
  string paramName = getUniqueVariableName(variable,
                                           variable->getNameAsString());

  varType varTypeResult = isTypeDef(getStructName(typeName),
                                    &this->traversingData->typeDefsmap);

  string isTypedefString = varTypeResult.isTypeDef ? "true" : "false";
  string isConstString = varTypeResult.isConst ? "true" : "false";
  string isVolatileString = varTypeResult.isVolatile ? "true" : "false";
  string str = parseQualTypeSimple(variable->getType(), this->traversingData);

  GlobalVariable *g = new GlobalVariable(paramName, getStructName(typeName),
                                         getSourceFileName(variable),
                                         getLineNumberDecl(variable),
                                         getColumnNumberDecl(variable), str,
                                         isTypedefString, isConstString,
                                         isVolatileString);
  if (str.compare("POINTER_ON_STRUCT") == 0) {
    string pointeeType = variable->getType()->getPointeeType().getAsString();
    if (pointeeType.find_first_of("struct ") == 0) {
      pointeeType = pointeeType.substr(7);
    }
    g->setPointeeType(pointeeType);
  }
  g->setProxy(proxy);
  if (isCFGActivated) {
    int blockID = varDeclToBlockID[variable];
    g->setBlockID(blockID);
  }

  if (this->traversingData->internalFunctionLocalVariablesMap.
            count(functionName) <= 0 ||
      (this->traversingData->internalFunctionLocalVariablesMap.
             count(functionName) > 0 && this
                                        ->traversingData->
                                        internalFunctionLocalVariablesMap[
         functionName] == nullptr)) {
    std::map<std::string, GlobalVariable*> *paramMap = new map<
      std::string, GlobalVariable*>();
    pair<string, GlobalVariable*> *localVarAndParams = new pair<
      string, GlobalVariable*>(paramName, g);
    paramMap->insert(*localVarAndParams);
    this->traversingData->internalFunctionLocalVariablesMap[functionName] =
        paramMap;
    this->traversingData->mapOfAllLocalAndParameter.insert(*localVarAndParams);
    currentAnalyzedFunction->addLocalVariable(g);
  } else {
    std::map<std::string, GlobalVariable*> *paramMap = this
                                                       ->traversingData->
                                                       internalFunctionLocalVariablesMap
        [functionName];
    pair<string, GlobalVariable*> *localVarAndParams = new pair<
      string, GlobalVariable*>(paramName, g);
    paramMap->insert(*localVarAndParams);
    this->traversingData->mapOfAllLocalAndParameter.insert(*localVarAndParams);
    currentAnalyzedFunction->addLocalVariable(g);
  }
}

bool FindInformationClassVisitor::VisitFunctionDecl(FunctionDecl *decl) {
  //cout << "start Function Decl: " << endl;

  currentAnalyzedFile = nullptr;
  string functionName = "";
  isInFunctionDecl = true;
  isInLocalVariableWithPointerType = false;
  localVarDeclOfPointerType = nullptr;

  try {
    //	std::cout << "Sudhindra inside the visit function decl" << std::endl;
    variableNameAndCounter.clear();
    addressAndCounter.clear();
    if (decl && decl->isThisDeclarationADefinition() && decl->hasBody()) {
      concurrentCout("start : FindInformationClassVisitor::VisitFunctionDecl");
      Stmt *stmt = decl->getBody();

      if (stmt) {
        list<BasicBlock> *basicBlockList;
        if (isCFGActivated) {
          basicBlockList = buildCFGModel(decl, stmt);
        }

        currentAnalyzedFunction = new FunctionAndCallees();

        unsigned line = getLineNumberDecl(decl);
        unsigned col = getColumnNumberDecl(decl);
        string *fileName = getSourceFileName(decl);
        currentAnalyzedFile = fileName;

        std::string f = fileName->substr(fileName->find_last_of("/\\") + 1);
        functionName = decl->getNameInfo().getAsString();
        string returnType = decl->getReturnType().getAsString();

        //Added for handling static functions for the task id 178563
        if (!decl->isGlobal() && !decl->isInlineSpecified()) {
          functionName = appendStringToStaticFunctions(
            functionName, *getSourceFileName(decl->getBody()));
        }

        //added by sudhindra for the task 152993 ..collecting the type of input parameters to the function.
        ArrayRef<ParmVarDecl *> p = decl->parameters();
        std::vector<FunctionParameter*> parameters;
        bool isPointerTypeAsParameterInThisFunction = false;
        for (FunctionDecl::param_const_iterator it = p.begin(); it != p.end();
             ++it) {
          ParmVarDecl *variable = *it;
          string typeName = variable->getType().getAsString();
          string paramName = variable->getNameAsString();
          bool isPointerType = variable->getType()->isPointerType();
          if (!isPointerTypeAsParameterInThisFunction && isPointerType) {
            isPointerTypeAsParameterInThisFunction = true;
            addParameterToGlobalMap(variable, functionName);
          }
          //variable->dumpColor();
          //cout << paramName << " -Type:" << typeName << " - " << "Is pointer:" << isPointerType << endl;

          parameters.push_back(
            new FunctionParameter(paramName, typeName, isPointerType));
        }

        FunctionEntry *functionEntry = new FunctionEntry(
          fileName, functionName, line, col, returnType, parameters);
        bool alreadyInserted = false;

        if (this->traversingData->internalStructuralCallGraphs.find(*fileName)
            != this->traversingData->internalStructuralCallGraphs.end()) {

          list<FunctionAndCallees*> *l = this
                                         ->traversingData->
                                         internalStructuralCallGraphs.find(
                                           *fileName)->second;

          for (FunctionAndCallees *f : *l) {
            if (f->getFunctionEntry()->getFunctionName().compare(functionName)
                == 0) {
              alreadyInserted = true;
            }
          }

        }
        //insert a new function in the map of functions in relation to the file name
        if (!alreadyInserted) {
          mapOfFunctions[functionName] = functionEntry;
        } else {
          FunctionEntry *functionEntryOld = mapOfFunctions[functionName];
          if (functionEntryOld) {
            functionEntryOld->setLine(functionEntry->getLine());
            functionEntryOld->setColumn(functionEntry->getColumn());
            functionEntryOld->setParameters(functionEntry->getParameters());
          }
        }
        if (!isFileAllreadyinserted(*fileName)) {
          this->traversingData->internalStructuralCallGraphs[*fileName] = new
              list<FunctionAndCallees*>();

        }
        currentAnalyzedFunction->setFunctionID(functionEntry);

        parseStatementGeneric(stmt);

        //add the new function to the map

        if (!alreadyInserted) {
          this->traversingData->internalStructuralCallGraphs.find(*fileName)->
                second->push_back(currentAnalyzedFunction);
          if (isCFGActivated) {
            currentAnalyzedFunction->setBasicBlockList(basicBlockList);

            list<unsigned int> *myList = new list<unsigned int>();

            if (basicBlockList && basicBlockList->size() > 0) {
              findAllPaths(myList, &(basicBlockList->front()), basicBlockList,
                           currentAnalyzedFunction);
            }
          }
        }
        if (isCFGActivated) {
          delete parentMap;
          delete mapOfCFGStmts;
        }
      }
      concurrentCout("end : FindInformationClassVisitor::VisitFunctionDecl");
    } else if (decl) {
      DeclContext *parent = decl->getLexicalParent();
      string declKindName(parent->getDeclKindName());
      if (declKindName.compare("Function") == 0) {
        functionName = decl->getNameInfo().getAsString();

        FunctionEntry *functionDeclaration = mapOfFunctions[functionName];

        int line, col = 0;
        line = getLineNumberDecl(decl);
        col = getColumnNumberDecl(decl);

        //added for handling static functions for the task 178563				
        if (!decl->isGlobal() && !decl->isInlineSpecified()) {
          functionName = appendStringToStaticFunctions(
            functionName, getProperFileForStaticFunction(decl));
        }

        if (!functionDeclaration) {
          std::vector<FunctionParameter*> parameters;
          string returnType = decl->getReturnType().getAsString();
          functionDeclaration = new FunctionEntry(
            currentAnalyzedFile, functionName, 0, 0, returnType, parameters);
          mapOfFunctions[functionName] = functionDeclaration;
        }

        CallEntry *callEntry = new CallEntry(functionDeclaration, line, col, 0);
        currentAnalyzedFunction->getCurrentContainer()->addChild(callEntry);
        currentAnalyzedFunction->addCallee(callEntry);
      }
    }
  } catch (...) {
    cerr << "An error happened during the Parsing of the function: " <<
        functionName << " inside  the file: " << *currentAnalyzedFile << endl;
    //std::cout<< "Standard exception: " << e.what() << endl;
    if (currentAnalyzedFile != nullptr)
      this->traversingData->analyzedFiles[*currentAnalyzedFile] = -1;
  }

  isInFunctionDecl = false;
  return true;
}

string parseQualTypeSimple(QualType &t, TraversingData *traversingData) {

  string referencedType = t.getAsString();
  //cout << referencedType << endl;
  //lock_typesMap.lock();

  if (traversingData->typesMap.count(referencedType) == 0) {
    traversingData->typesMap[referencedType] = new string(referencedType);
  }

  //lock_typesMap.unlock();
  //TypeDefType enumType;
  if (t.isCanonical()) {
    // primary type
    //cout << " Canonical " << varName << endl;
    if (t.getTypePtr()->isPointerType()) {
      return "POINTER";
    }
    if (t.getTypePtr()->isArrayType()) {
      return "ARRAY";
    }
    return "PRIMITIVE";
  }
  //cout << " Not Canonical " << varName << endl;
  if (t.getTypePtr()->isPointerType()) {
    if (t.getTypePtr()->getPointeeType().getTypePtr()->isStructureType()) {
      return "POINTER_ON_STRUCT";
    }
    return "POINTER";
  }
  if (t.getTypePtr()->isStructureType()) {
    return "STRUCT";
  }
  if (t.getTypePtr()->isEnumeralType()) {
    return "enum";
  }
  if (t.getTypePtr()->isUnionType()) {
    return "UNION";
  }
  if (t.getTypePtr()->isArrayType()) {
    return "ARRAY";
  }
  return "PRIMITIVE";
}

DeclRefExpr *getDeclarationRefExpression(ImplicitCastExpr *implicitCastExpr) {
  try {
    for (Stmt::child_iterator I = implicitCastExpr->child_begin(), E =
                                  implicitCastExpr->child_end(); I != E; ++I) {
      Stmt *childImplicitCastExpr = *I;
      UnaryOperator *unaryOperator = nullptr;
      if (childImplicitCastExpr) {
        switch (childImplicitCastExpr->getStmtClass()) {
        case Stmt::DeclRefExprClass:
          return llvm::cast<DeclRefExpr>(childImplicitCastExpr);
          break;
        default:
          return nullptr;
          break;
        }
      }
    }
  } catch (...) {
    cerr << "An error occured in getDeclarationRefExpression" << endl;
  }
  return nullptr;
}

bool FindInformationClassVisitor::VisitImplicitCastExpr(
  ImplicitCastExpr *implicitCastExpr) {

  if (isInLocalVariableWithPointerType) {

    DeclRefExpr *declRefExpr = getDeclarationRefExpression(implicitCastExpr);
    bool toBeConsidered = false;

    if (declRefExpr /*&& declRefExpr->getDecl()->hasLinkage()*/) {
      try {
        GlobalVariable *g = this->traversingData->internalGlobalVariablesMap[
          declRefExpr->getDecl()->getNameAsString()];
        if (g && g != nullptr && currentAnalyzedFunction) {

          string functionName = currentAnalyzedFunction
                                ->getFunctionEntry()->getFunctionName();
          string uniqueVariableName = getUniqueVariableName(
            localVarDeclOfPointerType,
            localVarDeclOfPointerType->getNameAsString());
          map<string, GlobalVariable*> *functionLocalVariableMap = this
                                                                   ->
                                                                   traversingData
                                                                   ->
                                                                   internalFunctionLocalVariablesMap
              [functionName];
          GlobalVariable *gLocal = functionLocalVariableMap->at(
            uniqueVariableName);
          gLocal->setProxy(g);
        }
      } catch (...) {
        cerr << "An error happened in VisitImplicitCastExpr if " << endl;
      }
    } else if (declRefExpr && llvm::isa<ParmVarDecl>(declRefExpr->getDecl())) {
      string functionName = currentAnalyzedFunction
                            ->getFunctionEntry()->getFunctionName();
      std::map<std::string, GlobalVariable*> *paramMap = this
                                                         ->traversingData->
                                                         internalFunctionsParametersMap
          [functionName];
      try {

        ParmVarDecl *pVar = llvm::cast<ParmVarDecl>(declRefExpr->getDecl());
        GlobalVariable *g = nullptr;
        string varName = getUniqueVariableName(pVar, pVar->getNameAsString());
        if (paramMap && paramMap != nullptr && paramMap->count(varName) > 0) {
          g = paramMap->at(varName);
        }
        if (g && g != nullptr) {
          map<string, GlobalVariable*> *functionLocalVariableMap = this
                                                                   ->
                                                                   traversingData
                                                                   ->
                                                                   internalFunctionLocalVariablesMap
              [functionName];
          functionLocalVariableMap->at(getUniqueVariableName(
            localVarDeclOfPointerType,
            localVarDeclOfPointerType->
            getNameAsString()))->setProxy(g);
        }
      } catch (...) {
        cout << "An error happened in VisitImplicitCastExpr else if " <<
            functionName << " - " << " - count:" << this
                                                    ->traversingData->
                                                    internalFunctionsParametersMap
                                                    .count(functionName) <<
            endl;
      }
    }
  }

  return true;

}

bool FindInformationClassVisitor::VisitDeclStmt(DeclStmt *declStmt) {
  if (isCFGActivated && declStmt->isSingleDecl()) {
    Decl *decl = declStmt->getSingleDecl();

    if (decl && llvm::isa<VarDecl>(decl)) {
      VarDecl *varDecl = llvm::cast<VarDecl>(decl);
      if ((varDecl && !varDecl->hasLinkage() && !llvm::isa<ParmVarDecl>(varDecl)
      )) {
        //local variable
        if (varDecl->getType()->isPointerType()) {
          if (currentAnalyzedFunction && currentAnalyzedFunction != nullptr) {
            varDeclToBlockID[varDecl] = getBlockID(declStmt);
          }
        }
      }
    }
  }

  return true;
}

bool FindInformationClassVisitor::VisitVarDecl(VarDecl *varDecl) {
  currentAnalyzedFile = nullptr;
  isInLocalVariableWithPointerType = false;
  localVarDeclOfPointerType = nullptr;
  try {
    string variableName = varDecl->getDeclName().getAsString();

    currentAnalyzedFile = getSourceFileName(varDecl);

    if ((varDecl && varDecl->hasLinkage()) || varDecl->isStaticLocal()) {
      concurrentCout("start : FindInformationClassVisitor::VisitVarDecl");
      currentAnalyzedFile = getSourceFileName(varDecl);
      string str = "";
      string typeName = varDecl->getType().getAsString();
      if (typeName.find("(anonymous") != string::npos) {
        typeName = varDecl->getDeclName().getAsString() + "_struct";
        if (traversingData->typesMap.count(typeName) == 0) {
          traversingData->typesMap[typeName] = new string(typeName);
          string fileName = *getSourceFileName(varDecl);
          string line = to_string(getLineNumberDecl(varDecl));
          string col = to_string(getColumnNumberDecl(varDecl));
          if (potentialAnonymousStructORUnions.count(fileName + ":" + line) == 1
          ) {
            TypeDefStructOrUnion *typedefAnonymous =
                potentialAnonymousStructORUnions[fileName + ":" + line];
            typedefAnonymous->setName(typeName);
            this->traversingData->typeDefsmap[typeName] = typedefAnonymous;
          }
        }
        str = "STRUCT";

      } else {
        str = parseQualTypeSimple(varDecl->getType(), this->traversingData);
      }
      if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
        if (varDecl->isStaticLocal()) {
          if (addressAndCounter.count(varDecl) <= 0) {
            variableName = appendStringToFunctionStaticLabels(
              variableName, varDecl);
          } else {
            return true;
          }
        }

        if (varDecl->getStorageClass() == 2 && !varDecl->isStaticLocal()) {
          variableName = appendStringToFileStaticLabels(
            variableName, *getSourceFileName(varDecl));
        }
      }
      varType varTypeResult = isTypeDef(getStructName(typeName),
                                        &this->traversingData->typeDefsmap);

      string isTypedefString = varTypeResult.isTypeDef ? "true" : "false";
      string isConstString = varTypeResult.isConst ? "true" : "false";
      string isVolatileString = varTypeResult.isVolatile ? "true" : "false";

      GlobalVariable *g = new GlobalVariable(variableName,
                                             getStructName(typeName),
                                             getSourceFileName(varDecl),
                                             getLineNumberDecl(varDecl),
                                             getColumnNumberDecl(varDecl), str,
                                             isTypedefString, isConstString,
                                             isVolatileString);
      if (str.compare("POINTER_ON_STRUCT") == 0) {
        string pointeeType = varDecl->getType()->getPointeeType().getAsString();
        if (pointeeType.find_first_of("struct ") == 0) {
          pointeeType = pointeeType.substr(7);
        }
        g->setPointeeType(pointeeType);
      }
      if (g != nullptr) {
        this->traversingData->internalGlobalVariablesMap[variableName] = g;
      }
      concurrentCout("end : FindInformationClassVisitor::VisitVarDecl");
    } else if (isCFGActivated && (
                 varDecl && !varDecl->hasLinkage() && !llvm::isa<ParmVarDecl
                 >(varDecl))) {
      //local variable

      bool isPointer = varDecl->getType()->isPointerType();

      if (isPointer) {
        //varDecl->dumpColor();
        isInLocalVariableWithPointerType = true;
        localVarDeclOfPointerType = varDecl;
        string functionName = "";
        if (currentAnalyzedFunction && currentAnalyzedFunction != nullptr) {
          functionName = currentAnalyzedFunction
                         ->getFunctionEntry()->getFunctionName();
          addLocalValiableToGlobalMap(varDecl, functionName, nullptr);
          //cout << "--------------added " << varDecl->getNameAsString() << endl;
          getGlobalVariableAsProxy(varDecl);
        }
      }
    } else {
      //cout << "--------------not added " << varDecl->getNameAsString() << endl;
    }

  } catch (...) {
    cerr <<
        "An error happened during the Parsing of variables declarations inside  the file: "
        << *currentAnalyzedFile << endl;
    //cerr << "Standard exception: " << e.what() << endl;
    if (currentAnalyzedFile != nullptr)
      this->traversingData->analyzedFiles[*currentAnalyzedFile] = -1;
  }
  //cout << "End variable Decl: " << endl;
  return true;
}


bool FindInformationClassVisitor::isFileAllreadyinserted(string fileName) {
  return this->traversingData->internalStructuralCallGraphs.find(fileName) !=
         this->traversingData->internalStructuralCallGraphs.end();
}

void FindInformationClassVisitor::parseStatementGeneric(Stmt *stmt) {
  //stmt->dumpColor();
  for (Stmt::child_iterator I = stmt->child_begin(), E = stmt->child_end();
       I != E; ++I) {
    Stmt *child = *I;
    selectAndExecuteStatement(child);
  }
}

unsigned int FindInformationClassVisitor::getBlockID(Stmt *stmt) {
  unsigned int block_id = -1;

  if (isCFGActivated) {
    if (mapOfCFGStmts && stmt) {
      try {
        CFGBlock *stmt_block = mapOfCFGStmts->getBlock(stmt);
        block_id = stmt_block->getBlockID();
      } catch (...) {
        cout << "problem retrieving the BlockID" << endl;
      }

    }
  }
  return block_id;
}

void FindInformationClassVisitor::selectAndExecuteStatement(Stmt *child) {
  try {

    if (child) {
      MemberExpr *memberExpr = nullptr;
      switch (child->getStmtClass()) {
      case Stmt::BinaryOperatorClass:
        parseBinaryOperator(child);
        break;
      case Stmt::UnaryOperatorClass:
        parseUnaryOperator(child);
        break;
      case Stmt::ReturnStmtClass:
      case Stmt::DeclStmtClass:
      case Stmt::CompoundStmtClass:
      case Stmt::ForStmtClass:
      case Stmt::SwitchStmtClass:
      case Stmt::WhileStmtClass:
      case Stmt::DoStmtClass:
      case Stmt::CaseStmtClass:
      case Stmt::DefaultStmtClass:
      case Stmt::ParenExprClass:
        parseStatementGeneric(child);
        break;
      case Stmt::IfStmtClass:
        parseIfStmt(child);
        break;
      case Stmt::CompoundAssignOperatorClass:
        parseBinaryOperator(child);
        break;
      case Stmt::CallExprClass:
        parseCallExprOperator(child);
        break;
      case Stmt::ImplicitCastExprClass:
        parseImplicitCastExpression(child);
        break;
      case Stmt::MemberExprClass:
        memberExpr = llvm::cast<MemberExpr>(child);
        memberExprName = memberExpr
                         ->getMemberNameInfo().getName().getAsString();
        parseStatementGeneric(child);
        memberExprName = "";
        break;
      default:
        parseStatementGeneric(child);
        break;
      }
    }
  } catch (...) {
    cerr << "An error happened in selectandExecute  " << child->
        getStmtClassName() << std::endl;
    //child->dumpColor();
  }
}

void FindInformationClassVisitor::parseUnaryOperator(Stmt *statement) {
  UnaryOperator *u = llvm::cast<UnaryOperator>(statement);
  MemberExpr *memberExpr = nullptr;
  DeclRefExpr *declRef = nullptr;
  unaryOperatorStr = u->getOpcodeStr(u->getOpcode()).str();

  if (u->getOpcodeStr(u->getOpcode()).str().compare("&") != 0 && u
                                                                 ->getOpcodeStr(
                                                                   u->
                                                                   getOpcode()).
                                                                 str().compare(
                                                                   "*") != 0) {
    isCurrentWrite = true;
  }

  for (Stmt::child_iterator I = statement->child_begin(), E = statement->
                                child_end(); I != E; ++I) {
    Stmt *child = *I;
    switch (child->getStmtClass()) {
    case Stmt::DeclRefExprClass:
      declRef = llvm::cast<DeclRefExpr>(child);
      parseDeclRefExpressionInUnaryOperator(u, declRef, child);
      break;
    case Stmt::ImplicitCastExprClass:
      parseImplicitCastExpression(child);
      break;
    case Stmt::MemberExprClass:
      memberExpr = llvm::cast<MemberExpr>(child);
      memberExprName = /*memberExprName + "." + */memberExpr
                                                  ->getMemberNameInfo().
                                                  getName().getAsString();
      for (Stmt::child_iterator I = memberExpr->child_begin(), E = memberExpr->
                                    child_end(); I != E; ++I) {
        Stmt *child = *I;
        switch (child->getStmtClass()) {
        case Stmt::DeclRefExprClass:
          declRef = llvm::cast<DeclRefExpr>(child);
          memberExprName += memberExpr
                            ->getMemberNameInfo().getName().getAsString();
          isInMemberExpression = true;
          parseDeclRefExpressionInUnaryOperator(u, declRef, child);
          isInMemberExpression = false;
          memberExprName = "";
          break;
        case Stmt::MemberExprClass:
          memberExpr = llvm::cast<MemberExpr>(child);
          memberExprName = /*memberExprName + "." + */memberExpr
                                                      ->getMemberNameInfo().
                                                      getName().getAsString();

          for (Stmt::child_iterator
                   I = memberExpr->child_begin(), E = memberExpr->child_end();
               I != E; ++I) {
            Stmt *child = *I;
            switch (child->getStmtClass()) {
            case Stmt::DeclRefExprClass:
              declRef = llvm::cast<DeclRefExpr>(child);
              memberExprName += memberExpr
                                ->getMemberNameInfo().getName().getAsString();
              isInMemberExpression = true;
              parseDeclRefExpressionInUnaryOperator(u, declRef, child);
              isInMemberExpression = false;
              memberExprName = "";
              break;
            case Stmt::ImplicitCastExprClass:
              parseImplicitCastExpression(child);
              break;
            default:
              //cout << "inside parseUnaryOperator() -> MemberExprClass -> MemberExprClass -> default: this case is not handeld." << endl;
              break;
            }
          }
          memberExprName = "";
          //parseUnaryOperator(child);
          break;
        case Stmt::ArraySubscriptExprClass:
          //Sudhindra adding for the task 166812..This case is for recognising arrays(when the address of the array of structs is passed to a runnable) as label accesses.
          if (u->getOpcodeStr(u->getOpcode()).str().compare("&") != 0) {
            isCurrentWrite = true;
          }
          parseStatementGeneric(child);
          isCurrentWrite = false;
          break;
        case Stmt::ImplicitCastExprClass:
          parseImplicitCastExpression(child);
          break;
        default:
          cout <<
              "inside parseUnaryOperator() -> MemberExprClass -> default: this case is not handeld."
              << endl;
          break;
        }
        memberExprName = "";
      }
      break;

    case Stmt::ArraySubscriptExprClass:
      if (u->getOpcodeStr(u->getOpcode()).str().compare("&") != 0) {
        isCurrentWrite = true;
      }
      parseStatementGeneric(child);
      isCurrentWrite = false;
      break;
    case Stmt::ParenExprClass:
      parseStatementGeneric(child);
      break;
    default:
      break;
    }
  }
  unaryOperatorStr = "";
  isCurrentWrite = false;
}

void FindInformationClassVisitor::parseIfStmt(Stmt *stmt) {
    auto ifStmt = llvm::cast<IfStmt>(stmt);
    Stmt *condition = ifStmt->getCond();
    Stmt *ifPart = ifStmt->getThen();
    Stmt *elsePart = ifStmt->getElse();
    CallGraphItemContainer *currentContainer = currentAnalyzedFunction->getCurrentContainer();
  
    selectAndExecuteStatement(condition);

    auto modeSwitch = new ModeSwitch(getLineNumberStmt(ifStmt), getColumnNumberStmt(ifStmt), getBlockID(ifStmt));
    const auto ifModeEntry = new ModeEntry();
    modeSwitch->addModeEntry(ifModeEntry);
    currentAnalyzedFunction->getCurrentContainer()->addChild(modeSwitch);
    currentAnalyzedFunction->setCurrentContainer(ifModeEntry);
    selectAndExecuteStatement(ifPart);

    if (elsePart) {
      const auto elseModeEntry = new ModeEntry();
      modeSwitch->addModeEntry(elseModeEntry);
      currentAnalyzedFunction->setCurrentContainer(elseModeEntry);
      selectAndExecuteStatement(elsePart);
    }

    // Reset the container to the one before the recursion
    currentAnalyzedFunction->setCurrentContainer(currentContainer);
}

void FindInformationClassVisitor::parseDeclRefExpressionInUnaryOperator(
  UnaryOperator *u, DeclRefExpr *declRef, Stmt *child) {
  //string operatorStr = u->getOpcodeStr(u->getOpcode()).str();
  VarDecl *varDecl;
  bool isStatic = false;
  //Sudhindra adding if to solve casting problem in clang 3.8
  if (!declRef->getDecl()->isFunctionOrFunctionTemplate() && !EnumConstantDecl::
      classof(declRef->getDecl())) {
    varDecl = llvm::cast<VarDecl>(declRef->getDecl());
    isStatic = varDecl->isStaticLocal();
    bool isVariableToBeConsidered =
        isVariableOrParameterToBeConsideredAsGlobal(varDecl);

    //if (declRef->getDecl()->hasLinkage() || isStatic || isVariableToBeConsidered){
    string variableName = declRef->getDecl()->getDeclName().getAsString();
    if (isInMemberExpression) {
      variableName = variableName + "." + memberExprName;
    }
    if (!declRef->getDecl()->isFunctionOrFunctionTemplate()) {
      std::string str = parseQualTypeSimple(varDecl->getType(),
                                            this->traversingData);
      if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
        if (varDecl->getStorageClass() == 2 && !varDecl->isStaticLocal()) {
          variableName = appendStringToFileStaticLabels(
            variableName, *getSourceFileName(varDecl));
        }
        if (varDecl->isStaticLocal()) {
          VisitVarDecl(varDecl);
          variableName = appendStringToFunctionStaticLabels(
            variableName, varDecl);
        }
      }
    } else {
      cout <<
          "++++++++++++++++An error happened in parseDeclRefExpressionInUnaryOperator while casting like before 2"
          << std::endl;
    }

    if (isVariableToBeConsidered) {
      variableName = getUniqueVariableName(varDecl, variableName);
    }
    if (unaryOperatorStr.compare("++") == 0 || unaryOperatorStr.compare("--") ==
        0) {
      if (u->isPostfix()) {

        addNewGlobalAccess(child, variableName, READ, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
        addNewGlobalAccess(child, variableName, WRITE, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
      } else {
        addNewGlobalAccess(child, variableName, WRITE, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
        addNewGlobalAccess(child, variableName, READ, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
      }
    } else {
      if (isCurrentWrite) {
        addNewGlobalAccess(child, variableName, WRITE, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
      } else {
        addNewGlobalAccess(child, variableName, READ, getLineNumberStmt(child),
                           getColumnNumberStmt(child));
      }

    }

    //}
  } else if (unaryOperatorStr.compare("&") == 0 && declRef
                                                   ->getDecl()->
                                                   isFunctionOrFunctionTemplate()
  ) {
    //this is done to identify function pointer assignments with the & symbol.The same piece of code is called when & symbol is used  while calling a function which accepts function pointer..
    string variableName = declRef->getDecl()->getDeclName().getAsString();
    FunctionDecl *declartion = llvm::cast<FunctionDecl>(declRef->getDecl());
    string category = parseQualTypeSimple(declartion->getReturnType(),
                                          this->traversingData);
    FunctionEntry *f = mapOfFunctions[variableName];
    //when a function is only declared and if it is used in function pointer assignemnts before it is defined , then we assume that it is defined in a dummy c file called function_pointer.c.
    string *filename = new string("function_Pointer.c");

    std::vector<FunctionParameter*> parameters;

    //Added to handle static functions for the task 178563
    if (!declartion->isGlobal() && !declartion->isInlineSpecified()) {
      variableName = appendStringToStaticFunctions(
        variableName, getProperFileForStaticFunction(declartion));
    }
    variableName = variableName.append("-FPTR");
    if (f == nullptr) {
      f = new FunctionEntry(filename, variableName, 0, 0,
                            declartion->getReturnType().getAsString(),
                            parameters);
    }
    GlobalVariable *g = nullptr;
    if (f != nullptr) {
      g = new GlobalVariable(variableName, f->getReturnType(), f->getFileName(),
                             f->getLine(), f->getColumn(), category, "", "",
                             "");
    }
    if (g != nullptr) {
      this->traversingData->internalGlobalVariablesMap[variableName] = g;
    }

    addNewGlobalAccess(child, variableName, READ, getLineNumberStmt(child),
                       getColumnNumberStmt(child));
  }

}

void FindInformationClassVisitor::parseBinaryOperator(Stmt *statement) {
  //statement->dumpColor();
  BinaryOperator *b = llvm::cast<BinaryOperator>(statement);

  //string operatorStr = b->getOpcodeStr(b->getOpcode()).str();
  Expr *leftSide = b->getLHS();
  Expr *rightSide = b->getRHS();

  if (b->isAssignmentOp() || b->isCompoundAssignmentOp() || b->isShiftAssignOp()
  ) {

    parseLeftSideBinaryOperator(leftSide);
    parseRightSideBinaryOperator(rightSide);

    if (isCFGActivated && b->isAssignmentOp()) {
      checkForVariableSwapping(leftSide, rightSide);
    }

  } else if (b->isAdditiveOp() || b->isMultiplicativeOp() || b->isComparisonOp()
             || b->isEqualityOp() || b->isLogicalOp() || b->isShiftOp() || b->
             isBitwiseOp()) {
    parseRightSideBinaryOperator(leftSide);
    parseRightSideBinaryOperator(rightSide);

  } else {
    //parse special case ","-Operator
  }

  //TODO: Continue the other cases
}


//Check if a variable swapping is possible and store the necessary information for that
void FindInformationClassVisitor::checkForVariableSwapping(
  Expr *leftSide, Expr *rightSide) {
  try {
    if (llvm::isa<DeclRefExpr>(leftSide) && (
          llvm::isa<UnaryOperator>(rightSide) || llvm::isa<ImplicitCastExpr>(
            rightSide))) {

      DeclRefExpr *leftSideDeclRef = llvm::cast<DeclRefExpr>(leftSide);
      DeclRefExpr *rightSideDeclRef = getDeclRefExprForSwapping(rightSide);

      if (isCFGActivated && rightSideDeclRef != nullptr && !leftSideDeclRef
                                                            ->getDecl()->
                                                            hasLinkage() && !
          leftSideDeclRef->getDecl()->isFunctionOrFunctionTemplate() && !
          EnumConstantDecl::classof(leftSideDeclRef->getDecl())) {
        if (currentAnalyzedFunction && currentAnalyzedFunction != nullptr) {
          string functionName = currentAnalyzedFunction
                                ->getFunctionEntry()->getFunctionName();
          string localVarName = leftSideDeclRef
                                ->getDecl()->getDeclName().getAsString();
          localVarName = localVarName + CONCAT_F + functionName;
          addSwappingInformation(localVarName, rightSideDeclRef, -1);
        }
      }

    }
  } catch (...) {
    cerr << "Problem happenned in checkForVariableSwapping method " << endl;
  }
}

void FindInformationClassVisitor::addSwappingInformation(
  string localVarName, DeclRefExpr *rightSideDeclRef, int blockID) {
  try {
    if (rightSideDeclRef && rightSideDeclRef->getDecl()->hasLinkage()) {
      int blockIDCalculated = blockID;
      if (blockID == -1) {
        blockIDCalculated = getBlockID(rightSideDeclRef);
        cout << "blockID=" << blockIDCalculated << endl;
      }
      int lineNumber = getLineNumberStmt(rightSideDeclRef);
      string globalVariableName = rightSideDeclRef
                                  ->getDecl()->getNameAsString();
      SwappingInfo *swappingInfo = new SwappingInfo(
        blockIDCalculated, localVarName, lineNumber, globalVariableName);
      currentAnalyzedFunction->addSwappingInfo(swappingInfo);
    }
  } catch (...) {
    cerr << "Problem happenned in addSwappingInformation method " << endl;
  }

}

void FindInformationClassVisitor::parseLeftSideBinaryOperator(Expr *lhs) {
  try {
    string variableName = "";
    DeclRefExpr *declRef = nullptr;
    MemberExpr *memberExpr = nullptr;
    ParenExpr *parenExpr = nullptr;
    VarDecl *varDecl;
    bool isStatic;
    bool isVariableToBeConsidered = false;
    //ImplicitCastExpr * implicitCastExpr = 0;
    //ArraySubscriptExpr* arraySubscript = 0;
    switch (lhs->getStmtClass()) {
    case Stmt::BinaryOperatorClass:
      parseBinaryOperator(lhs);
      break;
    case Stmt::UnaryOperatorClass:
      isCurrentWrite = true;
      parseUnaryOperator(lhs);
      isCurrentWrite = false;
      break;
    case Stmt::DeclRefExprClass:
      declRef = llvm::cast<DeclRefExpr>(lhs);
      //Sudhindra adding if for casting problem in clang 3.8
      if (!declRef->getDecl()->isFunctionOrFunctionTemplate() && !
          EnumConstantDecl::classof(declRef->getDecl())) {
        varDecl = llvm::cast<VarDecl>(declRef->getDecl());
        isStatic = varDecl->isStaticLocal();
        isVariableToBeConsidered = isVariableOrParameterToBeConsideredAsGlobal(
          varDecl);
      }

      if ((/*declRef->getDecl()->hasLinkage() && */!EnumConstantDecl::
          classof(declRef->getDecl()))
          /*|| isStatic || isVariableToBeConsidered*/
      ) {
        variableName = declRef->getDecl()->getDeclName().getAsString();
        if (isInMemberExpression) {
          variableName = variableName + "." + memberExprName;
        }
        if (!declRef->getDecl()->isFunctionOrFunctionTemplate()) {
          std::string str = parseQualTypeSimple(varDecl->getType(),
                                                this->traversingData);

          if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
            if (varDecl->getStorageClass() == 2 && !varDecl->isStaticLocal()) {
              variableName = appendStringToFileStaticLabels(
                variableName, *getSourceFileName(varDecl));
            }
            if (varDecl->isStaticLocal()) {
              VisitVarDecl(varDecl);
              variableName = appendStringToFunctionStaticLabels(
                variableName, varDecl);
            }
          }
        }
        if (isVariableToBeConsidered) {
          variableName = getUniqueVariableName(varDecl, variableName);
        }
        addNewGlobalAccess(lhs, variableName, WRITE, getLineNumberStmt(declRef),
                           getColumnNumberStmt(declRef));
      }
      break;
    case Stmt::MemberExprClass:
      memberExpr = llvm::cast<MemberExpr>(lhs);
      for (Stmt::child_iterator I = memberExpr->child_begin(), E = memberExpr->
                                    child_end(); I != E; ++I) {
        Stmt *child = *I;
        switch (child->getStmtClass()) {
        case Stmt::DeclRefExprClass:
          declRef = llvm::cast<DeclRefExpr>(child);
          memberExprName =
              memberExpr->getMemberNameInfo().getName().getAsString() + "." +
              memberExprName;
          isInMemberExpression = true;
          parseLeftSideBinaryOperator(declRef);
          isInMemberExpression = false;
          memberExprName = "";
          break;
        case Stmt::MemberExprClass:
          memberExprName = memberExpr
                           ->getMemberNameInfo().getName().getAsString();
          parseLeftSideBinaryOperator(llvm::cast<MemberExpr>(child));
          memberExprName = "";
          break;
        case Stmt::ImplicitCastExprClass:
          //in member of a pointer on struct type ( var->member)
          memberExprName = memberExpr
                           ->getMemberNameInfo().getName().getAsString();
          isInMemberExpression = true;
          isCurrentWrite = true;
          parseImplicitCastExpression(child);
          isCurrentWrite = false;
          isInMemberExpression = false;
          memberExprName = "";
          break;
        default:
          isCurrentWrite = true;
          parseStatementGeneric(lhs);
          isCurrentWrite = false;
          break;
        }
      }
      break;
    case Stmt::ImplicitCastExprClass:
      parseImplicitCastExpression(lhs);
      break;
    case Stmt::CallExprClass:
      parseCallExprOperator(lhs);
      break;
    case Stmt::ArraySubscriptExprClass:
      isCurrentWrite = true;
      //arraySubscript = llvm::cast<ArraySubscriptExpr>(lhs);
      parseStatementGeneric(lhs);
      isCurrentWrite = false;
      break;
    case Stmt::ParenExprClass:
      //added for the task id 158778. This case identifies all the labels enclosed in angular brackets with accessType as WRITE.
      parenExpr = llvm::cast<ParenExpr>(lhs);
      for (Stmt::child_iterator I = parenExpr->child_begin(), E = parenExpr->
                                    child_end(); I != E; ++I) {
        Stmt *child = *I;
        switch (child->getStmtClass()) {
        case Stmt::DeclRefExprClass:
          declRef = llvm::cast<DeclRefExpr>(child);
          parseLeftSideBinaryOperator(declRef);
          break;
        case Stmt::ParenExprClass:
          //added for the task id 158778. This case identifies all the lables enclosed in multiple angular brackets with accessType as WRITE.
          parenExpr = llvm::cast<ParenExpr>(child);
          parseLeftSideBinaryOperator(parenExpr);
          break;
        default:
          selectAndExecuteStatement(child);
          break;
        }
      }
      break;
    default:
      parseStatementGeneric(lhs);
      break;
    }
  } catch (...) {
    cerr << "An error happened in parseLeftSideBinaryOperator " << lhs->
        getStmtClassName() << endl;
  }
}

string *FindInformationClassVisitor::getStructMemberType(
  string memberName, string structTypeName) {
  TypeDefGeneral *t = this->traversingData->typeDefsmap[structTypeName];
  list<TypeDefGeneral*> *listOfMembers = nullptr;
  string categorie = "";
  string referencedType = "";
  string localMemberName = "";
  static string result[2] = {"STRUCT", "ANONYMOUS"};
  if (t != nullptr) {
    listOfMembers = ((TypeDefStructOrUnion*)t)->getMembers();
    if (listOfMembers != nullptr && listOfMembers->size() > 0) {
      set<string> anonymousStructs;
      for (TypeDefGeneral *tMember : *listOfMembers) {
        if (tMember->getName().compare(memberName) == 0) {
          result[0] = *(tMember->getReferencedType());
          result[1] = enumToString(tMember->getTypeDefType());
          return result;
        }

        int n = localMemberName.find_first_of(".");
        if (n != string::npos) {
          anonymousStructs.insert(tMember->getName().substr(0, n));
        }
      }
      if (anonymousStructs.size() > 0) {
        for (set<string>::iterator it = anonymousStructs.begin();
             it != anonymousStructs.end(); ++it) {
          if (it->compare("memberName") == 0) {
            return result;
          }
        }
      }
    }
  }
  return nullptr;
}

GlobalVariable *FindInformationClassVisitor::
createGlobalVariableFromLocalMember(string variableName) {
  int endStructVarName = -1;
  int startMemberName = -1;
  GlobalVariable *g = nullptr;
  string referencedType = "UNKNOW_TYPE";
  string category = "UNKNOW_CAT";

  if (variableName.find_first_of(".") != string::npos) {
    endStructVarName = variableName.find_first_of(".");
    startMemberName = endStructVarName + 1;
  } else if (variableName.find_first_of(POINTS2) != string::npos) {
    string t(POINTS2);
    endStructVarName = variableName.find_first_of(POINTS2);
    startMemberName = endStructVarName + t.size();
  }
  if (endStructVarName > 0) {
    string structVariableName = variableName.substr(0, endStructVarName);
    structVariableName = structVariableName + CONCAT_F + currentAnalyzedFunction
                                                         ->getFunctionEntry()->
                                                         getFunctionName();
    if (this->traversingData->mapOfAllLocalAndParameter.count(
          structVariableName) == 1) {
      try {
        g = this->traversingData->mapOfAllLocalAndParameter[structVariableName];
        string structTypeName = g->getPointeeType();
        int pos = -1;
        if (structTypeName.find("struct ") != string::npos) {
          pos = structTypeName.find_last_of("struct ");
        }
        if (pos != -1) {
          structTypeName = structTypeName.substr(pos + 7);
        }
        string memberName = variableName.substr(
          startMemberName, variableName.find_last_of(CONCAT_F));
        string *memberTypeInfo =
            getStructMemberType(memberName, structTypeName);
        if (memberTypeInfo != nullptr) {
          referencedType = memberTypeInfo[0];
          category = memberTypeInfo[1];
        }
        GlobalVariable *globalVariable = new GlobalVariable(
          variableName, referencedType, g->getFileName(), g->getLine(),
          g->getColumn(), category, "", "", "");
        this->traversingData->mapOfAllLocalAndParameter[variableName] =
            globalVariable;
        return globalVariable;
      } catch (...) {
        cerr << "An error happenned in addNewGlobalAccess: " << endl;

      }

    } else {
      GlobalVariable *globalVariable = new GlobalVariable(
        variableName, referencedType,
        currentAnalyzedFunction->getFunctionEntry()->getFileName(), -1, -1,
        category, "", "", "");
      this->traversingData->mapOfAllLocalAndParameter[variableName] =
          globalVariable;
      return globalVariable;
    }
  }
  return nullptr;
}

//TODO add local variables of simple types(not structs)
GlobalVariable *FindInformationClassVisitor::createGlobalVariableFromLocal(
  string variableName) {
  int endStructVarName = -1;
  int startMemberName = -1;
  GlobalVariable *g = nullptr;
  string referencedType = "UNKNOW_TYPE";
  string category = "UNKNOW_CAT";

  if (variableName.find_first_of(".") != string::npos) {
    endStructVarName = variableName.find_first_of(".");
    startMemberName = endStructVarName + 1;
  } else if (variableName.find_first_of(POINTS2) != string::npos) {
    string t(POINTS2);
    endStructVarName = variableName.find_first_of(POINTS2);
    startMemberName = endStructVarName + t.size();
  }
  if (endStructVarName == -1) {
    //is not a struct member variable-> it is a normal variable of type pointer
    GlobalVariable *globalVariable = new GlobalVariable(
      variableName, referencedType,
      currentAnalyzedFunction->getFunctionEntry()->getFileName(), -1, -1,
      category, "", "", "");
    //this->traversingData->mapOfAllLocalAndParameter[variableName] = globalVariable;
    return globalVariable;
  }
  return nullptr;
}


void FindInformationClassVisitor::addNewGlobalAccess(
  Stmt *stmt, string variableName, string accessType, unsigned lineNumber,
  unsigned columnNumber) {

  GlobalVariable *g = nullptr;
  unsigned int blockID = -1;
  if (isCFGActivated) {

    blockID = getBlockID(stmt);

  }

  if (this->traversingData->internalGlobalVariablesMap.count(variableName) == 1
  ) {
    g = this->traversingData->internalGlobalVariablesMap[variableName];
  } else if (isCFGActivated && variableName.find(CONCAT_F) != string::npos) {
    if ((this->traversingData->mapOfAllLocalAndParameter.count(variableName) ==
         1)) {
      g = this->traversingData->mapOfAllLocalAndParameter[variableName];
    } else if (isCFGActivated && variableName.find_first_of(POINTS2) == string::
               npos) {
      g = createGlobalVariableFromLocal(variableName);
      //cout << variableName << " - " << blockID << endl;
    } else if (isCFGActivated) {
      //member of a local variable of type Struct is being accessed
      g = createGlobalVariableFromLocalMember(variableName);
      //cout << variableName << " - " << blockID << endl;
    }
  } else {
    string *fileName = new string("Undefined");
    g = new GlobalVariable(variableName, "Undefined", fileName, 0, 0, "", "",
                           "", "");
  }

  GlobalVariableAccess *globalVariableAccess = new GlobalVariableAccess(
    g, accessType, lineNumber, columnNumber, blockID);
  //if (!isGlobalVariableAlreadyAdded(currentAnalyzedFunction->getGlobalVariableAccesses(), globalVariableAccess)){
  currentAnalyzedFunction->getCurrentContainer()->addChild(globalVariableAccess);
  currentAnalyzedFunction->addGlobalVariableAccess(globalVariableAccess);
  //}	

}

void FindInformationClassVisitor::parseRightSideBinaryOperator(Expr *rhs) {
  try {
    switch (rhs->getStmtClass()) {
    case Stmt::BinaryOperatorClass:
    case Stmt::CompoundAssignOperatorClass:
      parseBinaryOperator(rhs);
      break;
    case Stmt::UnaryOperatorClass:
      parseUnaryOperator(rhs);
      break;
    case Stmt::ImplicitCastExprClass:
      parseImplicitCastExpression(rhs);
      break;
    case Stmt::CallExprClass:
      parseCallExprOperator(rhs);
      break;
    case Stmt::ArraySubscriptExprClass:
      parseStatementGeneric(rhs);
      break;
    case Stmt::DeclRefExprClass:
      parseStatementGeneric(rhs);
      break;
    default:
      parseStatementGeneric(rhs);
      break;
    }
  } catch (...) {
    cerr << "An error happened in parseRightSideBinaryOperator " << rhs->
        getStmtClassName() << endl;
  }
}

void FindInformationClassVisitor::parseCallExprOperator(Stmt *statement) {
  int i = 0;
  for (Stmt::child_iterator I = statement->child_begin(), E = statement->
                                child_end(); I != E; ++I) {
    Stmt *child = *I;
    if (i == 0) {
      parseFunctionCall(child);
    } else {
      selectAndExecuteStatement(child);
    }
    i++;
  }
}

void FindInformationClassVisitor::parseFunctionCall(Stmt *statement) {
  string functionName = "";
  int line, col = 0;
  DeclRefExpr *declRef;
  unsigned int blockID = -1;
  string returnType = "";
  std::vector<FunctionParameter*> parameters;
  std::string fileName = "";
  for (Stmt::child_iterator I = statement->child_begin(), E = statement->
                                child_end(); I != E; ++I) {
    Stmt *child = *I;
    string fileNameOfDeclaration = "FunctionPointerCall";
    FullSourceLoc fullLocation = Context->getFullLoc(child->getBeginLoc());
    FunctionEntry *functionDeclaration;
    CallEntry *callEntry;
    FunctionDecl *declartion;

    switch (child->getStmtClass()) {
    case Stmt::DeclRefExprClass:
      declRef = llvm::cast<DeclRefExpr>(child);
      line = getLineNumberStmt(child);
      col = getColumnNumberStmt(child);
      //Sudhindra adding if for casting problem in clang 3.8
      if (declRef->getDecl()->isFunctionOrFunctionTemplate()) {
        declartion = llvm::cast<FunctionDecl>(declRef->getDecl());

        functionName = declartion->getDeclName().getAsString();

        //added for handling static functions for the task 178563				
        if (!declartion->isGlobal() && !declartion->isInlineSpecified()) {
          functionName = appendStringToStaticFunctions(
            functionName, getProperFileForStaticFunction(declartion));
        }

        //cout << functionName << endl;
        functionDeclaration = mapOfFunctions[functionName];

        if (functionDeclaration) {
          //fileNameOfDeclaration = functionDeclaration->getFileName();
        } else {
          if (functionName.length() > 0) {
            fileNameOfDeclaration = functionName;
          }
          string *fName = new string("function_Pointer.c");
          if (declRef->getDecl()->isFunctionOrFunctionTemplate()) {
            FunctionDecl *declartion = llvm::cast<FunctionDecl>(
              declRef->getDecl());
            returnType = declartion->getReturnType().getAsString();
          }

          functionDeclaration = new FunctionEntry(
            fName, fileNameOfDeclaration, 0, 0, returnType, parameters);
          mapOfFunctions[functionName] = functionDeclaration;
        }
      } else if (llvm::isa<VarDecl>(declRef->getDecl())) {
        VarDecl *varDecl = llvm::cast<VarDecl>(declRef->getDecl());
        string *fName = new string("function_Pointer.c");
        functionDeclaration = new FunctionEntry(
          fName, varDecl->getDeclName().getAsString(), 0, 0,
          varDecl->getType().getAsString(), parameters);
      }
      if (isCFGActivated) {
        blockID = getBlockID(child);
      }
      callEntry = new CallEntry(functionDeclaration, line, col, blockID);
      currentAnalyzedFunction->getCurrentContainer()->addChild(callEntry);
      currentAnalyzedFunction->addCallee(callEntry);

      break;
    case Stmt::ParenExprClass:
      // go twice deep in the hierarchy

      for (Stmt::child_iterator J = child->child_begin(), EJ = child->
                                    child_end(); J != EJ; ++J) {
        Stmt *child1 = *J;
        for (Stmt::child_iterator
                 J1 = child1->child_begin(), EJ1 = child1->child_end();
             J1 != EJ1; ++J1) {
          Stmt *child2 = *J1;
          switch (child2->getStmtClass()) {
          case Stmt::ImplicitCastExprClass:
            parseFunctionCall(child2);
            break;
          default:
            break;
          }
        }
      }
      break;
    default:
      break;
    }
  }
}


// checked against memory consumption

void FindInformationClassVisitor::parseImplicitCastExpression(Stmt *statement) {

  DeclRefExpr *declRef = nullptr;
  MemberExpr *memberExpr = nullptr;
  ParenExpr *parenExpr = nullptr;
  string variableName = "";
  VarDecl *varDecl;
  bool isStatic = false;
  bool isVariableToConsider = false;
  for (Stmt::child_iterator I = statement->child_begin(), E = statement->
                                child_end(); I != E; ++I) {
    Stmt *child = *I;
    try {
      switch (child->getStmtClass()) {
      case Stmt::DeclRefExprClass:
        declRef = llvm::cast<DeclRefExpr>(child);
        //Sudhindra adding if to solve the casting problem in clang 3.8 
        if (!declRef->getDecl()->isFunctionOrFunctionTemplate() && !
            EnumConstantDecl::classof(declRef->getDecl())) {
          varDecl = llvm::cast<VarDecl>(declRef->getDecl());
          isVariableToConsider = isVariableOrParameterToBeConsideredAsGlobal(
            varDecl);
          isStatic = varDecl->isStaticLocal();
        }
        if ((/*declRef->getDecl()->hasLinkage() && */!EnumConstantDecl::
          classof(declRef->getDecl()))/* || isStatic*/) {
          //explicitly made to avoid constant enum to be recognized as Label access in function calls
          variableName = declRef->getDecl()->getDeclName().getAsString();
          if (!declRef->getDecl()->isFunctionOrFunctionTemplate()) {
            std::string str = parseQualTypeSimple(
              varDecl->getType(), this->traversingData);
            if (str.compare("POINTER_ON_STRUCT") == 0 && memberExprName.length()
                > 0) {
              variableName = variableName + POINTS2 + memberExprName;
              memberExprName = "";
            }
            if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
              if (varDecl->getStorageClass() == 2 && !varDecl->isStaticLocal()
              ) {
                variableName = appendStringToFileStaticLabels(
                  variableName, *getSourceFileName(varDecl));
              }
              if (varDecl->isStaticLocal()) {
                VisitVarDecl(varDecl);
                variableName = appendStringToFunctionStaticLabels(
                  variableName, varDecl);
              }
            }
          }
          if (declRef->getType()->isFunctionPointerType()) {
            parseFunctionCall(statement);
          } else {
            //this is done to identify function pointer assignments without the & symbol.The same piece of code is called when & symbol is not used  while calling a function which accepts function pointer..
            if (declRef->getDecl()->isFunctionOrFunctionTemplate()) {
              variableName = declRef->getDecl()->getDeclName().getAsString();
              FunctionDecl *declartion = llvm::cast<FunctionDecl>(
                declRef->getDecl());
              string category = parseQualTypeSimple(
                declartion->getReturnType(), this->traversingData);
              FunctionEntry *f = mapOfFunctions[variableName];

              GlobalVariable *g = nullptr;
              //when a function is only declared and if it is used in function pointer assignemnts before it is defined , then we assume that it is defined in a dummy c file called function_pointer.c.
              string *filename = new string("function_Pointer.c");
              //Added for handling static functions for the task 178563
              if (!declartion->isGlobal() && !declartion->isInlineSpecified()) {
                variableName = appendStringToStaticFunctions(
                  variableName, getProperFileForStaticFunction(declartion));
              }

              variableName = variableName.append("-FPTR");
              std::vector<FunctionParameter*> parameters;
              if (f == nullptr) {
                f = new FunctionEntry(filename, variableName, 0, 0,
                                      declartion->getReturnType().getAsString(),
                                      parameters);
              }
              if (f != nullptr) {
                g = new GlobalVariable(variableName, f->getReturnType(),
                                       f->getFileName(), f->getLine(),
                                       f->getColumn(), category, "", "", "");
              }
              if (g != nullptr) {
                this->traversingData->internalGlobalVariablesMap[variableName] =
                    g;
              }
            }
            if (isVariableToConsider) {
              variableName = getUniqueVariableName(varDecl, variableName);
            }
            if (!isCurrentWrite) {
              addNewGlobalAccess(child, variableName, READ,
                                 getLineNumberStmt(declRef),
                                 getColumnNumberStmt(declRef));
              if (unaryOperatorStr.compare("++") == 0 || unaryOperatorStr.
                  compare("--") == 0) {
                addNewGlobalAccess(child, variableName, WRITE,
                                   getLineNumberStmt(declRef),
                                   getColumnNumberStmt(declRef));
              }
            } else {
              if (unaryOperatorStr.compare("++") == 0 || unaryOperatorStr.
                  compare("--") == 0) {
                addNewGlobalAccess(child, variableName, READ,
                                   getLineNumberStmt(declRef),
                                   getColumnNumberStmt(declRef));
              }
              addNewGlobalAccess(child, variableName, WRITE,
                                 getLineNumberStmt(declRef),
                                 getColumnNumberStmt(declRef));
            }

          }
        }
        break;
      case Stmt::ImplicitCastExprClass:
        parseImplicitCastExpression(child);
        break;
      case Stmt::MemberExprClass:
        memberExpr = llvm::cast<MemberExpr>(child);
        memberExprName = memberExpr
                         ->getMemberNameInfo().getName().getAsString();
        isStatic = false;
        for (Stmt::child_iterator
                 I = memberExpr->child_begin(), E = memberExpr->child_end();
             I != E; ++I) {
          Stmt *child2 = *I;
          switch (child2->getStmtClass()) {
          case Stmt::DeclRefExprClass:
            declRef = llvm::cast<DeclRefExpr>(child2);
            //Sudhindra adding if for casting problem in clang 3.8
            if (!declRef->getDecl()->isFunctionOrFunctionTemplate() && !
                EnumConstantDecl::classof(declRef->getDecl())) {
              varDecl = llvm::cast<VarDecl>(declRef->getDecl());
              isStatic = varDecl->isStaticLocal();
            }
            if ((/*declRef->getDecl()->hasLinkage() && */!EnumConstantDecl::
                  classof(declRef->getDecl())) || isStatic) {
              variableName = declRef->getDecl()->getDeclName().getAsString();
              variableName = variableName + "." + memberExpr
                                                  ->getMemberNameInfo().
                                                  getName().getAsString();
              if (!declRef->getDecl()->isFunctionOrFunctionTemplate()) {
                std::string str = parseQualTypeSimple(
                  varDecl->getType(), this->traversingData);
                if (str.compare("POINTER_ON_STRUCT") == 0) {
                  variableName =
                      variableName + "." + memberExpr
                                           ->getMemberNameInfo().getName().
                                           getAsString();
                }
                if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
                  if (varDecl->getStorageClass() == 2 && !varDecl->
                      isStaticLocal()) {
                    variableName = appendStringToFileStaticLabels(
                      variableName, *getSourceFileName(varDecl));
                  }
                  if (varDecl->isStaticLocal()) {
                    VisitVarDecl(varDecl);
                    variableName = appendStringToFunctionStaticLabels(
                      variableName, varDecl);
                  }
                }
              }
              addNewGlobalAccess(child2, variableName, READ,
                                 getLineNumberStmt(declRef),
                                 getColumnNumberStmt(declRef));
            }
            break;
          case Stmt::CallExprClass:
            parseCallExprOperator(child2);
            break;
          case Stmt::ImplicitCastExprClass:
            //added for the task id 158778.This case is for identifying struct members enclosed in angular brackets.
            parseImplicitCastExpression(child2);
            break;
          default:
            parseStatementGeneric(child2);
            break;
          }
        }
        memberExprName = "";
        break;
      case Stmt::BinaryOperatorClass:
        parseBinaryOperator(child);
        break;
      case Stmt::UnaryOperatorClass:
        parseUnaryOperator(child);
        break;
      case Stmt::CallExprClass:
        parseCallExprOperator(child);
        break;
      case Stmt::ArraySubscriptExprClass:
        parseStatementGeneric(child);
        break;
      case Stmt::ParenExprClass:
        //added for the task id 158778. This case identifies all the labels which are enclosed in angular brackets with accessType READ.
        try {
          bool isStatic = false;
          parenExpr = llvm::cast<ParenExpr>(child);
          //	varDecl = llvm::cast<VarDecl>(declRef->getDecl());
          for (Stmt::child_iterator
                   I = parenExpr->child_begin(), E = parenExpr->child_end();
               I != E; ++I) {
            Stmt *child2 = *I;

            switch (child2->getStmtClass()) {
            case Stmt::DeclRefExprClass:
              declRef = llvm::cast<DeclRefExpr>(child2);

              //Sudhindra adding if for casting problem in clang 3.8
              if (!declRef->getDecl()->isFunctionOrFunctionTemplate() && !
                  EnumConstantDecl::classof(declRef->getDecl())) {
                varDecl = llvm::cast<VarDecl>(declRef->getDecl());
                isStatic = varDecl->isStaticLocal();
              }
              if (/*(declRef->getDecl()->hasLinkage() && */!EnumConstantDecl::
                classof(declRef->getDecl())/*) || isStatic*/) {
                variableName = declRef->getDecl()->getDeclName().getAsString();
                if (!declRef->getDecl()->isFunctionOrFunctionTemplate()) {
                  std::string str = parseQualTypeSimple(
                    varDecl->getType(), this->traversingData);
                  if (str.compare("STRUCT") != 0 && str.compare("enum") != 0) {
                    if (varDecl->getStorageClass() == 2 && !varDecl->
                        isStaticLocal()) {
                      variableName = appendStringToFileStaticLabels(
                        variableName, *getSourceFileName(varDecl));
                    }
                    if (varDecl->isStaticLocal()) {
                      VisitVarDecl(varDecl);
                      variableName = appendStringToFunctionStaticLabels(
                        variableName, varDecl);
                    }
                  }
                }
                addNewGlobalAccess(child2, variableName, READ,
                                   getLineNumberStmt(declRef),
                                   getColumnNumberStmt(declRef));
              }
              break;
            case Stmt::ParenExprClass:
              //This case is for identifying the labels which are enclosed in multiple angular brackets with accessType READ.
              parseImplicitCastExpression(child2);
              break;
            default:
              selectAndExecuteStatement(child2);
              break;
            }

          }
        } catch (...) {
          cerr << "An error occured in parenclass " << endl;
        }
        break;
      default:
        parseStatementGeneric(child);
        break;
      }

    } catch (...) {
      //cerr << "An error happened in parseImplicitCastExpression " << child->getStmtClassName() << endl;
      //child->dumpColor();
    }
  }

}

int FindInformationClassVisitor::getLineNumberDecl(Decl *stmt) {
  FullSourceLoc fullLocation = Context->getFullLoc(stmt->getBeginLoc());
  SourceManager &srcMgr = Context->getSourceManager();
  if (fullLocation.isValid()) {
    return srcMgr.getExpansionLineNumber(stmt->getBeginLoc());
  }
  return 0;
}

int FindInformationClassVisitor::getColumnNumberDecl(Decl *stmt) {
  FullSourceLoc fullLocation = Context->getFullLoc(stmt->getBeginLoc());
  SourceManager &srcMgr = Context->getSourceManager();

  if (fullLocation.isValid()) {
    return srcMgr.getExpansionColumnNumber(stmt->getBeginLoc());
  }
  return 0;
}

int FindInformationClassVisitor::getLineNumberStmt(Stmt *stmt) {
  FullSourceLoc fullLocation = Context->getFullLoc(stmt->getBeginLoc());
  SourceManager &srcMgr = Context->getSourceManager();
  if (fullLocation.isValid()) {
    return srcMgr.getExpansionLineNumber(stmt->getBeginLoc());
  }
  return 0;
}

int FindInformationClassVisitor::getColumnNumberStmt(Stmt *stmt) {
  FullSourceLoc fullLocation = Context->getFullLoc(stmt->getBeginLoc());
  SourceManager &srcMgr = Context->getSourceManager();

  if (fullLocation.isValid()) {
    return srcMgr.getExpansionColumnNumber(stmt->getBeginLoc());
  }
  return 0;
}

string *getUniqueFileName(string str, TraversingData *t) {
  string *filename = nullptr;
  //lock_filesMap.lock();
  if (t->filesMap.count(str) == 0) {
    t->filesMap[str] = new string(str);
  }
  filename = t->filesMap[str];
  //lock_filesMap.unlock();

  return filename;
}

string FindInformationClassVisitor::getAbsolutePath(std::string str) {

  TCHAR full_path[MAX_PATH];
  int size = str.size();
  TCHAR *input = new TCHAR[size + 1];
  input[size] = 0;
  std::copy(str.begin(), str.end(), input);
  GetFullPathName(input, MAX_PATH, full_path, nullptr);
  string output;
  // conversion taken here https://stackoverflow.com/questions/6291458/how-to-convert-a-tchar-array-to-stdstring
#ifndef UNICODE
	output = *full_path;
#else
  std::wstring wStr = full_path;
  output = std::string(wStr.begin(), wStr.end());
#endif
  return output;

}

string *FindInformationClassVisitor::getSourceFileName(Decl *stmt) {

  SourceManager &srcMgr = Context->getSourceManager();
  string str = srcMgr.getFilename(stmt->getLocation()).str();
  string str2 = srcMgr.getFileLoc(stmt->getLocation()).printToString(srcMgr);

  int pos = str2.find_first_of(":", 2);
  str2 = str2.substr(0, pos);
  if (str.empty()) {
    str = str2;
  }
  if (str.find("<scratch space>") != string::npos) {
    return new string("external_functions.c");
  }
  str = getAbsolutePath(str);
  return getUniqueFileName(str, this->traversingData);
}

string *FindInformationClassVisitor::getSourceFileName(Stmt *stmt) {

  SourceManager &srcMgr = Context->getSourceManager();
  string str = srcMgr.getFilename(stmt->getBeginLoc()).str();
  //string str3 = srcMgr.getSpellingLoc(stmt->getBeginLoc()).printToString(srcMgr);
  string str2 = srcMgr.getFileLoc(stmt->getBeginLoc()).printToString(srcMgr);

  int pos = str2.find_first_of(":", 2);
  str2 = str2.substr(0, pos);

  if (str.empty()) {
    str = str2;
    //str = getAbsolutePath(str);
  }
  if (str.find("<scratch space>") != string::npos) {
    /*	cout << "Space---getSpellingLoc: " << str2 << endl;
            cout << "Space---getFileLoc: " << str3 << endl; */
    return new string("external_functions.c");
  }
  str = getAbsolutePath(str);
  return getUniqueFileName(str, this->traversingData);
}


TypeDefGeneral *parseQualType(QualType &t, string varName, int line, int col,
                              string *filename, TraversingData *traversingData,
                              std::list<std::tuple<
                                std::string, int, int, std::string>> *
                              currentAnonymousStructTypeNamesList) {
  TypeDefGeneral *typeDefGeneral = nullptr;
  string referencedType = t.getAsString();

  referencedType = getStructName(referencedType);
  if (referencedType.find("(anonymous") != string::npos) {
    referencedType = varName;
    currentAnonymousStructTypeNamesList->push_back(
      make_tuple(*filename, line, col, varName));
  }
  if (traversingData->typesMap.count(referencedType) == 0) {
    traversingData->typesMap[referencedType] = new string(referencedType);
  }

  if (t.getTypePtr()->isStructureType()) {
    typeDefGeneral = new TypeDefStructOrUnion(varName, STRUCT,
                                              traversingData->typesMap[
                                                referencedType]);
  } else if (t.getTypePtr()->isEnumeralType()) {
    typeDefGeneral = new TypeDefStructOrUnion(varName, ENUM,
                                              traversingData->typesMap[
                                                referencedType]);
  } else if (t.getTypePtr()->isUnionType()) {
    //cout << "union" << endl;
    typeDefGeneral = new TypeDefStructOrUnion(varName, UNION,
                                              traversingData->typesMap[
                                                referencedType]);
  } else if (t.getTypePtr()->isArrayType()) {
    typeDefGeneral = new TypeDefArray(varName, ARRAY,
                                      traversingData->typesMap[referencedType]);
  } else if (t.getTypePtr()->isPointerType()) {
    typeDefGeneral = new TypeDefGeneral(varName, POINTER,
                                        traversingData->typesMap[referencedType
                                        ]);
  } else {
    typeDefGeneral = new TypeDefGeneral(varName, PRIMITIVE,
                                        traversingData->typesMap[referencedType
                                        ]);
  }

  if (typeDefGeneral != nullptr) {
    typeDefGeneral->setLine(line);
    typeDefGeneral->setCol(col);
    typeDefGeneral->setFileName(filename);
  }

  return typeDefGeneral;
}


bool FindInformationClassVisitor::VisitTypeDecl(TypeDecl *typeDecl) {
  string typeKind(typeDecl->getDeclKindName());
  bool unknownEnumFound = false;
  if (typeKind.compare("Typedef") == 0) {
    concurrentCout("start : FindInformationClassVisitor::VisitTypeDecl");
    fieldLevel = 0;

    int line = getLineNumberDecl(typeDecl);
    int col = getColumnNumberDecl(typeDecl);
    string *fileName = getSourceFileName(typeDecl);

    string typeDeclName = typeDecl->getNameAsString();
    TypedefDecl *typedefDecl = llvm::cast<TypedefDecl>(typeDecl);
    QualType t = typedefDecl->getUnderlyingType();
    TypeDefGeneral *typeDefGeneral = parseQualType(
      t, typeDeclName, line, col, fileName, this->traversingData,
      this->currentAnonymousStructTypeNamesList);

    if (currentTypeDefForFieldDecl == nullptr && currentListEnumConstants ==
        nullptr && !(typeDefGeneral->getTypeDefType() == UNION || typeDefGeneral
                     ->getTypeDefType() == STRUCT)) {
      //is a simple typedef
      this->traversingData->typeDefsmap[typeDeclName] = typeDefGeneral;
    } else if (typeDefGeneral && (
                 typeDefGeneral->getTypeDefType() == UNION || typeDefGeneral->
                 getTypeDefType() == STRUCT)) {
      // union or struct
      if (currentHigherLevelTypeDef != nullptr) {
        ((TypeDefStructOrUnion*)(typeDefGeneral))->setMembers(
          ((TypeDefStructOrUnion*)currentHigherLevelTypeDef)->getMembers());
        this->traversingData->typeDefsmap[typeDeclName] = typeDefGeneral;
      } else {
        this->traversingData->typeDefsmap[typeDeclName] = typeDefGeneral;
      }

      currentHigherLevelTypeDef = nullptr;
    } else if (currentTypeDefForFieldDecl == nullptr && currentListEnumConstants
               != nullptr && currentListEnumConstants->size() > 0) {

      if (typeDefGeneral && typeDefGeneral->getTypeDefType() == ENUM) {
        ((TypeDefEnum*)(typeDefGeneral))->setElements(currentListEnumConstants);
        this->traversingData->typeDefsmap[typeDeclName] = typeDefGeneral;
      } else {
        unknownEnumFound = true;
      }
    }
    currentTypeDefForFieldDecl = nullptr;
    currentListEnumConstants = nullptr;
    concurrentCout("end : FindInformationClassVisitor::VisitTypeDecl");
    currentRecordHigherLevelDecl = nullptr;
  }
  return true;
}

TypeDefGeneral *getRecordDefinition(RecordDecl *recordDecl,
                                    list<pair<RecordDecl*, TypeDefGeneral*>*> *
                                    listOfRecords) {
  if (listOfRecords != nullptr) {
    for (pair<RecordDecl*, TypeDefGeneral*> *p : *listOfRecords) {
      if (p->first == recordDecl) {
        return p->second;
      }
    }
  }
  return nullptr;
}

bool FindInformationClassVisitor::VisitRecordDecl(RecordDecl *recordDecl) {
  TypeDefType recordType = STRUCT;
  int line = getLineNumberDecl(recordDecl);
  int col = getColumnNumberDecl(recordDecl);
  string fileName = *getSourceFileName(recordDecl);

  bool inHigherLevelRecord = false;
  if (recordDecl->isUnion()) {
    recordType = UNION;
  }
  if (recordDecl->getOuterLexicalRecordContext() == recordDecl) {
    currentAnonymousStructTypeNamesList = new list<tuple<
      string, int, int, string>>();
    listOfRecords = new list<pair<RecordDecl*, TypeDefGeneral*>*>();
    inHigherLevelRecord = true;
    currentTypeDefForFieldDecl = nullptr;
    currentRecordHigherLevelDecl = recordDecl;
  }
  TypeDefStructOrUnion *typeDefStructOrUnionRecord = new TypeDefStructOrUnion(
    recordDecl->getNameAsString(), recordType, nullptr);
  typeDefStructOrUnionRecord->setMembers(new list<TypeDefGeneral*>());
  pair<RecordDecl*, TypeDefGeneral*> *p = new pair<RecordDecl*, TypeDefGeneral*>
      (recordDecl, typeDefStructOrUnionRecord);
  listOfRecords->push_back(p);

  if (inHigherLevelRecord) {
    potentialAnonymousStructORUnions[fileName + ":" + to_string(line)] =
        typeDefStructOrUnionRecord;
  }

  if (inHigherLevelRecord) {
    if (recordDecl->getNameAsString().size() > 0) {
      traversingData->typeDefsmap[recordDecl->getNameAsString()] =
          typeDefStructOrUnionRecord;
    } else {
      currentHigherLevelTypeDef = typeDefStructOrUnionRecord;
    }
  } else {
    if (recordDecl->getNameAsString().size() == 0) {

      for (tuple<string, int, int, string> t : *this->
           currentAnonymousStructTypeNamesList) {
        string filenameTuple = std::get<0>(t);
        int lineTuple = std::get<1>(t);
        int colTuple = std::get<2>(t);
        string typeTuple = std::get<3>(t);
        if (filenameTuple.compare(fileName) == 0 && lineTuple == line && col ==
            colTuple) {
          typeDefStructOrUnionRecord->setName(typeTuple);
          typeDefStructOrUnionRecord->setMembers(new list<TypeDefGeneral*>());
          traversingData->typeDefsmap[typeTuple] = typeDefStructOrUnionRecord;
          break;
        }
      }
    } else {
      traversingData->typeDefsmap[recordDecl->getNameAsString()] =
          typeDefStructOrUnionRecord;
    }
  }
  int numberOfField = 0;
  for (RecordDecl::field_iterator iterator = recordDecl->field_begin();
       iterator != recordDecl->field_end(); ++iterator) {
    FieldDecl *fielDecl = *iterator;
    parseFieldDecl(fielDecl);
    numberOfField++;
  }
  if (numberOfField == 0 && currentHigherLevelTypeDef != nullptr) {
    currentHigherLevelTypeDef = nullptr;
  }
  return true;
}

bool FindInformationClassVisitor::parseFieldDecl(FieldDecl *fieldDecl) {
  int line = getLineNumberDecl(fieldDecl);
  int col = getColumnNumberDecl(fieldDecl);
  string *fileName = getSourceFileName(fieldDecl);
  RecordDecl *parentRecordDecl = fieldDecl->getParent();

  string fieldDeclName = fieldDecl->getNameAsString();
  if (currentTypeDefForFieldDecl == nullptr) {
    currentTypeDefForFieldDecl = new list<TypeDefGeneral*>();
  }
  QualType t = fieldDecl->getType();
  if (fieldDecl->isImplicit() && fieldDeclName.size() < 1) {
    fieldDeclName = "implicit_field#";
  }
  TypeDefGeneral *fieldObjekt = parseQualType(t, fieldDeclName, line, col,
                                              fileName, this->traversingData,
                                              this->
                                              currentAnonymousStructTypeNamesList);
  TypeDefStructOrUnion *recordRelated = (TypeDefStructOrUnion*)
      getRecordDefinition(parentRecordDecl, listOfRecords);
  recordRelated->addMember(fieldObjekt);
  return true;
}

bool FindInformationClassVisitor::VisitEnumConstantDecl(
  EnumConstantDecl *enumConstantDecl) {

  string enumConstantDeclName = enumConstantDecl->getNameAsString();
  if (currentListEnumConstants == nullptr) {
    currentListEnumConstants = new list<string>();
  }
  currentListEnumConstants->push_back(enumConstantDeclName);
  return true;
}


std::string FindInformationClassVisitor::appendStringToFileStaticLabels(
  std::string variableName, std::string fileName) {
  std::size_t found = fileName.find_last_of("/\\");
  std::string file = fileName.substr(found + 1);
  found = file.find_last_of(".");
  file = file.replace(found, 1, "_");
  variableName = variableName.append(".").append(file).append(".APP4MC_CS");
  return variableName;
}

std::string FindInformationClassVisitor::appendStringToFunctionStaticLabels(
  std::string variableName, VarDecl *varDecl) {
  int count = 0;
  if (variableNameAndCounter.count(variableName) < 1) {
    variableNameAndCounter[variableName] = 0;
    addressAndCounter[varDecl] = 0;
  } else {
    if (addressAndCounter.count(varDecl) < 1) {
      count = variableNameAndCounter[variableName];
      count++;
      variableNameAndCounter[variableName] = count;
      addressAndCounter[varDecl] = count;
    } else {
      count = addressAndCounter[varDecl];
    }
  }

  std::size_t found = currentAnalyzedFile->find_last_of("/\\");
  std::string file = currentAnalyzedFile->substr(found + 1);
  found = file.find_last_of(".");
  file = file.replace(found, 1, "_");

  std::string functionName = "";
  unsigned int find_dot = currentAnalyzedFunction
                          ->getFunctionEntry()->getFunctionName().find_first_of(
                            '.');
  if (find_dot != std::string::npos) {
    functionName = currentAnalyzedFunction
                   ->getFunctionEntry()->getFunctionName().substr(0, find_dot);
  } else {
    functionName = currentAnalyzedFunction
                   ->getFunctionEntry()->getFunctionName();
  }
  variableName = variableName.append(".").append(file).append(".").
                              append(functionName).append(".").append(
                                std::to_string(count)).append(".APP4MC_FS");
  return variableName;
}

std::string FindInformationClassVisitor::appendStringToStaticFunctions(
  std::string functionName, std::string fileName) {
  std::size_t found = fileName.find_last_of("/\\");
  std::string file = fileName.substr(found + 1);
  found = file.find_last_of(".");
  file = file.replace(found, 1, "_");
  string name = functionName.append(".").append(file).append(".APP4MC_CS");
  return name;
}

std::string FindInformationClassVisitor::getProperFileForStaticFunction(
  FunctionDecl *functionDecl) {
  Stmt *stmt = functionDecl->getBody();
  std::string fileName;
  if (stmt) {
    fileName = *getSourceFileName(functionDecl->getBody());
  } else {
    fileName = *getSourceFileName(functionDecl);
  }
  return fileName;
}
