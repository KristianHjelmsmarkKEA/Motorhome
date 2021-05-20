package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContractDetails {

    @Id
    //Fields & Attributes
    private int detailsID;
    private double calculatedPrice;
    private int foreign_feeID;
    private int foreign_orderID;


}
