package com.benicio.vm;

import java.util.List;
import java.util.Stack;

public class VirtualMachine {
    private final Stack<Integer> stack = new Stack<>();
    private int ip = 0; // Instruction Pointer
    private final List<Instruction> program;

    public VirtualMachine(List<Instruction> program) {
        this.program = program;
    }

    public void run() {
        System.out.println("--- VM Started ---");
        
        while (ip < program.size()) {
            Instruction instruction = program.get(ip);
            ip++; // Default: Move to next line

            switch (instruction.getOpcode()) {
                case PUSH -> stack.push(instruction.getOperand());
                case ADD -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a + b);
                }
                case SUB -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                }
                case MUL -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a * b);
                }
                
                // --- LOGIC ---
                case EQ -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a == b ? 1 : 0);
                }
                case DUP -> stack.push(stack.peek());
                
                // --- CONTROL FLOW ---
                case JMP -> ip = instruction.getOperand();
                case JZ -> {
                    int check = stack.pop();
                    if (check == 0) {
                        ip = instruction.getOperand();
                    }
                }

                case PRINT -> System.out.println("OUTPUT: " + stack.pop());
                case HALT -> {
                    System.out.println("--- VM Halted ---");
                    return;
                }
            }
        }
    }

    // --- THIS WAS MISSING ---
    public int peekStack() {
        if (stack.isEmpty()) return 0;
        return stack.peek();
    }
}
