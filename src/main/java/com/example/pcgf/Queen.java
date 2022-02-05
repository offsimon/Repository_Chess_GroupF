package com.example.pcgf;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Queen {

    ChessboardController cc;

    public Queen(ChessboardController cc) {
        this.cc = cc;
    }

    private Rook rook;
    private Bishop bishop;

    public boolean movementQueen(Integer pressedRow, Integer pressedColumn, Integer row, Integer column){

        rook = new Rook(cc);
        bishop = new Bishop(cc);

        if(!rook.movementRook(pressedRow, pressedColumn, row, column)){
            return bishop.movementBishop(pressedRow, pressedColumn, row, column);
        }else return true;
    }
}
