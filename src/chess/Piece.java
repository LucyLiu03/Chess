/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author advai
 */
public class Piece {
    String owner;
    Point location;
    char id;
    ArrayList<Point> threateningLocations;
    ChessGame game;
    
    public Piece(String owner, Point initialLocation, ChessGame cGame) {
        this.owner = owner;
        Point location = initialLocation;
        threateningLocations = new ArrayList<>();
        this.game = cGame;
        game.getChessBoard().placePieceAt(this, initialLocation);
    }
}
