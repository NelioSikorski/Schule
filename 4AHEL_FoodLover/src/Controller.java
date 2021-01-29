import food.IFoodPublisher;
import food.IFoodSubscriber;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Controller implements IFoodPublisher {
    /**
     * Alle GUI - Elemente, die über den Controller gesteuert
     * werden sollen, MÜSSEN über die FXML - Datei mittels
     * ALT + ENTER mit der COntroller - Klasse verknüpft werden.
     */

    public TextArea messageTextArea;

    private ArrayList<IFoodSubscriber> subscribers = new ArrayList<>();

    public void shoutButtoClicked(ActionEvent actionEvent) {
        if (messageTextArea.getText().length() > 0) {
            notifySubscriber(messageTextArea.getText());

            messageTextArea.clear();
        }
    }

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
        // for (Klassenname variablenname : Liste)
        // --> Für jedes IFoodSubscriber "element" aus der Liste "subscribers"
        //     mache folgendes...
        for (IFoodSubscriber element : subscribers) {
            // Benachrichtige jeden subscriber (Fleischesser, Veganer, Vegetarier),
            // dass es etwas zum Essen gibt.
            element.notify(meal);
        }
    }
}

