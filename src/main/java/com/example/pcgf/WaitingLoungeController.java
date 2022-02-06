package com.example.pcgf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingLoungeController{
    public ImageView loading;
    public LoginController loginController;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public String username = "";

   /* public WaitingLoungeController(LoginController loginController, ObjectInputStream ois, ObjectOutputStream oos, String username, Server server) {
        this.loginController = loginController;
        this.ois = ois;
        this.oos = oos;
        this.username = username;
        boolean run = true;

        while(run){
            if(server.count == 2){
                try {
                    Stage chessboard = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ChessboardController.fxml"));
                    loader.setControllerFactory(call -> new ChessboardController(loginController, ois, oos, username));
                    chessboard.setResizable(false);
                    chessboard.setTitle("Chess");
                    chessboard.setScene(new Scene(loader.load()));
                    chessboard.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }*/

    /* @FXML
    public void initialize(URL url, ResourceBundle rb) {
        Image i = new Image(new File("/com/example/pcgf/images/loading.gif").toURI().toString());
        loading.setImage(i);
    }*/
}