package com.uek.etl.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private Long productId;
    private String category;
    private String manufacturer;
    private String model;
    private String additionalInfo;


    public Product(Long productId, String category, String manufacturer, String model, String additionalInfo) {

        this.productId = productId;
        this.category = category;
        this.manufacturer = manufacturer;
        this.model = model;
        this.additionalInfo = additionalInfo;
    }

    public Product() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", category='" + category + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
