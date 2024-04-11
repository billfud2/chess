package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import recordsForReqAndRes.pieceString;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class BoardPrinter {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String h = "h";
    private static final String g = "g";
    private static final String f = "f";
    private static final String e = "e";
    private static final String d = "d";
    private static final String c = "c";
    private static final String b = "b";
    private static final String a = "a";
    private static final String whiteColor = SET_TEXT_COLOR_RED;
    private static final String blackColor = SET_TEXT_COLOR_BLUE;

    static String[] b1Headers = {h, g, f, e ,d, c, b, a};
    static String[] b2Headers = {a, b, c, d, e, f, g, h};


    private static final String EMPTY = " ";
    private static ChessBoard board;




    public static void printBoard(ChessBoard board) {
        board = board;
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawBoarderRow(out, b1Headers);
        b1DrawChessBoard(out);
        drawBoarderRow(out, b1Headers);
        drawDivider(out);
        drawBoarderRow(out, b2Headers);
        b2DrawChessBoard(out);
        drawBoarderRow(out, b2Headers);
    }
    private static void drawDivider(PrintStream out){
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 2; ++boardCol) {
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(EMPTY);
            out.print(" ");
            out.print(EMPTY);
        }
        out.print(SET_BG_COLOR_DARK_GREY);
        out.println();
    }

    private static void drawBoarderRow(PrintStream out, String[] headers) {
        drawHeader(out, " ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        drawHeader(out, " ");
        out.print(SET_BG_COLOR_DARK_GREY);
        out.println();

    }
    private static void drawHeader(PrintStream out, String headerText) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(EMPTY);
        out.print(headerText);
        out.print(EMPTY);
    }
    private static void b1DrawChessBoard(PrintStream out) {
        int color = 0;// 0 means White
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            for (int boardCol = BOARD_SIZE_IN_SQUARES + 1 ; boardCol >= 0; --boardCol) {
                color = drawRowSquares(out, boardRow+1, boardCol, color);
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.println();
            if(color == 0){
                color = 1;
            }
            else{
                color = 0;
            }

        }
    }
    private static void b2DrawChessBoard(PrintStream out) {
        int color = 0;// 0 means White
        for (int boardRow = BOARD_SIZE_IN_SQUARES - 1; boardRow >= 0; --boardRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 2; ++boardCol) {
                color = drawRowSquares(out, boardRow+1, boardCol, color);
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.println();
            if(color == 0){
                color = 1;
            }
            else{
                color = 0;
            }
        }
    }

    private static int drawRowSquares(PrintStream out, int row, int boardCol, int color){
            if (boardCol == 0 || boardCol == 9){
                drawSquare(out,Integer.toString(row), SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLACK);
            }
            else{
                pieceString piece = getPiece(row, boardCol);
                if(color == 0){
                    drawSquare(out, piece.s(),SET_BG_COLOR_WHITE , piece.color());
                    color = 1;
                }else{
                    drawSquare(out, piece.s(),SET_BG_COLOR_BLACK , piece.color());
                    color = 0;
                }
            }
            return color;
    }
    private static void drawSquare(PrintStream out, String text, String backCol, String textCol){
        out.print(backCol);
        out.print(textCol);
        out.print(EMPTY);
        out.print(text);
        out.print(EMPTY);
        }
    private static pieceString getPiece(int row, int col){
        ChessPiece piece = board.getPiece(new ChessPosition(row, col));
        pieceString player = new pieceString(" ",SET_TEXT_COLOR_BLACK);
        if (piece != null) {
            ChessGame.TeamColor team = piece.getTeamColor();
            ChessPiece.PieceType type = piece.getPieceType();
            if (team.equals(ChessGame.TeamColor.WHITE)) {
                if (type == ChessPiece.PieceType.ROOK) {
                    player = new pieceString("R", whiteColor);
                } else if (type == ChessPiece.PieceType.KNIGHT) {
                    player = new pieceString("N", whiteColor);
                } else if (type == ChessPiece.PieceType.BISHOP) {
                    player = new pieceString("B", whiteColor);
                } else if (type == ChessPiece.PieceType.KING) {
                    player = new pieceString("K", whiteColor);
                } else if (type == ChessPiece.PieceType.QUEEN) {
                    player = new pieceString("Q", whiteColor);
                } else if (type == ChessPiece.PieceType.PAWN) {
                    player = new pieceString("P", whiteColor);
                }
            } else {
                if (type.equals(ChessPiece.PieceType.ROOK)) {
                    player = new pieceString("R", blackColor);
                } else if (type == ChessPiece.PieceType.KNIGHT) {
                    player = new pieceString("N", blackColor);
                } else if (type == ChessPiece.PieceType.BISHOP) {
                    player = new pieceString("B", blackColor);
                } else if (type == ChessPiece.PieceType.KING) {
                    player = new pieceString("K", blackColor);
                } else if (type == ChessPiece.PieceType.QUEEN) {
                    player = new pieceString("Q", blackColor);
                } else if (type == ChessPiece.PieceType.PAWN) {
                    player = new pieceString("P", blackColor);
                }
            }
        }
        return player;
    }
}
