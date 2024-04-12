package dataAccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AccessGameData {
    private static Connection conn;
    private static Gson gson = new Gson();
    static {
        try {
            conn = DatabaseManager.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AccessGameData() {
    }
    static public void clear() throws Exception {
        try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE game")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
    static public Integer createGame(String gameName) throws BadRequestException, DataAccessException {
        if (gameName != null) {
            ChessGame game = new ChessGame();
            ChessBoard bord = new ChessBoard();
            bord.resetBoard();
            game.setBoard(bord);
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, game) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, gson.toJson(game));
                preparedStatement.executeUpdate();
                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Get the value of the auto_increment column

                    return generatedKeys.getInt(1);
                } else{
                    throw new DataAccessException("game not created");
                }
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
        throw new BadRequestException("bad request");

    }

    static public Collection<GameData> listGames(String auth) throws DataAccessException {
        AccessAuthData.getAuth(auth);
        Collection<GameData> games = new HashSet<>() {
        };
        try (var preparedStatement = conn.prepareStatement("SELECT * FROM game")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    games.add(new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), gson.fromJson(rs.getString("game"), ChessGame.class)));
                }
                return games;
            }catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public GameData getGame(int gameID) throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("SELECT * FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            var rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), gson.fromJson(rs.getString("game"), ChessGame.class));
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public void updateGame(int gameID, String gameString) throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET game = ? WHERE gameID=?")) {
            preparedStatement.setString(1, gameString);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public void removePlayer(int gameID, String username) throws Exception {
        GameData data = getGame(gameID);
        String colString;
        if (data.whiteUsername() != null && data.whiteUsername().equals(username)){
            colString = "whiteUsername";
        }else if(data.blackUsername() != null && data.blackUsername().equals(username)){
            colString = "blackUsername";
        }else{
            return;
        }
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET "+ colString +" = NULL WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    static public void addBlackPlayer(String username, int gameID) throws AlreadyTakenException, DataAccessException, BadRequestException {
        if(getGame(gameID) == null){
            throw new BadRequestException("bad request");
        }
        if(getGame(gameID).blackUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET blackUsername=? WHERE gameID=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public static void addWhitePlayer(String username, int gameID) throws AlreadyTakenException, BadRequestException, DataAccessException {
        if(getGame(gameID) == null){
            throw new BadRequestException("bad request");
        }
        if(getGame(gameID).whiteUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername=? WHERE gameID=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
