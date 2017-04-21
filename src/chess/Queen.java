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
public class Queen extends Piece{
    public Queen(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'Q';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'q';
        }
    }
    
    protected void updateThreateningLocations(){
        threateningLocations.clear();
        
        threateningLocations.addAll(getVerticalLocations(1, location)); // locations up
        threateningLocations.addAll(getVerticalLocations(-1, location)); //locations down
        
        threateningLocations.addAll(getHorizontalLocations(1, location)); //locations right
        threateningLocations.addAll(getHorizontalLocations(-1, location)); // location left
        
        threateningLocations.addAll(getDiagonalLocations("NE", location));//diagonal NE
        threateningLocations.addAll(getDiagonalLocations("NW", location)); //diagonal NW
        threateningLocations.addAll(getDiagonalLocations("SE", location)); //diagonal SE
        threateningLocations.addAll(getDiagonalLocations("SW", location)); //diagonal SW
        
        updateMovableLocations();
    }
}
