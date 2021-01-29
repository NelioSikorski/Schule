package food;

import java.util.ArrayList;

public class Vegetarian implements IFoodSubscriber {
    private ArrayList<String> favoriteFood = new ArrayList<>();

    public Vegetarian() {
        favoriteFood.add("Käse");
        favoriteFood.add("Eier");
        favoriteFood.add("Früchte");
    }

    @Override
    public void notify(String meal) {
        for(String food : favoriteFood) {
            if (meal.contains(food)) {
                System.out.println("Vegetarian: Yummy");
            }
        }
    }
}

