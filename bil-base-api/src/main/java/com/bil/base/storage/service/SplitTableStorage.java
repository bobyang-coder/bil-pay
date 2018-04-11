package com.bil.base.storage.service;

import com.bil.base.storage.model.HashStorageNode;
import com.bil.base.storage.model.SplitTableStorageNode;

import java.util.List;

/**
 * 分表策略类
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public interface SplitTableStorage {

    <T extends SplitTableStorageNode> int saveStorageNode(T t);

    <T extends SplitTableStorageNode> boolean updateStorageNode(T t);

    /**
     * 根据id获取hash分表节点信息
     *
     * @param nodeId 节点id
     * @return
     */
    HashStorageNode findHashStorageNode(Integer nodeId);

    List<HashStorageNode> queryByTablePrefix(String tablePrefix);

    HashStorageNode getNext(HashStorageNode currNode);

    HashStorageNode getHashStorageNode(String tablePrefix, String key);
}
