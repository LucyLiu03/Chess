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
//    ArrayList<Point> movableLocations;
    ChessGame game;
    
    public Piece(String owner, Point initialLocation, ChessGame cGame) {
        this.owner = owner;
        Point location = initialLocation;
        threateningLocations = new ArrayList<>();
//        movableLocations = new ArrayList<>();
        this.game = cGame;
        game.getChessBoard().placePieceAt(this, initialLocation);
    }
    protected ArrayList<Point> getVerticalLocations(int direction){
        ArrayList<Point> verticalLocations = new ArrayList<>();
        if (direction == 1){            
            for (int row = location.x + 1; row < 8; row++){
                if (game.getChessBoard().isPieceAt(row, location.y)){
                    if (!game.getChessBoard().getPieceAt(new Point(row, location.y)).owner.equals(owner)){
                        verticalLocations.add(new Point (row, location.y));
                    }
                    break;
                }
                else{                
                    verticalLocations.add(new Point (row, location.y));
                }                    
            }            
        }
        else{
            for (int row = location.x - 1; row >= 0; row--){
                if (game.getChessBoard().isPieceAt(row, location.y)){
                    if (!game.getChessBoard().getPieceAt(new Point(row, location.y)).owner.equals(owner)){
                        verticalLocations.add(new Point (row, location.y));
                    }
                    break;
                }
                else{                
                    verticalLocations.add(new Point (row, location.y));
                }                
            }
        }
        return verticalLocations;
    }
    
    protected ArrayList<Point> getHorizontalLocations(int direction){
        ArrayList<Point> horizontalLocations = new ArrayList<>();
        
        if (direction == 1){ // 1 = move to the right
            for (int column = location.y + 1; column < 8; column++){
                if (game.getChessBoard().isPieceAt(location.x, column)){
                    if (!game.getChessBoard().getPieceAt(new Point(location.x, column)).owner.equals(owner)){
                        horizontalLocations.add(new Point (location.x, column));
                    }
                    break;
                }
                else{                
                    horizontalLocations.add(new Point (location.x, column));
                }                
            }
        }
        else{ // move to the left
            for (int column = location.y - 1; column >=0; column--){
                if (game.getChessBoard().isPieceAt(location.x, column)){
                    if (!game.getChessBoard().getPieceAt(new Point(location.x, column)).owner.equals(owner)){
                        horizontalLocations.add(new Point (location.x, column));
                    }
                    break;
                }
                else{                
                    horizontalLocations.add(new Point (location.x, column));
                }
            }
        }
        return horizontalLocations;
    }
    
    protected void updateThreateningLocations(){};
    
//    protected void updateMovableLocations(){
//        movableLocations = threateningLocations;
//    };
    
    public void printThreateningLocations(){
        for (int i = 0; i < threateningLocations.size(); i++){
            System.out.println(threateningLocations.get(i).toString());
        }
        System.out.println("awasdasdasdwasd");
    }
    
//    public void printMovableLocations(){
//        for (int i = 0; i < movableLocations.size(); i++){
//            System.out.println(movableLocations.get(i).toString());
//        }
//    }
    
    
    protected ArrayList<Point> getDiagonalLocations(String direction){
        ArrayList<Point> diagonalLocations = new ArrayList<>();
        
        int curRow = location.x;
        int curCol = location.y;
        
        if (direction.equals("NW")){          
            while (1 <= curRow && 1 <= curCol){
                curRow--;
                curCol--;
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    break;
                }
                else{                
                    diagonalLocations.add(new Point (curRow, curCol));
                }         
            }
        }
        
        else if (direction.equals("NE")){
           while ( 1 <= curRow && curCol <= 6){
               curRow--;
               curCol++;
               if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    break;
                }
                else{                
                    diagonalLocations.add(new Point (curRow, curCol));
                }
           }
        }
        
        else if (direction.equals("SW")){
            while ( curRow <= 6 && 1 <= curCol){
                curRow++;
                curCol--;
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    break;
                }
                else{                
                    diagonalLocations.add(new Point (curRow, curCol));
                }
            }
        }
        
        else if (direction.equals("SE")){
            while ( curRow <= 6 && curCol <= 6){
                curRow++;
                curCol++;
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    break;
                }
                else{                
                    diagonalLocations.add(new Point (curRow, curCol));
                }
            }
        }
      
        else{
            System.out.println("***Invalid direction given***");
        }
        
        return diagonalLocations;
    }
}
