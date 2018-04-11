package com.bil.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 记账凭证
 *
 * @author bob
 * @since 2018年04月05日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcTransaction implements Serializable {
    private Long id;

    private String commandNo;

    private String commandType;

    private Integer sortNum;

    private String transactionCode;

    private String fromAccountNo;

    private String toAccountNo;

    private BigDecimal amount;

    private String currency;

    private String transferType;

    private String productCode;

    private String businessCode;

    private String tradeNo;

    private Date tradeTime;

    private String outTradeNo;

    private String resultCode;

    private String bookkeepingCoder;

    private String note;

    private Date createTime;

    private String version;

}