package com.bil.base.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Hash分表策略节点
 *
 * @author bob
 * @since 2018年04月06日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashStorageNode implements SplitTableStorageNode {
    private Integer id;

    private Integer hashCode;

    private String tablePrefix;

    private String tableSuffix;

    private String nodeStatus;

    private String dataStatus;

    private Date updateTime;

    private Date createTime;

    private String version;
}