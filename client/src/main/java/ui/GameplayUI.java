package ui;

import chess.ChessGame;

import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class GameplayUI {
    public BoardPrinter printer = new BoardPrinter();

    public void run(ChessGame.TeamColor color) {
        while (true) {
            if(color == null) {
                System.out.printf("\n[OBSERVING]>>> ");
            }else if(color == WHITE) {
                System.out.printf("\n[PLAYING_AS_WHITE]>>> ");
            }else{
                System.out.printf("\n[PLAYING_AS_BLACK]>>> ");
            }
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            if (words[0].equals("help")) {
                System.out.println("Type:'redraw' - to redraw the chessboard \nType: 'leave' - to leave the game\nType: 'move <STARTING COORDINATE> <ENDING COORDINATE>' - to move a piece from the starting coordinate to the ending coordinate\nType: 'resign' - to resign from the game\nType: 'possible <COORDINATES OF PIECE>' - to highlight all the possible moves for the piece in that square\nType: 'help' - to find out what you can do");
            }
        }
    }
}
