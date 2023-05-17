package com.innovation.manageShop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {

    private String id;
    private String title;
    private String description;
    private String image; //URL
    private int price;
}
