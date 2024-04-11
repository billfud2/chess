package facade;
import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import ui.BoardPrinter;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient extends Endpoint{

    static WSClient instance;
    static String websiteURL;
    static Gson gson = new Gson();
    public static String authLog;
    public Session session;
    public ChessGame curGame;

    public WSClient(String URL, int port, ChessGame.TeamColor color) throws Exception {
        websiteURL = "ws://" + URL + ":" + Integer.toString(port);
        URI uri = new URI(websiteURL + "/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage servMessage = gson.fromJson(message, ServerMessage.class);
                if (servMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
                    LoadGame load = gson.fromJson(message, LoadGame.class);
                    curGame = load.game;
                    BoardPrinter.printBoard(curGame.getBoard(), color, null, null);
                } else if (servMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                    Notification note = gson.fromJson(message, Notification.class);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(note.message);
                } else if (servMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                    Error err = gson.fromJson(message, Error.class);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Error: " + err.errorMessage);
                }
                printTurn();
            }
            public void onClose(){

            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void printTurn(){
        if(curGame.getTeamTurn() == ChessGame.TeamColor.WHITE){
            System.out.printf("\n[WHITE TURN]>>> ");
        } else if (curGame.getTeamTurn() == ChessGame.TeamColor.BLACK) {
            System.out.printf("\n[BLACK TURN]>>> ");
        }
    }


}
