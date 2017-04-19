/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author advai
 */
public abstract class Piece {
    String owner;
    Point location;
    char id;
    ArrayList<Point> threateningLocations;
    ArrayList<Point> movableLocations;
    ChessGame game;
    
    public Piece(String owner, Point initialLocation, ChessGame cGame) {
        this.owner = owner;
        Point location = initialLocation;
        threateningLocations = new ArrayList<>();
        this.game = cGame;
        game.getChessBoard().placePieceAt(this, initialLocation);
    }
    protected ArrayList<Point> getVerticalLocations(int direction, Point curLocation){
        ArrayList<Point> verticalLocations = new ArrayList<>();
        if (direction == 1){            
            for (int row = curLocation.x + 1; row < 8; row++){
                if (game.getChessBoard().isPieceAt(row, row)){
                    break;
                }
                verticalLocations.add(new Point(row, curLocation.y));                
            }            
        }
        else{
            for (int row = curLocation.x - 1; row >= 0; row--){
                if (game.getChessBoard().isPieceAt(row, row)){
                    break;
                }
                verticalLocations.add(new Point(row, curLocation.y));                
            }
        }
        return verticalLocations;
    }
    protected void updateThreateningLocations(){};
    protected void updateMovableLocations(){};
    protected void printThreateningLocations(){};
    
    
//    protected ArrayList<Point> getDiagonalLocations(String direction, Point curLocation){
//        if 
//    }
}
