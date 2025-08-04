package com.cheems.ailearningagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.cheems.ailearningagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 文件操作工具
 */
public class FileOperationTool {


    private  final  String FIR_DIR = FileConstant.FILE_SAVE_DIR+"/file";


    @Tool(description = "Read content from  a file")
    public String readFile(@ToolParam(description = "Name of the file to read") String fileName){

        String filePath = FIR_DIR +"/"+ fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (IORuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @Tool(description = "Write content to a file")
    public String writeFile(@ToolParam(description = "Name of the file to write") String fileName,
                            @ToolParam(description = "Content to write to the file")String content){

        String filePath = FIR_DIR +"/"+ fileName;
        try {

            FileUtil.mkdir(FIR_DIR);
            FileUtil.writeUtf8String(content,filePath);
            return "File written  successfully to :" + filePath;
        } catch (IORuntimeException e) {
            throw new RuntimeException(e);
        }

    }





}
