package com.oraola.usedcar.bean;


import java.util.Date;

//交易信息
public class CarTransaction {
    private int transactionId;         // 交易记录唯一标识
    private int carId;                 // 车辆ID
    private String transactionType;    // 交易类型（acquisition, sale）
    private double transactionPrice;   // 交易价格
    private Date transactionDate;      // 交易日期
    private int dealerId;              // 车商ID

    public CarTransaction() {
    }

    public CarTransaction(int transactionId, int carId, String transactionType, double transactionPrice, Date transactionDate, int dealerId) {
        this.transactionId = transactionId;
        this.carId = carId;
        this.transactionType = transactionType;
        this.transactionPrice = transactionPrice;
        this.transactionDate = transactionDate;
        this.dealerId = dealerId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }
}