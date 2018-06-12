package com.bil.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户
 *
 * @author bob
 * @since 2018年04月05日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 币种
     */
    private String currency;

    /**
     * 是否允许透支
     */
    private String overdraft;

    /**
     * 余额方向(debit-借记:D,credit-贷记:C)
     */
    private String balanceOfDirection;

    /**
     * 会计科目
     */
    private String accountingSubjects;

    /**
     * 账户登记
     */
    private String accountLevel;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 对象号(账户所属对象)
     */
    private String objectNo;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 版本号
     */
    private String version;

}