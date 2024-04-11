package webSocketMessages.serverMessages;

import chess.ChessGame;

public class Error extends ServerMessage {
    public String errorMessage ;
    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

}