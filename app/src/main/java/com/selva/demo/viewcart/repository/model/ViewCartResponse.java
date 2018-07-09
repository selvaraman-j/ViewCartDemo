package com.selva.demo.viewcart.repository.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/29/2018
 */

public class ViewCartResponse {
    @SerializedName("itemName")
    private String title;
    @SerializedName("itemDescription")
    private String description;
    @SerializedName("ItemDelivery")
    private String delivery;
    @SerializedName("itemUrl")
    private String image;
    private String quantity;
    @SerializedName("itemPrice")
    private String price;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
