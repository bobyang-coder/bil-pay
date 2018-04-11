package com.bob.test;

import com.bob.biz.BaseReq;
import com.bob.biz.BaseRes;
import com.bob.biz.EncryDecry;
import org.springframework.stereotype.Controller;

/**
 * Created by bob on 2018/1/18.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/18
 */
@Controller
public class TestController {

    @EncryDecry
    public BaseRes index(BaseReq baseReq){

        return "";
    }


}
