package server.handlers;

import com.google.gson.Gson;
import service.ClearService;
import spark.Response;
import spark.Route;

import java.util.Map;

public class ClearHandler {
    static ClearHandler instance;
    Gson gson = new Gson();
    ClearService serve = new ClearService();
    public String handleClear(Response res){
        try {
            serve.clearAll();
            return gson.toJson(null);
        }catch(Exception e){
            var body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            res.status(500);
            return body;
        }
    }
    public static ClearHandler getInstance() {
        if (instance == null) {
            instance = new ClearHandler();
        }
        return instance;
    }
}
