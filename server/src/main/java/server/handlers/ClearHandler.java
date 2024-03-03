package server.handlers;

import com.google.gson.Gson;
import service.ClearService;
import spark.Route;

public class ClearHandler {
    static ClearHandler instance;
    Gson gson = new Gson();
    ClearService serve = new ClearService();
    public String handleClear(){
        serve.clearAll();
        return gson.toJson(null);
    }
    public static ClearHandler getInstance() {
        if (instance == null) {
            instance = new ClearHandler();
        }
        return instance;
    }
}
