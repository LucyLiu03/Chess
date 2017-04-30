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
    
    @Override
    protected void updateThreateningLocations(){
        threateningLocations.clear();
        for ( int i = -2; i <= 2; i++){
            for ( int j = -2; j <= 2; j++){
                Point proposedLocation = new Point(location.x+i, location.y+j);
                if ( i != 0 && j != 0  && Math.abs(Math.abs(i)-Math.abs(j)) == 1 
                        && ChessBoard.locationInBounds(proposedLocation) && !game.getChessBoard().getPieceOwner(proposedLocation).equals(owner)){
                    threateningLocations.add(proposedLocation);
                }
            }
        }
    }
}
