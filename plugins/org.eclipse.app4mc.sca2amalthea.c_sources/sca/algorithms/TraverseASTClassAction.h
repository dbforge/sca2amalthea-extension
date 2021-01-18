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
#ifndef TRAVERSEASTCLASSACTION_H
#define TRAVERSEASTCLASSACTION_H

#include "clang/AST/ASTConsumer.h"
#include "clang/AST/RecursiveASTVisitor.h"
#include "clang/AST/ParentMap.h"

#include "clang/Frontend/CompilerInstance.h"
#include "clang/Frontend/FrontendAction.h"
#include "clang/Tooling/Tooling.h"
#include "clang/Tooling/CommonOptionsParser.h"
#include "llvm/Support/CommandLine.h"
#include "clang/Analysis/CFG.h"
#include "clang/Analysis/CFGStmtMap.h"
#include "data_structure\TraverseASTDataStructure.h"
#include "output\Output.h"
#include "helpers\Helpers.h"
#include "data_structure/ModeSwitch.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <string>


class FindInformationClassVisitor
	: public clang::RecursiveASTVisitor < FindInformationClassVisitor > {
public:
	explicit FindInformationClassVisitor(clang::ASTContext *Context, TraversingData* traversingData) : Context(Context) {
		this->traversingData = traversingData;
	}

	//Quick map to find the functionentry related to a function inside a file
	std::map < std::string, FunctionEntry*> mapOfFunctions;
	std::list<FunctionAndCallees*>* functionInfos = new std::list<FunctionAndCallees*>();
	FunctionAndCallees* currentAnalyzedFunction;
	std::string* currentAnalyzedFile = nullptr;
	std::map<string, TypeDefStructOrUnion*> potentialAnonymousStructORUnions;

	std::list<TypeDefGeneral*>* currentTypeDefForFieldDecl = nullptr;
	std::list<TypeDefGeneral*>* currentTypeDefForFieldDecl_secondLevel = nullptr;
	std::map<clang::Decl*, TypeDefGeneral*>* recordsMap = new  std::map<clang::Decl*, TypeDefGeneral*>();
	std::map<clang::VarDecl*, int> varDeclToBlockID;
	std::list<std::string>* currentListEnumConstants = nullptr;
	TypeDefGeneral* currentHigherLevelTypeDef = nullptr;

	//http://stackoverflow.com/questions/13760197/how-to-get-basic-block-id-of-a-statement-inside-visit-methods
	clang::ParentMap* parentMap;
	clang::CFGStmtMap* mapOfCFGStmts;


	bool VisitFunctionDecl(clang::FunctionDecl* decl);

	bool VisitVarDecl(clang::VarDecl* varDecl);

	bool VisitDeclStmt(clang::DeclStmt* declStmt);

	bool VisitImplicitCastExpr(clang::ImplicitCastExpr* implicitCastExpr);

	//bool VisitTypeDefDecl(clang::TypedefDecl* typedefDecl);
	bool VisitTypeDecl(clang::TypeDecl* typeDecl);
	bool VisitRecordDecl(clang::RecordDecl* recordDecl);
	bool parseFieldDecl(clang::FieldDecl* fieldDecl);
	//bool VisitEnumDecl(clang::EnumDecl* enumDecl);
	bool VisitEnumConstantDecl(clang::EnumConstantDecl* enumConstantDecl);
	std::map < clang::VarDecl*, int> addressAndCounter;
	std::map<std::string, int>variableNameAndCounter;
	
	
private:

	clang::ASTContext *Context;
	bool isCurrentWrite = false;
	bool isCurrentFunctionCall = false;
	bool iscurrentTypeDef = false;
	bool isInMemberExpression = false;
	std::string memberExprName = "";
	bool isInFunctionDecl = false;
	bool isInLocalVariableWithPointerType = false;
	clang::VarDecl* localVarDeclOfPointerType = nullptr;
	int fieldLevel = 0;
	clang::RecordDecl* currentRecordHigherLevelDecl = nullptr;
	std::list<std::pair<clang::RecordDecl*, TypeDefGeneral*>*>* listOfRecords = nullptr;
	TraversingData* traversingData;
	string unaryOperatorStr = "";

	std::list<std::tuple<std::string, int, int, std::string>>* currentAnonymousStructTypeNamesList = nullptr;

	std::list<BasicBlock>* buildCFGModel(clang::FunctionDecl* decl, clang::Stmt *stmt);

	unsigned int getBlockID(clang::Stmt *stmt);
	

	void parseDeclRefExpressionInUnaryOperator(clang::UnaryOperator *u, clang::DeclRefExpr *declRef, clang::Stmt *child);

	bool isFileAllreadyinserted(std::string fileName);

	void parseStatementGeneric(clang::Stmt *stmt);

	void selectAndExecuteStatement(clang::Stmt *child);

	void parseIfStmt(clang::Stmt *statement);

	void parseUnaryOperator(clang::Stmt *statement);

	void parseBinaryOperator(clang::Stmt *statement);

	void parseLeftSideBinaryOperator(clang::Expr *lhs);

	void addNewGlobalAccess(clang::Stmt* stmt, std::string variableName, std::string accessType, unsigned lineNumber, unsigned columnNumber);

	void parseRightSideBinaryOperator(clang::Expr *rhs);

	void parseCallExprOperator(clang::Stmt* statement);

	void parseFunctionCall(clang::Stmt* statement);

	void parseImplicitCastExpression(clang::Stmt *statement);

	int getLineNumberDecl(clang::Decl *stmt);

	int getColumnNumberDecl(clang::Decl *stmt);

	int getLineNumberStmt(clang::Stmt *stmt);

	int getColumnNumberStmt(clang::Stmt *stmt);

	std::string* getSourceFileName(clang::Decl *stmt);

	std::string* getSourceFileName(clang::Stmt *stmt);
	
	std::string  getAbsolutePath(std::string str);

	std::string appendStringToFunctionStaticLabels(std::string variableName, clang::VarDecl* varDecl);

	std::string appendStringToFileStaticLabels(std::string variableName, std::string fileName);

	std::string appendStringToStaticFunctions(std::string functionName,std::string fileName);

	std::string getProperFileForStaticFunction(clang::FunctionDecl* functionDecl);

	void addParameterToGlobalMap(clang::ParmVarDecl* variable, std::string functionName);

	void addLocalValiableToGlobalMap(clang::VarDecl* variable, std::string functionName, GlobalVariable* proxy);

	/* check if a variable is a loal variable pointiong to a global variable or a function's parameter with a pointer type*/
	bool isVariableOrParameterToBeConsideredAsGlobal(clang::VarDecl* variable);

	/* get The unique name of a localVariable inside a function*/
	std::string getUniqueVariableName(clang::VarDecl* variable, std::string oldVariableName);

	GlobalVariable* createGlobalVariableFromLocalMember(std::string variableName);

	GlobalVariable* createGlobalVariableFromLocal(std::string variableName);
	/* create a global variable out of a temporary local struct variable member*/
	std::string* FindInformationClassVisitor::getStructMemberType(std::string memberName, std::string structTypeName);

	/* Check if the variable declation is initialized with a global variable and keep that information*/
	void getGlobalVariableAsProxy(clang::VarDecl* variable);


	//Check if a variable swapping is possible and store the necessary information for that
	void checkForVariableSwapping(clang::Expr* leftSide, clang::Expr* rightSide);

	clang::DeclRefExpr* getDeclRefExprForSwapping(clang::Expr* initialisationExpression);

	//In the current function the local variable variable can be replaced by the global variable inside declRef.
	void addSwappingInformation(std::string localVarName, clang::DeclRefExpr* rightSideDeclRef, int blockID);
};



