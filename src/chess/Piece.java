/*
 * A piece superclass with shared attributes such as owner, location and id.
 * All piece subclasses e.g. Rook and Knight, inherit from this class.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece {
    String owner;
    Point location;
    char id;
    ArrayList<Point> threateningLocations;
    ArrayList<Point> movableLocations;
    ChessGame game;
    
    public Piece(String owner, Point initialLocation, ChessGame cGame) {
        //Piece owner - can be "player1" - White or "player2" - Black
        this.owner = owner;
        //Initial location of the piece
        location = initialLocation;
        
        //Initialize array lists to store threatening/movable locations
        threateningLocations = new ArrayList<>();
        movableLocations = new ArrayList<>();
        //The chess game in which the piece is
        this.game = cGame;
        //Places piece at the initial location
        game.getChessBoard().placePieceAt(this, initialLocation);
    }
    
    //Get all possible vertical threatening locations in a specific direction vertically
    protected ArrayList<Point> getVerticalLocations(int direction){
        ArrayList<Point> verticalLocations = new ArrayList<>();
        //1 means down (increasing rows)
        if (direction == 1){            
            for (int row = location.x + 1; row < 8; row++){
                if (game.getChessBoard().isPieceAt(row, location.y)){
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(row, location.y)).owner.equals(owner)){
                        verticalLocations.add(new Point (row, location.y));
                    }
                    //Break if there is a piece in the way
                    break;
                }
                else{                
                    verticalLocations.add(new Point (row, location.y));
                }                    
            }            
        }
        //-1 means up (decreasing rows)
        else{
            for (int row = location.x - 1; row >= 0; row--){
                if (game.getChessBoard().isPieceAt(row, location.y)){
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(row, location.y)).owner.equals(owner)){
                        verticalLocations.add(new Point (row, location.y));
                    }
                    //Break if there is a piece in the way
                    break;
                }
                else{                
                    verticalLocations.add(new Point (row, location.y));
                }                
            }
        }
        return verticalLocations;
    }
    
    //Get all possible vertical threatening locations in a specific direction horizontally
    protected ArrayList<Point> getHorizontalLocations(int direction){
        ArrayList<Point> horizontalLocations = new ArrayList<>();
        
        if (direction == 1){ // 1 = Move to the right
            for (int column = location.y + 1; column < 8; column++){
                if (game.getChessBoard().isPieceAt(location.x, column)){
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(location.x, column)).owner.equals(owner)){
                        horizontalLocations.add(new Point (location.x, column));
                    }
                    //Break if there is a piece in the way
                    break;
                }
                else{                
                    horizontalLocations.add(new Point (location.x, column));
                }                
            }
        }
        else{ // -1 = Move to the left
            for (int column = location.y - 1; column >=0; column--){
                if (game.getChessBoard().isPieceAt(location.x, column)){
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(location.x, column)).owner.equals(owner)){
                        horizontalLocations.add(new Point (location.x, column));
                    }
                    //Break if there is a piece in the way
                    break;
                }
                else{                
                    horizontalLocations.add(new Point (location.x, column));
                }
            }
        }
        return horizontalLocations;
    }
    
    //Must be implemented in subclasses (updates threatening locations for a specific piece)
    protected abstract void updateThreateningLocations();
    
    //Used in the Pawn subclass to update movable locations (it cannot attack if moving forward)
    protected void updateMovableLocations(){};
    
    //Get all possible vertical threatening locations in a specific direction diagonally
    protected ArrayList<Point> getDiagonalLocations(String direction){
        ArrayList<Point> diagonalLocations = new ArrayList<>();
        
        int curRow = location.x;
        int curCol = location.y;
        
        if (direction.equals("NW")){          
            while (1 <= curRow && 1 <= curCol){
                curRow--;
                curCol--;
                if (game.getChessBoard().isPieceAt(curRow, curCol)){
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    //Break if there is a piece in the way
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
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    //Break if there is a piece in the way
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
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    //Break if there is a piece in the way
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
                    //Add location to desired locations if the piece is not on the same team (must be opposing team)
                    if (!game.getChessBoard().getPieceAt(new Point(curRow, curCol)).owner.equals(owner)){
                        diagonalLocations.add(new Point (curRow, curCol));
                    }
                    //Break if there is a piece in the way
                    break;
                }
                else{                
                    diagonalLocations.add(new Point (curRow, curCol));
                }
            }
        }        
        return diagonalLocations;
    }
}
//End of Piece class