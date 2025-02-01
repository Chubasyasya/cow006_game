package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Text resultText = new Text("Результат игры");;

    public void setResultMessage(String message) {
        resultText.setText(message);
    }

    @FXML
    private void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/menuScene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) resultText.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}