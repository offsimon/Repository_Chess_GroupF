package com.example.pcgf;

import javafx.scene.image.ImageView;

public class Knight {

    public boolean movementKnight(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){
        if(pressedRow-2 == row && pressedColumn-1 == column || pressedRow-2 == row && pressedColumn+1 == column) {
            return true;
        }else if(pressedRow-1 == row && pressedColumn-2 == column || pressedRow+1 == row && pressedColumn+2 == column){
            return true;
        }else if(pressedRow+1 == row && pressedColumn-2 == column || pressedRow-1 == row && pressedColumn+2 == column){
            return true;
        }else if(pressedRow+2 == row && pressedColumn-1 == column || pressedRow+2 == row && pressedColumn+1 == column){
            return true;
        }else{
            return false;
        }
    }

    /*public boolean movementBlackKnight(Integer pressedRow, Integer pressedColumn, Integer row, Integer column) {
        return true;
    }*/
}
