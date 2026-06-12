package com.coffeeshop.decorator;

import com.coffeeshop.model.MenuItem;

public class ExtraShotDecorator extends CoffeeDecorator {
    private double priceExtra; // Lấy từ bảng tuyChonMon

    public ExtraShotDecorator(MenuItem item, double priceExtra) {
        super(item);
        this.priceExtra = priceExtra;
    }

    @Override
    public double getFinalPrice() {
        return super.getFinalPrice() + priceExtra;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (+ Extra Shot)";
    }

    @Override
    public String getName() {
    // Trả về tên món được bọc + tên topping mới
    return decoratedItem.getName() + " + Extra Shot";
}

    @Override
    public void setName(String name) {
        
    }

    @Override
    public void setCategory(String category) {
        
    }

    @Override
    public void setBasePrice(double basePrice) {
        
    }

    @Override
    public void setDescription(String description) {
        
    }

    @Override
    public void setIcon(String icon) {
        
    }

    @Override
    public void setStatus(String status) {
        
    }
}