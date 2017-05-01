/*
 * A King subclass, inheriting from the Piece superclass.
 */
package chess;

import java.awt.Point;

public class King extends Piece{
    public King(String owner, Point initialLocation, ChessGame game) {
        //Call superclass constructor to set up some attributes
        super(owner, initialLocation, game);
        
        //Set id according the owner
        if (owner.equalsIgnoreCase("player1")) {
            id = 'K';
        } else if (owner.equalsIgnoreCase("player2")) {
            id = 'k';
        }
    }
    
    //Calculate distance between 2 points
    public static double distance(Point a, Point b){
        return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
    }
   
    @Override
    protected void updateThreateningLocations(){
        //Clear exisiting threatening locations
        threateningLocations.clear();
        //Create a backup snapshot of the board
        Piece[][] backup =  new Piece[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                backup[i][j] = game.getChessBoard().getPieceAt(new Point(i, j));
            }
        }
        //Backup the piece's current location
        int originalX = location.x;
        int originalY = location.y;
        
        //Loop through the surrounding area (King's possible moves)
        for (int curi = -1; curi <= 1; curi++){
            for(int curj = -1; curj <= 1; curj++){
                //Moving 0 spaces left/right and up/down is not a move
                if (!(curi == 0 && curj == 0)){
                    //The proposed location for the king ot move tos
                    Point proposedLocation = new Point(originalX + curi, originalY + curj);    
                    if (ChessBoard.locationInBounds(proposedLocation) && !game.getChessBoard().getPieceOwner(proposedLocation).equals(owner)){
                        //Move the king to the proposed location
                        game.getChessBoard().placePieceAt(this, proposedLocation);
                        //Update the threatening locations of all pieces
                        game.getChessBoard().updateAllThreateningLocations();
                        
                        //Keeps track of whether the current position selected to move to is valid
                        boolean add = true;
                        for (int rowi = 0; rowi < 8; rowi++) {
                             for (int coli = 0; coli < 8; coli++) {
                                 if (game.getChessBoard().getPieceAt(new Point(rowi, coli)) != null){
                                     //If the position in question is under threat from the enemy do not add it to the possible moves
                                     if (game.getChessBoard().getPieceAt(new Point(rowi, coli)).threateningLocations.contains(proposedLocation)){
                                         add = false;
                                     }
                                 }
                             }
                        }
                        //Find out where the other king is (enemy)
                        Point other = null;
                        if ("player1".equals(owner)){
                            for (int rowi = 0; rowi < 8; rowi++) {
                                 for (int coli = 0; coli < 8; coli++) {
                                     if (game.getChessBoard().board[rowi][coli] != null){
                                         if (game.getChessBoard().board[rowi][coli].id == 'k'){
                                             other = game.getChessBoard().board[rowi][coli].location;
                                         }
                                     }
                                 }
                            }
                        }
                        else{
                            for (int rowi = 0; rowi < 8; rowi++) {
                                 for (int coli = 0; coli < 8; coli++) {
                                     if (game.getChessBoard().board[rowi][coli] != null){
                                         if (game.getChessBoard().board[rowi][coli].id == 'K'){
                                             other = game.getChessBoard().board[rowi][coli].location;
                                         }
                                     }
                                 }
                            }
                        }
                        
                        //Prevent Kings from threatening each other
                        //Adding other king to updateThreateningAll method would cause infinite recursion
                        if (add && distance(new Point(originalX + curi, originalY + curj), other) >= 2){
                            threateningLocations.add(new Point(originalX + curi, originalY + curj));      
                        }
                        
                        //Restore from the backup array
                        for (int rowi = 0; rowi < 8; rowi++){
                            System.arraycopy(backup[rowi], 0, game.getChessBoard().board[rowi], 0, 8);
                        }
                        //Reset location values
                        this.location.x = originalX;
                        this.location.y = originalY;
                   }
               }              
           }
       }
   } 
}
