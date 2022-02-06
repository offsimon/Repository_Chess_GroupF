package com.example.pcgf;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginController {

    public Button submitButton;
    public Text errorMessage;
    public TextField usernameTextField;
    public static String username = "";
    public Button createGameButton;
    public Button joinGameButton;
    public boolean isTheServer = false;

    private static String ipAddress = "";
    public TextField ipAddressTextField;
    private static ObjectOutputStream streamToServer = null;
    private static ObjectInputStream streamFromServer = null;
    public Label usernameTooLong;
    public Label wrongInput;

    public LoginController(ObjectOutputStream streamToServer, ObjectInputStream streamFromServer) {
        this.streamToServer = streamToServer;
        this.streamFromServer = streamFromServer;
    }

    public void onClickCreate(ActionEvent actionEvent) {
        /*Thread thread = new Thread(()->{
            Server.main(null);
        });
        thread.start();*/
        if(checkInput()){

            usernameTooLong.setVisible(false);
            wrongInput.setVisible(false);

            isTheServer = true;
            ipAddress = ipAddressTextField.getText();
            username = usernameTextField.getText();
            System.out.println(ipAddress);
            System.out.println(username);
            connectToServerWrite();
            try {
                Stage chessboard = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Chessboard.fxml"));
                loader.setControllerFactory(call -> new ChessboardController(this, streamFromServer, streamToServer, username));
                chessboard.setResizable(false);
                chessboard.setTitle("Chess");
                chessboard.setScene(new Scene(loader.load()));
                chessboard.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static String getUsername() {
        return username;
    }

    public void onClickJoin(ActionEvent actionEvent) {

        if(checkInput()) {

            usernameTooLong.setVisible(false);
            wrongInput.setVisible(false);

            ipAddress = ipAddressTextField.getText();
            username = usernameTextField.getText();
            System.out.println(ipAddress);
            System.out.println(username);
            connectToServerWrite();
            try {
                Stage chessboard = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Chessboard.fxml"));
                loader.setControllerFactory(call -> new ChessboardController(this, streamFromServer, streamToServer, username));
                chessboard.setResizable(false);
                chessboard.setTitle("Chess");
                chessboard.setScene(new Scene(loader.load()));
                chessboard.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public void connectToServerWrite() {
        String userName = usernameTextField.getText();
        String ipAddress = getIpAddress();
        Socket toServer;

        try {
            toServer = new Socket(ipAddress, 4711);
            streamToServer = new ObjectOutputStream(toServer.getOutputStream());
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            //streamToServer.writeObject(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkInput(){
        if(usernameTextField.getText() == "" || ipAddressTextField.getText() == "" || usernameTextField.getText() == null || ipAddressTextField.getText() == null){
            wrongInput.setVisible(true);
            usernameTextField.setText(null);
            ipAddressTextField.setText(null);
            return false;
        }else if(usernameTextField.getText().length() > 10){
            usernameTooLong.setVisible(true);
            usernameTextField.setText(null);
            ipAddressTextField.setText(null);
            return false;
        }
        return true;
    }

}
