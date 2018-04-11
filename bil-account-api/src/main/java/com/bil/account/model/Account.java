package com.bil.account.model;

import lombok.*;

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
    private Long id;

    private String accountNo;

    private String accountName;

    private String accountType;

    private BigDecimal balance;

    private String currency;

    private String overdraft;

    private String balanceOfDirection;

    private String accountingSubjects;

    private String accountLevel;

    private String status;

    private String objectNo;

    private Date updateTime;

    private Date createTime;

    private String version;

}