#include "chunk.h"
#include "common.h"
#include "debug.h"
#include "memory.h"

int main(int argc, const char* argv[]) {
  (void)argc;
  (void)argv;

  initMemory();

  Chunk chunk;
  initChunk(&chunk);

  writeConstant(&chunk, 1.2, 123);

  writeChunk(&chunk, OP_RETURN, 123);

  disassembleChunk(&chunk, "test chunk");
  freeChunk(&chunk);
  return 0;
}