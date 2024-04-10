package webSocketMessages.userCommands;

import chess.ChessGame;

class JoinPlayer extends UserGameCommand {
    static CommandType type = CommandType.JOIN_PLAYER;
    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor color) {
        super(authToken);
    }

}