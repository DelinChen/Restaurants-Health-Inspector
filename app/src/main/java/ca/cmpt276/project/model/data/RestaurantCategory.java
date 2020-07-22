package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;

import ca.cmpt276.project.R;

/*This enum class is for us to insert specific images for some restaurants for the requirement
, we will only work on some of the restaurants, since we dont have enough time to work on 12K
restaurant*/
public enum RestaurantCategory {
    ANW("A&W", R.drawable.anw),
    BLENZ("Blenz", R.drawable.blenz),
    BOSTON_PIZZA("Boston Pizza", R.drawable.bostonpizza),
    BURGERKING("Burger king", R.drawable.burgerking),
    CAFE("Cafe", R.drawable.cafe),
    CHINESE("Chinese", R.drawable.chinese),
    CURRY("Curry", R.drawable.curry), //Will put somme indian restaurant into this catergory if later have time
    DAIRYQUEEN("Dairy queen", R.drawable.dairyqueen),
    FASTFOOD("fastfood", R.drawable.fastfood),
    FRESHSLICE("Freshslice", R.drawable.freshslice),
    GROCREY("Grocrey", R.drawable.grocrey),
    JUICE("Juice", R.drawable.juice),
    KOREAN("Korean", R.drawable.kimchi),
    MCDONALD("Mcdonald", R.drawable.mcdonalds),
    PANAGO("Panago", R.drawable.panago),
    PHO("Pho", R.drawable.pho),
    PIZZA("Pizza", R.drawable.pizza),
    RAMEN("Ramen", R.drawable.ramen),
    SAFE_WAY("Safeway", R.drawable.safeway),
    SAVEONFOOD("Save On Food", R.drawable.saveonfood),
    SEVENELVEN("7-Eleven", R.drawable.seveneleven),
    STARBUCKS("Starbucks", R.drawable.starbucks),
    SUBWAY("Subway", R.drawable.subway),
    SUSHI("Sushi", R.drawable.sushi),
    TEA("Tea", R.drawable.tea),//For BubbleTea use
    WHITESPOT("White Spot", R.drawable.whitespot),
    GENERAL_CATEGORY("", R.drawable.restaurant); //not specific defined in the catergories

    //Field of enum

    public final String stringVal;
    public final int picID;

    //Constructors
    RestaurantCategory(String stringVal, int picID) {
        this.stringVal = stringVal;
        this.picID = picID;
    }


    @NonNull
    @Override
    public String toString() {
        return this.stringVal;
    }



    //methods
    public static RestaurantCategory fromString(String val) {
        for (RestaurantCategory restaurantCategory : RestaurantCategory.values()) {
            if (restaurantCategory.stringVal.equals(val)) {
                return restaurantCategory;
            }
        }
        return GENERAL_CATEGORY;
    }

}





