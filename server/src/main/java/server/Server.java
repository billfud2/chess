package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AccessAuthData;
import dataAccess.AccessGameData;
import dataAccess.AccessUserData;
import dataAccess.DataAccessException;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import requestAndResult.JoinGameRequest;
import server.handlers.ClearHandler;
import server.handlers.GameHandler;
import server.handlers.UserHandler;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import service.GameService;
import spark.*;
import webSocketMessages.serverMessages.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@WebSocket
public class Server {
    static Gson gson = new Gson();
    static Set<Session> sessions = new HashSet<>();
    static GameService gameServ;


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
        try {
            UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
            if (command.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
                JoinPlayer join = gson.fromJson(message, JoinPlayer.class);
                sessions.add(session);
                gameServ = new GameService();
                gameServ.joinGame(new JoinGameRequest(join.color, join.gameID), join.getAuthString());
                session.getRemote().sendString(gson.toJson(new LoadGame(AccessGameData.getGame(join.gameID).game())));
                sendOther(AccessAuthData.getAuth(join.getAuthString()).username() + " joined as " + join.color, session);
            } else if (command.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
                JoinObserver obser = gson.fromJson(message, JoinObserver.class);
                sessions.add(session);
                gameServ = new GameService();
                gameServ.joinGame(new JoinGameRequest(null, obser.gameID), obser.getAuthString());
                session.getRemote().sendString(gson.toJson(new LoadGame(AccessGameData.getGame(obser.gameID).game())));
                sendOther(AccessAuthData.getAuth(obser.getAuthString()).username() + " joined as an observer", session);
            } else if (command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
                MakeMove make = gson.fromJson(message, MakeMove.class);
                GameData data = AccessGameData.getGame(make.gameID);
                ChessGame game = data.game();
                if (game.isOver){
                    session.getRemote().sendString(gson.toJson(new Error("game has ended no moves can be made")));
                }else {
                    game.makeMove(make.move);
                    AccessGameData.updateGame(make.gameID, gson.toJson(game));
                    sendOther(AccessAuthData.getAuth(make.getAuthString()).username() + " moved " + make.move.getStartPosition().toString() + " -> " + make.move.getEndPosition().toString(), session);
                    sendGameAll(AccessGameData.getGame(make.gameID).game());
                    if (game.isInCheckmate(ChessGame.TeamColor.WHITE)){
                        sendOther(data.whiteUsername() + " is in checkmate " + data.blackUsername() + " wins!!!", null);
                    } else if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                        sendOther(data.blackUsername() + " is in checkmate " + data.whiteUsername() + " wins!!!", null);
                    }
                    if (game.isInCheck(ChessGame.TeamColor.WHITE)){
                        sendOther(data.whiteUsername() + " is in check", null);
                    } else if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
                        sendOther(data.blackUsername() + " is in check", null);
                    }
                }
            } else if (command.getCommandType() == UserGameCommand.CommandType.LEAVE) {
                Leave lev = gson.fromJson(message, Leave.class);
                String name = AccessAuthData.getAuth(lev.getAuthString()).username();
                AccessGameData.removePlayer(lev.gameID, name);
                sendOther(name + " left", session);
                sessions.remove(session);
                session.close();
            } else if (command.getCommandType() == UserGameCommand.CommandType.RESIGN) {
                Resign res = gson.fromJson(message, Resign.class);
                ChessGame game = AccessGameData.getGame(res.gameID).game();
                game.isOver = true;
                AccessGameData.updateGame(res.gameID, gson.toJson(game));
                String name = AccessAuthData.getAuth(res.getAuthString()).username();
                sendOther(name + " resigned the game is over", null);
            }
        }catch(Exception e){
            session.getRemote().sendString(gson.toJson(new Error(e.getMessage())));
        }
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private void sendOther(String message, Session root) throws IOException {
        for (Session session : sessions) {
            if ((root == null) || (!session.equals(root))){
            session.getRemote().sendString(gson.toJson(new Notification(message)));
            }
        }
    }
    private void sendGameAll(ChessGame game) throws IOException {
        for (Session session : sessions) {
            session.getRemote().sendString(gson.toJson(new LoadGame(game)));
        }
    }

}