package com.bil.base.storage.service.impl;


import com.bil.base.storage.dao.HashStorageNodeMapper;
import com.bil.base.storage.model.HashStorageNode;
import com.bil.base.storage.service.HashStorageNodeService;
import com.bil.base.storage.utils.Safes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bob
 */
@Service
public class HashStorageNodeServiceImpl implements HashStorageNodeService {

    @Autowired
    private HashStorageNodeMapper hashStorageNodeMapper;


    @Override
    public int save(HashStorageNode node) {
        return hashStorageNodeMapper.insertSelective(node);
    }

    @Override
    public List<HashStorageNode> queryByTablePrefix(String tablePrefix) {
        HashStorageNode node = new HashStorageNode();
        node.setTablePrefix(tablePrefix);
        return hashStorageNodeMapper.queryList(node);
    }

    @Override
    public List<HashStorageNode> queryByNodeStatus(String nodeStatus) {
        HashStorageNode build = HashStorageNode.builder().nodeStatus(nodeStatus).build();
        List<HashStorageNode> list = hashStorageNodeMapper.queryList(build);
        return Safes.of(list);
    }

    @Override
    public HashStorageNode findById(Integer nodeId) {
        return hashStorageNodeMapper.selectByPrimaryKey(nodeId);
    }

    @Override
    public int update(HashStorageNode storageNode) {
        return hashStorageNodeMapper.updateByPrimaryKeySelective(storageNode);
    }
}