package webSocketMessages.userCommands;

import chess.ChessGame;

class Leave extends UserGameCommand {
    static CommandType type = CommandType.JOIN_PLAYER;
    public Leave(String authToken, int gameID) {
        super(authToken);
    }

}