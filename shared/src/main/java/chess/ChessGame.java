package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor turn;
    ChessBoard board;
    public ChessGame() {
        this.turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessBoard bord = getBoard();
        ChessPiece piece = bord.getPiece(startPosition);
        return piece.pieceMoves(bord,startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessBoard bord = getBoard();
        ChessPiece piece = bord.getPiece(start);
        if (validMoves(start).contains(move) && piece.getTeamColor() == getTeamTurn()) {
            ChessPosition end = move.getEndPosition();
            bord.addPiece(end, piece);
            bord.removePiece(start);
            if (isInCheck(piece.getTeamColor())){
                ChessPosition pos = end;
                end = start;
                start = pos;
                bord.addPiece(end, piece);
                bord.removePiece(start);
                throw new InvalidMoveException("Not a Valid Move");
            }
            ChessPiece.PieceType promo = move.getPromotionPiece();
            if (promo != null){
                bord.addPiece(end ,new ChessPiece(piece.getTeamColor(), promo));
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KING){
                if (piece.getTeamColor() == TeamColor.WHITE){
                    bord.moveWKing(end);
                } else{
                    bord.moveBKing(end);
                }
            }
        } else{
            throw new InvalidMoveException("Not a Valid Move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessBoard bord = getBoard();
        ChessPosition king = bord.kingPos(teamColor);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i,j);
                ChessPiece piece = bord.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(bord,pos);
                    for (ChessMove move : moves){
                        if (move.getEndPosition() == king){
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)){

        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

}
