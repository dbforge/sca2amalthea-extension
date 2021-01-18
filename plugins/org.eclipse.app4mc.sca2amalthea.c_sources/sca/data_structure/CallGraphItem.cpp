#include "CallGraphItem.h"

unsigned CallGraphItem::getLine(){
	return this->line;
}
unsigned CallGraphItem::getColumn(){
	return this->col;
}

unsigned CallGraphItem::getBasicBlockID(){
	return this->basicBlockID;
}