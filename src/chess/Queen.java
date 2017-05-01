/*
 * A Queen subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

public class Queen extends Piece{
    public Queen(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'Q';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'q';
        }
    }
    
    @Override
    protected void updateThreateningLocations(){
        //Clear exisiting threatening locations
        threateningLocations.clear();
        
        threateningLocations.addAll(getVerticalLocations(1)); // locations up
        threateningLocations.addAll(getVerticalLocations(-1)); //locations down
        
        threateningLocations.addAll(getHorizontalLocations(1)); //locations right
        threateningLocations.addAll(getHorizontalLocations(-1)); // location left
        
        threateningLocations.addAll(getDiagonalLocations("NE"));//diagonal NE
        threateningLocations.addAll(getDiagonalLocations("NW")); //diagonal NW
        threateningLocations.addAll(getDiagonalLocations("SE")); //diagonal SE
        threateningLocations.addAll(getDiagonalLocations("SW")); //diagonal SW
    }
}
//End of Queen class