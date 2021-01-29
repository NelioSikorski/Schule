package food;

public class MeatEater implements IFoodSubscriber {
    private String[] favouriteFood = {"Fleisch", "Käse", "Eier", "Früchte"};

    @Override
    public void notify(String meal) {
        for (String food : favouriteFood) {
            if (meal.contains(food)) {
                System.out.println("MeatEater: Oida bees!");
            }
        }
    }
}
