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
public class ChessGame {
    private ChessBoard chessBoard;
    public Point player1K;
    public Point player2K;

    
    public ChessGame() {
        chessBoard = new ChessBoard(this);
        setupTeam(0, "player2");
        setupTeam(7, "player1");
        System.out.println("DONEONDOEMN");
    }
    
    public ChessBoard getChessBoard() {
        return chessBoard;
    }
    
    private void setupTeam(int homeRow, String player) {
        int oneOffsetPawn = (homeRow > 0) ? -1: 1;
        int colIncrement = 0;

        //Rook
        Piece r1 = new Rook(player, new Point(homeRow, colIncrement), this);
        Piece r2 = new Rook(player, new Point(homeRow, 7-colIncrement), this);
        colIncrement += 1;

        //Knight
        Piece n1 = new Knight(player, new Point(homeRow, colIncrement), this);
        Piece n2 = new Knight(player, new Point(homeRow, 7-colIncrement), this);
        colIncrement += 1;

        //Bishop
        Piece b1 = new Bishop(player, new Point(homeRow, colIncrement), this);
        Piece b2 = new Bishop(player, new Point(homeRow, 7-colIncrement), this);
        colIncrement += 1;

        //King
        if (player.equalsIgnoreCase("player1")) {
            Piece k = new King(player, new Point(homeRow, colIncrement+1), this);            
            player1K = new Point(homeRow, colIncrement+1);
            Piece q = new Queen(player, new Point(homeRow, 6-colIncrement), this);
        } else {
            Piece k = new King(player, new Point(homeRow, colIncrement+1), this);
            player2K = new Point(homeRow, colIncrement+1);
            Piece q = new Queen(player, new Point(homeRow, 6-colIncrement), this);
        }       
        

        //Pawns
        for (int i = 0; i < 8; i++) {
            Piece p = new Pawn(player, new Point(homeRow + oneOffsetPawn, i), this);
        }
        this.getChessBoard().toString();
        System.out.println("hereew");
    }
    

}
