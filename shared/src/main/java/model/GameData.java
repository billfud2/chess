package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername,String gameName, ChessGame game) {
    @Override
    public String toString() {
        return gameName + "(White = " + whiteUsername + ", Black = " + blackUsername + ")";
    }
}
