package com.bil.account.service.impl;

import com.bil.account.OriginalTable;
import com.bil.account.dao.AccountMapper;
import com.bil.account.model.Account;
import com.bil.account.model.query.AccountQry;
import com.bil.account.service.AccountService;
import com.bil.base.storage.model.HashStorageNode;
import com.bil.base.storage.model.SplitTableStorageNode;
import com.bil.base.storage.service.SplitTableStorage;
import com.bil.base.storage.utils.HashUtil;
import com.bil.base.storage.utils.IdGenrator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SplitTableStorage splitTableStorage;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Account findAccount(Long accountNo) {
        LOGGER.debug("[查询账户{}]", accountNo);
        return accountMapper.selectByPrimaryKey(accountNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveAccount(Account account) {
        LOGGER.debug("[保存账户{}]", account);
        HashStorageNode storageNode = splitTableStorage.getHashStorageNode(OriginalTable.AC_ACCOUNT.getTablePrefix(), account.getObjectNo());
        AccountQry accountQry = new AccountQry();
        accountQry.setTableSuffix(storageNode.getTableSuffix());
        BeanUtils.copyProperties(account, accountQry);
        return accountMapper.insert(accountQry);
    }

    @Override
    public Account findByObjNo(String objectNo) {
        HashStorageNode storageNode = splitTableStorage.getHashStorageNode(OriginalTable.AC_ACCOUNT.getTablePrefix(), objectNo);
        AccountQry accountQry = new AccountQry();
        accountQry.setObjectNo(objectNo);
        accountQry.setTableSuffix(storageNode.getTableSuffix());
        List<Account> list = accountMapper.queryList(accountQry);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public void copyTableStructure(String newTableName, String originalTableName) {
        accountMapper.copyTableStructure(newTableName, OriginalTable.AC_ACCOUNT.getTableName());
    }

    @Override
    public void rehash(Integer splitNodeId) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("1.创建新的分表策略节点");
        Pair<HashStorageNode, HashStorageNode> nodePair = createHashStorageNode(splitNodeId);
        HashStorageNode splitNode = nodePair.getKey();
        HashStorageNode newNode = nodePair.getValue();
        stopWatch.stop();
        stopWatch.start("2.创建表");
        String oldTableSuffix = splitNode.getTableSuffix();
        accountMapper.copyTableStructure(newNode.getTablePrefix() + "_" + newNode.getTableSuffix()
                , splitNode.getTablePrefix() + "_" + oldTableSuffix);
        stopWatch.stop();
        stopWatch.start("3.迁移数据");
        List<Long> succList = this.transferData(splitNode, newNode);
        stopWatch.stop();
        stopWatch.start("4.修改分表节点状态");
        newNode.setNodeStatus(SplitTableStorageNode.NodeStatus.ONLINE);
        newNode.setDataStatus(SplitTableStorageNode.DataStatus.READY);
        splitTableStorage.updateStorageNode(newNode);
        stopWatch.stop();
        stopWatch.start("5.删除已迁移数据");
        succList.forEach(id -> accountMapper.deleteByPrimaryKey(id, oldTableSuffix));
        stopWatch.stop();
        LOGGER.info("扩表秒表记录========>>>>>>\n{}", stopWatch.prettyPrint());
    }


    /**
     * 创建新的分表策略节点
     *
     * @param splitNodeId 待拆分节点id
     * @return
     */
    private Pair<HashStorageNode, HashStorageNode> createHashStorageNode(Integer splitNodeId) {
        Assert.notNull(splitNodeId, "待拆分节点id空");
        HashStorageNode splitNode = splitTableStorage.findHashStorageNode(splitNodeId);
        Assert.notNull(splitNode, "待拆分节点不存在");
        List<HashStorageNode> hashStorageNodes = splitTableStorage.queryByTablePrefix(OriginalTable.AC_ACCOUNT.getTablePrefix());
        int maxHashCode = HashUtil.MOD_VALUE;
        if (hashStorageNodes.size() > 1) {
            maxHashCode = splitTableStorage.getNext(splitNode).getHashCode();
        }
        HashStorageNode newNode = new HashStorageNode();
        BeanUtils.copyProperties(splitNode, newNode);
        newNode.setId(IdGenrator.get());//TODO
        newNode.setHashCode((maxHashCode - splitNode.getHashCode()) / 2);
        newNode.setNodeStatus(SplitTableStorageNode.NodeStatus.OFFLINE);
        newNode.setDataStatus(SplitTableStorageNode.DataStatus.NON_READY);
        newNode.setTableSuffix(String.valueOf(hashStorageNodes.size() + 1));
        int i = splitTableStorage.saveStorageNode(newNode);
        Assert.state(i == 1, "新分表节点保存失败");
        return new Pair<HashStorageNode, HashStorageNode>(splitNode, newNode);
    }


    /**
     * 迁移数据
     *
     * @return
     */
    private List<Long> transferData(HashStorageNode splitNode, HashStorageNode newNode) {
        AccountQry qry = new AccountQry();
        qry.setTableSuffix(splitNode.getTableSuffix());
        List<Account> list = accountMapper.queryList(qry);
        ArrayList<Long> succList = Lists.newArrayList();
        HashMap<String, List<Account>> map = Maps.newHashMap();
        list.forEach(account -> {
            HashStorageNode node = splitTableStorage.getHashStorageNode(OriginalTable.AC_ACCOUNT.getTablePrefix(), account.getObjectNo());
            if (!node.getTableSuffix().equals(splitNode.getTableSuffix())) {
                List<Account> accountList = map.computeIfAbsent(node.getTableSuffix(), k -> Lists.newArrayList());
                accountList.add(account);
            }
        });
        map.forEach((suffix, accountList) -> {
            AccountQry qryTmp = new AccountQry();
            qryTmp.setTableSuffix(suffix);
            for (Account account : accountList) {
                BeanUtils.copyProperties(account, qryTmp);
                int insert = accountMapper.insertSelective(qryTmp);
                if (1 == insert) {
                    succList.add(account.getId());
                    LOGGER.info("迁移数据id:[{}]-from:[{}]-to:[{}]", account.getId(), splitNode.getTableSuffix(), newNode.getTableSuffix());
                }
            }
        });
        return succList;
    }
}
