package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
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
        try (var preparedStatement = conn.prepareStatement("TURNCATE TABLE game")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
    static public Integer createGame(String gameName) throws BadRequestException, DataAccessException {
        if (gameName != null) {
            ChessGame game = new ChessGame();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, game) VALUES(?, ?, ?)")) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, gson.toJson(game));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
        throw new BadRequestException("bad request");

    }

    static public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> games = null;
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
            return new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), gson.fromJson(rs.getString("game"), ChessGame.class));
        } catch (SQLException e) {
            throw new DataAccessException("unauthorized");
        }
    }

    static public void addBlackPlayer(String username, int gameID) throws AlreadyTakenException, DataAccessException {
        if(getGame(gameID).blackUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET blackUsername=? WHERE gameID=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("unauthorized");
        }
    }
    public static void addWhitePlayer(String username, int gameID) throws AlreadyTakenException, BadRequestException, DataAccessException {
        if(getGame(gameID).whiteUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername=? WHERE gameID=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("unauthorized");
        }
    }
}
