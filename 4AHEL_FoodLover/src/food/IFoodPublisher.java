package food;

public interface IFoodPublisher {

    void addSubscriber(IFoodSubscriber subscriber);

    void removeSubscriber(IFoodSubscriber subscriber);

    void notifySubscriber(String meal);

}
