package server.handlers;

import com.google.gson.Gson;
import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class GameHandler {
    static GameHandler instance;
    Gson gson = new Gson();
    GameService serve = new GameService();

    public GameHandler() throws DataAccessException {
    }

    public static GameHandler getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }
    public String handleCreateGame(Request req, Response res) throws DataAccessException {
        try {
            CreateGameRequest request = (CreateGameRequest) gson.fromJson(req.body(), CreateGameRequest.class);
            String authToken= req.headers("authorization");
            CreateGameResult result = serve.createGame(request.gameName(), authToken);
            return gson.toJson(result);
        }catch (BadRequestException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(400);
            return body;
        }catch (DataAccessException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(401);
            return body;
        }catch (Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
    public String handleJoinGame(Request req, Response res) throws DataAccessException {
        try {
            JoinGameRequest request = (JoinGameRequest) gson.fromJson(req.body(), JoinGameRequest.class);
            String authToken = req.headers("authorization");
            serve.joinGame(request,authToken);
            return gson.toJson(null);
        }catch (BadRequestException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(400);
            return body;
        }catch (DataAccessException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(401);
            return body;
        }catch (AlreadyTakenException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(403);
            return body;
        }catch (Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
    public String handleListGames(Request req, Response res) throws DataAccessException {
        try {
            String request = req.headers("authorization");
            ListGamesResult result = serve.listGames(request);
            return gson.toJson(result);
        }catch (DataAccessException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(401);
            return body;
        }catch (Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
}
