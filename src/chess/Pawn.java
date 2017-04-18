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
public class Pawn extends Piece{
    private boolean firstMove;
    
    public Pawn(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'P';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'p';
        }
        firstMove = true;
    }
    
}