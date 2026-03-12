package com.benicio.vm;

import com.benicio.compiler.Compiler;
import com.benicio.vm.Instruction;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // THE GOAL: Count down from 5 to 1 using a Loop.
        // Equivalent Java:
        // int x = 5;
        // while (x > 0) {
        //    print(x);
        //    x = x - 1;
        // }
        
        String sourceCode = """
            PUSH 5
            
            LOOP_START:
                DUP         
                PRINT       
                PUSH 1
                SUB         
                DUP         
                JZ END_LOOP 
                JMP LOOP_START
                
            END_LOOP:
                HALT
        """;
        
        /* * LOGIC EXPLAINED:
         * 1. PUSH 5 (Stack: 5)
         * 2. LOOP_START:
         * 3. DUP (Stack: 5, 5) -> We dup because PRINT consumes one.
         * 4. PRINT (Output: 5. Stack: 5)
         * 5. PUSH 1 (Stack: 5, 1)
         * 6. SUB (Stack: 4)
         * 7. DUP (Stack: 4, 4) -> Dup for the Check.
         * 8. JZ (Jump if Zero). 4 is not 0. No jump. (Stack: 4)
         * 9. JMP LOOP_START -> Go back to step 3.
         */

        System.out.println("--- Compiling Control Flow Demo ---");
        Compiler compiler = new Compiler();
        List<Instruction> program = compiler.compile(sourceCode);

        VirtualMachine vm = new VirtualMachine(program);
        vm.run();
    }
}
