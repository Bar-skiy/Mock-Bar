package ru.bar.mock.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    private String id;

    private int debit;

    private int credit;

    @JsonProperty("date of registration")
    private String regDate;

    private List<Item> items;

    public String getId() {
        return id;
    }

    public int getDebit() {
        return debit;
    }

    public int getCredit() {
        return credit;
    }

    public String getRegDate() {
        return regDate;
    }

    public List<Item> getItems() {
        return items;
    }
}