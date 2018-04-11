package com.bil.account;

/**
 * Created by bob on 2018/4/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public enum OriginalTable {
    AC_ACCOUNT("AC_ACCOUNT_1", "AC_ACCOUNT");

    private String tableName;

    /**
     * 表名前缀
     */
    private String tablePrefix;

    OriginalTable(String tableName, String tablePrefix) {
        this.tableName = tableName;
        this.tablePrefix = tablePrefix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
