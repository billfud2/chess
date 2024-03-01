package requestAndResult;

import com.google.gson.Gson;
import model.AuthData;

public class Result {
    public String authResult(AuthData authData){
        Gson parse = new Gson();
        return parse.toJson(authData);
    }
}
