package com.cheems.cheemsimagesearchmcpserver.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageSearchToolTest {

    @Test
    void searchImage() {

    ImageSearchTool imageSearchTool =new ImageSearchTool();
        String s = imageSearchTool.searchImage("有关学习激励的图片");
        System.out.println(s);
    }
}