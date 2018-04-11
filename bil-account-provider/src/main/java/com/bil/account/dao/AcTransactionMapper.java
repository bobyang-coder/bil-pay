package com.bil.account.dao;

import com.bil.account.model.AcTransaction;

public interface AcTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AcTransaction record);

    int insertSelective(AcTransaction record);

    AcTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcTransaction record);

    int updateByPrimaryKey(AcTransaction record);
}