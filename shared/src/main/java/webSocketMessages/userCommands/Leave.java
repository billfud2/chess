package webSocketMessages.userCommands;

import chess.ChessGame;

public class Leave extends UserGameCommand {
    public int gameID;
    public Leave(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }

}