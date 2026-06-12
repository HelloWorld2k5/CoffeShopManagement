package com.coffeeshop.model;

public class TableItem {
    private int id;
    private String name;
    private String status; // 'FREE' hoặc 'BUSY'

    public TableItem(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Ghi đè phương thức toString để JComboBox hiển thị đẹp mắt
    @Override
    public String toString() {
        String friendlyStatus = "FREE".equalsIgnoreCase(status) ? "Trống" : "Có khách";
        return name + " (" + friendlyStatus + ")";
    }
}