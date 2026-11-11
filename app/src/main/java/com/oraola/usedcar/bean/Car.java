package com.oraola.usedcar.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//车辆信息
public class Car {
    private int carId;                 // 车辆唯一标识
    private String engineNumber;       // 发动机号
    private String brand;              // 品牌
    private String model;              // 型号
    private String grade;              // 车况 (S, A, B, C)
    private double acquisitionPrice;   // 收购价格
    private Date acquisitionDate;      // 收购日期
    private Double salePrice;          // 销售价格（如果已售出）
    private Date saleDate;             // 销售日期（如果已售出）
    private String status;             // 状态（available, sold）
    private int dealerId;              // 所属车商ID

    public Car() {
    }

    public Car(int carId, String engineNumber, String brand, String model, String grade, double acquisitionPrice, Date acquisitionDate, Double salePrice, Date saleDate, String status, int dealerId) {
        this.carId = carId;
        this.engineNumber = engineNumber;
        this.brand = brand;
        this.model = model;
        this.grade = grade;
        this.acquisitionPrice = acquisitionPrice;
        this.acquisitionDate = acquisitionDate;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.status = status;
        this.dealerId = dealerId;
    }

    public Car(int carId, String engineNumber, String brand, String model, String grade, double acquisitionPrice, String acquisitionDate, Double salePrice, String saleDate, String status, int dealerId) throws ParseException {
        this.carId = carId;
        this.engineNumber = engineNumber;
        this.brand = brand;
        this.model = model;
        this.grade = grade;
        this.acquisitionPrice = acquisitionPrice;
        this.acquisitionDate = new SimpleDateFormat("yyyy-MM-dd").parse(acquisitionDate);
        this.salePrice = salePrice;
        this.status = status;
        this.dealerId = dealerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getAcquisitionPrice() {
        return acquisitionPrice;
    }

    public void setAcquisitionPrice(double acquisitionPrice) {
        this.acquisitionPrice = acquisitionPrice;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }
}