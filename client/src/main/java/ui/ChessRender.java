package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessRender {

    private static final String NEW_LINE = SET_BG_COLOR_BLACK + "\n";

    /**
     * @param chessBoard board to render
     * @return string which when printed looks like a chess board
     */
    public static String render(ChessBoard chessBoard) {
        StringBuilder builder = new StringBuilder();

        builder.append(SET_BG_COLOR_MAGENTA + SET_TEXT_COLOR_BLACK + "    H  G  F  E  D  C  B  A    ");
        builder.append(SET_BG_COLOR_BLACK + NEW_LINE);
        for (int i = 8; i >= 1; i--) {
            renderLine(i, builder, chessBoard);
        }
        builder.append(SET_BG_COLOR_MAGENTA + SET_TEXT_COLOR_BLACK + "    H  G  F  E  D  C  B  A    ");

        return builder.toString();
    }

    private static void renderLine(int row, StringBuilder builder, ChessBoard chessBoard) {
        builder.append(SET_BG_COLOR_MAGENTA + SET_TEXT_COLOR_BLACK + " ");
        builder.append(row);
        builder.append(" ");
        for (int i = 1; i <= 8; i++) {
            renderSquare(row, i, builder, chessBoard);
        }
        builder.append(SET_BG_COLOR_MAGENTA + SET_TEXT_COLOR_BLACK + " ");
        builder.append(row);
        builder.append(" " + NEW_LINE);
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
        builder.append(squareColor);
        builder.append(pieceColor);
        builder.append(" ");
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
