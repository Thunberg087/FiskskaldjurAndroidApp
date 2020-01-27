package com.example.fiskskaldjurandroidapp.Fragments.Orders;

public class OrderData {

    private String fishName;
    private String weight;
    private int fridgeId;

    public OrderData(String fishName, String weight, int fridgeId){
        this.fishName = fishName;
        this.weight = weight;
        this.fridgeId = fridgeId;
    }

    //Getters for all data
    public String getFishName() {
        return fishName;
    }

    public String getWeight() {
        return weight;
    }

    public int getFridgeId() {
        return fridgeId;
    }
}
