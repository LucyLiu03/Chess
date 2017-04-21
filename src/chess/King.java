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
    
   
   protected void updateMovableLocations(){
       threateningLocations.clear();
       
       for (int i = -1; i<=1; i++){
           for( int j = -1; j<=1; j++){
               if (!( i == 0 && j == 0)){
                   threateningLocations.add(new Point(location.x + i, location.y + j));
               }              
           }
       }
       updateMovableLocations();
   } 
}
