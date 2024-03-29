set(LLVM_LINK_COMPONENTS
  ${LLVM_TARGETS_TO_BUILD}
  asmparser
  bitreader
  core
  support
  mc
  )

add_clang_executable(sca
  TraverseASTMain.cpp
  algorithms/TraverseASTClassAction.cpp
  algorithms/TraverseASTWorker.cpp
  helpers/TraverseASTMainHelpers.cpp
  helpers/Helpers.cpp
  data_structure/BasicBlock.cpp
  data_structure/CallEntry.cpp
  data_structure/CallGraphItem.cpp
  data_structure/CallGraphItemContainer.cpp
  data_structure/ClientDataForThread.cpp
  data_structure/FunctionAndCallees.cpp
  data_structure/FunctionEntry.cpp
  data_structure/GlobalVariable.cpp
  data_structure/GlobalVariableAccess.cpp
  data_structure/ModeEntry.cpp
  data_structure/ModeSwitch.cpp
  data_structure/TraversingData.cpp
  data_structure/TraverseASTDataStructure.cpp
  data_structure/FunctionParameter.cpp
  output/Output.cpp
  )
target_link_libraries(sca PRIVATE
  libclang
  clangFrontend
  clangSerialization
  clangDriver
  clangCodeGen
  clangSema
  clangTooling
  clangStaticAnalyzerFrontend
  clangStaticAnalyzerCheckers
  clangStaticAnalyzerCore
  clangAnalysis
  clangRewrite
  clangEdit
  clangAST
  clangParse
  clangLex
  clangBasic
  )
  