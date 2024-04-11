package ui;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import facade.WSClient;
import webSocketMessages.userCommands.*;

import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class GameplayUI {
    public BoardPrinter printer = new BoardPrinter();
    static Gson gson = new Gson();
    

    public void run(ChessGame.TeamColor color, WSClient ws, int gameID, String auth) {
        try {
            while (true) {
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
                    System.out.println("Type:'redraw' - to redraw the chessboard \nType: 'leave' - to leave the game\nType: 'move <STARTING COORDINATE> <ENDING COORDINATE>' - to move a piece from the starting coordinate to the ending coordinate\nType: 'resign' - to resign from the game\nType: 'possible <COORDINATES OF PIECE>' - to highlight all the possible moves for the piece in that square\nType: 'help' - to find out what you can do");
                } else if (words[0].equals("redraw") && words.length == 1) {
                    printer.printBoard(ws.curBoard, color);
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
                        System.out.println("never give up!! that's the attitude");
                    }
                }
            }
        }catch(Exception e){
            
        }
    }
}
