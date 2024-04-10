package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

class MakeMove extends UserGameCommand {
    static CommandType type = CommandType.MAKE_MOVE;
    public MakeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);
    }

}