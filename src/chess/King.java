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
public class King extends Piece{
    public King(String owner, Point initialLocation, ChessGame game) {
        super(owner, initialLocation, game);
        if (owner.equalsIgnoreCase("player1")) {
            id = 'K';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'k';
        }
    }
    
   
   protected void updateThreateningLocations(){
       threateningLocations.clear();
       Piece[][] backup =  new Piece[8][8];
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               backup[i][j] = game.getChessBoard().getPieceAt(new Point(i, j));
           }
       }
       System.out.println("ASDAS" + location.toString());
       int originalX = location.x;
       int originalY = location.y;
       for (int curi = -1; curi<=1; curi++){
           for( int curj = -1; curj<=1; curj++){
               System.out.println("WADADADADADAAD");
               if (!( curi == 0 && curj == 0)){
                   System.out.println("----");
                   Point proposedLocation = new Point(originalX + curi, originalY + curj);
                   System.out.println(proposedLocation.toString());
                   System.out.println("----");
                   if (ChessBoard.locationInBounds(proposedLocation) && !game.getChessBoard().getPieceOwner(proposedLocation).equals(owner)){
                       
                       game.getChessBoard().placePieceAt(this, proposedLocation);
                       game.getChessBoard().updateAllThreateningLocations();
                       System.out.println("DONE");
                       boolean add = true;
                       for (int rowi = 0; rowi < 8; rowi++) {
                            for (int coli = 0; coli < 8; coli++) {
                                if (game.getChessBoard().getPieceAt(new Point(rowi, coli)) != null){
                                    System.out.println("WERREFSD");
                                    if (game.getChessBoard().getPieceAt(new Point(rowi, coli)).threateningLocations.contains(proposedLocation)){
                                        add = false;
                                        System.out.println("WE BE BALLIN");
                                    }
                                }
                            }
                       }
                       if (add){
                           threateningLocations.add(new Point(originalX + curi, originalY + curj));
                           System.out.println(proposedLocation.toString());
                           System.out.println("INININININ");
                       }
                       for (int rowi = 0; rowi < 8; rowi++){
                            for (int rowj = 0; rowj < 8; rowj++){
                                game.getChessBoard().board[rowi][rowj] = backup[rowi][rowj];
//                                if (backup[rowi][rowj] != null){
//                                    if (backup[rowi][rowj].id == id){
//                                        
//                                    }
//                                }
                            }
                       }   
                       this.location.x = originalX;
                       this.location.y = originalY;
                       System.out.println("sd");
//                       game.getChessBoard().board = backup;
                   }
               }              
           }
       }
   } 
}
