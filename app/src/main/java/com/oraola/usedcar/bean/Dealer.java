package com.oraola.usedcar.bean;

/**
 * ClassName: Dealer
 * Package:
 *
 * @Author: OraOla
 * @Create: 2024/12/31 13:22
 * @Version: 1.0
 */

//车商

public class Dealer {
    private int dealerId;        // 车商唯一标识
    private String dealername;   // 用户名
    private String password;     // 密码

    public Dealer() {
    }

    public Dealer(int dealerId, String dealername, String password) {
        this.dealerId = dealerId;
        this.dealername = dealername;
        this.password = password;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealername() {
        return dealername;
    }

    public void setDealername(String dealername) {
        this.dealername = dealername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}