package com.jashu.shopping_website.dto;

public class ShippingOrderItem {

    String name;
    String sku;
    int units;
    int sellingPrice;

//    "discount": "",
//    "tax": "",
//    "hsn": ""


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
