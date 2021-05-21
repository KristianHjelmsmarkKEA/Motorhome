package com.example.demo.Model;

public class Price {

    //Fields/Attributes
    private int feeID;
    private String itemName;
    private double itemPrice;
    private int foreign_categoryID;

    private int categoryID;
    private String itemCategory;


    //Constructor
    public Price(){ }

    //Getters & Setters
    public int getFeeID() {return feeID;}

    public void setFeeID(int feeID) {this.feeID = feeID;}

    public String getItemName() {return itemName;}

    public void setItemName(String itemName) {this.itemName = itemName;}

    public double getItemPrice() {return itemPrice;}

    public void setItemPrice(double itemPrice) {this.itemPrice = itemPrice;}

    public int getForeign_categoryID() {
        return foreign_categoryID;
    }

    public void setForeign_categoryID(int foreign_categoryID) {
        this.foreign_categoryID = foreign_categoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemCategory() {return itemCategory;}

    public void setItemCategory(String itemCategory) {this.itemCategory = itemCategory;}
}
