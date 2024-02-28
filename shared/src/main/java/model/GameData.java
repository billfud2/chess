package model;

import chess.ChessGame;

import java.util.Objects;

public class GameData {
    public ChessGame game;
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.game = game;
        this.blackUsername = blackUsername;
        this.whiteUsername = whiteUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return gameID == gameData.gameID && Objects.equals(whiteUsername, gameData.whiteUsername) && Objects.equals(blackUsername, gameData.blackUsername) && Objects.equals(gameName, gameData.gameName) && Objects.equals(game, gameData.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public String toString() {
        return "GameData{" +
                "gameID=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", gameName='" + gameName + '\'' +
                ", game=" + game +
                '}';
    }
}
