package com.benicio.vm;

public enum OpCode {
    PUSH,   // Push operand to stack
    ADD,    // Pop 2, Add, Push result
    SUB,    // Pop 2, Sub, Push result
    MUL,    // Pop 2, Mul, Push result
    
    // --- NEW LOGIC OPS ---
    EQ,     // Pop 2. If equal, Push 1. Else Push 0.
    DUP,    // Duplicate the top value (Crucial for loops!)
    
    // --- NEW CONTROL FLOW ---
    JMP,    // Jump to instruction index unconditionally
    JZ,     // "Jump if Zero". Pop value. If 0, jump. Else continue.
    
    PRINT,  // Print top value
    HALT    // Stop
}
