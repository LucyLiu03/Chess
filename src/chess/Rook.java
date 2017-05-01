/*
 * A Rook subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

public class Rook extends Piece{
    public Rook(String owner, Point initialLocation, ChessGame game) {
        //Call superclass constructor to set up some attributes
        super(owner, initialLocation, game);
        
        //Set id according the owner
        if (owner.equalsIgnoreCase("player1")) {
            id = 'R';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'r';
        }
    }
    @Override
    protected void updateThreateningLocations() {
        //Clear exisiting threatening locations
        threateningLocations.clear();
        
        //A rook can move vertically and horizontally according to its line of sight
        threateningLocations.addAll(getVerticalLocations(1));
        threateningLocations.addAll(getVerticalLocations(-1));        
        threateningLocations.addAll(getHorizontalLocations(1));
        threateningLocations.addAll(getHorizontalLocations(-1));
    }
}
//End of Rook class