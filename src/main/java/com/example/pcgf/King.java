package com.example.pcgf;

import javafx.scene.image.ImageView;

public class King {

    public boolean movementKing(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){

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
