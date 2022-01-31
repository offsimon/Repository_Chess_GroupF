package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ChessboardController {

    public GridPane gridPane;
    public Rectangle r5c0;
    public ImageView pawnWhite1;
    public ImageView pawnBlack1;
    public ImageView pawnBlack2;
    public ImageView pawnBlack3;
    public ImageView pawnBlack4;
    public ImageView pawnBlack5;
    public ImageView pawnBlack6;
    public ImageView pawnBlack7;
    public ImageView pawnBlack8;
    public ImageView rookBlack1;
    public ImageView rookBlack2;
    public ImageView knightBlack1;
    public ImageView knightBlack2;
    public ImageView bishopBlack1;
    public ImageView bishopBlack2;
    public ImageView queenBlack;
    public ImageView kingBlack;
    public ImageView pawnWhite2;
    public ImageView pawnWhite7;
    public ImageView pawnWhite5;
    public ImageView pawnWhite4;
    public ImageView pawnWhite3;
    public ImageView pawnWhite6;
    public ImageView pawnWhite8;
    public ImageView rookWhite1;
    public ImageView rookWhite2;
    public ImageView knightWhite1;
    public ImageView knightWhite2;
    public ImageView bishopWhite1;
    public ImageView bishopWhite2;
    public ImageView queenWhite;
    public ImageView kingWhite;
    public Rectangle r6c0;

    private boolean isPressed = false;
    private ImageView pressed = null;
    private Rectangle pressedRec = null;
    private static MouseEvent me = null;
    private static LoginController login;

    public ChessboardController(LoginController loginController) {
        login = loginController;
    }

    public void selectFigure(MouseEvent mouseEvent) {
        me = mouseEvent;
        if (pressed == mouseEvent.getSource() && isPressed) {//same figure selected again
            String name = pressed.getId().replaceAll(".$", "");
            pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
            pressed = null;
            isPressed = false;
        } else if (pressed != mouseEvent.getSource() && isPressed) {//figure already selected but not the same == capture figure

            String name = pressed.getId().replaceAll(".$", "");
            pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
            Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());
            if(row == null){row = 0;}
            Integer column = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            if(column == null){column = 0;}

            for (Node node : gridPane.getChildren()) {
                if(node == mouseEvent.getSource()) node.setVisible(false);
            }
            GridPane.setRowIndex(pressed, row);
            GridPane.setColumnIndex(pressed, column);

            pressed = null;
            isPressed = false;


        } else {//no figure selected

            pressed = (ImageView) mouseEvent.getSource();
            isPressed = true;
            String name = pressed.getId().replaceAll(".$", "");
            name += "Selected";
            pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));

            connectToServerWrite();
        }
    }

    public void moveFigure(MouseEvent mouseEvent) {
        me = mouseEvent;
        if (isPressed && pressed != null) {
            Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());
            if (row == null) {
                row = 0;
            }

            Integer column = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            if (column == null) {
                column = 0;
            }

            GridPane.setRowIndex(pressed, row);
            GridPane.setColumnIndex(pressed, column);

            String name = pressed.getId().replaceAll(".$", "");
            pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));


            isPressed = false;
            pressed = null;
            connectToServerWrite();
        }
    }

    public static void connectToServerWrite() {
        String ipAddress = login.getIpAddress();
        ObjectOutputStream streamToServer;
        BufferedReader streamFromServer;
        Socket toServer;
        try{
            String name;
            toServer = new Socket(ipAddress,7543);
            streamToServer = new ObjectOutputStream(toServer.getOutputStream());
            streamToServer.writeObject(getRowIndex() +":"+getColumnIndex());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Integer[] connectToServerRead(){
        String ipAddress = login.getIpAddress();
        ObjectInputStream streamFromServer;
        Socket toServer;
        Integer row = null;
        Integer column = null;
        try{
            String name;
            toServer = new Socket(ipAddress, 7543);
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            String readFromServer = (String) streamFromServer.readObject();
            String[] split = readFromServer.split(":");
            row = Integer.valueOf(split[0]);
            column = Integer.valueOf(split[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new Integer[]{row, column};
    }

    public static String getColumnIndex() {
        return GridPane.getColumnIndex((Node) me.getSource()).toString();
    }

    public static String getRowIndex() {
        return GridPane.getRowIndex((Node) me.getSource()).toString();
    }


    /*public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }*/
}
