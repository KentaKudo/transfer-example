package com.kentakudo.api;

public class Account 
{
    public Account(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    private int id;
    private String name;
    private int amount;
}