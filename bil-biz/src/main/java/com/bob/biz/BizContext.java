package com.bob.biz;

/**
 * 业务上下文基础方法
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/18
 */
public interface BizContext<Q extends BaseReq, S extends BaseRes> {


    Q getReq();

    S getRes();
}
