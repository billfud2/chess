package server.handler;

import dataAccess.DataAccess;
import model.UserData;
import requestAndResult.Requester;
import requestAndResult.Result;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {
    Requester request = new Requester();
    Result result = new Result();
    DataAccess dB;
    UserService userService;
    public UserHandler(DataAccess dB) {
        dB = dB;
        userService = new UserService(dB);
        this.request = request;
    }

    public Object registerRequest(Request req, Response res) {
        UserData user = request.requestUserData(req.body());
        res.body(result.authResult(userService.register(user)));
        return res;
    }
}
