package com.bil.base.storage.service.impl;

import com.bil.base.storage.model.DateStorageNode;
import com.bil.base.storage.model.HashStorageNode;
import com.bil.base.storage.service.SplitTableStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application_context.xml")
public class SplitTableStorageImplTest {
    @Autowired
    private SplitTableStorage splitTableStorage;

    @Test
    public void saveStorageNode() {
        DateStorageNode dateStorageNode = DateStorageNode.builder()
                .id(1)
                .startDate(new Date())
                .tablePrefix("AC_ACCOUNT")
                .tableSuffix("2018_04")
                .dataStatus("1")
                .nodeStatus("1")
                .createTime(new Date())
                .updateTime(new Date())
                .version("1.0.0.0")
                .build();
        int insertNum = splitTableStorage.saveStorageNode(dateStorageNode);
        Assert.assertEquals(1, insertNum);
    }

    @Test
    public void saveHashStorageNode() {
        HashStorageNode hashStorageNode = HashStorageNode.builder()
                .id(1)
                .hashCode(0)
                .tablePrefix("AC_ACCOUNT")
                .tableSuffix("0")
                .dataStatus("1")
                .nodeStatus("1")
                .createTime(new Date())
                .updateTime(new Date())
                .version("1.0.0.0")
                .build();
        int insertNum = splitTableStorage.saveStorageNode(hashStorageNode);
        Assert.assertEquals(1, insertNum);
    }
}

