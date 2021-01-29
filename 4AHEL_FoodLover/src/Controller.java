import food.IFoodPublisher;
import food.IFoodSubscriber;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Controller implements IFoodPublisher {
    /**
     * Alle GUI - Elemente, die über den Controller gesteuert
     * werden sollen, MÜSSEN über die FXML - Datei mittels
     * ALT + ENTER mit der Controller - Klasse verknüpft werden.
     */
    public TextArea messageTextArea;

    private ArrayList<IFoodSubscriber> subscribers = new ArrayList<>();

    @Override
    public void addSubscriber(IFoodSubscriber subscriber) {
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(IFoodSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber(String meal) {
        // for (Klassenname variablenname : liste)
        // --> Für jedes IFoodSubscriber "element" aus der Liste "subscribers"
        //     mache folgendes...
        for (IFoodSubscriber element : subscribers) {
            // benachrichtige jeden Subscriber (Fleischesser, Veganer, Vegetarier),
            // dass es etwas zum Essen geht.
            element.notify(meal);
        }
    }

    public void shoutButtonClicked(ActionEvent actionEvent) {

        // wenn etwas eingegeben wurde!
        if (messageTextArea.getText().length() > 0) {
            notifySubscriber(messageTextArea.getText());

            messageTextArea.clear();
        }

    }
}
