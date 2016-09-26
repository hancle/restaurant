package com.laioffer.laiofferproject;

import android.graphics.Bitmap;

/**
 * A class for restaurant, which contains all information of a restaurant.
 */
public class Restaurant {

    /**
     * All data for a restaurant.
     */
    private String name;
    private String address;
    private String type;
    private String businessId;
    private double lat;
    private double lng;



    /**
     * Constructor
     *
     * @param name name of the restaurant
     */
    public Restaurant(
            String name,
            String address,
            String type,
            String businessId,
            double lat,
            double lng) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.businessId = businessId;
        this.lat = lat;
        this.lng = lng;
    }
    //public Bitmap getRating() { return rating; }



    /**
     * Getters for private attributes of Restaurant class.
     */
    public String getName() { return this.name; }

    public String getAddress() { return this.address; }

    public String getBusinessId() {return this.businessId; }

    public String getType() { return this.type; }

    public double getLat() { return lat; }

    public double getLng() { return lng; }

    //public Bitmap getThumbnail() { return thumbnail; }

}

