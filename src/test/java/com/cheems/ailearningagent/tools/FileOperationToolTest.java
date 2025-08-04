package com.cheems.ailearningagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationToolTest {

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool =new FileOperationTool();
        String s = fileOperationTool.writeFile("cheems的测试文件.txt", "这是cheems的文件操作工具");
        Assertions.assertNotNull(s);
    }

    @Test
    void readFile(){
        FileOperationTool fileOperationTool =new FileOperationTool();
        String name = "cheems的测试文件.txt";
        String s = fileOperationTool.readFile(name);
        Assertions.assertNotNull(s);
    }
}