package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Rook {

    ChessboardController cc;

    public Rook(ChessboardController cc) {
        this.cc = cc;
    }

    public boolean movementRook(Integer pressedRow, Integer pressedColumn, Integer row, Integer column) {

        if (pressedRow == row) {
            if (pressedColumn < column) {
                //MOVE RIGHT
                for (Node n : cc.getGridPane().getChildren()) {
                    if(n instanceof ImageView){
                        Integer checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        Integer checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row) {
                            if (checkedColumn > pressedColumn && checkedColumn < column) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            } else {
                //MOVE LEFT
                for (Node n : cc.getGridPane().getChildren()) {
                    if(n instanceof ImageView){
                        Integer checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        Integer checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row) {
                            if (checkedColumn < pressedColumn && checkedColumn > column) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        } else if (pressedColumn == column) {
            if (pressedRow < row) {
                //MOVE DOWN
                for (Node n : cc.getGridPane().getChildren()) {
                    if(n instanceof ImageView){
                        Integer checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        Integer checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedColumn == column) {
                            if (checkedRow > pressedRow && checkedRow < row) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            } else {
                //MOVE UP
                for (Node n : cc.getGridPane().getChildren()) {
                    if(n instanceof ImageView){
                        Integer checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        Integer checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedColumn == column) {
                            if (checkedRow < pressedRow && checkedRow > row) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }

        return false;
    }
}