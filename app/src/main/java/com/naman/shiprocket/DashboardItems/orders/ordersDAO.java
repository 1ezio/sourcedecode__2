package com.naman.shiprocket.DashboardItems.orders;

public class ordersDAO {

        String name, phone, paymentStatus, total,
                paymentMethod, createdAt,email,
                updatedAt, productId, productName,channel, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ordersDAO() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public ordersDAO(String name,String id, String email,String phone, String paymentStatus, String total, String paymentMethod, String createdAt, String updatedAt, String productId, String productName, String channel) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.paymentStatus = paymentStatus;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productId = productId;
        this.productName = productName;
        this.channel = channel;
    }
}
