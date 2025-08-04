package com.cheems.ailearningagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool terminalOperationTool =new TerminalOperationTool();
        String s = terminalOperationTool.executeTerminalCommand("java -version");
        Assertions.assertNotNull(s);
    }
}