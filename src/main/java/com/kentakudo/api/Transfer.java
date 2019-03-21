package com.kentakudo.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transfer {
    @JsonCreator
    public Transfer(
        @JsonProperty("id") int id,
        @JsonProperty("amount") int amount,
        @JsonProperty("from_user_id") int fromUserId,
        @JsonProperty("to_user_id") int toUserId
    ) {
        this.id = id;
        this.amount = amount;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public Object[] serialise() {
        return new Object[] {getId(), getAmount(), getFromUserId(), getToUserId()};
    }

    public static Transfer deserialise(Object[] args) {
        return new Transfer((Integer)args[0], (Integer)args[1], (Integer)args[2], (Integer)args[3]);
    }
    
    private int id;
    private int amount;
    @JsonProperty("from_user_id") private int fromUserId;
    @JsonProperty("to_user_id") private int toUserId;
}