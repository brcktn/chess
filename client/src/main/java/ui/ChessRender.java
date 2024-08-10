package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessRender {

    private static final String NEW_LINE = SET_BG_COLOR_DARK_GREY + "\n";
    private static final String BORDER_COLOR = SET_BG_COLOR_LIGHT_GREY;
    private static final String NUMBER_COLOR = SET_TEXT_COLOR_BLACK;
    private static final String LIGHT_SQUARE_COLOR = SET_BG_COLOR_WHITE;
    private static final String DARK_SQUARE_COLOR = SET_BG_COLOR_BLACK;
    private static final String LIGHT_PIECE_COLOR = SET_TEXT_COLOR_BLUE;
    private static final String DARK_PIECE_COLOR = SET_TEXT_COLOR_RED;

    /**
     * @param chessBoard board to render
     * @return string which when printed looks like a chess board
     */
    public static String render(ChessBoard chessBoard) {
        StringBuilder builder = new StringBuilder();

        builder.append(BORDER_COLOR + NUMBER_COLOR + "    H  G  F  E  D  C  B  A    ");
        builder.append(NEW_LINE);
        for (int i = 8; i >= 1; i--) {
            renderLine(i, builder, chessBoard, false);
        }
        builder.append(BORDER_COLOR + NUMBER_COLOR + "    H  G  F  E  D  C  B  A    ");
        builder.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE);

        return builder.toString();
    }

    public static String render(ChessBoard chessBoard, ChessGame.TeamColor renderTeam) {
        if (renderTeam == ChessGame.TeamColor.WHITE) {
            return render(chessBoard);
        }

        StringBuilder builder = new StringBuilder();

        builder.append(BORDER_COLOR + NUMBER_COLOR + "    A  B  C  D  E  F  G  H    ");
        builder.append(NEW_LINE);
        for (int i = 1; i <= 8; i++) {
            renderLine(i, builder, chessBoard, true);
        }
        builder.append(BORDER_COLOR + NUMBER_COLOR + "    A  B  C  D  E  F  G  H    ");
        builder.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE);

        return builder.toString();
    }

    private static void renderLine(int row, StringBuilder builder, ChessBoard chessBoard, boolean reverseOrder) {
        builder.append(BORDER_COLOR + NUMBER_COLOR + " ");
        builder.append(row);
        builder.append(" ");
        if (reverseOrder) {
            for (int i = 8; i >= 1; i--) {
                renderSquare(row, i, builder, chessBoard);
            }
        } else {
            for (int i = 1; i <= 8; i++) {
                renderSquare(row, i, builder, chessBoard);
            }
        }
        builder.append(BORDER_COLOR + NUMBER_COLOR + " ");
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
            return LIGHT_SQUARE_COLOR;
        } else {
            return DARK_SQUARE_COLOR;
        }
    }

    private static String getPieceColor(int row, int col, ChessBoard chessBoard) {
        ChessPiece chessPiece = chessBoard.getPiece(new ChessPosition(row, col));
        if (chessPiece == null) {
            return "";
        }
        if (chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            return DARK_PIECE_COLOR;
        } else {
            return LIGHT_PIECE_COLOR;
        }
    }
}
