package com.selva.demo.viewcart.repository.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.model.ViewCartResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/9/2018
 */
@Entity
public class ViewCartModelEntity {
    @PrimaryKey
    @NonNull
    public final String id;
    public final String itemName;
    public final String itemDescription;
    public final String itemDelivery;
    public final String itemImage;
    public final String itemQuantity;
    public final String itemPrice;

    /**
     * Creates a ViewCartModelEntity
     *
     * @param id              The cart item id
     * @param itemName        The cart item name
     * @param itemDescription The cart item description
     * @param itemDelivery    The cart item delivery date
     * @param itemImage       The cart item image url
     * @param itemQuantity    The cart item quantity
     * @param itemPrice       The cart item price
     */
    public ViewCartModelEntity(@NonNull String id, String itemName, String itemDescription
            , String itemDelivery, String itemImage, String itemQuantity, String itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDelivery = itemDelivery;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    /**
     * Returns ViewCartModelEntity object from {@link ViewCartModel}
     *
     * @param viewCartModel The view cart model
     * @return ViewCartModelEntity for store this entity in db
     */
    public static ViewCartModelEntity getViewCartModelEntity(ViewCartModel viewCartModel) {
        return new ViewCartModelEntity(viewCartModel.id, viewCartModel.itemName
                , viewCartModel.itemDescription, viewCartModel.itemDelivery, viewCartModel.itemImage
                , "".equals(viewCartModel.itemQuantity) ? "1" : viewCartModel.itemQuantity
                , viewCartModel.itemPrice);
    }

    /**
     * Returns the list of view cart model entity from list of {@link ViewCartResponse}
     *
     * @param viewCartResponseList the list of view cart response object which is received from web service
     * @return The list of view cart model entity for to store it in db
     */
    public static List<ViewCartModelEntity> getViewCartModelEntity(List<ViewCartResponse> viewCartResponseList) {
        List<ViewCartModelEntity> entities = new ArrayList<>();
        for (ViewCartResponse viewCartResponse : viewCartResponseList) {
            entities.add(getViewCartModelEntity(viewCartResponse));
        }
        return entities;
    }

    /**
     * Returns view cart model entity from {@link ViewCartResponse}
     *
     * @param viewCartResponse The view cart response object
     * @return The ViewCartModelEntity
     */
    private static ViewCartModelEntity getViewCartModelEntity(ViewCartResponse viewCartResponse) {
        return new ViewCartModelEntity(viewCartResponse.getId(), viewCartResponse.getTitle()
                , viewCartResponse.getDescription()
                , viewCartResponse.getDelivery()
                , viewCartResponse.getImage()
                , "".equals(viewCartResponse.getQuantity()) ? "1" : viewCartResponse.getQuantity()
                , viewCartResponse.getPrice());
    }
}
