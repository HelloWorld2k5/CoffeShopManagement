package com.coffeeshop.decorator;

import com.coffeeshop.model.MenuItem;

public class AlmondMilkDecorator extends CoffeeDecorator {
    private double priceExtra;

    public AlmondMilkDecorator(MenuItem item, double priceExtra) {
        super(item);
        this.priceExtra = priceExtra;
    }

    // ✅ Copy constructor
    public AlmondMilkDecorator(AlmondMilkDecorator other) {
        super(other.decoratedItem.clone()); // 🔑 Clone đệ quy
        this.priceExtra = other.priceExtra;  // Primitive → copy trực tiếp
    }

    @Override
    public double getFinalPrice() {
        return super.getFinalPrice() + priceExtra;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (+ Sữa Hạnh Nhân)";
    }

    @Override
    public String getName() {
        return decoratedItem.getName() + " + Sữa Hạnh Nhân";
    }

    @Override
    public void setName(String name) {
        decoratedItem.setName(name);
    }

    @Override
    public void setBasePrice(double basePrice) {
        decoratedItem.setBasePrice(basePrice);
    }

    @Override
    public void setIcon(String icon) {
        decoratedItem.setIcon(icon);
    }

    @Override
    public void setDescription(String description) {
        decoratedItem.setDescription(description);
    }

    @Override
    public void setCategory(String category) {
        decoratedItem.setCategory(category);
    }

    @Override
    public void setStatus(String status) {
        decoratedItem.setStatus(status);
    }

    // ✅ Clone với covariant return type
    @Override
    public AlmondMilkDecorator clone() {
        return new AlmondMilkDecorator(this);
    }
}
