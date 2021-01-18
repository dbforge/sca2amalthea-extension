#include "CallGraphItemContainer.h"

void CallGraphItemContainer::addChild(CallGraphItem *callGraphItem) {
        this->children.push_back(callGraphItem);
}

void CallGraphItemContainer::removeChild(CallGraphItem *callGraphItem) {
        this->children.remove(callGraphItem);
}

std::list<CallGraphItem*> CallGraphItemContainer::getChildren() {
        return this->children;
}