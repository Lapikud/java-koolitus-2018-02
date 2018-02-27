package ee.lapikud;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;


public class Main extends Application {

    private TextArea textArea = new TextArea();
    private FileChooser fileChooser = new FileChooser();

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(actionEvent -> openFile(primaryStage));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(actionEvent -> saveFile(primaryStage));
	MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Platform.exit();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem date = new MenuItem("Date");
        date.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.appendText(String.valueOf(LocalDate.now()));
            }
        });
        menuFile.getItems().addAll(open, save, date, exit);
        menuBar.getMenus().add(menuFile);
        root.setTop(menuBar);

        root.setCenter(textArea);

        Scene scene = new Scene(root,640, 480);
        primaryStage.setTitle("Notepad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(Stage primaryStage) {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) {
            return;
        }

        System.out.printf("Opening file %s\n", file.getAbsolutePath());
        if (!file.exists()) {
            return;
        }

        try {
            textArea.setText(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.printf("Error opening file: %s", e.getMessage());
        }
    }

    private void saveFile(Stage primaryStage) {
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file == null) {
            return;
        }

        System.out.printf("Saving file %s\n", file.getAbsolutePath());
        try {
            Files.write(file.toPath(), textArea.getText().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.printf("Error saving file: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
