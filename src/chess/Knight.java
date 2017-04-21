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
public class Knight extends Piece{
    public Knight(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'N';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'n';
        }
    }
    
    protected void updateThreateningLocations(){
        threateningLocations.clear();
        

        threateningLocations.add(new Point(location.x - 2, location.y - 1 )); // up to left
        threateningLocations.add(new Point(location.x - 2, location.y + 1 )); // up to right           
        threateningLocations.add(new Point(location.x - 1, location.y + 2 )); // right to up
        threateningLocations.add(new Point(location.x + 1, location.y + 2 )); // right to down
        threateningLocations.add(new Point(location.x + 2, location.y + 1 )); // down to right        
        threateningLocations.add(new Point(location.x + 2, location.y - 1 )); // down to left
        threateningLocations.add(new Point(location.x + 1, location.y - 2 )); // left to down
        threateningLocations.add(new Point(location.x - 1, location.y - 2 )); // left to up
        updateMovableLocations();
        //not sure how to take care of values that come up out of range of chess board
    }
}
