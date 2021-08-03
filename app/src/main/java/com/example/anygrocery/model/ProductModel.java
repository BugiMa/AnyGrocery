package com.example.anygrocery.model;

public class ProductModel {

    private String name;
    private Float amount;
    private Boolean isChecked;

    public ProductModel( String name, Float amount) {
        this.name = name;
        this.amount = amount;
        this.isChecked = false;
    }

    public ProductModel() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }
    public void setName(Float amount) {
        this.amount = amount;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

}
