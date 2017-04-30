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
public class Rook extends Piece{
    public Rook(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'R';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'r';
        }
    }
    @Override
    protected void updateThreateningLocations() {
        threateningLocations.clear();
        threateningLocations.addAll(getVerticalLocations(1));
        threateningLocations.addAll(getVerticalLocations(-1));
        
        threateningLocations.addAll(getHorizontalLocations(1));
        threateningLocations.addAll(getHorizontalLocations(-1));
    }
}
