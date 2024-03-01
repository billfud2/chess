package server;

import dataAccess.DataAccess;
import server.handler.UserHandler;
import spark.*;

public class Server {
    DataAccess dB = new DataAccess();
    UserHandler userHandler = new UserHandler(dB);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.post("/user", (req, res) ->
                (userHandler).registerRequest(req, res));


        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
