package com.bil.base.storage.dao;


import com.bil.base.storage.model.HashStorageNode;

import java.util.List;

/**
 * @author bob
 */
public interface HashStorageNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HashStorageNode record);

    int insertSelective(HashStorageNode record);

    HashStorageNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HashStorageNode record);

    int updateByPrimaryKey(HashStorageNode record);

    /**
     * 查询节点
     *
     * @param storageNode
     * @return
     */
    List<HashStorageNode> queryList(HashStorageNode storageNode);

}