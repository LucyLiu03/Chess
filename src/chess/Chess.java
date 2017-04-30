/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

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
        System.out.println(cgame.getChessBoard().boardToText());
//        cgame.getChessBoard().createGraphics(8, 8);
//        cgame.getChessBoard().placePieceAt(cgame.getChessBoard().getPieceAt(new Point (1, 5)), new Point(0, 5));
//        cgame.getChessBoard().placePieceAt(cgame.getChessBoard().getPieceAt(new Point (7, 3)), new Point(5, 6));
//        
//        cgame.getChessBoard().updateGraphics();
//        cgame.getChessBoard().getPieceAt(new Point (0, 6)).updateThreateningLocations();
//        cgame.getChessBoard().getPieceAt(new Point (0, 6)).printThreateningLocations();
//        
//        
//        ChessBoard gt = new ChessBoard();
//        try {
//            gt.chessBoard(8, 8);
//        } catch (IOException ex) {
//            Logger.getLogger(Chess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        cgame.getChessBoard().getPieceAt(new Point (1, 1)).updateThreateningLocations();
        
//        cgame.getChessBoard().getPieceAt(new Point (1, 1)).updateMovableLocations();
//        
//        cgame.getChessBoard().getPieceAt(new Point (1, 1)).printThreateningLocations();
//        
//        cgame.getChessBoard().getPieceAt(new Point (1, 1)).printMovableLocations();
//        cgame.getChessBoard().graphicsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        cgame.getChessBoard().graphicsFrame.pack();
//        cgame.getChessBoard().graphicsFrame.setVisible(true);
        
        
    }
    
}