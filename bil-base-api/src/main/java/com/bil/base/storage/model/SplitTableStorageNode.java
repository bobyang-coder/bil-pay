package com.bil.base.storage.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 分表策略节点
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public interface SplitTableStorageNode extends Serializable {

    /**
     * 存储节点状态
     */
    interface NodeStatus {
        /**
         * 离线
         */
        String OFFLINE = "0";
        /**
         * 离线
         */
        String ONLINE = "1";
    }

    /**
     * 节点数据状态
     */
    interface DataStatus {
        /**
         * 未准备好
         */
        String NON_READY = "0";

        /**
         * 已准备号
         */
        String READY = "1";
    }


    @Data
    class TableInfo {

        private String tablePrefix;

        private String tableSuffix;
    }
}
