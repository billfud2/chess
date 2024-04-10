package webSocketMessages.userCommands;

import chess.ChessGame;

class Resign extends UserGameCommand {
    static CommandType type = CommandType.JOIN_PLAYER;
    public Resign(String authToken, int gameID) {
        super(authToken);
    }

}