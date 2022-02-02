package com.example.pcgf;

import javafx.scene.image.ImageView;

public class Pawn{

    public boolean movementWhitePawn(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){
        if(pressedRow == 6){
            if(pressedRow - 1 == row && pressedColumn == column || pressedRow - 2 == row && pressedColumn == column){
                return true;
            }
        }else{
            if(pressedRow - 1 == row && pressedColumn == column){
                return true;
            }
        }
        return false;
    }

    public boolean movementBlackPawn(Integer pressedRow, Integer pressedColumn, Integer row, Integer column) {
        if(pressedRow == 1){
            if (pressedRow + 1 == row && pressedColumn == column || pressedRow + 2 == row && pressedColumn == column){
                return true;
            }

        }else{
             if(pressedRow + 1 == row && pressedColumn == column){
                 return true;
             }
        }
        return false;
    }
}
