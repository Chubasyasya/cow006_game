package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/menuScene.fxml"));
        VBox root = loader.load();

        BaseController controller = loader.getController();

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            launch(args);
        }).start();
    }


}
