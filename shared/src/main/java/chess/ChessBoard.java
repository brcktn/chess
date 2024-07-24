package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] boardGrid;

    public ChessBoard() {
        boardGrid = new ChessPiece[8][8];
    }

    public ChessBoard(ChessBoard other) {
        boardGrid = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(other.boardGrid[i], 0, boardGrid[i], 0, 8);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     * if there is a piece already there, it is replaced
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        boardGrid[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Removes a chess piece from the board
     *
     * @param position where to remove piece from
     */
    public void removePiece(ChessPosition position) {
        boardGrid[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return boardGrid[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // resets middle 4 rows
        for (int i = 3; i <= 6; i++) {
            for (int j = 1; j <= 8; j++) {
                removePiece(new ChessPosition(i, j));
            }
        }
        // adds white pawns
        for (int j = 1; j <= 8; j++) {
            addPiece(new ChessPosition(2, j), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }
        // adds black pawns
        for (int j = 1; j <= 8; j++) {
            addPiece(new ChessPosition(7, j), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        // adds rest of white pieces
        addPiece(new ChessPosition(1, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(1, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(1, 6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        // adds rest of black pieces
        addPiece(new ChessPosition(8, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(boardGrid, that.boardGrid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(boardGrid);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            builder.append("|");
            for (int j = 0; j < 8; j++) {
                if (boardGrid[i][j] == null) {
                    builder.append(" ");
                } else {
                    builder.append(boardGrid[i][j]);
                }
                builder.append("|");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
