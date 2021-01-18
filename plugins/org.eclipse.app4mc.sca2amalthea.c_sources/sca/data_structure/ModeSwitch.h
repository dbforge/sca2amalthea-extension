#ifndef MODESWITCH_H
#define MODESWITCH_H

#include <list>
#include "ModeEntry.h"

class ModeSwitch : public CallGraphItem {
private:
    std::list<ModeEntry*> modeEntries;
public:
    ModeSwitch(unsigned line, unsigned col, unsigned basicBlockID);
    std::list<ModeEntry*>* getModeEntries();
    void addModeEntry(ModeEntry *modeEntry);

    static bool classof(const CallGraphItem *S) {
		return S->getKind() == K_ModeSwitch;
	}
};
#endif