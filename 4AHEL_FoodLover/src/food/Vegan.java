package food;

public class Vegan implements IFoodSubscriber {
    @Override
    public void notify(String meal) {
        if (meal.contains("Gemüse") || meal.contains("Früchte")) {
            System.out.println("Vegan: Ehre!\n");
        }
    }
}
