package com.example.pcgf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root = new FXMLLoader(getClass().getResource("Login.fxml"));
        root.setControllerFactory(call -> new LoginController(null, null));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Connection");
        primaryStage.setScene(new Scene(root.load()));
        primaryStage.show();

        /*FXMLLoader root = new FXMLLoader(getClass().getResource("WaitingLounge.fxml"));
        root.setControllerFactory(call -> new LoginController());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Waiting");
        primaryStage.setScene(new Scene(root.load()));
        primaryStage.show();*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
