/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author advai
 */
public class ChessBoard {
    private Piece[][] board;
    int row, col = 8;
    JFrame graphics;

    public ChessBoard() {
        board = new Piece[8][8];
    }

    public boolean isPieceAt(int row, int col) {
        return board[row][col] != null;
    }

    public void placePieceAt(Piece piece, Point location) {
        if (isPieceAt(location.x, location.y)) {
            removePieceAt(location);
        }
        if (piece.location != null) {
            removePieceAt(piece.location);
        }
        board[location.x][location.y] = piece;
        piece.location = location;
    }

    private void removePieceAt(Point location) {
        board[location.x][location.y] = null;
    }
    
    public static boolean locationInBounds(Point location) {
        return location.x >= 0 && location.x < 8 && 
               location.y >= 0 && location.y < 8;
    }

    public Piece getPieceAt(Point location) {
        return board[location.x][location.y];
    }
    Image[] images = new Image[9];
    public void initializeImages() throws IOException{
         String[] IMAGE_FILES = {
          "Rook.png", "Knight.png", "Bishop.png", "Queen.png", "King.png", "Bishop.png"
            , "Knight.png", "Rook.png", "Pawn.png"};
         
         
         for (int k = 0; k < IMAGE_FILES.length; k ++){
            images [k] = ImageIO.read(new File(String.format(IMAGE_FILES[k])));      
            
         }
         
    }
    public void chessBoard ( int row, int col){
        graphics = new JFrame();
        
        graphics.setTitle("Chess");
        graphics.setSize(800, 800);
        graphics.setBackground(Color.black);
        Container pane = graphics.getContentPane();
         
        pane.setLayout(new GridLayout (row, col));
        Dimension d = new Dimension (50,50);
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
        try {
            initializeImages();
        } catch (IOException ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                
            
            JButton button = new JButton();
            button.setPreferredSize(d);
            
            button.setBackground(c1);
            
            if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
                button.setBackground(c2);
                
            }
            button.setBorderPainted(false);
            pane.add(button);
            
            final int finalI = i;
            final int finalJ = j;
            if (i == 0){
                Image img = images[j];
                System.out.println(images[j]);
                if (new ImageIcon(img) == null){
                System.out.println("null");
                }
                
                Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH ) ;  
                ImageIcon icon = new ImageIcon( newimg );
                button.setIcon(icon);
                
            }  
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                        System.out.println(Integer.toString(finalI) + Integer.toString(finalJ));
               
                }
            });
            }
        }
    }
    
    @Override
    public String toString() {
        String output = "  0 1 2 3 4 5 6 7\n";
        for (int row = 0; row < 8; row++) {
            output += row;
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    output += " " + board[row][col].id;
                } else {
                    output += " -";
                }
            }
            output += "\n";
        }
        return output;
    }
}
