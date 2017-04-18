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
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChessGame cgame = new ChessGame();
        System.out.println(cgame.getChessBoard().toString());
        cgame.getChessBoard().placePieceAt(cgame.getChessBoard().getPieceAt(new Point (0, 0)), new Point(2, 0));
        
        System.out.println(cgame.getChessBoard().toString());
    }
}
