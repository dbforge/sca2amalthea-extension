#ifndef CALLGRAPHITEMCONTAINER_H
#define CALLGRAPHITEMCONTAINER_H

#include "CallGraphItem.h"
#include <list>

class CallGraphItemContainer {
private:
	std::list<CallGraphItem*> children;
public:
	void addChild(CallGraphItem *callGraphItem);
	void removeChild(CallGraphItem *callGraphItem);
	std::list<CallGraphItem*> getChildren();
};
#endif