package com.example.pcgf;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class King {

    /*ChessboardController cc;

    public King(ChessboardController cc) {
        this.cc = cc;
    }*/

    public boolean movementWhiteKing(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){

        if(pressedRow+1 == row && pressedColumn == column || pressedRow-1 == row && pressedColumn == column){
            return true;
        }else if(pressedColumn+1 == column && pressedRow == row || pressedColumn-1 == column && pressedRow == row){
            return true;
        }else if(pressedColumn+1 == column && pressedRow+1 == row || pressedColumn+1 == column && pressedRow-1 == row){
            return true;
        }else if(pressedColumn-1 == column && pressedRow-1 == row || pressedColumn-1 == column && pressedRow+1 == row){
            return true;
        }
        return false;
    }

    public boolean movementBlackKing(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){

        if(pressedRow+1 == row && pressedColumn == column || pressedRow-1 == row && pressedColumn == column){
            return true;
        }else if(pressedColumn+1 == column && pressedRow == row || pressedColumn-1 == column && pressedRow == row){
            return true;
        }else if(pressedColumn+1 == column && pressedRow+1 == row || pressedColumn+1 == column && pressedRow-1 == row){
            return true;
        }else if(pressedColumn-1 == column && pressedRow-1 == row || pressedColumn-1 == column && pressedRow+1 == row){
            return true;
        }
        return false;
    }
}
