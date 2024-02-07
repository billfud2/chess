package chess;

import java.util.Collection;
import java.util.HashSet;

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
        ChessPiece piece = this.board.getPiece(startPosition);
        Collection<ChessMove> moves = piece.pieceMoves(this.board,startPosition);
        Collection<ChessMove> newMoves = new HashSet<>();
        for(ChessMove move : moves) {
            ChessPosition start = move.getStartPosition();
            ChessPosition end = move.getEndPosition();
            ChessPiece holder = this.board.getPiece(end);
            move(start, end);
            if(isInCheck(piece.getTeamColor())) {
                move(end, start);
                if (holder != null) {
                    this.board.addPiece(end, holder);
                }
                continue;
            }
            move(end, start);
            newMoves.add(move);
            if (holder != null) {
                this.board.addPiece(end, holder);
            }
        }
        return newMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece piece = this.board.getPiece(start);
        if (validMoves(start).contains(move) && piece.getTeamColor() == getTeamTurn()) {
            move(start, end);
//            System.out.println(this.board.getPiece(end).getTeamColor() + "," + this.board.getPiece(end).getPieceType() +" Moves: {" +  start.getRow() + "," + start.getColumn() + "} -> {" + end.getRow() + "," + end.getColumn() + "}");
            ChessPiece.PieceType promo = move.getPromotionPiece();
            if (promo != null){
                this.board.addPiece(end ,new ChessPiece(piece.getTeamColor(), promo));
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KING){
                if (piece.getTeamColor() == TeamColor.WHITE){
                    this.board.moveWKing(end);
                } else{
                    this.board.moveBKing(end);
                }
            }
        } else{
//            System.out.println(this.board.getPiece(start).getTeamColor() + "," + this.board.getPiece(start).getPieceType() +"Faild Moves: {" +  start.getRow() + "," + start.getColumn() + "} -> {" + end.getRow() + "," + end.getColumn() + "}");
            throw new InvalidMoveException("Not a Valid Move");
        }
        if (getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        else{
            setTeamTurn(TeamColor.WHITE);
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = this.board.kingPos(teamColor);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i,j);
                ChessPiece piece = this.board.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(this.board, pos);
                    for (ChessMove move : moves){
                        if (move.getEndPosition().equals(king)){
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
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition pos = new ChessPosition(i,j);
                    ChessPiece piece = this.board.getPiece(pos);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> moves = validMoves(pos);
                        if (!moves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition pos = new ChessPosition(i,j);
                    ChessPiece piece = this.board.getPiece(pos);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> moves = validMoves(pos);
                        if (!moves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
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
    
    private void move(ChessPosition start,ChessPosition end){
        ChessPiece piece = this.board.getPiece(start);
        this.board.addPiece(end, piece);
        this.board.removePiece(start);
    }

}
