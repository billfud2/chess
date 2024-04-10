package webSocketMessages.serverMessages;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

class LoadGame extends ServerMessage {

    public LoadGame(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
    }

}