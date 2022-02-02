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

    Pawn pawn = new Pawn();
    Bishop bishop = new Bishop();
    Knight knight = new Knight();
    Rook rook = new Rook();
    Queen queen = new Queen();
    King king = new King();

    private boolean isPressed = false;
    private ImageView pressed = null;
    private Rectangle pressedRec = null;
    private static MouseEvent me = null;
    private static LoginController login;
    private boolean movementIsPossible = true;

    public ChessboardController(LoginController loginController) {
        login = loginController;
    }

    public void figureOnClick(MouseEvent mouseEvent) {
        me = mouseEvent;
        if (pressed == mouseEvent.getSource() && isPressed) {//same figure selected again
            replaceImageToUnselected();
            pressed = null;
            isPressed = false;
        } else if (pressed != mouseEvent.getSource() && isPressed) {//figure already selected but not the same == capture figure

            replaceImageToUnselected();
            Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());
            row = checkInteger(row);
            Integer column = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            column = checkInteger(column);

            for (Node node : gridPane.getChildren()) {
                if(node == mouseEvent.getSource()) node.setVisible(false);
            }
            moveFigure(row, column);
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
            if(movementIsPossible){
                replaceImageToUnselected();
                moveFigure(row, column);
                connectToServerWrite();
            }else{
                replaceImageToUnselected();
                isPressed = false;
                pressed = null;
            }
        }
    }

    public void moveFigure(Integer row, Integer column){

            GridPane.setRowIndex(pressed, row);
            GridPane.setColumnIndex(pressed, column);
            isPressed = false;
            pressed = null;
            movementIsPossible = false;
    }

    public static Integer checkInteger(Integer num){
        if(num == null){
            num = 0;
        }
        return num;
    }

    public void replaceImageToUnselected(){
        String name = pressed.getId().replaceAll(".$", "");
        pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
    }

    public void replaceImageToSelected(){
        String name = pressed.getId().replaceAll(".$", "");
        name += "Selected";
        pressed.setImage(new Image(String.valueOf(this.getClass().getResource("/com/example/pcgf/images/" + name + ".png"))));
    }

    public static void connectToServerWrite() {
        String ipAddress = login.getIpAddress();
        ObjectOutputStream streamToServer;
        ObjectInputStream streamFromServer;
        Socket toServer;

        try{
            String name;
            toServer = new Socket(ipAddress,7543);
            streamToServer = new ObjectOutputStream(toServer.getOutputStream());
            streamToServer.writeObject(getRowIndex() +":"+getColumnIndex());
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            String readFromServer = (String) streamFromServer.readObject();
            System.out.println(readFromServer);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void connectToServerRead() {
        String ipAddress = login.getIpAddress();
        ObjectInputStream streamFromServer;
        Socket toServer;
        try{
            String name;
            toServer = new Socket(ipAddress,7543);
            streamFromServer = new ObjectInputStream(toServer.getInputStream());
            String readFromServer = (String) streamFromServer.readObject();
            System.out.println(readFromServer);
            String[] split = readFromServer.split(":");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void assignFigure(Integer row, Integer column){

        String id = pressed.getId().replaceAll(".$", "");
        Integer pressedRow = checkInteger(GridPane.getRowIndex(pressed));
        Integer pressedColumn = checkInteger(GridPane.getColumnIndex(pressed));

        switch(id){
            case "pawnWhite":
                if(pawn.movementWhitePawn(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "bishopWhite":
                if(bishop.movementWhiteBishop(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "knightWhite":

            case "knightBlack":
                if(knight.movementKnight(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "rookWhite":
                if(rook.movementWhiteRook(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "queenWhite":
                if(queen.movementWhiteQueen(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "kingWhite":

            case "kingBlack":
                if(king.movementKing(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "pawnBlack":
                if(pawn.movementBlackPawn(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "bishopBlack":
                if(bishop.movementBlackBishop(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "rookBlack":
                if(rook.movementBlackRook(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;

            case "queenBlack":
                if(queen.movementBlackQueen(pressedRow, pressedColumn, row, column)){
                    movementIsPossible = true;
                }
                break;
        }
    }

}
