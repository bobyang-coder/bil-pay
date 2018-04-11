package com.bil.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户流水
 *
 * @author bob
 * @since 2018年04月05日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcSequential implements Serializable {
    private Long id;

    private String transactionId;

    private String accountNo;

    private String balance;

    private String preBalance;

    private String creditAmount;

    private String debitAmount;

    private String oppositeAccNo;

    private Date createdTime;

    private String version;
}