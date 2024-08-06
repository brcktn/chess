package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import org.junit.jupiter.api.Test;

import static ui.EscapeSequences.*;

public class ChessRender {

    private static final String newLine = SET_BG_COLOR_BLACK + "\n";
    public static String render(ChessBoard chessBoard) {
        StringBuilder builder = new StringBuilder();

        builder.append(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + "    H  G  F  E  D  C  B  A    ");
        builder.append(SET_BG_COLOR_BLACK + newLine);
        for (int i = 8; i >= 1; i--) {
            renderLine(i, builder, chessBoard);
        }
        builder.append(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + "    H  G  F  E  D  C  B  A    ");

        return builder.toString();
    }

    private static void renderLine(int row, StringBuilder builder, ChessBoard chessBoard) {
        builder.append(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + " ");
        builder.append(String.valueOf(row));
        builder.append(" ");
        for (int i = 1; i <= 8; i++) {
            renderSquare(row, i, builder, chessBoard);
        }
        builder.append(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + " ");
        builder.append(String.valueOf(row));
        builder.append(" " + newLine);
    }

    private static void renderSquare(int row, int col, StringBuilder builder, ChessBoard chessBoard) {
        String squareColor = getSquareColor(row, col);
        String pieceColor = getPieceColor(row, col, chessBoard);
        ChessPiece chessPiece = chessBoard.getPiece(new ChessPosition(row, col));
        String pieceString;
        if (chessPiece == null) {
            pieceString = " ";
        } else {
            pieceString = chessPiece.toString();
        }
        builder.append(squareColor + pieceColor + " ");
        builder.append(pieceString);
        builder.append(" ");
    }

    private static String getSquareColor(int row, int col) {
        if ((row + col) % 2 == 1) {
            return SET_BG_COLOR_LIGHT_GREY;
        } else {
            return SET_BG_COLOR_DARK_GREEN;
        }
    }

    private static String getPieceColor(int row, int col, ChessBoard chessBoard) {
        ChessPiece chessPiece = chessBoard.getPiece(new ChessPosition(row, col));
        if (chessPiece == null) {
            return "";
        }
        if (chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            return SET_TEXT_COLOR_BLACK;
        } else {
            return SET_TEXT_COLOR_WHITE;
        }
    }
}
