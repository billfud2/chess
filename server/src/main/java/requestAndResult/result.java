package requestAndResult;

import com.google.gson.Gson;
import model.AuthData;

public class result {
    public String authResult(AuthData authData){
        Gson parse = new Gson();
        return parse.toJson(authData);
    }
}
