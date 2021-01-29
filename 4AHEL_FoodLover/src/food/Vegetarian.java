package food;

import java.util.ArrayList;

public class Vegetarian implements IFoodSubscriber {
    private ArrayList<String> favouriteFood = new ArrayList<>();

    public Vegetarian(){
        favouriteFood.add("Käse");
        favouriteFood.add("Eier");
        favouriteFood.add("Früchte");
    }
    @Override
    public void notify(String meal) {
        for(String food: favouriteFood){
            if(meal.contains(food)){
                System.out.println("Vegetarian: Do bin i dabei!");
            }
        }
    }
}
