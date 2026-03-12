package com.benicio.vm;

import com.benicio.compiler.Compiler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class VirtualMachineTest {

    // Helper to compile & run, then return the top of the stack
    private int run(String sourceCode) {
        Compiler compiler = new Compiler();
        List<Instruction> program = compiler.compile(sourceCode);
        VirtualMachine vm = new VirtualMachine(program);
        vm.run();
        
        // We peek at the stack to verify the result
        return vm.peekStack();
    }

    @Test
    void testSimpleAddition() {
        String code = """
            PUSH 10
            PUSH 20
            ADD
            HALT
        """;
        Assertions.assertEquals(30, run(code));
    }

    @Test
    void testOrderOfOperations() {
        // (5 * 2) - 3 = 7
        String code = """
            PUSH 5
            PUSH 2
            MUL
            PUSH 3
            SUB
            HALT
        """;
        Assertions.assertEquals(7, run(code));
    }

    @Test
    void testEqualityTrue() {
        // 50 == 50 should push 1 (True)
        String code = """
            PUSH 50
            PUSH 50
            EQ
            HALT
        """;
        Assertions.assertEquals(1, run(code));
    }

    @Test
    void testEqualityFalse() {
        // 99 == 100 should push 0 (False)
        String code = """
            PUSH 99
            PUSH 100
            EQ
            HALT
        """;
        Assertions.assertEquals(0, run(code));
    }

    @Test
    void testJumpLogic() {
        // Should skip "PUSH 999" and go straight to "PUSH 42"
        String code = """
            JMP TARGET
            PUSH 999
            TARGET:
                PUSH 42
                HALT
        """;
        Assertions.assertEquals(42, run(code));
    }
}
