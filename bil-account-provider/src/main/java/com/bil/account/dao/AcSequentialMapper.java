package com.bil.account.dao;

import com.bil.account.model.AcSequential;

public interface AcSequentialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AcSequential record);

    int insertSelective(AcSequential record);

    AcSequential selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcSequential record);

    int updateByPrimaryKey(AcSequential record);
}