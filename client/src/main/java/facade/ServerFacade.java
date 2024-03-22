package facade;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import recordsForReqAndRes.CreateGameResult;
import recordsForReqAndRes.JoinGameRequest;
import recordsForReqAndRes.ListGamesResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerFacade {
    static ServerFacade instance;
    static String websiteURL;
    static Gson gson = new Gson();
    public static String authLog;


    public ServerFacade() {
    }
    public static ServerFacade getInstance(String URL, int port){
        if (instance == null) {
            instance = new ServerFacade();
            websiteURL = "http://" + URL + ":" + Integer.toString(port);
        }
        return instance;
    }

    public static void register(UserData user) throws IOException {
        URL url = new URL(websiteURL + "/user");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "json");
        connection.setDoOutput(true);
        connection.connect();
        try(OutputStream reqBody = connection.getOutputStream();){
            reqBody.write(gson.toJson(user).getBytes("UTF-8"));
        }
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)  {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }

    }
    public static void login(UserData user) throws IOException{
        URL url = new URL(websiteURL + "/session");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "json");
        connection.setDoOutput(true);
        connection.connect();
        try(OutputStream reqBody = connection.getOutputStream();){
            reqBody.write(gson.toJson(user).getBytes("UTF-8"));
            reqBody.close();
        }
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            authLog = ((AuthData) gson.fromJson(response.toString(), AuthData.class)).authToken();
        }
        else {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }
    }
    public static void logout() throws IOException{
        URL url = new URL(websiteURL + "/session");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", authLog);
        connection.setDoOutput(true);
        connection.connect();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }
    }
    public static ListGamesResult listGames()throws IOException{
        URL url = new URL(websiteURL + "/game");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authLog);
        connection.setDoOutput(true);
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream resBody = connection.getInputStream();
            ListGamesResult games = (ListGamesResult) gson.fromJson((new String(resBody.readAllBytes(), "UTF-8")), ListGamesResult.class);
            return games;
        }
        else {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }
    }
    public static int createGame(String gameName)throws IOException{
        URL url = new URL(websiteURL + "/game");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", authLog);
        connection.setRequestProperty("Content-Type", "json");
        connection.setDoOutput(true);
        connection.connect();
        try(OutputStream reqBody = connection.getOutputStream();){
            reqBody.write(gson.toJson(gameName).getBytes("UTF-8"));
            reqBody.close();
        }
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream resBody = connection.getInputStream();
            CreateGameResult result = (CreateGameResult) gson.fromJson((new String(resBody.readAllBytes(), "UTF-8")), CreateGameResult.class);
            return result.gameID();
        }
        else {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }
    }
    public static void joinGame(JoinGameRequest join) throws IOException{
        URL url = new URL(websiteURL + "/game");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", authLog);
        connection.setRequestProperty("Content-Type", "json");
        connection.setDoOutput(true);
        connection.connect();
        try(OutputStream reqBody = connection.getOutputStream();){
            reqBody.write(gson.toJson(join).getBytes("UTF-8"));
            reqBody.close();
        }
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            InputStream resBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            String Error = new String(resBody.readAllBytes(), "UTF-8");
            throw new IOException(Error);
        }
    }
}
