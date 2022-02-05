package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Bishop {

    ChessboardController cc;

    public Bishop(ChessboardController cc) {
        this.cc = cc;
    }

    public boolean movementBishop(Integer pressedRow, Integer pressedColumn, Integer row, Integer column) {

        if (Math.abs(pressedRow - row) == Math.abs(pressedColumn - column)) {

            int rowOffset;
            int colOffset;

            if (pressedRow < row) {
                rowOffset = 1;
            } else {
                rowOffset = -1;
            }

            if (pressedColumn < column) {
                colOffset = 1;
            } else {
                colOffset = -1;
            }

            int y = pressedColumn + colOffset;
            for (int x = pressedRow + rowOffset; x != row; x += rowOffset) {

                for(Node n : cc.getGridPane().getChildren()){
                    if(cc.checkInteger(GridPane.getRowIndex(n)) == x && cc.checkInteger(GridPane.getColumnIndex(n)) == y && n.getId() != null){
                        return false;
                    }
                }
                y += colOffset;
            }
            return true;
        }
        return false;
    }
}