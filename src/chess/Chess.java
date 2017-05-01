/*
 * The Chess class contains the main() method used to start the game.
 */
package chess;

/**
 *
 * @author advai
 */
public class Chess {
    //Used to start the game
    public static void main(String[] args) {
        //Initialize a new chess game
        ChessGame cgame = new ChessGame();
        //Shows the user the textual representation of the initial setup
        System.out.println("Initial setup: ");
        System.out.println(cgame.getChessBoard().boardToText());
    }    
}
//End of Chess class