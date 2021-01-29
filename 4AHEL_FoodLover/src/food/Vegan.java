package food;

public class Vegan implements IFoodSubscriber {
    @Override
    public void notify(String meal) {
        if (meal.contains("Früchte") || meal.contains("Gemüse")) {
            System.out.println("Vegan: Yummy");
        }
    }
}
