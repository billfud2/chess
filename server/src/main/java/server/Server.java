package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AccessAuthData;
import dataAccess.AccessGameData;
import dataAccess.AccessUserData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import server.handlers.ClearHandler;
import server.handlers.GameHandler;
import server.handlers.UserHandler;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.*;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Set;


@WebSocket
public class Server {
    static Gson gson = new Gson();
    static Set<Session> sessions;
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");
        Spark.webSocket("/connect", Server.class);
        Spark.post("/user",(req, res) -> (UserHandler.getInstance()).handleRegister(req, res));
        Spark.post("/session",(req, res) -> (UserHandler.getInstance()).handleLogin(req, res));
        Spark.delete("/session",(req, res) -> (UserHandler.getInstance()).handleLogout(req, res));
        Spark.delete("/db",(req, res) -> (ClearHandler.getInstance()).handleClear(res ));
        Spark.post("/game",(req, res) -> (GameHandler.getInstance()).handleCreateGame(req, res));
        Spark.put("/game",(req, res) -> (GameHandler.getInstance()).handleJoinGame(req, res));
        Spark.get("/game",(req, res) -> (GameHandler.getInstance()).handleListGames(req, res));
        Spark.awaitInitialization();
        return Spark.port();
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        if (command.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER){
            JoinPlayer join = gson.fromJson(message, JoinPlayer.class);
            sessions.add(session);
            session.getRemote().sendString(gson.toJson(new LoadGame(AccessGameData.getGame(join.gameID).game())));
            sendOther(gson.toJson(new Notification(AccessAuthData.getAuth(join.getAuthString()).username() + " joined as " + join.color)), session);
        } else if (command.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            JoinObserver obser = gson.fromJson(message, JoinObserver.class);
            sessions.add(session);
            session.getRemote().sendString(gson.toJson(new LoadGame(AccessGameData.getGame(obser.gameID).game())));
            sendOther(gson.toJson(new Notification(AccessAuthData.getAuth(obser.getAuthString()).username() + " joined as an observer")), session);
        } else if (command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
        } else if (command.getCommandType() == UserGameCommand.CommandType.LEAVE) {

        } else if (command.getCommandType() == UserGameCommand.CommandType.RESIGN) {

        }
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private void sendOther(String message, Session root) throws IOException {
        for (Session session : sessions) {
            if (!session.equals(root)){
            session.getRemote().sendString(gson.toJson(new Notification(message)));
            }
        }
    }

}