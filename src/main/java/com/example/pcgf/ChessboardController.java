package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.control.Label;
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
    public Label serverUsername;
    public Label clientUsername;

    Pawn pawn = new Pawn(this);
    Bishop bishop = new Bishop(this);
    Knight knight = new Knight();
    Rook rook = new Rook(this);
    Queen queen = new Queen(this);
    King king = new King();

    private boolean isPressed = false;
    private ImageView pressed = null;
    private Rectangle pressedRec = null;
    private static MouseEvent me = null;
    private static LoginController login;
    private boolean movementIsPossible = false;

    public ChessboardController(LoginController loginController) {

        login = loginController;
        //serverUsername.setText(login.username);
        /*if(loginController.isTheServer){
            serverUsername.setText(login.username);
        }else{
            clientUsername.setText(login.username);
        }*/
    }

    public void figureOnClick(MouseEvent mouseEvent) {
        me = mouseEvent;
        if (pressed == mouseEvent.getSource() && isPressed) {//same figure selected again
            replaceImageToUnselected();
            pressed = null;
            isPressed = false;
        } else if (pressed != mouseEvent.getSource() && isPressed) {//figure already selected but not the same == capture figure

            Node n = (Node) mouseEvent.getSource();
            if (pressed.getId().contains("White") && n.getId().contains("White") || pressed.getId().contains("Black") && n.getId().contains("Black")) { //ensure the player dont capture his own figure
                replaceImageToUnselected();
                unselectFigure();
            } else {

                assignFigure(checkInteger(GridPane.getRowIndex((Node) mouseEvent.getSource())), checkInteger(GridPane.getColumnIndex((Node) mouseEvent.getSource())));
                if (movementIsPossible) {
                    replaceImageToUnselected();
                    Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());
                    row = checkInteger(row);
                    Integer column = GridPane.getColumnIndex((Node) mouseEvent.getSource());
                    column = checkInteger(column);

                    for (Node node : gridPane.getChildren()) {
                        if (node == mouseEvent.getSource()) { /*node.setVisible(false)*/
                            node.setVisible(false);
                            node.setId(null);
                        }
                    }
                    moveFigure(row, column);
                } else {
                    replaceImageToUnselected();
                    unselectFigure();
                }
            }


        } else {//no figure selected
            pressed = (ImageView) mouseEvent.getSource();
            isPressed = true;
            replaceImageToSelected();
            connectToServerWrite();
        }
    }

    public void fieldOnClick(MouseEvent mouseEvent) {
        me = mouseEvent;
        if (isPressed && pressed != null) {
            Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());
            row = checkInteger(row);

            Integer column = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            column = checkInteger(column);

            assignFigure(row, column);
            if (movementIsPossible) {
                replaceImageToUnselected();
                moveFigure(row, column);
                connectToServerWrite();
            } else {
                replaceImageToUnselected();
                unselectFigure();
            }
        }
    }

    public static void connectToServerWrite() {
        String ipAddress = login.getIpAddress();
        ObjectOutputStream streamToServer;
        ObjectInputStream streamFromServer;
        Socket toServer;

        try {
            String name;
            toServer = new Socket(ipAddress, 7543);
            streamToServer = new ObjectOutputStream(toServer.getOutputStream());
            streamToServer.writeObject(getRowIndex() + ":" + getColumnIndex());
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            String readFromServer = (String) streamFromServer.readObject();
            System.out.println(readFromServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connectToServerRead() {
        String ipAddress = login.getIpAddress();
        ObjectInputStream streamFromServer;
        Socket toServer;
        try {
            String name;
            toServer = new Socket(ipAddress, 7543);
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            String readFromServer = (String) streamFromServer.readObject();
            System.out.println(readFromServer);
            String[] split = readFromServer.split(":");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignFigure(Integer row, Integer column) {

        String id = pressed.getId().replaceAll(".$", "");
        Integer pressedRow = checkInteger(GridPane.getRowIndex(pressed));
        Integer pressedColumn = checkInteger(GridPane.getColumnIndex(pressed));

        switch (id) {
            case "pawnWhite":
                if (pawn.movementWhitePawn(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "pawnBlack":
                if (pawn.movementBlackPawn(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "bishopWhite":

            case "bishopBlack":
                if (bishop.movementBishop(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "knightWhite":

            case "knightBlack":
                if (knight.movementKnight(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "rookWhite":

            case "rookBlack":
                if (rook.movementRook(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "queenWhite":

            case "queenBlack":
                if (queen.movementQueen(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;

            case "kingWhite":
                if(king.movementWhiteKing(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "kingBlack":
                if (king.movementBlackKing(pressedRow, pressedColumn, row, column)) {
                    movementIsPossible = true;
                }
                break;
        }
    }

    public void moveFigure(Integer row, Integer column) {

        GridPane.setRowIndex(pressed, row);
        GridPane.setColumnIndex(pressed, column);
        unselectFigure();
        movementIsPossible = false;
    }

    public static Integer checkInteger(Integer num) {
        if (num == null) {
            num = 0;
        }
        return num;
    }

    public void replaceImageToUnselected() {
        String name = pressed.getId().replaceAll(".$", "");
        pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
    }

    public void replaceImageToSelected() {
        String name = pressed.getId().replaceAll(".$", "");
        name += "Selected";
        pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
    }

    public void unselectFigure() {
        isPressed = false;
        pressed = null;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public static String getColumnIndex() {
        return checkInteger(GridPane.getColumnIndex((Node) me.getSource())).toString();
    }

    public static String getRowIndex() {
        return checkInteger(GridPane.getRowIndex((Node) me.getSource())).toString();
    }

    public ImageView getPressed() {
        return pressed;
    }
}
