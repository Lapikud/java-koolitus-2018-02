package ee.lapikud;

import javafx.application.Application;
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


public class Main extends Application {

    private TextArea textArea = new TextArea();

    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    openFile(file);
                }

            }
        });
        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    saveFile(file);
                }
            }
        });
        menuFile.getItems().addAll(open, save);
        menuBar.getMenus().add(menuFile);
        root.setTop(menuBar);

        root.setCenter(textArea);

        Scene scene = new Scene(root,640, 480);
        primaryStage.setTitle("Notepad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(File file) {
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

    private void saveFile(File file) {
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