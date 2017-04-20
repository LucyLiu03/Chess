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
                verticalLocations.add(new Point(row, curLocation.y)); 
                if (game.getChessBoard().isPieceAt(row, curLocation.y)){
                    break;
                }                          
            }            
        }
        else{
            for (int row = curLocation.x - 1; row >= 0; row--){
                verticalLocations.add(new Point(row, curLocation.y)); 
                if (game.getChessBoard().isPieceAt(row, curLocation.y)){
                    break;
                }                    
            }
        }
        return verticalLocations;
    }
    
    protected ArrayList<Point> getHorizontalLocations(int direction, Point curLocation){
        ArrayList<Point> horizontalLocations = new ArrayList<>();
        
        if (direction == 1){ // 1 = move to the right
            for (int column = curLocation.y + 1; column < 8; column++){
                horizontalLocations.add(new Point (curLocation.x, column));
                if (game.getChessBoard().isPieceAt(curLocation.x, column)){
                    break;
                }
                
            }
        }
        else{ // move to the left
            for (int column = curLocation.y - 1; column >=0; column--){
                horizontalLocations.add(new Point (curLocation.x, column));
                if (game.getChessBoard().isPieceAt(curLocation.x, column)){
                    break;
                }
            }
        }
        return horizontalLocations;
    }
    
    protected void updateThreateningLocations(){};
    protected void updateMovableLocations(){};
    protected void printThreateningLocations(){};
    
    
    protected ArrayList<Point> getDiagonalLocations(String direction, Point curLocation){
        ArrayList<Point> diagonalLocations = new ArrayList<>();
        
        int curRow = curLocation.x;
        int curCol = curLocation.y;
        
        if (direction.equals("NW")){          
            while (0 <= curRow && 0 <= curCol){
                curRow--;
                curCol--;
                diagonalLocations.add(new Point (curRow, curCol));
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    break;
                }             
            }
        }
        
        else if (direction.equals("NE")){
           while ( 0 <= curRow && curCol <= 7){
               curRow--;
               curCol++;
               diagonalLocations.add(new Point (curRow, curCol));
               if (game.getChessBoard().isPieceAt(curRow, curCol)){
                   break;
               }
           }
        }
        
        else if (direction.equals("SW")){
            while ( curRow <= 7 && 0 <= curCol){
                curRow++;
                curCol--;
                diagonalLocations.add(new Point (curRow, curCol));
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    break;
                } 
            }
        }
        
        else if (direction.equals("SE")){
            while ( curRow <= 7 && curCol <= 7){
                curRow++;
                curCol++;
                diagonalLocations.add(new Point (curRow, curCol));
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    break;
                } 
            }
        }
      
        else{
            System.out.println("***Invalid direction given***");
        }
        
        return diagonalLocations;
    }
}
