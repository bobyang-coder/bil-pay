package com.bil.account.service.impl;

import com.bil.account.model.Account;
import com.bil.account.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application_context.xml")
//@Transactional()
public class AccountServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImplTest.class);

    @Autowired
    private AccountService accountService;


    @Test
    public void saveAccount() {
        Account account = Account.builder()
                .id(2L)
                .accountNo("10000100001000011156")
                .accountName("yhb-init")
                .accountType("100011")
                .balance(BigDecimal.ZERO)
                .currency("1")
                .overdraft("1")
                .balanceOfDirection("D")
                .accountingSubjects("1100010001")
                .accountLevel("1")
                .status("1")
                .objectNo("100001000010000")
                .version("1")
                .build();
        int insertNum = accountService.saveAccount(account);
        Assert.assertEquals(1, insertNum);
    }

    @Test
    public void findAccount() {
        Account account = accountService.findAccount(1L);
        LOGGER.info("{}", account);
        Assert.assertNotNull(account);
    }

    @Test
    public void findByObjNo() {
        Account account = accountService.findByObjNo("100001000010000");
        LOGGER.info("{}", account);
        Assert.assertNotNull(account);
    }

    @Test
    public void copyTableStructure() {
        accountService.copyTableStructure("AC_ACCOUNT_2", "AC_ACCOUNT_1");
    }

    @Test
    public void rehash() {
        accountService.rehash(1);
    }
}