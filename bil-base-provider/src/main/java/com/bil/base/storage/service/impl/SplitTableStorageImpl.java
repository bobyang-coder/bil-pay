package com.bil.base.storage.service.impl;

import com.bil.base.storage.dao.DateStorageNodeMapper;
import com.bil.base.storage.model.DateStorageNode;
import com.bil.base.storage.model.HashStorageNode;
import com.bil.base.storage.model.SplitTableStorageNode;
import com.bil.base.storage.service.HashStorageNodeService;
import com.bil.base.storage.service.SplitTableStorage;
import com.bil.base.storage.utils.HashUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
@Service
public class SplitTableStorageImpl implements SplitTableStorage {

    @Autowired
    private DateStorageNodeMapper dateStorageNodeMapper;
    @Autowired
    private HashStorageNodeService hashStorageNodeService;


    /**
     * Hash分表节点配置
     * 表名前缀：(hash:Object)
     */
    private Map<String, SortedMap<Integer, HashStorageNode>> hashStorageNodeMap;
    private Map<String, SortedMap<Integer, HashStorageNode>> dateStorageNodesMap;


    @PostConstruct
    private void init() {
        List<HashStorageNode> nodeList = hashStorageNodeService.queryByNodeStatus(SplitTableStorageNode.NodeStatus.ONLINE);
        hashStorageNodeMap = Maps.newHashMap();
        nodeList.stream()
                .collect(Collectors.groupingBy(HashStorageNode::getTablePrefix))
                .forEach((s, hashStorageNodes) -> {
                    SortedMap<Integer, HashStorageNode> sortedMap = Maps.newTreeMap();
                    for (HashStorageNode hashStorageNode : hashStorageNodes) {
                        sortedMap.put(hashStorageNode.getHashCode(), hashStorageNode);
                    }
                    hashStorageNodeMap.put(s, sortedMap);
                });
    }


    @Override
    public <T extends SplitTableStorageNode> int saveStorageNode(T t) {
        int insertNum = 0;
        if (t instanceof DateStorageNode) {
            insertNum = dateStorageNodeMapper.insertSelective((DateStorageNode) t);
        } else if (t instanceof HashStorageNode) {
            HashStorageNode node = (HashStorageNode) t;
            insertNum = hashStorageNodeService.save(node);
            updateHashStorageNodeMap(node);
        }
        return insertNum;
    }

    public <T extends SplitTableStorageNode> boolean updateStorageNode(T t) {
        int updateNum = 0;
        if (t instanceof DateStorageNode) {
            updateNum = dateStorageNodeMapper.updateByPrimaryKeySelective((DateStorageNode) t);
        } else if (t instanceof HashStorageNode) {
            HashStorageNode node = (HashStorageNode) t;
            updateNum = hashStorageNodeService.update(node);
            updateHashStorageNodeMap(node);
        }
        return updateNum > 0;
    }


    private void updateHashStorageNodeMap(HashStorageNode node) {
        SortedMap<Integer, HashStorageNode> sortedMap = hashStorageNodeMap.get(node.getTablePrefix());
        if (null == sortedMap) {
            sortedMap = Maps.newTreeMap();
        }
        sortedMap.put(node.getHashCode(), node);
    }

//    HashStorageNode rehash(String key){
//
//    }

    /**
     * 根据id获取hash分表节点信息
     *
     * @param nodeId 节点id
     * @return
     */
    @Override
    public HashStorageNode findHashStorageNode(Integer nodeId) {
        return hashStorageNodeService.findById(nodeId);
    }

    @Override
    public List<HashStorageNode> queryByTablePrefix(String tablePrefix) {
        return hashStorageNodeService.queryByTablePrefix(tablePrefix);
    }

    @Override
    public HashStorageNode getNext(HashStorageNode currNode) {
        int hashCode = currNode.getHashCode();
        String tablePrefix = currNode.getTablePrefix();
        SortedMap<Integer, HashStorageNode> sortedMap = hashStorageNodeMap.get(tablePrefix);
        SortedMap<Integer, HashStorageNode> tailMap = sortedMap.tailMap(hashCode);
        return tailMap.size() <= 1 ? sortedMap.get(sortedMap.firstKey())
                : getStorageNode(sortedMap, hashCode + 1);
    }

    @Override
    public HashStorageNode getHashStorageNode(String tablePrefix, String key) {
        if (hashStorageNodeMap.isEmpty()) {
            return null;
        }
        SortedMap<Integer, HashStorageNode> sortedMap = hashStorageNodeMap.get(tablePrefix);
        if (sortedMap.isEmpty()) {
            return null;
        }
        // node 用String来表示,获得node在哈希环中的hashCode
        int hash = HashUtil.hashMod(key);
        return getStorageNode(sortedMap, hash);
    }


    public HashStorageNode getStorageNode(SortedMap<Integer, HashStorageNode> sortedMap, int hash) {
        //数据映射在两台虚拟机器所在环之间,就需要按顺时针方向寻找机器
        if (!sortedMap.containsKey(hash)) {
            SortedMap<Integer, HashStorageNode> tailMap = sortedMap.tailMap(hash);
            hash = tailMap.isEmpty() ? sortedMap.firstKey() : tailMap.firstKey();
        }
        return sortedMap.get(hash);
    }
}
