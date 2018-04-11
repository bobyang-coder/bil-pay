package com.bil.base.storage.service;

import com.bil.base.storage.model.HashStorageNode;

import java.util.List;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public interface HashStorageNodeService {
    /**
     * 保存
     *
     * @param node
     * @return
     */
    int save(HashStorageNode node);

    /**
     * 根据表名前缀
     *
     * @param tablePrefix 表名前缀
     * @return
     */
    List<HashStorageNode> queryByTablePrefix(String tablePrefix);

    /**
     * 根据节点状态查询
     *
     * @param nodeStatus 节点状态
     * @return
     */
    List<HashStorageNode> queryByNodeStatus(String nodeStatus);

    /**
     * 根据id查询
     * @param nodeId
     * @return
     */
    HashStorageNode findById(Integer nodeId);

    int update(HashStorageNode storageNode);
}
