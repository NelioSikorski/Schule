package food;

public interface IFoodPublisher {

    public void addSubscriber(IFoodSubscriber subscriber);

    public void removeSubscriber(IFoodSubscriber subscriber );

    public void notifySubscriber(String meal);

}
