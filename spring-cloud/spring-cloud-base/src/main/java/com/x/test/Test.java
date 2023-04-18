package com.x.test;

import com.x.base.util.CodeGeneratorUtil;

/**测试类
 * @author hp
 */
public class Test {

    public static void main(String[] args) {
        String[] tableArray = new String[]{
//			"mq_message",
//			"mq_message_history"
                "course_base",
                "course_market",
                "teachplan",
                "teachplan_media",
                "course_teacher",
                "course_category"
//			 "course_publish",
//			 "course_publish_pre"
        };
        CodeGeneratorUtil.generator("content","root","rootroot",tableArray,false);
    }
}
