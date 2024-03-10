package com.it.banksystem;

import java.io.Serial;
import java.io.Serializable;

public class Manager implements Serializable {

    @Serial
    private static final long serialVersionUID = 3159496554542791343L;
    private String mgName;
    private String accountId;

    public Manager() {
    }

    public Manager(String mgName, String accountId) {
        this.mgName = mgName;
        this.accountId = accountId;
    }

    /**
     * 获取
     * @return mgName
     */
    public String getMgName() {
        return mgName;
    }

    /**
     * 设置
     * @param mgName
     */
    public void setMgName(String mgName) {
        this.mgName = mgName;
    }

    /**
     * 获取
     * @return accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 设置
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String toString() {
        return "Manager{mgName = " + mgName + ", accountId = " + accountId + "}";
    }
}
