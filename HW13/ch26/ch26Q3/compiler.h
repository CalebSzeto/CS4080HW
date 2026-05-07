#ifndef clox_compiler_h
#define clox_compiler_h
#include "object.h"

ObjFunction* compile(const char* source);
void markCompilerRoots(void);
bool hasActiveCompilerRoots(void);

#endif