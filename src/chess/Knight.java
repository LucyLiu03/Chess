/*
 * A Knight subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

public class Knight extends Piece{
    public Knight(String owner, Point initialLocation, ChessGame game) {
        //Call superclass constructor to set up some attributes
        super(owner, initialLocation, game);        
        
        //Set id according the owner
        if (owner.equalsIgnoreCase("player1")) {
            id = 'N';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'n';
        }
    }
    
    @Override
    protected void updateThreateningLocations(){
        //Clear exisiting threatening locations
        threateningLocations.clear();
        //A knight can move in an "L" shape 
        //(2 rows and 1 column anywhere from its current position or 2 columns and 1 row anywhere from its current position)
        for ( int i = -2; i <= 2; i++){
            for ( int j = -2; j <= 2; j++){
                Point proposedLocation = new Point(location.x+i, location.y+j);
                //If the current i and j values match the "L" movement of the knight 
                //and there is not a friendly piece at the proposed location
                if ( i != 0 && j != 0  && Math.abs(Math.abs(i)-Math.abs(j)) == 1 
                        && ChessBoard.locationInBounds(proposedLocation) && !game.getChessBoard().getPieceOwner(proposedLocation).equals(owner)){
                    threateningLocations.add(proposedLocation);
                }
            }
        }
    }
}
//End of Knight class