/*Interface used to write generic actions on an AST*/
class FindNamedClassConsumer : public clang::ASTConsumer {
public:
	explicit FindNamedClassConsumer(clang::ASTContext *Context, TraversingData* traversingData)
		: Visitor(Context, traversingData) {}

	virtual void HandleTranslationUnit(clang::ASTContext &Context) {
		// Traversing the translation unit decl via a RecursiveASTVisitor
		// will visit all nodes in the AST.
		//Context.getTranslationUnitDecl()->dumpColor(); 
		try{
			Visitor.TraverseDecl(Context.getTranslationUnitDecl());
		}
		catch (...){
			cerr << "An error happened in HandleTranslationUnit" << endl;
		}
		
		//Visitor.persistInformationsFound();
	}
	virtual ~FindNamedClassConsumer(){
		
	}

private:
	// A RecursiveASTVisitor implementation.
	FindInformationClassVisitor Visitor;

};


class FindNamedClassAction : public clang::ASTFrontendAction {
protected:

public:

	virtual std::unique_ptr<clang::ASTConsumer> CreateASTConsumer(clang::CompilerInstance &Compiler, llvm::StringRef InFile)
	{
		try{
			lock_allTraversingDataMap.lock();
			TraversingData* t = traversingDatas[InFile.str()];
			lock_allTraversingDataMap.unlock();
			t->currentlyAnalyzedFile = InFile.str();
			printCurrentAnalyzedFile(InFile.str(), t);
			t->analyzedFiles[InFile.str()] = 1;
			return  std::unique_ptr<clang::ASTConsumer>(new FindNamedClassConsumer(&Compiler.getASTContext(), t));
		}
		catch (...){
			cout << "Sudhindra error occured in findnamedclassaction" << endl;
		}
	}
};

std::string parseQualTypeSimple(clang::QualType &t, TraversingData* traversingData);

#endif /* TRAVERSEASTCLASSACTION_H */
