package com.example.pcgf;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.Socket;

public class ChessboardController {

    public GridPane gridPane;

    public Label serverUsername;
    public Label clientUsername;
    public Label whiteChecked;
    public Label blackChecked;
    public Button reloadButton;

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
    private ObjectOutputStream streamToServer;
    private ObjectInputStream streamFromServer;
    private static int count = 0;
    private static String gegner = "";
    private String color;

    public ChessboardController(LoginController loginController, ObjectInputStream ois, ObjectOutputStream oos, String username, String color) {
        login = loginController;
        streamToServer = oos;
        streamFromServer = ois;
        this.color = color;
        // serverUsername.setText(LoginController.getUsername());
        // clientUsername.setText(LoginController.getUsername());
    }

    public void figureOnClick(MouseEvent mouseEvent) {
        me = mouseEvent;

        Node meN = (Node) me.getSource();

            if (pressed == mouseEvent.getSource() && isPressed) {//same figure selected again
                if(meN.getId().contains(color)){
                    replaceImageToUnselected();
                    pressed = null;
                    isPressed = false;
                }
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
                            if (node == mouseEvent.getSource()) {
                                if(!node.getId().startsWith("king")){//king cant be captured
                                    node.setVisible(false);
                                    node.setId(null);
                                }else{
                                    movementIsPossible = false;
                                }

                            }
                        }
                        if(movementIsPossible){
                            moveFigure(row, column);
                        }
                    } else {
                        replaceImageToUnselected();
                        unselectFigure();
                    }
                }


            } else {//no figure selected
                if(meN.getId().contains(color)){
                    pressed = (ImageView) mouseEvent.getSource();
                    isPressed = true;
                    replaceImageToSelected();
                }
                //connectToServerWrite();
            }
        }

    public void testClient(){
        Thread thread = new Thread(() -> {
            try {
                gegner = (String) streamFromServer.readObject();
                reloadButton.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        //
        System.out.println(gegner);
        if(!gegner.equals("")){
            boolean needToCapture = false;
            Node capture = null;
            String[] split = gegner.split(",");
            Integer r = Integer.valueOf(split[0]);
            r = checkInteger(r);
            Integer c = Integer.valueOf(split[1]);
            c = checkInteger(c);
            String id = split[2];

            for (Node n : gridPane.getChildren()){
                Integer nr = checkInteger(GridPane.getRowIndex(n));
                Integer nc = checkInteger(GridPane.getColumnIndex(n));
                if(nr == r && nc == c && n.getId() != null){
                    capture = n;
                }
            }

            for (Node n : gridPane.getChildren()) {
                if(n.getId() != null){
                    if(n.getId().equals(id)){
                        if (capture != null){
                           capture.setVisible(false);
                           capture.setId(null);
                            GridPane.setRowIndex(n, r);
                            GridPane.setColumnIndex(n, c);
                        }else{
                            GridPane.setRowIndex(n, r);
                            GridPane.setColumnIndex(n, c);
                        }

                    }
                }
            }
        }

        //
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
                //connectToServerWrite();
            } else {
                replaceImageToUnselected();
                unselectFigure();
            }
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

        if(whiteChecked.isVisible() ||blackChecked.isVisible()){
            whiteChecked.setVisible(false);
            blackChecked.setVisible(false);
        }

        GridPane.setRowIndex(pressed, row);
        GridPane.setColumnIndex(pressed, column);
        isKingChecked();

        //
        try {
            /*streamToServer.writeObject(LoginController.getIpAddress());
            streamToServer.flush();*/
            streamToServer.writeObject(row+","+column +","+pressed.getId());
            streamToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //

        unselectFigure();
        movementIsPossible = false;
    }

    public void isKingChecked(){
        if(pressed.getId().contains("White")){
            for (Node n : gridPane.getChildren()) {
                if(n.getId() != null){
                    if(n.getId().startsWith("kingBlack")){
                        Integer r = checkInteger(GridPane.getRowIndex(n));
                        Integer c = checkInteger(GridPane.getColumnIndex(n));
                        movementIsPossible = false;
                        assignFigure(r, c);
                        if(movementIsPossible){
                            blackChecked.setVisible(true);
                        }
                        movementIsPossible = false;
                    }
                }
            }

        }else{
            for (Node n : gridPane.getChildren()) {
                if(n.getId() != null){
                    if(n.getId().contains("kingWhite")){
                        Integer r = checkInteger(GridPane.getRowIndex(n));
                        Integer c = checkInteger(GridPane.getColumnIndex(n));
                        movementIsPossible = false;
                        assignFigure(r, c);
                        if(movementIsPossible){
                            whiteChecked.setVisible(true);
                        }
                        movementIsPossible = false;
                    }

                }
            }
        }
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

    public void reloadOnClick(ActionEvent actionEvent) {
        reloadButton.setVisible(false);
        testClient();
    }
}
