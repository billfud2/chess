package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import facade.ServerFacade;
import facade.WSClient;
import model.GameData;
import recordsForReqAndRes.JoinGameRequest;
import webSocketMessages.userCommands.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.PreloginUI.desPort;

public class PostloginUI {
    static Gson gson = new Gson();
    private ArrayList<GameData> games;
    WSClient ws;

    public boolean run(ServerFacade facade, String username){
        System.out.printf("You logged in as " + username + "!\nWelcome " + username);
        try {games = facade.listGames().games();
        } catch(Exception e){
            System.out.println("Sorry something went wrong");
        }
        while (true) {
            System.out.printf("\n[LOGGED_IN]>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            if (words[0].equals("create") && words.length >= 2){
                String name = "";
                int k = 0;
                for(String word : words) {
                    if (k == 0) {
                        k++;
                    } else {
                        name = name + word;
                    }
                }
                try {
                    facade.createGame(name);
                }catch(Exception e){
                    System.out.println("Sorry something went wrong");
                }
            }
            else if(words[0].equals("list")){
                try {
                    games = facade.listGames().games();
                    int i = 1;
                    for(GameData game : games){
                        System.out.println("\n" + i +  ". " + game);
                        i++;
                    }
                }catch(Exception e){
                    System.out.println("Sorry Something went wrong");
                }
            }
            else if(words[0].equals("join")&& words.length == 3){
                try {
                    int id = Integer.parseInt(words[1]) - 1;
                    if(id >= 0 && id <= games.size()) {
                        if (words[2].equals("WHITE")) {
                            facade.joinGame(new JoinGameRequest(WHITE, games.get(id).gameID()));
                            ws = new WSClient("localhost", desPort, WHITE);
                            ws.send(gson.toJson(new JoinPlayer(facade.authLog, games.get(id).gameID(), WHITE)));
                            Thread.sleep(100);
                            new GameplayUI().run(WHITE, ws, games.get(id).gameID(), facade.authLog);
                        } else if (words[2].equals("BLACK")) {
                            facade.joinGame(new JoinGameRequest(BLACK, games.get(id).gameID()));
                            ws = new WSClient("localhost", desPort, BLACK);
                            ws.send(gson.toJson(new JoinPlayer(facade.authLog, games.get(id).gameID(), BLACK)));
                            Thread.sleep(100);
                            new GameplayUI().run(BLACK, ws, games.get(id).gameID(), facade.authLog);
                        } else {
                            System.out.println("not a team color");
                        }
                    }
                    else{
                        System.out.println("not a valid game id try again");
                    }
                }catch(Exception e){
                    System.out.println("Sorry Something went wrong");
                }
            }
            else if(words[0].equals("observe")&& words.length == 2){
                try {
                    int id = Integer.parseInt(words[1]) - 1;
                    if(id >= 0 && id <= games.size()) {
                        facade.joinGame(new JoinGameRequest(null, games.get(id).gameID()));
                        ws = new WSClient("localhost", desPort, null);
                        ws.send(gson.toJson(new JoinObserver(facade.authLog, games.get(id).gameID())));
                        Thread.sleep(100);
                        new GameplayUI().run(null, ws, games.get(id).gameID(), facade.authLog);
                    }
                    else{
                        System.out.println("not a valid game id try again");
                    }
                }catch(Exception e){
                    System.out.println("Sorry Something went wrong");
                }
            }
            else if(words[0].equals("logout")){
                System.out.println("BYE!");
                return false;
            }
            else if(words[0].equals("quit")){
                System.out.println("BYE!");
                return true;
            }
            else if (words[0].equals("help")){
                System.out.println("Type:'create <NAME>' - to create GAME \nType: 'list' - to list all the games\nType: 'join <ID> [WHITE|BLACK]' - to join a game as white or black\nType: 'observe <ID>' - to observe a game\nType: 'logout' - to logout when you are done\nType: 'quit' - to stop playing chess\nType: 'help' - to find out what you can do");
            }
            else{
                System.out.println("INVALID COMMAND: try typing 'help' for valid commands");
            }

        }

    }
}
