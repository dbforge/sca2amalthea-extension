#include "ModeSwitch.h"

ModeSwitch::ModeSwitch(unsigned line, unsigned col, unsigned basicBlockID) : CallGraphItem(line, col, basicBlockID, K_ModeSwitch) { }

std::list<ModeEntry *> *ModeSwitch::getModeEntries() {
    return &this->modeEntries;
}

void ModeSwitch::addModeEntry(ModeEntry *modeEntry) {
    this->modeEntries.push_back(modeEntry);
}