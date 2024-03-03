package server;

import server.handlers.ClearHandler;
import server.handlers.GameHandler;
import server.handlers.UserHandler;
import spark.*;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
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

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
