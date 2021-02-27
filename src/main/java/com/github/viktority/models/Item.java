package com.github.viktority.models;

public class Item {
    public enum Currency {
        EUR, USD;
    }


    private String name;
    private long amount;
    private String currency;
    private String image;
    private long quantity;
    private String clientSecret;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Item(String name, long amount, String currency, String image, long quantity) {
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.image = image;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
