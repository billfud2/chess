package ui;

import chess.*;
import com.google.gson.Gson;
import facade.WSClient;
import webSocketMessages.userCommands.*;

import java.util.Collection;
import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class GameplayUI {
    public BoardPrinter printer = new BoardPrinter();
    static Gson gson = new Gson();
    

    public void run(ChessGame.TeamColor color, WSClient ws, int gameID, String auth) {
        while (true) {
            try {
            if (color == null) {
                System.out.printf("\n[OBSERVING]>>> ");
            } else if (color == WHITE) {
                System.out.printf("\n[PLAYING_AS_WHITE]>>> ");
            } else {
                System.out.printf("\n[PLAYING_AS_BLACK]>>> ");
            }
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            if (words[0].equals("help") && words.length == 1) {
                System.out.println("Type:'redraw' - to redraw the chessboard \nType: 'leave' - to leave the game\nType: 'move <STARTING COORDINATE> <ENDING COORDINATE> <PROMOTION IF APPLICABLE>' - to move a piece from the starting coordinate to the ending coordinate for example a2 a3\nValid Promotions: 'queen','rook','bishop','knight'\nType: 'resign' - to resign from the game\nType: 'possible <COORDINATES OF PIECE>' - to highlight all the possible moves for the piece in that square\nType: 'help' - to find out what you can do");
            } else if (words[0].equals("redraw") && words.length == 1) {
                printer.printBoard(ws.curGame.getBoard(), color, null);
            } else if (words[0].equals("leave") && words.length == 1) {
                ws.send(gson.toJson(new Leave(auth, gameID)));
                return;
            } else if (words[0].equals("resign") && words.length == 1) {
                System.out.printf("\nAre you sure you want to resign? [type 'yes' to confirm anything else will cancel]");
                Scanner scan = new Scanner(System.in);
                String word = scanner.nextLine();
                String[] ans = line.split(" ");
                if(ans[0].equals("yes")){
                    ws.send(gson.toJson(new Resign(auth, gameID)));
                }else{
                    System.out.println("never give up!! that's the attitude!!");
                }
            } else if (words[0].equals("move") && (words.length == 3 || words.length == 4)){
                ChessPiece.PieceType promo;
                if(words.length == 4){
                    String piece = words[3];
                    if(piece.equals("rook")){
                        promo = ChessPiece.PieceType.ROOK;
                    } else if (piece.equals("queen")) {
                        promo = ChessPiece.PieceType.QUEEN;
                    }else if (piece.equals("bishop")) {
                        promo = ChessPiece.PieceType.BISHOP;
                    }else if (piece.equals("knight")) {
                        promo = ChessPiece.PieceType.KNIGHT;
                    }else{
                        throw new Exception("Invalid promotion piece");
                    }
                }else{
                    promo = null;
                }
                ChessMove move = new ChessMove(stringToPosition(words[1]), stringToPosition(words[2]), promo);
                ws.send(gson.toJson(new MakeMove(auth, gameID, move)));
            } else if (words[0].equals("possible") && words.length == 2) {
                Collection<ChessMove> valid = ws.curGame.validMoves(stringToPosition(words[1]));
                printer.printBoard(ws.curGame.getBoard(), color, valid);
            }
            }catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public ChessPosition stringToPosition(String cord) throws Exception {
        char[] chars = cord.toCharArray();
        int row;
        int col;
        if (chars.length == 2){
            if(chars[0] == 'a'){
                col = 1;
            } else if (chars[0] == 'b') {
                col = 2;
            }else if (chars[0] == 'c') {
                col = 3;
            }else if (chars[0] == 'd') {
                col = 4;
            }else if (chars[0] == 'e') {
                col = 5;
            }else if (chars[0] == 'f') {
                col = 6;
            }else if (chars[0] == 'g') {
                col = 7;
            }else if (chars[0] == 'h') {
                col = 8;
            }else{
                throw new Exception("Bad Coordinate");
            }
            if(chars[1] <= 8 && chars[1] > 0){
                row = chars[1];
            }else{
                throw new Exception("Bad Coordinate");
            }
            return new ChessPosition(row, col);
        }else{
            throw new Exception("Bad Coordinate");
        }
    }
}
