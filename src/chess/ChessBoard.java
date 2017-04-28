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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author advai
 */
public class ChessBoard {
    public Piece[][] board;
    int row, col = 8;
    JPanel boardPanel;
    JFrame graphicsFrame;
    JButton[][] buttons;
    String curPlayer;
    Point curMove;
    ChessGame curGame;
    boolean inPromotion;
    boolean curCheck;
    
    Image[] images = new Image[9];

    public ChessBoard(ChessGame game) {
        board = new Piece[8][8];
        buttons = new JButton[8][8];
        curPlayer = "player1";
        curMove = null;
        curGame = game;
        inPromotion = false;
        curCheck = false;
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
        //Check if the piece is of the Pawn type. If it is then ensure that its firstMove attribute is set to false
        if (piece instanceof Pawn && ((Pawn)piece).firstMove){
            ((Pawn)piece).firstMove = false;
        }
        if (isPieceAt(location.x, location.y)) {
//            System.out.println("CUR2" + location.toString());
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
    
    public void initializeImages() throws IOException{
         String[] IMAGE_FILES = {
          "Rook.png", "Knight.png", "Bishop.png", "Queen.png", "King.png", "Bishop.png"
            , "Knight.png", "Rook.png", "Pawn.png"};
         
         
         for (int k = 0; k < IMAGE_FILES.length; k ++){
            images [k] = ImageIO.read(new File(String.format(IMAGE_FILES[k])));      
            
         }
         
    }
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } 
        catch (Exception e) {}
    }
    
