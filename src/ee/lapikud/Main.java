package ee.lapikud;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World");
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        launch();
    }
}
