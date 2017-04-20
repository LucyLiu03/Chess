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
public class Pawn extends Piece{
    private boolean firstMove;
    
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
        if (owner.equalsIgnoreCase("player1") && location.x <= 6) {
            oneOffset = 1;
        } 
        else if (owner.equalsIgnoreCase("player2") && location.x >= 1) {
            oneOffset = -1;
        }
        movableLocations.clear();
        if (firstMove){
            Point proposedLocation = new Point(location.x + oneOffset*2, location.y);
            if (ChessBoard.locationInBounds(proposedLocation)){
                movableLocations.add(proposedLocation);
            }
        }
        if (oneOffset != 0 && !game.getChessBoard().isPieceAt(location.x+oneOffset, location.y)){
            movableLocations.add(new Point(location.x+oneOffset, location.y));
        }
    }
    
    @Override
    protected void updateThreateningLocations() {
        int oneOffset = 0;
        if (owner.equalsIgnoreCase("player1") && location.x <= 6) {
            oneOffset = 1;
        } 
        else if (owner.equalsIgnoreCase("player2") && location.x >= 1) {
            oneOffset = -1;
        }

        threateningLocations.clear();
        
        //Diagonally opposite squares are being threatened
        //Column to the left of the current square if possible (needs to be in board bounds)
        if (location.y >= 1) {
            threateningLocations.add(new Point(location.x + oneOffset, location.y - 1));
        }
        //Column to the right of the current square if possible (needs to be in board bounds)
        if (location.y <= 6) {
            threateningLocations.add(new Point(location.x + oneOffset, location.y + 1));
        }
    }
    
    @Override
    public void printThreateningLocations(){
        for (int i = 0; i < threateningLocations.size(); i++){
            System.out.println(threateningLocations.get(i).toString());
        }
    }
    
    @Override
    public void printMovableLocations(){
        for (int i = 0; i < movableLocations.size(); i++){
            System.out.println(movableLocations.get(i).toString());
        }
    }
}