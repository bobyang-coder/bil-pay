package com.bob.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
@Controller
@RequestMapping(value = "/test")
public class TestAopController {
    @Autowired
    private HelloController helloController;
    @Autowired
    private TestServiceApi testService;
    private String resource = "/Users/bob/Applications/bob-workspace/github/bob-bil/bil-test-web/src/main/resources/";

    @RequestMapping(value = "/hello", method = {RequestMethod.POST})
    @ResponseBody
    public String hello() throws IOException {
        ProxyBeanToClassUtil.convertBeanToClassFile(helloController, resource);
        return "hello web";
    }

    /**
     * @param dirPath  目录以/结尾，且必须存在
     * @param fileName
     * @param data
     */
    private void exportClazzToFile(String dirPath, String fileName, byte[] data) {
        try {
            File file = new File(dirPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            System.out.println("exception occured while doing some file operation");
            e.printStackTrace();
        }
    }

}
