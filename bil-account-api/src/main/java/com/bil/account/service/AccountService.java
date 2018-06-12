package com.bil.account.service;

import com.bil.account.model.Account;

/**
 * 账户服务
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public interface AccountService {

    //TODO 1、开户
    //TODO 2、查询
    //TODO 3、转账


    /**
     * @param accountNo
     * @return
     */
    Account findAccount(Long accountNo);

    Account findByObjNo(String objectNo);

    int saveAccount(Account account);

    void copyTableStructure(String newTableName, String originalTableName);

    void rehash(Integer splitNodeId);

}
