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
public class Bishop extends Piece{
     public Bishop(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'B';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'b';
        }
    }
     
     @Override
    protected void updateThreateningLocations(){
        threateningLocations.clear();
        threateningLocations.addAll(getDiagonalLocations("NE"));
        threateningLocations.addAll(getDiagonalLocations("NW"));
        threateningLocations.addAll(getDiagonalLocations("SW"));
        threateningLocations.addAll(getDiagonalLocations("SE"));
        
        updateMovableLocations();
            
    }
}
