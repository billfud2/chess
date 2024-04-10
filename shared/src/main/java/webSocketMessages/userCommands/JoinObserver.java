package webSocketMessages.userCommands;

import chess.ChessGame;

class JoinObserver extends UserGameCommand {
    static CommandType type = CommandType.JOIN_OBSERVER;
    public JoinObserver(String authToken, int gameID) {
        super(authToken);
    }

}