    public boolean inCheck(String player){
        Point kLocation = null;
        char id;
        if (player == "player1"){
            id = 'K';
        }
        else {
            id = 'k';
        }
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] != null && board[i][j].id == id){
                    kLocation = board[i][j].location;
                }
            }
        }
        updateAllThreateningLocations();
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] != null && board[i][j].threateningLocations.contains(kLocation)){
                    return true;
                }
            }
        }
        return false;
    }
    public void createGraphics(int row, int col){
        graphicsFrame = new JFrame();
        
        graphicsFrame.setTitle("Chess");
        graphicsFrame.setSize(800, 800);
        graphicsFrame.setBackground(Color.black);
//        graphics.setVisible(true);
        
        boardPanel = new JPanel();
        JPanel sidebar = new JPanel();
        final JLabel curImageLabel = new JLabel();
        Image logoImage = null;
        try {
            logoImage = ImageIO.read(new File(String.format("logo.png")));
        } catch (IOException ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon logoIcon = new ImageIcon(logoImage.getScaledInstance(200, 250, java.awt.Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        sidebar.add(logoLabel);
        sidebar.add(curImageLabel);
        sidebar.setBackground(new Color(134, 0, 179));
        final String blackLabel = "<html><div style='text-align: center;'><font color='yellow'>Current Player: </font><br><font color='black'>Black</font></div></html>";
        final String whiteLabel = "<html><div style='text-align: center;'><font color='yellow'>Current Player: </font><br><font color='white'>White</font></div></html>";
        final JLabel description = new JLabel(whiteLabel);
        description.setFont(new Font("TimesRoman", Font.BOLD, 26));
        final ImageIcon curPiece = new ImageIcon();
//        description.setAlignmentX(50);
        sidebar.setPreferredSize(new Dimension (200, 800));
        sidebar.add(description, BorderLayout.CENTER);
        graphicsFrame.add(sidebar, BorderLayout.EAST);
        boardPanel.setLayout(new GridLayout (row, col));
        graphicsFrame.add(boardPanel);
        Dimension d = new Dimension (100, 100);
//        try {
//            initializeImages();
//        } catch (IOException ex) {
//            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                
            
            buttons[i][j] = new JButton();
                    
            buttons[i][j].setPreferredSize(d);
            
            
//            if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
//                buttons[i][j].setBackground(c2);  
//            }
//            else{                
//                buttons[i][j].setBackground(c1);
//            }
            buttons[i][j].setBorderPainted(false);
            boardPanel.add(buttons[i][j]);
            final int finalI = i;
            final int finalJ = j;
            buttons[i][j].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent arg0) {
//                    if (board[finalI][finalJ] != null && board[finalI][finalJ] instanceof Pawn 
//                            && (board[finalI][finalJ].location.x == 7 || board[finalI][finalJ].location.x == 0)){
//                        if (board[finalI][finalJ].location.x == 7){
//                            board[finalI][finalJ] = new Queen("player1", board[finalI][finalJ].location, curGame);
//                        }
//                        updateGraphics();
//                    }
//                    else{
                    
                        if (curMove != null){
                            if (board[curMove.x][curMove.y].threateningLocations.contains(new Point(finalI, finalJ)) || board[curMove.x][curMove.y].movableLocations.contains(new Point(finalI, finalJ))){
                                placePieceAt(board[curMove.x][curMove.y], new Point(finalI, finalJ));
                                if (board[finalI][finalJ] instanceof Pawn && 
                                        (board[finalI][finalJ].location.x == 7 || board[finalI][finalJ].location.x == 0)){
//                                    String type = "Knight";
//                                    Class<?> clazz;
//                                    try {
//                                        clazz = Class.forName(type);
//                                        Piece board[finalI][finalJ] = (Piece) clazz.w;
//                                        
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
                                    final JFrame promotionFrame = new JFrame();
                                    JPanel promotionPanel = new JPanel();
                                    JLabel description = new JLabel("Select a piece to promote the pawn to: ");
                                    description.setFont(new Font("TimesRoman", Font.BOLD, 20));
                                    JButton queenB = new JButton("Queen");
                                    JDialog promotionDialog = new JDialog();
                                    JRadioButton qOption = new JRadioButton("Queen");
                                    JRadioButton nOption = new JRadioButton("Knight");
                                    JRadioButton rOption = new JRadioButton("Rook");
                                    JRadioButton bOption = new JRadioButton("Bishop");
                                    inPromotion = true;
//                                    promotionPanel.setLayout();
                                    promotionPanel.add(description);
                                    promotionPanel.add(qOption);
                                    qOption.addActionListener(new ActionListener(){

                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if (curPlayer == "player1"){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Queen(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                        }
                                        
                                    });
                                    promotionPanel.add(nOption);
                                    nOption.addActionListener(new ActionListener(){

                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if (curPlayer == "player1"){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Knight(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                        }
                                        
                                    });
                                    promotionPanel.add(rOption);
                                    rOption.addActionListener(new ActionListener(){

                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if (curPlayer == "player1"){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Rook(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                        }
                                        
                                    });
                                    promotionPanel.add(bOption);
                                    bOption.addActionListener(new ActionListener(){

                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if (curPlayer == "player1"){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Bishop(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                        }
                                        
                                    });
                                    promotionPanel.setPreferredSize(new Dimension(400, 100));
                                    promotionFrame.add(promotionPanel);
                                    promotionPanel.setVisible(true);
                                    promotionFrame.pack();
                                    promotionFrame.setVisible(true);
//                                    updateGraphics();
//                                    sleep(2000);
//                                    if (qOption.isSelected() || nOption.isSelected() || rOption.isSelected() 
//                                            || bOption.isSelected() || pOption.isSelected()){
//                                        if (qOption.isSelected()){
//                                            board[finalI][finalJ] = new Queen(curPlayer, board[finalI][finalJ].location, curGame);
//                                            updateGraphics();
//                                            promotionFrame.dispose();
//                                        }
//                                        else if (nOption.isSelected()){
//                                            board[finalI][finalJ] = new Knight(curPlayer, board[finalI][finalJ].location, curGame);
//                                            updateGraphics();
//                                            promotionFrame.dispose();
//                                        }
//                                        
//                                    }
                                    System.out.println("DONE");
                                    
//                                    promotionPanel.
//                                    promotionPanel.add(queenB);
//                                    ButtonGroup bG = new ButtonGroup();
//                                    bG.add(male);
//                                    bG.add(female);
//                                    promotionPanel.setLayout(new FlowLayout());
//                                    promotionPanel.add(male);
//                                    promotionPanel.add(female);
                                    
                                    System.out.println("ELLOSDSD");
//                                    if (board[finalI][finalJ].location.x == 0){
//                                        board[finalI][finalJ] = new Queen("player1", board[finalI][finalJ].location, curGame);
//                                    }
//                                    else{
//                                        board[finalI][finalJ] = new Queen("player2", board[finalI][finalJ].location, curGame);
//                                    }
                                }
//                                System.out.println("CUR" + curMove.toString());
                                updateGraphics();
                                
                                if (curPlayer.equals("player1")){
                                    curPlayer = "player2";
                                    description.setText(blackLabel);
                                }
                                else{
                                    curPlayer = "player1";                                
                                    description.setText(whiteLabel);
                                }
                                
                                curCheck = inCheck(curPlayer);
                                curMove = null;
                            }
//                        if (curMove != null){
//                            if (board[curMove.x][curMove.y].movableLocations.contains(new Point(finalI, finalJ))){
//                                placePieceAt(board[curMove.x][curMove.y], new Point(finalI, finalJ));
//                                System.out.println("CUR" + curMove.toString());
//                                updateGraphics();
//                                if (curPlayer.equals("player1")){
//                                    curPlayer = "player2";                                
//                                    description.setText(blackLabel);
//                                }
//                                else{
//                                    curPlayer = "player1";                                
//                                    description.setText(whiteLabel);
//                                }
//                                curMove = null;
//                            }
//                        }
//                        String output = "  0 1 2 3 4 5 6 7\n";
//                        for (int rowi = 0; rowi < 8; rowi++) {
//                            output += rowi;
//                            for (int coli = 0; coli < 8; coli++) {
//                                if (board[rowi][coli] != null) {
//                                    output += " " + board[rowi][coli].id;
//                                } else {
//                                    output += " -";
//                                }
//                            }
//                            output += "\n";
//                        }
//                        System.out.println(output);
//                        System.out.println("hereew");

                        }
                        if (board[finalI][finalJ] != null && board[finalI][finalJ].owner.equals(curPlayer) && !inPromotion){
                                //if instanceof Pawn and board[i][j].location.x == 0 or 7
                                //board[i]
                            
                            board[finalI][finalJ].updateThreateningLocations();
                            System.out.println(board[finalI][finalJ].threateningLocations.toString());
                            System.out.println("--------------sadas--------------");
                            if (!(board[finalI][finalJ] instanceof King)){
                                if (curCheck){                        
                                    System.out.println("HEREEEEEE");
                                    Piece[][] backup =  new Piece[8][8];
                                    for (int i = 0; i < 8; i++){
                                        for (int j = 0; j < 8; j++){
                                            backup[i][j] = board[i][j];
                                        }
                                    }
                                    int originalX = board[finalI][finalJ].location.x;
                                    int originalY = board[finalI][finalJ].location.y;                                
                                    ArrayList<Point> curThreatening = new ArrayList<>();
                                    //deep copy
                                    for (int i = 0; i < board[finalI][finalJ].threateningLocations.size(); i ++){
                                        curThreatening.add(new Point(board[finalI][finalJ].threateningLocations.get(i).x, board[finalI][finalJ].threateningLocations.get(i).y));
                                    }
                                    System.out.println(board[finalI][finalJ].threateningLocations.size()-1);
                                    System.out.println(board[finalI][finalJ].threateningLocations.size());
                                    System.out.println(board[finalI][finalJ].threateningLocations.toString());
                                    for (int loc = board[finalI][finalJ].threateningLocations.size()-1; loc >= 0; loc --){
                                        System.out.println(board[finalI][finalJ].threateningLocations.get(loc).toString());
                                        placePieceAt(board[finalI][finalJ], board[finalI][finalJ].threateningLocations.get(loc));
                                        if (inCheck(curPlayer)){
                                            System.out.println("REM");
                                            curThreatening.remove(loc);
                                        }
                                        for (int rowi = 0; rowi < 8; rowi++){
                                            for (int rowj = 0; rowj < 8; rowj++){
                                                board[rowi][rowj] = backup[rowi][rowj];
                                            }
                                        }
                                        board[finalI][finalJ].location.x = originalX;
                                        board[finalI][finalJ].location.y = originalY;
                                        board[finalI][finalJ].updateThreateningLocations();
                                        System.out.println(board[finalI][finalJ].threateningLocations.toString());
                                    }
                                    board[finalI][finalJ].threateningLocations = curThreatening;
                                }
                            }
                                resetBackgrounds();
                                buttons[finalI][finalJ].setBackground(Color.CYAN);
                                System.out.println(board[finalI][finalJ].threateningLocations);
                                System.out.println(board[finalI][finalJ].movableLocations);
                                if (board[finalI][finalJ].threateningLocations.size() != 0){
                                    for (int loc = 0; loc < board[finalI][finalJ].threateningLocations.size(); loc++){
                                        if (isPieceAt(board[finalI][finalJ].threateningLocations.get(loc).x, board[finalI][finalJ].threateningLocations.get(loc).y)){
                                            buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.RED);
                                        }
                                        else{
                                            buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.GREEN);
                                        }                                
                                    }

                                    curMove = new Point(finalI, finalJ);
                                }
                                if (board[finalI][finalJ].movableLocations.size() != 0){
                                    for (int loc = 0; loc < board[finalI][finalJ].movableLocations.size(); loc++){
                                        buttons[board[finalI][finalJ].movableLocations.get(loc).x][board[finalI][finalJ].movableLocations.get(loc).y].setBackground(Color.GREEN);
                                    }
                                    curMove = new Point(finalI, finalJ);
                                }
                            
                            
                        }
                        if (board[finalI][finalJ] != null){
                            Image curImg = null;

                            if (getPieceAt(new Point(finalI, finalJ)).owner.equals("player1")){                    
                                try {
                                    curImg = ImageIO.read(new File(String.format(getPieceAt(new Point(finalI, finalJ)).id+".png")));
                                } catch (IOException ex) {
                                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                                try {
                                    curImg = ImageIO.read(new File(String.format(getPieceAt(new Point(finalI, finalJ)).id+"-black.png")));
                                } catch (IOException ex) {
                                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            ImageIcon curIcon = new ImageIcon(curImg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
    //                        curPlayerImage = curIcon;
                            curImageLabel.setIcon(curIcon);
                        }
//                        System.out.println("Current state of board: ");
//                        System.out.println(boardToText());
                    }
//                        System.out.println(Integer.toString(finalI) + Integer.toString(finalJ));
               
//                }
                
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
    public void updateGraphics(){
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
                    Image img = null;
                    
                    if (this.getPieceAt(new Point(i, j)).owner.equals("player1")){                    
                        try {
                            img = ImageIO.read(new File(String.format(this.getPieceAt(new Point(i, j)).id+".png")));
                        } catch (IOException ex) {
                            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        try {
                            img = ImageIO.read(new File(String.format(this.getPieceAt(new Point(i, j)).id+"-black.png")));
                        } catch (IOException ex) {
                            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH ) ;  
                    ImageIcon icon = new ImageIcon( newimg );
                    buttons[i][j].setIcon(icon);             
                }
                else{
                    buttons[i][j].setIcon(null);
                }
            }
        }
    }
    public void updateAllThreateningLocations(){
        for (int rowi = 0; rowi < 8; rowi++) {
            for (int coli = 0; coli < 8; coli++) {
                if (board[rowi][coli]!= null && !(board[rowi][coli] instanceof King)){
                    board[rowi][coli].updateThreateningLocations();
//                    System.out.println(rowi + " " +coli);
                }                
            }
        }
    }
    public void setChessBoard(Piece[][] newBoard){
        board = newBoard;
    }
      
    public String boardToText() {
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
