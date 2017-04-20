/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import javax.swing.JFrame;
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
        System.out.println("---------------------");
        System.out.println("---------------");
        cgame.getChessBoard().placePieceAt(cgame.getChessBoard().getPieceAt(new Point (0, 0)), new Point(2, 0));
        
        cgame.getChessBoard().getPieceAt(new Point (2, 0)).updateThreateningLocations();
        cgame.getChessBoard().getPieceAt(new Point (2, 0)).printThreateningLocations();
        System.out.println(cgame.getChessBoard().toString());
        
        ChessBoard gt = new ChessBoard();
        gt.chessBoard(8, 8);
        cgame.getChessBoard().getPieceAt(new Point (1, 1)).updateThreateningLocations();
        
        cgame.getChessBoard().getPieceAt(new Point (1, 1)).updateMovableLocations();
        System.out.println("----");
        cgame.getChessBoard().getPieceAt(new Point (1, 1)).printThreateningLocations();
        System.out.println("----");
        cgame.getChessBoard().getPieceAt(new Point (1, 1)).printMovableLocations();
        gt.graphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gt.graphics.pack();
        gt.graphics.setVisible(true);
    }
}