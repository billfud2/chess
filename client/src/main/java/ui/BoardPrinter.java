package ui;

import chess.*;
import recordsForReqAndRes.pieceString;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
    private static ChessBoard curBoard;
    private static ChessPosition start;
    private static Collection<ChessPosition> ends;


    public static void printBoard(ChessBoard board, ChessGame.TeamColor color, Collection<ChessMove> valid, ChessPosition start) {
        ends = new HashSet<>();
        if (valid != null){
            for( ChessMove move : valid){
                ends.add(move.getEndPosition());
            }
        }
        curBoard = board;
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print("\033[H\033[2J");
        out.flush();
        out.print("\n");
        if(color == ChessGame.TeamColor.BLACK){
            drawBoarderRow(out, b1Headers);
            b1DrawChessBoard(out);
            drawBoarderRow(out, b1Headers);
        }else {
            drawBoarderRow(out, b2Headers);
            b2DrawChessBoard(out);
            drawBoarderRow(out, b2Headers);
        }
        out.print("\u001B[49m");
        out.print("\u001B[0m");
    }
//    private static void drawDivider(PrintStream out){
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 2; ++boardCol) {
//            out.print(SET_BG_COLOR_BLACK);
//            out.print(SET_TEXT_COLOR_BLACK);
//            out.print(EMPTY);
//            out.print(" ");
//            out.print(EMPTY);
//        }
//        out.print(SET_BG_COLOR_DARK_GREY);
//        out.println();
//    }

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
            ChessPosition pos = new ChessPosition(row, boardCol);
            pieceString piece = getPiece(row, boardCol);
            if(color == 0 ){
                String background = SET_BG_COLOR_WHITE;
                if(!ends.isEmpty() && ends.contains(pos)){
                    background = SET_BG_COLOR_GREEN;
                } else if (start != null && start.equals(pos)) {
                    background = SET_BG_COLOR_YELLOW;
                }
                drawSquare(out, piece.s(),background , piece.color());
                color = 1;
            }else{
                String background = SET_BG_COLOR_BLACK;
                if(!ends.isEmpty() && ends.contains(pos)){
                    background = SET_BG_COLOR_DARK_GREEN;
                } else if (start != null && start.equals(pos)) {
                    background = SET_BG_COLOR_YELLOW;
                }
                drawSquare(out, piece.s(),background , piece.color());
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
        ChessPiece piece = curBoard.getPiece(new ChessPosition(row, col));
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
