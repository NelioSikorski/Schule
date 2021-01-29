import food.MeatEater;
import food.Vegan;
import food.Vegetarian;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @ToDo edit run configuration
     * Open "Run" -> "Edit Configurations..."
     * Add this to your VM options (just copy & paste the following line):
     * --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
     */

    /**
     * main method
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * Erstellen Sie eine einfache GUI.
         * Die GUI soll ein TextField und einen "Shout" Button
         * enthalten.
         *
         * Erstellen Sie im food Package 3 Klassen:
         * -> Vegetarian (mag Käse, Eier und Früchte)
         * -> MeatEater (mag Fleisch, Käse, Eier und Früchte)
         * -> Vegan (mag Früchte und Gemüse)
         *
         * Diese drei Klassen sollen informiert werden, wenn
         * der Shout - Button geklickt wurde.
         *
         * Erstellen Sie von jeder der Drei Klassen jewils ein Objekt
         * und fügen Sie jedes Objekt als Subscriber zum Controller hinzu.
         *
         * Wenn im Textfeld eine der oben genannten Speisen eingegeben wird
         * und der Shout Button geklickt wird, geben all jene Klassen,
         * die die Mahlzeit mögen "Yummy" in der Konsole aus.
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load(); //Vom Ausgabe Menü die INfo holen was der benutzer schreibt

        /**
         * Nachträgliches holen des Controllers!
         * --> Das Problem ist, dass der Controller automatisch
         *     beim Laden der fxml - Datei erzeugt wird.
         *     Um ihn in einer Variable zu speichern, muss diese
         *     Instanz geholt werden.
         */
        Controller c = loader.getController();

        MeatEater tmp = new MeatEater();
        c.addSubscriber(tmp);

        c.addSubscriber(new Vegetarian());
        c.addSubscriber(new Vegan());

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
