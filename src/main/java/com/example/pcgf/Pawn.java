package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Pawn{

    ChessboardController cc;

    public Pawn(ChessboardController cc) {
        this.cc = cc;
    }

    public boolean movementWhitePawn(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){

        if(pressedColumn == column){

            for (Node n : cc.getGridPane().getChildren()) {
                if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == column && n instanceof ImageView){
                    return false;
                }
            }

            if(pressedRow == 6 && pressedRow-2 == row || pressedRow-1 == row){
                return true;
            }
        }
        return false;
    }

    public boolean movementBlackPawn(Integer pressedRow, Integer pressedColumn, Integer row, Integer column) {

        if(pressedColumn == column){

            for (Node n : cc.getGridPane().getChildren()) {
                if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == column && n instanceof ImageView){
                    return false;
                }
            }

            if(pressedRow == 1 && pressedRow+2 == row || pressedRow+1 == row){
                return true;
            }
        }
        return false;
    }
}
