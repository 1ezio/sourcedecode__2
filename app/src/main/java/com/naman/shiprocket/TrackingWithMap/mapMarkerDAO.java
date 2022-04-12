package com.naman.shiprocket.TrackingWithMap;

public class mapMarkerDAO {

    private String cusName,cusPhn,cuspayMethod,cusAddress , cusProductName, cusTotal,cusAdd2Show;

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusProductName() {
        return cusProductName;
    }

    public void setCusProductName(String cusProductName) {
        this.cusProductName = cusProductName;
    }

    public String getCusTotal() {
        return cusTotal;
    }

    public void setCusTotal(String cusTotal) {
        this.cusTotal = cusTotal;
    }

    public String getCusPhn() {
        return cusPhn;
    }

    public String getCusAdd2Show() {
        return cusAdd2Show;
    }

    public void setCusAdd2Show(String cusAdd2Show) {
        this.cusAdd2Show = cusAdd2Show;
    }

    public void setCusPhn(String cusPhn) {
        this.cusPhn = cusPhn;
    }

    public String getCuspayMethod() {
        return cuspayMethod;
    }

    public void setCuspayMethod(String cuspayMethod) {
        this.cuspayMethod = cuspayMethod;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public mapMarkerDAO(String cusName, String cusPhn, String cuspayMethod, String cusAddress ,String cusProductName,String cusTotal,String cusAdd2Show) {
        this.cusName = cusName;
        this.cusAdd2Show = cusAdd2Show;
        this.cusPhn = cusPhn;
        this.cuspayMethod = cuspayMethod;
        this.cusAddress = cusAddress;
        this.cusProductName = cusProductName;
        this.cusTotal = cusTotal;
    }
}
