package com.it.banksystem;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Customers implements Serializable {


    @Serial
    private static final long serialVersionUID = -1069345691522305938L;
    private  String name;//用户姓名
    private  double allMoney;//用户总资产
    private  int rank;//用户排名
    private  int passWord;//密码
    private  String cardNumber;//银行卡号


    public Customers() {
    }

    public Customers(String name, double allMoney, int rank, int passWord, String cardNumber) {
        this.name = name;
        this.allMoney = allMoney;
        this.rank = rank;
        this.passWord = passWord;
        this.cardNumber = cardNumber;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return allMoney
     */
    public double getAllMoney() {
        return allMoney;
    }

    /**
     * 设置
     * @param allMoney
     */
    public void setAllMoney(double allMoney) {
        this.allMoney = allMoney;
    }

    /**
     * 获取
     * @return rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * 设置
     * @param rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 获取
     * @return passWord
     */
    public int getPassWord() {
        return passWord;
    }

    /**
     * 设置
     * @param passWord
     */
    public void setPassWord(int passWord) {
        this.passWord = passWord;
    }

    /**
     * 获取
     * @return cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String toString() {
        return "Customers{name = " + name + ", allMoney = " + allMoney + ", rank = " + rank + ", passWord = " + passWord + ", cardNumber = " + cardNumber + "}";
    }
}
