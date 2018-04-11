package com.bob.boss.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bob on 2017/12/16.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/12/16
 */
@Controller
@RequestMapping
public class IndexController {


    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
