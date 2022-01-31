package com.example.pcgf;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    public Button submitButton;
    public Text errorMessage;
    public TextField usernameTextField;
    public Button createGameButton;
    public Button joinGameButton;

    private static String ipAddress = "";
    public TextField ipAddressTextField;

    public void onClickCreate(ActionEvent actionEvent) {

        ipAddress = ipAddressTextField.getText();
        System.out.println(ipAddress);
        try{
            Stage chessboard = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chessboard.fxml"));
            loader.setControllerFactory(call -> new ChessboardController(this));
            chessboard.setResizable(false);
            chessboard.setTitle("Chess");
            chessboard.setScene(new Scene(loader.load(), 670, 670));
            chessboard.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public void onClickJoin(ActionEvent actionEvent) {
        ipAddress = ipAddressTextField.toString();
        System.out.println(ipAddress);
        try{
            Stage chessboard = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chessboard.fxml"));
            loader.setControllerFactory(call -> new ChessboardController(this));
            chessboard.setResizable(false);
            chessboard.setTitle("Chess");
            chessboard.setScene(new Scene(loader.load(), 670, 670));
            chessboard.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
