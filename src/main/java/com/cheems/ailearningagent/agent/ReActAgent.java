package com.cheems.ailearningagent.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实现 思考 -行动的 循环模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ReActAgent extends BaseAgent  {


    //思考的方法
    public abstract boolean think();

    //执行行动的方法
    public abstract  String act();


    //执行单个步骤的方法


    @Override
    public String step() {
        //先思考， 后执行

        try {
            boolean shouldact = think();
            if(!shouldact){
                return  "思考完成-无需行动";
            }
            return  act();
        } catch (Exception e) {
           e.printStackTrace();
           return  "步骤执行失败:"+ e.getMessage();
        }


    }
}
