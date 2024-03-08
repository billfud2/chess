package server.handlers;

import com.google.gson.Gson;
import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

import service.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;


public class UserHandler {
    static UserHandler instance;
    Gson gson = new Gson();
    UserService serve = new UserService();

    public UserHandler() throws DataAccessException {
    }

    public String handleLogin(Request reqData, Response res) throws DataAccessException {
        try {
            UserData request = (UserData) gson.fromJson(reqData.body(), UserData.class);
            System.out.println(request);
            AuthData result = serve.login(request);
            return gson.toJson(result);
        }
        catch (DataAccessException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(401);
            return body;
        }catch (Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
    public String handleRegister(Request reqData, Response res) throws DataAccessException, BadRequestException {
        try {
            UserData request = (UserData) gson.fromJson(reqData.body(), UserData.class);
            AuthData result = serve.register(request);
            return gson.toJson(result);
        }catch (AlreadyTakenException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(403);
            return body;
        }catch (BadRequestException e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(400);
            return body;
        }catch (Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
    public String handleLogout(Request req, Response res ) throws DataAccessException {
        try {
            String request = req.headers("authorization");
            serve.logout(request);
            return gson.toJson(null);
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
    public static UserHandler getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new UserHandler();
        }
        return instance;
    }
}
