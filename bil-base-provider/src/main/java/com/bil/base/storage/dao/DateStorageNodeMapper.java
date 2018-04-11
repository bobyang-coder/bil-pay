package com.bil.base.storage.dao;


import com.bil.base.storage.model.DateStorageNode;

public interface DateStorageNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DateStorageNode record);

    int insertSelective(DateStorageNode record);

    DateStorageNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DateStorageNode record);

    int updateByPrimaryKey(DateStorageNode record);
}