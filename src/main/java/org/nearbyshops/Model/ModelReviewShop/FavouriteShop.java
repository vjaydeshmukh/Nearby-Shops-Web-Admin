package org.nearbyshops.Model.ModelReviewShop;

import org.nearbyshops.Model.ModelRoles.User;
import org.nearbyshops.Model.Shop;

/**
 * Created by sumeet on 8/8/16.
 */
public class FavouriteShop {

    // Table Name
    public static final String TABLE_NAME = "FAVOURITE_SHOP";

    // column Names
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String SHOP_ID = "SHOP_ID"; // foreign Key
    public static final String IS_FAVOURITE = "IS_FAVOURITE";


    // Create Table Statement
    public static final String createTableFavouriteBookPostgres = "CREATE TABLE IF NOT EXISTS "
            + FavouriteShop.TABLE_NAME + "("

            + " " + FavouriteShop.END_USER_ID + " INT,"
            + " " + FavouriteShop.SHOP_ID + " INT,"
            + " " + FavouriteShop.IS_FAVOURITE + " boolean,"

            + " FOREIGN KEY(" + FavouriteShop.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + FavouriteShop.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ") ON DELETE CASCADE,"
            + " PRIMARY KEY (" + FavouriteShop.END_USER_ID + ", " + FavouriteShop.SHOP_ID + ")"
            + ")";



    // instance Variables

    private Integer endUserID;
    private Integer shopID;

    private Shop shopProfile;

    // Getter and Setter


    public Shop getShopProfile() {
        return shopProfile;
    }

    public void setShopProfile(Shop shopProfile) {
        this.shopProfile = shopProfile;
    }

    public Integer getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(Integer endUserID) {
        this.endUserID = endUserID;
    }

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }
}
