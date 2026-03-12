#  Compiler & Bytecode VM: A Turing-Complete Stack-Based Virtual Machine

**Compiler & Bytecode VM** is a custom implementation of a **Bytecode Virtual Machine (VM)** and **Compiler**, written entirely in **Java 21** with **zero external dependencies**.

Unlike standard projects that rely on frameworks, this project explores the low-level engineering of how computers actually work: parsing raw source code, compiling it into a custom instruction set (ISA), and executing it on a stack-based architecture with memory management and control flow.

---

##  Architecture
The system follows a classic **Fetch-Decode-Execute** cycle, simulating a real CPU's behavior in software.


graph LR
    A[Source Code] -->|Lexer & Parser| B(Bytecode Instructions)
    B -->|Load| C{Virtual Machine}
    C -->|Fetch| D[Instruction Pointer]
    D -->|Decode| E[Switch / Dispatch]
    E -->|Execute| F[Operand Stack]


### 3. Run the Automated Test Suite (JUnit)
This project includes a professional JUnit 5 test suite to verify the Virtual Machine's logic, math accuracy, and stack consistency.

**Run the tests with Maven:**
```bash
mvn test

*TESTS*

1. Simple Math (testSimpleAddition)
The Code: PUSH 10, PUSH 20, ADD.

The Test: It verifies that 10 + 20 actually equals 30.

Why: This proves your Stack works (Pushing two items) and your ALU (Arithmetic Logic Unit) works (Popping them and adding).

2. Order of Operations (testOrderOfOperations)
The Code: PUSH 5, PUSH 2, MUL, PUSH 3, SUB.

The Logic: (5 * 2) - 3.

The Test: It expects 7.

Why: This proves your VM handles State. It calculates 10 first, holds it in memory, then uses it for the next subtraction.

3. Boolean Logic (testEqualityTrue / False)
The Code: PUSH 50, PUSH 50, EQ.

The Test: It expects 1 (True).

Why: A computer needs to know if two things are equal to make decisions. This tests your comparison logic.

4. Control Flow / Jumping (testJumpLogic)
The Code:

Plaintext

JMP TARGET
PUSH 999     <-- This is a "Trap"
TARGET:
PUSH 42
The Test: It expects 42.

Why: If the result was 999, your VM is broken (it read lines linearly). If the result is 42, your VM successfully teleported (updated the Instruction Pointer), skipping the trap. This proves you can build Loops and If-Statements.
