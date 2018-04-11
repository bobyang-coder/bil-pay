package com.bil.account.model.query;

import com.bil.account.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bob on 2018/4/7.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/7
 */
@Data
@NoArgsConstructor
public class AccountQry extends Account {

    private String tableSuffix;
}
