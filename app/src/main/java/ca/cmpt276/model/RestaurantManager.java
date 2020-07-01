package ca.cmpt276.model;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private static List<Restaurant> restList = new ArrayList<>();

    /*Singleton*/
    private static RestaurantManager instance;
    private RestaurantManager(){
        //do nothing;
    }

    public RestaurantManager getInstance() {return instance;}
    /*Singleton*/


    public List getRestList(){
        return restList;
    }

    public Restaurant getRestaurant(int value){
        for(int i = 0; i < restList.size(); i++) {
            if (i == value) {
                return restList.get(i);
            }
        }
        return null;
    }

}
