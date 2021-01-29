package food;

public class MeatEater implements IFoodSubscriber {
    private String[] favoriteFood = {"Fleisch", "Käse", "Eier", "Früchte"};

    @Override
    public void notify(String meal) {
        for (String food : favoriteFood) {
            if (meal.contains(food)) {
                System.out.println("MeatEater: Yummy");
            }
        }
    }
}
