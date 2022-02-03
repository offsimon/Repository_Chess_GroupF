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

        Integer checkedRow;
        Integer checkedColumn;

        if (pressedRow == row) {
            if (pressedColumn < column) {
                //GO RIGHT
                for (int i = pressedColumn + 1; i <= column; i++) {
                    for (Node n : cc.getGridPane().getChildren()) {
                        checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row && checkedColumn == column) {
                            return true;
                        } else if (checkedRow == pressedRow && checkedColumn == i && n instanceof ImageView) {
                            return false;
                        }
                    }

                }
            } else {
                //GO LEFT
                for (int i = pressedColumn - 1; i >= column; i--) {
                    for (Node n : cc.getGridPane().getChildren()) {
                        checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row && checkedColumn == column) {
                            return true;
                        } else if (checkedRow == pressedRow && checkedColumn == i && n instanceof ImageView) {
                            return false;
                        }
                    }
                }
            }
        } else if (pressedColumn == column) {
            if (pressedRow < row) {
                //GO DOWN
                for (int i = pressedRow + 1; i <= row; i++) {
                    for (Node n : cc.getGridPane().getChildren()) {
                        checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row && checkedColumn == column) {
                            return true;
                        } else if (checkedRow == i && checkedColumn == pressedColumn && n instanceof ImageView) {
                            return false;
                        }
                    }
                }
            } else {
                //GO UP
                for (int i = pressedRow - 1; i >= row; i--) {
                    for (Node n : cc.getGridPane().getChildren()) {
                        checkedRow = cc.checkInteger(GridPane.getRowIndex(n));
                        checkedColumn = cc.checkInteger(GridPane.getColumnIndex(n));
                        if (checkedRow == row && checkedColumn == column) {
                            return true;
                        } else if (checkedRow == i && checkedColumn == pressedColumn && n instanceof ImageView) {
                            return false;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

}