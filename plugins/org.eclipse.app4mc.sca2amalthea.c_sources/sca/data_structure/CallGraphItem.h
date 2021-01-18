#ifndef CALLGRAPHITEM_H
#define CALLGRAPHITEM_H

#include "llvm/Support/Casting.h"

class CallGraphItem {
public:
	enum CallGraphItemKind {
		K_CallEntry,
		K_GlobalVariableAccess,
		K_ModeSwitch
	};
private:
	unsigned line;
	unsigned col;
	unsigned basicBlockID;
	const CallGraphItemKind Kind;
public:
    CallGraphItem(unsigned line, unsigned col, unsigned basicBlockID, CallGraphItemKind K) : line(line), col(col), basicBlockID(basicBlockID), Kind(K) {}

	unsigned getLine();
	unsigned getColumn();
	unsigned getBasicBlockID();
	CallGraphItemKind getKind() const { return Kind; }
};
#endif