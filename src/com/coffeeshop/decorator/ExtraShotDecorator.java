package com.coffeeshop.decorator;

import com.coffeeshop.model.MenuItem;

public class ExtraShotDecorator extends CoffeeDecorator {
    private double priceExtra;

    public ExtraShotDecorator(MenuItem item, double priceExtra) {
        super(item);
        this.priceExtra = priceExtra;
    }

    // ✅ Copy constructor
    public ExtraShotDecorator(ExtraShotDecorator other) {
        super(other.decoratedItem.clone()); // 🔑 Clone đệ quy
        this.priceExtra = other.priceExtra;  // Primitive → copy trực tiếp
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
        return decoratedItem.getName() + " + Extra Shot";
    }

    @Override
    public void setName(String name) {
        // Empty
    }

    @Override
    public void setCategory(String category) {
        // Empty
    }

    @Override
    public void setBasePrice(double basePrice) {
        // Empty
    }

    @Override
    public void setDescription(String description) {
        // Empty
    }

    @Override
    public void setIcon(String icon) {
        // Empty
    }

    @Override
    public void setStatus(String status) {
        // Empty
    }

    // ✅ Clone với covariant return type
    @Override
    public ExtraShotDecorator clone() {
        return new ExtraShotDecorator(this);
    }
}
