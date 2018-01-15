package com.derekpoon.starbuzzdb;

/**
 * Created by derekpoon on 01/12/2017.
 */

public class Drink {

    //member variables
    private String name;
    private String description;
    private int imageResourceId;

    //drinks is an array of Drink
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of expresso shots with steamed milk", R.drawable.latte),
            new Drink("Cappuccino", "Expresso, hot milk, and a steamed milk foam", R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };

    //each drink has a name, description, and am image resource
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    /*
    getters for the private varviables
     */

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    //string representation of a Drink
    public String toString() {
        return this.name;
    }
}