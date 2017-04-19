/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;

/**
 *
 * @author advai
 */
public class ChessBoard {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
    }

    public boolean isPieceAt(int row, int col) {
        return board[row][col] != null;
    }

    public void placePieceAt(Piece piece, Point location) {
        if (isPieceAt(location.x, location.y)) {
            removePieceAt(location);
        }
        if (piece.location != null) {
            removePieceAt(piece.location);
        }
        board[location.x][location.y] = piece;
        piece.location = location;
    }

    private void removePieceAt(Point location) {
        board[location.x][location.y] = null;
    }
    
    public static boolean locationInBounds(Point location) {
        return location.x >= 0 && location.x < 8 && 
               location.y >= 0 && location.y < 8;
    }

    public Piece getPieceAt(Point location) {
        return board[location.x][location.y];
    }

    @Override
    public String toString() {
        String output = "  0 1 2 3 4 5 6 7\n";
        for (int row = 0; row < 8; row++) {
            output += row;
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    output += " " + board[row][col].id;
                } else {
                    output += " -";
                }
            }
            output += "\n";
        }
        return output;
    }
}
