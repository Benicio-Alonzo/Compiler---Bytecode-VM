package com.benicio.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instruction {
    private OpCode opcode;
    private int operand; // Only used for PUSH (e.g., PUSH 5). Others use 0.

    // Helper for non-operand instructions (like ADD, PRINT)
    public Instruction(OpCode opcode) {
        this.opcode = opcode;
        this.operand = 0;
    }
}
