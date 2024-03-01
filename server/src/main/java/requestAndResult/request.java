package requestAndResult;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

public class request {

    public UserData requestUserData(String userDataJSON){
        Gson parse = new Gson();
        return parse.fromJson(userDataJSON, UserData.class);
    }
    public AuthData requestAuthData(String authDataJSON){
        Gson parse = new Gson();
        return parse.fromJson(authDataJSON, AuthData.class);
    }
    public GameData requestGameData(String gameDataJSON){
        Gson parse = new Gson();
        return parse.fromJson(gameDataJSON, GameData.class);
    }

}
