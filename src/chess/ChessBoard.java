/*
 * The ChessBoard class which stores a 2D array of Piece objects and a 2D array of JButtons for graphical representation.
 * This class handles all piece movements and updates the graphics as needed.
 */
package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ChessBoard {
    public Piece[][] board;
    final static int rows = 8;
    final static int cols = 8;
    JPanel boardPanel;
    JFrame graphicsFrame;
    JButton[][] buttons;
    String curPlayer;
    Point curMove;
    ChessGame curGame;
    boolean inPromotion;
    boolean curCheck;

    public ChessBoard(ChessGame game) {
        //Initialize a 2D array to hold Piece objects
        board = new Piece[rows][cols];
        //Initialize a 2D array to hold JButtons
        buttons = new JButton[rows][cols];
        //White always goes first (player1)
        curPlayer = "player1";
        //Initialize curMove
        curMove = null;
        //Define which ChessGame this board is a part of
        curGame = game;
        //Not in pawn promotion stage at the start
        inPromotion = false;
        //Not in check at the start
        curCheck = false;
        
        //Initialize graphics frame (JFrame)
        graphicsFrame = new JFrame();        
    }

    //Checks if there is a piece at the specified location (boolean return value)
    public boolean isPieceAt(int row, int col) {
        return board[row][col] != null;
    }
    
    //Finds out who owns a certain piece at a specified location
    public String getPieceOwner(Point location){
        if (board[location.x][location.y] == null){
            return "";
        }
        return (board[location.x][location.y].owner);
    }
    
    //Move a certain piece to a specified location
    public void placePieceAt(Piece piece, Point location) {
        //Remove the piece at the new location
        if (isPieceAt(location.x, location.y)) {
            //If a king is being captured, end the game
            if (board[location.x][location.y] instanceof King){
                if (piece.location != null) {
                    removePieceAt(piece.location);
                }
                board[location.x][location.y] = piece;
                piece.location = location;
                updateGraphics();
                //Inform the player who has won the game
                JOptionPane.showMessageDialog(graphicsFrame, ("The " + (curPlayer.equals("player1") ? "black":"white") + " team's king has been taken. " + (curPlayer.equals("player1") ? "White":"Black") + " has won the game!"));
                graphicsFrame.dispose();
                System.exit(0);
            }
            removePieceAt(location);
        }
        
        //Remove the piece from its current location
        if (piece.location != null) {
            removePieceAt(piece.location);
        }
        
        //Update the piece at the new location
        board[location.x][location.y] = piece;
        
        //Set the location data of the piece to its new location
        piece.location = location;
    }

    //Remove piece at a specific location
    public void removePieceAt(Point location) {
        board[location.x][location.y] = null;
    }
    
    //Check if location is within bounds of chess board
    public static boolean locationInBounds(Point location) {
        return location.x >= 0 && location.x < rows && 
               location.y >= 0 && location.y < cols;
    }
    
    //Get piece at a specific location
    public Piece getPieceAt(Point location) {
        return board[location.x][location.y];
    }
    
    //Checks if the player is in checkmate (the king and any pieces cannot move/attack to stop the check)
    //Returns true if in checkmate and false otherwise
    public boolean inCheckMate(String player){  
        Piece[][] backup =  new Piece[rows][cols];
        for (int i = 0; i < rows; i++){
            System.arraycopy(board[i], 0, backup[i], 0, cols);
        }
        updateAllThreateningLocations();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (board[i][j] != null && board[i][j].owner.equals(player) && !(board[i][j] instanceof King)){
                    for (int loc = 0; loc < board[i][j].threateningLocations.size(); loc++){
                        placePieceAt(board[i][j], board[i][j].threateningLocations.get(loc));
                        
                        if (!inCheck(curPlayer)){
                            
                            
                            for (int rowi = 0; rowi < rows; rowi++){
                                System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                            }
                            board[i][j].location.x = i;
                            board[i][j].location.y = j;
                            
                            updateAllThreateningLocations();
                            return false;
                        }
                        for (int rowi = 0; rowi < rows; rowi++){
                            System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                        }
                        board[i][j].location.x = i;
                        board[i][j].location.y = j;
                        
                        updateAllThreateningLocations();
                    }
                    //Checks if pawn can move forward to stop the check
                    if (board[i][j] instanceof Pawn){
                        for (int loc = 0; loc < board[i][j].movableLocations.size(); loc++){
                            placePieceAt(board[i][j], board[i][j].movableLocations.get(loc));
                            
                            if (!inCheck(curPlayer)){
                                for (int rowi = 0; rowi < rows; rowi++){
                                    System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                                }
                                board[i][j].location.x = i;
                                board[i][j].location.y = j;

                                updateAllThreateningLocations();
                                return false;
                            }
                            for (int rowi = 0; rowi < rows; rowi++){
                                System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                            }
                            board[i][j].location.x = i;
                            board[i][j].location.y = j;

                            updateAllThreateningLocations();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (board[i][j] != null && board[i][j] instanceof King && board[i][j].owner.equals(player)){
                    board[i][j].updateThreateningLocations();
                    if (!board[i][j].threateningLocations.isEmpty()){        
                        
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
    
    //Returns true if the player is in check and false otherwise
    public boolean inCheck(String player){
        Point kLocation = null;
        //Decides which king to check
        char id;
        if ("player1".equals(player)){
            id = 'K';
        }
        else {
            id = 'k';
        }
        
        //Find the location of the king
        for (int i = 0; i < rows; i ++){
            for (int j = 0; j < cols; j++){
                if (board[i][j] != null && board[i][j].id == id){
                    board[i][j].updateThreateningLocations();
                    kLocation = board[i][j].location;
                }
            }
        }
        updateAllThreateningLocations();
        
        //Checks if the player's king is in danger (contained within the enemy's threatening locations)
        for (int i = 0; i < rows; i ++){
            for (int j = 0; j < cols; j++){
                if (board[i][j] != null && board[i][j].threateningLocations.contains(kLocation)){                    
                    return true;
                }
            }
        }
        return false;
    }
    
    //Creates the grid of JButtons and sets up the main JFrame
    public void createGraphics(){
        //Set up the main JFrame
        graphicsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        graphicsFrame.setTitle("Chess");
        graphicsFrame.setSize(800, 800);
        graphicsFrame.setBackground(Color.black);
        
        //Used to store the button grid
        boardPanel = new JPanel();
        
        //Set up the sidebar
        JPanel sidebar = new JPanel();
        final JLabel curImageLabel = new JLabel();
        Image logoImage = null;
        
        //Load the chess logo into the game
        try {
            logoImage = ImageIO.read(getClass().getResource("/images/logo.PNG"));
        } catch (IOException ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon logoIcon = new ImageIcon(logoImage.getScaledInstance(200, 250, java.awt.Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        sidebar.add(logoLabel);
        sidebar.add(curImageLabel);
        sidebar.setBackground(new Color(204, 204, 0));
        
        //Used to display the current player label in sidebar (uses HTML syntax for formatting)
        final String blackLabel = "<html><div style='text-align: center;'><font color='purple'>Current Player: </font><br><font color='black'>Black</font></div></html>";
        final String whiteLabel = "<html><div style='text-align: center;'><font color='purple'>Current Player: </font><br><font color='white'>White</font></div></html>";
        final JLabel description = new JLabel(whiteLabel);
        description.setFont(new Font("TimesRoman", Font.BOLD, 26));
        sidebar.setPreferredSize(new Dimension (200, 800));
        sidebar.add(description, BorderLayout.CENTER);
        graphicsFrame.add(sidebar, BorderLayout.EAST);
        
        //Use a GridLayout for the button grid
        boardPanel.setLayout(new GridLayout(rows, cols));
        graphicsFrame.add(boardPanel);
        Dimension d = new Dimension (100, 100);
        
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                //Create a button and set it to the preferred size
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(d);
                buttons[i][j].setBorderPainted(false);

                //Add button to the JPanel
                boardPanel.add(buttons[i][j]);

                //Create final variables so they can be called within the override actionPerformed method
                final int finalI = i;
                final int finalJ = j;
                
                buttons[i][j].addActionListener(new ActionListener(){
                    //Override the actionPerformed method so that it is possible 
                    //to customize what happens after the user presses a button
                    @Override
                    public void actionPerformed(ActionEvent arg0) {                    
                        if (curMove != null){
                            if (board[curMove.x][curMove.y].threateningLocations.contains(new Point(finalI, finalJ)) || board[curMove.x][curMove.y].movableLocations.contains(new Point(finalI, finalJ))){
                                placePieceAt(board[curMove.x][curMove.y], new Point(finalI, finalJ));
                                //If the pawn is eligible for pawn promotion
                                if (board[finalI][finalJ] instanceof Pawn && 
                                        (board[finalI][finalJ].location.x == 7 || board[finalI][finalJ].location.x == 0)){
                                    //Create a pawn promotion dialog to let the player select the piece to which they would like to promote their pawn
                                    final JFrame promotionFrame = new JFrame();
                                    JPanel promotionPanel = new JPanel();
                                    JLabel promotionDescription = new JLabel("Select a piece to promote the pawn to: ");
                                    promotionDescription.setFont(new Font("TimesRoman", Font.BOLD, 20));

                                    JRadioButton qOption = new JRadioButton("Queen");
                                    JRadioButton nOption = new JRadioButton("Knight");
                                    JRadioButton rOption = new JRadioButton("Rook");
                                    JRadioButton bOption = new JRadioButton("Bishop");
                                    inPromotion = true;
                                    //Add the description the panel
                                    promotionPanel.add(promotionDescription);

                                    //Add each possible option to the panel
                                    promotionPanel.add(qOption);

                                    //Override the actionPerformed method for so that it can promote the pawn properly
                                    qOption.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if ("player1".equals(curPlayer)){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Queen(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                            curCheck = inCheck(curPlayer);
                                            if (curCheck){
                                                if (inCheckMate(curPlayer)){

                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in checkmate. " + (curPlayer.equals("player1") ? "Black":"White") + " has won the game!");
                                                    graphicsFrame.dispose();
                                                    System.exit(0);
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in check. Please choose a valid move (highlighted in green/red).");
                                                }
                                            }
                                        }

                                    });

                                    promotionPanel.add(nOption);
                                    //Override the actionPerformed method for so that it can promote the pawn properly
                                    nOption.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if ("player1".equals(curPlayer)){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Knight(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                            curCheck = inCheck(curPlayer);
                                            if (curCheck){
                                                if (inCheckMate(curPlayer)){

                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in checkmate. " + (curPlayer.equals("player1") ? "Black":"White") + " has won the game!");
                                                    graphicsFrame.dispose();
                                                    System.exit(0);
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in check. Please choose a valid move (highlighted in green/red).");
                                                }
                                            }
                                        }

                                    });

                                    promotionPanel.add(rOption);
                                    //Override the actionPerformed method for so that it can promote the pawn properly
                                    rOption.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if ("player1".equals(curPlayer)){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Rook(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                            curCheck = inCheck(curPlayer);
                                            if (curCheck){
                                                if (inCheckMate(curPlayer)){

                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in checkmate. " + (curPlayer.equals("player1") ? "Black":"White") + " has won the game!");
                                                    graphicsFrame.dispose();
                                                    System.exit(0);
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in check. Please choose a valid move (highlighted in green/red).");
                                                }
                                            }
                                        }                                        
                                    });

                                    promotionPanel.add(bOption);
                                    //Override the actionPerformed method for so that it can promote the pawn properly
                                    bOption.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String prevPlayer;
                                            if ("player1".equals(curPlayer)){
                                                prevPlayer = "player2";
                                            }
                                            else{
                                                prevPlayer = "player1";
                                            }
                                            board[finalI][finalJ] = new Bishop(prevPlayer, board[finalI][finalJ].location, curGame);
                                            inPromotion = false;
                                            updateGraphics();
                                            promotionFrame.dispose();
                                            curCheck = inCheck(curPlayer);
                                            if (curCheck){
                                                if (inCheckMate(curPlayer)){

                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in checkmate. " + (curPlayer.equals("player1") ? "Black":"White") + " has won the game!");
                                                    graphicsFrame.dispose();
                                                    System.exit(0);
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in check. Please choose a valid move (highlighted in green/red).");
                                                }
                                            }
                                        }
                                    });
                                    //Set panel size
                                    promotionPanel.setPreferredSize(new Dimension(400, 100));
                                    promotionFrame.add(promotionPanel);
                                    //Force user to select an option (cannot close the window)
                                    promotionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                                    //Show the user the promotion dialog
                                    promotionFrame.pack();
                                    promotionFrame.setVisible(true);
                                }

                                //Update board graphics to show the moved piece
                                updateGraphics();

                                //Ensure the firstMove attribute is set to false if a pawn is moving for its first time
                                if (board[finalI][finalJ] instanceof Pawn && ((Pawn)board[finalI][finalJ]).firstMove){
                                    ((Pawn)board[finalI][finalJ]).firstMove = false;
                                }

                                //Update current player
                                if (curPlayer.equals("player1")){
                                    curPlayer = "player2";
                                    description.setText(blackLabel);
                                }
                                else{
                                    curPlayer = "player1";                                
                                    description.setText(whiteLabel);
                                }

                                //Update whether or not the current player is in check
                                curCheck = inCheck(curPlayer);

                                if (curCheck){
                                    if (inCheckMate(curPlayer)){

                                        JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in checkmate. " + (curPlayer.equals("player1") ? "Black":"White") + " has won the game!");
                                        graphicsFrame.dispose();
                                        System.exit(0);
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(graphicsFrame, (curPlayer.equals("player1") ? "White":"Black") + " is in check. Please choose a valid move (highlighted in green/red).");
                                    }
                                }
                                curMove = null;
                            }
                        }
                        if (board[finalI][finalJ] != null && board[finalI][finalJ].owner.equals(curPlayer) && !inPromotion){                            
                                board[finalI][finalJ].updateThreateningLocations();                                

                                if (!(board[finalI][finalJ] instanceof King)){
                                    if (curCheck){                        
                                        //Backup array used to store a snapshot of the game
                                        Piece[][] backup =  new Piece[rows][cols];
                                        for (int i = 0; i < rows; i++){
                                            System.arraycopy(board[i], 0, backup[i], 0, cols);
                                        }
                                        int originalX = board[finalI][finalJ].location.x;
                                        int originalY = board[finalI][finalJ].location.y;                                
                                        ArrayList<Point> curThreatening = new ArrayList<>();
                                        
                                        //Create a copy of the x/y values to prevent passing by reference
                                        for (int i = 0; i < board[finalI][finalJ].threateningLocations.size(); i ++){
                                            curThreatening.add(new Point(board[finalI][finalJ].threateningLocations.get(i).x, board[finalI][finalJ].threateningLocations.get(i).y));
                                        }

                                        for (int loc = board[finalI][finalJ].threateningLocations.size()-1; loc >= 0; loc --){

                                            placePieceAt(board[finalI][finalJ], board[finalI][finalJ].threateningLocations.get(loc));
                                            if (inCheck(curPlayer)){

                                                curThreatening.remove(loc);
                                            }
                                            for (int rowi = 0; rowi < rows; rowi++){
                                                System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                                            }
                                            board[finalI][finalJ].location.x = originalX;
                                            board[finalI][finalJ].location.y = originalY;
                                            board[finalI][finalJ].updateThreateningLocations();
                                        }

                                        if (board[finalI][finalJ] instanceof Pawn){
                                            ArrayList<Point> curMovable = new ArrayList<>();
                                            
                                            //Add movable locations to the curMovable arrayList
                                            for (int i = 0; i < board[finalI][finalJ].movableLocations.size(); i ++){
                                                curMovable.add(new Point(board[finalI][finalJ].movableLocations.get(i).x, board[finalI][finalJ].movableLocations.get(i).y));
                                            }
                                            
                                            //Check if the pawn can stop the check
                                            for (int loc = board[finalI][finalJ].movableLocations.size()-1; loc >= 0; loc --){
                                                //Place pawn at proposed location
                                                placePieceAt(board[finalI][finalJ], board[finalI][finalJ].movableLocations.get(loc));
                                                
                                                //If still in check, this is not a legal move
                                                if (inCheck(curPlayer)){
                                                    curMovable.remove(loc);
                                                }
                                                //Restore from backup
                                                for (int rowi = 0; rowi < rows; rowi++){
                                                    System.arraycopy(backup[rowi], 0, board[rowi], 0, cols);
                                                }
                                                //Restore location data
                                                board[finalI][finalJ].location.x = originalX;
                                                board[finalI][finalJ].location.y = originalY;
                                                //Update threatening locations
                                                board[finalI][finalJ].updateMovableLocations();

                                            }
                                            board[finalI][finalJ].movableLocations = curMovable;
                                        }
                                        board[finalI][finalJ].threateningLocations = curThreatening;
                                    }
                                }
                                
                                //Reset chessboard tile backgrounds
                                resetBackgrounds();
                                //Set current piece (selected) background to cyan
                                buttons[finalI][finalJ].setBackground(Color.CYAN);
                                
                                //Reset curMove to null if needed 
                                //(prevents a player from clicking a movable piece, then an unmovable piece and moving the movable piece while the second piece is selected)
                                boolean setNew = false;
                                if (!board[finalI][finalJ].threateningLocations.isEmpty()){
                                    for (int loc = 0; loc < board[finalI][finalJ].threateningLocations.size(); loc++){
                                        if (isPieceAt(board[finalI][finalJ].threateningLocations.get(loc).x, board[finalI][finalJ].threateningLocations.get(loc).y)){
                                            buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.RED);
                                        }
                                        else{
                                            buttons[board[finalI][finalJ].threateningLocations.get(loc).x][board[finalI][finalJ].threateningLocations.get(loc).y].setBackground(Color.GREEN);
                                        }                                
                                    }

                                    curMove = new Point(finalI, finalJ);
                                    setNew = true;
                                }
                                if (!board[finalI][finalJ].movableLocations.isEmpty()){
                                    for (int loc = 0; loc < board[finalI][finalJ].movableLocations.size(); loc++){
                                        buttons[board[finalI][finalJ].movableLocations.get(loc).x][board[finalI][finalJ].movableLocations.get(loc).y].setBackground(Color.GREEN);
                                    }
                                    curMove = new Point(finalI, finalJ);
                                    setNew = true;
                                }
                                if (!setNew){
                                    curMove = null;
                                }



                        }
                        
                        //Update the current piece image shown in the sidebar if the piece clicked belongs to the current player
                        if (board[finalI][finalJ] != null && getPieceAt(new Point(finalI, finalJ)).owner.equals(curPlayer)){
                            Image curImg = null;

                            if (getPieceAt(new Point(finalI, finalJ)).owner.equals("player1")){                    
                                try {
                                    curImg = ImageIO.read(getClass().getResource("/images/" + getPieceAt(new Point(finalI, finalJ)).id + ".png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                                try {
                                    curImg = ImageIO.read(getClass().getResource("/images/" + getPieceAt(new Point(finalI, finalJ)).id + "-black.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            ImageIcon curIcon = new ImageIcon(curImg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
                            curImageLabel.setIcon(curIcon);
                        }     
                    }
                });
            }
        }
        //Update graphics
        updateGraphics();
    }
    
    //Reset all tile backgrounds (brown/light brown)
    public void resetBackgrounds(){
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if ((i%2==0 && j%2==0)|| (i%2 != 0 && j%2 != 0)){
                    buttons[i][j].setBackground(c2);  
                }
                else{                
                    buttons[i][j].setBackground(c1);
                }
            }
        }
    }
    
    //Update graphics according to the board 2D array
    public void updateGraphics(){
        Color c1 = new Color (139,69,19);
        Color c2 = new Color (222,184,135);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
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
                            img = ImageIO.read(getClass().getResource("/images/" + getPieceAt(new Point(i, j)).id + ".png"));
                        } catch (IOException ex) {
                            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        try {
                            img = ImageIO.read(getClass().getResource("/images/" + getPieceAt(new Point(i, j)).id + "-black.png"));
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
    
    //Update all threatening locations 
    //(except Kings since they would cause an infinite recursion loop - this method is called in their updateThreateningLocations())
    public void updateAllThreateningLocations(){
        for (int rowi = 0; rowi < rows; rowi++) {
            for (int coli = 0; coli < cols; coli++) {
                if (board[rowi][coli]!= null && !(board[rowi][coli] instanceof King)){
                    board[rowi][coli].updateThreateningLocations();
                }                
            }
        }
    }
    
    //Converts the board to its textual representation and returns the String
    public String boardToText() {
        String output = "  0 1 2 3 4 5 6 7\n";
        for (int rowi = 0; rowi < rows; rowi++) {
            output += rowi;
            for (int coli = 0; coli < cols; coli++) {
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
//End of ChessBoard class