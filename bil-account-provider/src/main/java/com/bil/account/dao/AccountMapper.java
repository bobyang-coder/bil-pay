package com.bil.account.dao;

import com.bil.account.model.Account;
import com.bil.account.model.query.AccountQry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    /**
     *
     * @param id
     * @param tableSuffix
     * @return
     */
    int deleteByPrimaryKey(Long id, @Param("tableSuffix") String tableSuffix);

    int insert(AccountQry record);

    int insertSelective(AccountQry record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    /**
     * 复制表结构
     *
     * @param newTableName      新表明
     * @param originalTableName
     */
    void copyTableStructure(@Param("newTableName") String newTableName
            , @Param("originalTableName") String originalTableName);

    /**
     * 查询list
     *
     * @param accountQry
     * @return
     */
    List<Account> queryList(AccountQry accountQry);
}