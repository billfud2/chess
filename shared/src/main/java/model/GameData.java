package model;

import chess.ChessGame;

public class GameData {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    ChessGame game;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.game = game;
        this.blackUsername = blackUsername;
        this.whiteUsername = whiteUsername;
    }
}
