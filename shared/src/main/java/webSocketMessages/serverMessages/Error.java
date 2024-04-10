package webSocketMessages.serverMessages;

import chess.ChessGame;

class Error extends ServerMessage {

    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);
    }

}