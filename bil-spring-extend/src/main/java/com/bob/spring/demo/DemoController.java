package com.bob.spring.demo;

import com.alibaba.fastjson.JSON;
import com.bob.spring.extend.RequestDecrypt;
import com.bob.spring.extend.ResponseEncrypt;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
@Controller
@RequestMapping(value = "/demo", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
public class DemoController {

    @RequestMapping(value = "/hello", method = { RequestMethod.POST })
    @ResponseBody
    public String hello() {
        return "hello web";
    }

    @RequestMapping(value = "/hello1", method = { RequestMethod.POST })
    @ResponseBody
    public Message hello1(@RequestDecrypt Message message) {
        return message;
    }

    @RequestMapping(value = "/hello2", method = { RequestMethod.POST })
    @ResponseEncrypt
    public Message hello2(@RequestDecrypt Message message) {
        return message;
    }

    @RequestMapping(value = "/hello3", method = { RequestMethod.POST })
    @ResponseEncrypt
    public Message hello3(@RequestBody Message message) {
        return message;
    }

    @RequestMapping(value = "/hello4", method = { RequestMethod.POST })
    @ResponseEncrypt
    public Map<String, Object> hello4(@RequestBody Map<String, Object> message) {
        return message;
    }

    @RequestMapping(value = "/hello5", method = { RequestMethod.POST })
    @ResponseEncrypt
    public List<Object> hello5(@RequestBody List<Object> message) {
        return message;
    }

    public static class Message {
        private String name;
        private int    age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
