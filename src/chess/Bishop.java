/*
 * A Bishop subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

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
        //Clear exisiting threatening locations
        threateningLocations.clear();
        //A bishop can move in any direction diagonally
        threateningLocations.addAll(getDiagonalLocations("NE"));
        threateningLocations.addAll(getDiagonalLocations("NW"));
        threateningLocations.addAll(getDiagonalLocations("SW"));
        threateningLocations.addAll(getDiagonalLocations("SE"));
    }
}
