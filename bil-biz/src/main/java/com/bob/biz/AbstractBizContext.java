package com.bob.biz;

/**
 * 抽象业务上下文
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/18
 */
public class AbstractBizContext<Q extends BaseReq, S extends BaseRes> implements BizContext<Q, S> {

    private Q req;
    private S res;


    @Override
    public Q getReq() {
        return req;
    }

    @Override
    public S getRes() {
        return res;
    }
}
