/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;
import java.awt.*;
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
    JButton[][] buttons;
    int playerCounter;

    public ChessBoard() {
        board = new Piece[8][8];
        buttons = new JButton[8][8];
        playerCounter = 1;
    }

    public boolean isPieceAt(int row, int col) {
        return board[row][col] != null;
    }
    
    public String getPieceOwner(Point location){
        if (board[location.x][location.y] == null){
            return "";
        }
        return (board[location.x][location.y].owner);
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
    public void createGraphics(int row, int col) throws IOException{
        graphics = new JFrame();
        
        graphics.setTitle("Chess");
        graphics.setSize(800, 800);
        graphics.setBackground(Color.black);
        Container pane = graphics.getContentPane();
         
        pane.setLayout(new GridLayout (row, col));
        Dimension d = new Dimension (100, 100);
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
//        try {
//            initializeImages();
//        } catch (IOException ex) {
//            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                
            
            buttons[i][j] = new JButton();
                    
            buttons[i][j].setPreferredSize(d);
            
            
            if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
                buttons[i][j].setBackground(c2);  
            }
            else{                
                buttons[i][j].setBackground(c1);
            }
            buttons[i][j].setBorderPainted(false);
            pane.add(buttons[i][j]);
            
            final int finalI = i;
            final int finalJ = j;
            
            buttons[i][j].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (board[finalI][finalJ] != null){
                        board[finalI][finalJ].updateThreateningLocations();                        
                        resetBackgrounds();
                        if (board[finalI][finalJ].threateningLocations.size() != 0){
                            for (int loc = 0; loc < board[finalI][finalJ].threateningLocations.size(); loc++){
                                if (isPieceAt(board[finalI][finalJ].threateningLocations.get(loc).x, board[finalI][finalJ].threateningLocations.get(loc).y)){
                                    buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.RED);
                                }
                                else{
                                    buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.GREEN);
                                }
                            }
                        }
                    }
//                        System.out.println(Integer.toString(finalI) + Integer.toString(finalJ));
               
                }
            });
            }
        }
        updateGraphics();
    }
    public void resetBackgrounds(){
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
                    buttons[i][j].setBackground(c2);  
                }
                else{                
                    buttons[i][j].setBackground(c1);
                }
            }
        }
    }
    public void updateGraphics() throws IOException{
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
                    buttons[i][j].setBackground(c2);  
                }
                else{                
                    buttons[i][j].setBackground(c1);
                }
                if (getPieceAt(new Point(i, j)) != null ){
                    Image img;
                    
                    if (this.getPieceAt(new Point(i, j)).owner.equals("player1")){                    
                        img = ImageIO.read(new File(String.format(this.getPieceAt(new Point(i, j)).id+".png")));
                    }
                    else{
                        img = ImageIO.read(new File(String.format(this.getPieceAt(new Point(i, j)).id+"-black.png")));
                    }
                    Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH ) ;  
                    ImageIcon icon = new ImageIcon( newimg );
                    buttons[i][j].setIcon(icon);             
                }
            }
        }
    }
      
    @Override
    public String toString() {
        String output = "  0 1 2 3 4 5 6 7\n";
        for (int rowi = 0; rowi < 8; rowi++) {
            output += rowi;
            for (int coli = 0; coli < 8; coli++) {
                if (board[rowi][coli] != null) {
                    output += " " + board[rowi][coli].id;
                } else {
                    output += " -";
                }
            }
            output += "\n";
        }
        return output;
    }
}
