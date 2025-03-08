package com.restapi.demo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private long id;
    private String name;
    private double price;
}
