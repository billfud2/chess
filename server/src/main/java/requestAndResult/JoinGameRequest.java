package requestAndResult;

import chess.ChessGame;

public record JoinGameRequest(ChessGame.TeamColor playerColor, Integer gameID) {
}
