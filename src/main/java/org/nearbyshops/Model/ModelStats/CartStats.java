package org.nearbyshops.Model.ModelStats;

import org.nearbyshops.Model.ModelUtility.DeliveryConfig;
import org.nearbyshops.Model.Shop;

/**
 * Created by sumeet on 1/6/16.
 */
public class CartStats {



    private int cartID;
    private int itemsInCart;
    private double cart_Total;
    private double savingsOverMRP;
    private int shopID;
    private Shop shop;
    private DeliveryConfig deliveryConfig;




    // Getter and Setter

    public DeliveryConfig getDeliveryConfig() {
        return deliveryConfig;
    }

    public void setDeliveryConfig(DeliveryConfig deliveryConfig) {
        this.deliveryConfig = deliveryConfig;
    }

    public double getSavingsOverMRP() {
        return savingsOverMRP;
    }

    public void setSavingsOverMRP(double savingsOverMRP) {
        this.savingsOverMRP = savingsOverMRP;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getItemsInCart() {
        return itemsInCart;
    }

    public void setItemsInCart(int itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public double getCart_Total() {
        return cart_Total;
    }

    public void setCart_Total(double cart_Total) {
        this.cart_Total = cart_Total;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
