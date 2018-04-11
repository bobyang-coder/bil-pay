package com.bil.base.storage.model;

import com.bil.base.storage.service.SplitTableStorage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 时间分表策略节点
 *
 * @author bob
 * @since 2018年04月06日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateStorageNode implements SplitTableStorageNode {
    private Integer id;

    private Date startDate;

    private String tablePrefix;

    private String tableSuffix;

    private String nodeStatus;

    private String dataStatus;

    private Date updateTime;

    private Date createTime;

    private String version;
}