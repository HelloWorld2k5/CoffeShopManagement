package com.coffeeshop.decorator;

import com.coffeeshop.model.MenuItem;

public abstract class CoffeeDecorator implements MenuItem {
    protected MenuItem decoratedItem;

    public CoffeeDecorator(MenuItem item) {
        this.decoratedItem = item;
    }

    @Override public int getId() { return decoratedItem.getId(); }
    @Override public String getName() { return decoratedItem.getName(); }
    @Override public String getCategory() { return decoratedItem.getCategory(); }
    @Override public double getBasePrice() { return decoratedItem.getBasePrice(); }
    @Override public String getDescription() { return decoratedItem.getDescription(); }
    @Override public String getIcon() { return decoratedItem.getIcon(); }
    @Override public String getStatus() { return decoratedItem.getStatus(); }
    @Override public double getFinalPrice() { return decoratedItem.getFinalPrice(); }

    // ✅ Clone abstract - bắt buộc subclass phải implement
    @Override
    public abstract CoffeeDecorator clone();
}
