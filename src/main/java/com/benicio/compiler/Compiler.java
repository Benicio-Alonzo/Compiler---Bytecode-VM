package com.benicio.compiler;

import com.benicio.vm.Instruction;
import com.benicio.vm.OpCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compiler {

    public List<Instruction> compile(String sourceCode) {
        List<Instruction> program = new ArrayList<>();
        Map<String, Integer> labelMap = new HashMap<>();
        
        // Split into lines to handle labels easier
        String[] lines = sourceCode.split("\\n");
        List<String> validTokens = new ArrayList<>();

        // --- PASS 1: FIND LABELS & CLEANUP ---
        // We simulate building the program list to know what index each Label points to.
        int instructionIndex = 0;
        
        for (String line : lines) {
            String cleanLine = line.trim();
            if (cleanLine.isEmpty()) continue;
            
            // If line ends with ':', it is a Label.
            if (cleanLine.endsWith(":")) {
                String labelName = cleanLine.substring(0, cleanLine.length() - 1); // Remove ':'
                labelMap.put(labelName, instructionIndex);
                // Labels are metadata, they don't become instructions!
            } else {
                // If it's a real instruction, add to tokens list
                String[] parts = cleanLine.split("\\s+");
                for (String part : parts) {
                    validTokens.add(part);
                }
                // Special case: PUSH/JMP take 2 tokens but count as 1 instruction in OUR VM?
                // Actually, our VM stores Instruction Objects.
                // So "PUSH 5" is ONE instruction index.
                instructionIndex++; 
            }
        }

        // --- PASS 2: GENERATE BYTECODE ---
        for (int i = 0; i < validTokens.size(); i++) {
            String token = validTokens.get(i);
            
            switch (token.toUpperCase()) {
                case "PUSH" -> {
                    i++;
                    int val = Integer.parseInt(validTokens.get(i));
                    program.add(new Instruction(OpCode.PUSH, val));
                }
                case "JMP" -> {
                    i++;
                    String labelTarget = validTokens.get(i);
                    // Look up the address in our Map
                    int address = labelMap.get(labelTarget);
                    program.add(new Instruction(OpCode.JMP, address));
                }
                case "JZ" -> {
                    i++;
                    String labelTarget = validTokens.get(i);
                    int address = labelMap.get(labelTarget);
                    program.add(new Instruction(OpCode.JZ, address));
                }
                case "ADD" -> program.add(new Instruction(OpCode.ADD));
                case "SUB" -> program.add(new Instruction(OpCode.SUB));
                case "MUL" -> program.add(new Instruction(OpCode.MUL));
                case "EQ"  -> program.add(new Instruction(OpCode.EQ));
                case "DUP" -> program.add(new Instruction(OpCode.DUP));
                case "PRINT" -> program.add(new Instruction(OpCode.PRINT));
                case "HALT" -> program.add(new Instruction(OpCode.HALT));
                default -> throw new IllegalArgumentException("Unknown Command: " + token);
            }
        }
        
        // Safety Halt
        if (program.isEmpty() || program.get(program.size() - 1).getOpcode() != OpCode.HALT) {
            program.add(new Instruction(OpCode.HALT));
        }

        return program;
    }
}
