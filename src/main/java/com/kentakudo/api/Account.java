package com.kentakudo.api;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Account 
{
    @JsonCreator
    public Account(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("amount") int amount) {
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Object[] serialise() {
        return new Object[] {getId(), getName(), getAmount()};
    }

    public static Account deserialise(Object[] args) {
        return new Account((Integer)args[0], (String)args[1], (Integer)args[2]);
    }

    private int id;
    private String name;
    private int amount;
}