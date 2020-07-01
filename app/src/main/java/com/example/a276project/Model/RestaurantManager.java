package com.example.a276project.Model;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private static List<Restaurant> restList = new ArrayList<>();

    /*Singleton*/
    private static RestaurantManager instance;
    private RestaurantManager(){
        //do nothing;
    }

    public RestaurantManager getInstance() {return instance;};

}
