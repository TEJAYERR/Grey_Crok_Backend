package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingOrderItem {

    String name;
    String sku;
    int units;
    int sellingPrice;

//    "discount": "",
//    "tax": "",
//    "hsn": ""


}
