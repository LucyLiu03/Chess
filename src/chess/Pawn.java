/*
 * A Pawn subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

public class Pawn extends Piece{
    public boolean firstMove;
    
    public Pawn(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'P';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'p';
        }
        firstMove = true;
    }
    
    @Override
    protected void updateMovableLocations(){
        int oneOffset = 0;
        if (owner.equalsIgnoreCase("player1") && location.x >= 1) {
            oneOffset = -1;
        } 
        else if (owner.equalsIgnoreCase("player2") && location.x <= 6) {
            oneOffset = 1;
        }
        movableLocations.clear();
        
        if (oneOffset != 0 && !game.getChessBoard().isPieceAt(location.x+oneOffset, location.y) && ChessBoard.locationInBounds(new Point(location.x+oneOffset, location.y))){
            movableLocations.add(new Point(location.x+oneOffset, location.y));
            if (firstMove){
                Point proposedLocation = new Point(location.x + oneOffset*2, location.y);
                if (ChessBoard.locationInBounds(proposedLocation) && !game.getChessBoard().isPieceAt(location.x+oneOffset*2, location.y)){
                    movableLocations.add(proposedLocation);
                }
            }
        }
        
    }
    
    @Override
    protected void updateThreateningLocations() {
        //Clear exisiting threatening locations
        int oneOffset = 0;
        if (owner.equalsIgnoreCase("player1") && location.x >= 1) {
            oneOffset = -1;
        } 
        else if (owner.equalsIgnoreCase("player2") && location.x <= 6) {
            oneOffset = 1;
        }

        threateningLocations.clear();
        if (oneOffset != 0){
            //Diagonally opposite squares are being threatened
            //Column to the left of the current square if possible (needs to be in board bounds)
            if (location.y >= 1 && game.getChessBoard().isPieceAt(location.x + oneOffset, location.y - 1)) {
                if (!game.getChessBoard().getPieceOwner(new Point(location.x + oneOffset, location.y - 1)).equals(owner)){
                    threateningLocations.add(new Point(location.x + oneOffset, location.y - 1));
                }
            }
            //Column to the right of the current square if possible (needs to be in board bounds)
            if (location.y <= 6 && game.getChessBoard().isPieceAt(location.x + oneOffset, location.y + 1)) {
                if (!game.getChessBoard().getPieceOwner(new Point(location.x + oneOffset, location.y + 1)).equals(owner)){
                    threateningLocations.add(new Point(location.x + oneOffset, location.y + 1));
                }
            }
            updateMovableLocations();
        }
    }
}
//End of Pawn class