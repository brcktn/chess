package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Some functions and helper functions for returning
 * valid moves for pieces on the board.
 */
public class ChessMoveCalc {
    /**
     * Calculates all the positions a chess piece can move to.
     * Calls correct function based on piece type
     *
     * @return Collection of valid moves
     */
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (board.getPiece(myPosition).getPieceType()) {
            case KING -> KingMovesCalc.kingMoves(board, myPosition);
            case QUEEN -> QueenMovesCalc.queenMoves(board, myPosition);
            case BISHOP -> BishopMoveCalc.bishopMoves(board, myPosition);
            case KNIGHT -> KnightMovesCalc.knightMoves(board, myPosition);
            case ROOK -> RookMovesCalc.rookMoves(board, myPosition);
            case PAWN -> PawnMovesCalc.pawnMoves(board, myPosition);
        };
    }

    /**
     * Adds a piece to the collection of moves if the
     * square to be moved to is either null or a piece
     * of the opposite team
     *
     * @param moves collection to be added to
     * @param currPosition ChessPosition of where the piece currently is
     * @param i row of where to be moved to
     * @param j column of where to be moved to
     */
    public static void addIfAllowed(Collection<ChessMove> moves, ChessBoard board, ChessPosition currPosition, int i, int j) {
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            ChessPosition newPosition = new ChessPosition(i, j);
            ChessPiece blockingPiece = board.getPiece(newPosition);
            ChessPiece currPiece = board.getPiece(currPosition);
            if (blockingPiece == null || currPiece.getTeamColor() != blockingPiece.getTeamColor()) {
                moves.add(new ChessMove(currPosition, newPosition, null));
            }
        }
    }

    /**
     * Returns false if square is empty, otherwise true.
     * If square is empty or of the oposite color, adds
     * move to moveList
     *
     * @param moveList List of moves to be added to
     * @param myPosition current position
     * @param i row to move to
     * @param j column to move to
     * @return bool representing if the piece is stopped by a particular square
     */
    public static boolean addToMovesHelper(ArrayList<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, int i, int j) {
        ChessPosition newPosition = new ChessPosition(i, j);
        ChessPiece blockingPiece = board.getPiece(newPosition);
        ChessPiece currentPiece = board.getPiece(myPosition);

        if (board.getPiece(newPosition) != null) {
            if (blockingPiece.getTeamColor() != currentPiece.getTeamColor()) {
                moveList.add(new ChessMove(myPosition, newPosition, null));
            }
            return true;
        }
        moveList.add(new ChessMove(myPosition, newPosition, null));
        return false;
    }

    /**
     * Adds all possible moves in the diagonal directions,
     * e.g. for bishop and queen
     *
     * @param moves List of moves to be added to
     * @param currPosition Current position on board
     */
    public static void diagonalMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition currPosition) {
        // NE moves
        for (int i = currPosition.getRow() + 1, j = currPosition.getColumn() + 1; i <= 8 && j <= 8; i++, j++) {
            if (addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // NW moves
        for (int i = currPosition.getRow() + 1, j = currPosition.getColumn() - 1; i <= 8 && j >= 1; i++, j--) {
            if (addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // SE moves
        for (int i = currPosition.getRow() - 1, j = currPosition.getColumn() + 1; i >= 1 && j <= 8; i--, j++) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // SW moves
        for (int i = currPosition.getRow() - 1, j = currPosition.getColumn() - 1; i >= 1 && j >= 1; i--, j--) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }
    }

    /**
     * adds all possible moves in horizonal and veritical directions
     * e.g. for rook and queen
     *
     * @param moves List of moves to add to
     * @param currPosition Current position on board
     */
    public static void straightMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition currPosition) {
        // N moves
        for (int i = currPosition.getRow() + 1, j = currPosition.getColumn(); i <= 8; i++) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // S moves
        for (int i = currPosition.getRow() - 1, j = currPosition.getColumn(); i >= 1; i--) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // E moves
        for (int i = currPosition.getRow(), j = currPosition.getColumn() + 1; j <= 8; j++) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // W moves
        for (int i = currPosition.getRow(), j = currPosition.getColumn() - 1; j >= 1; j--) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }
    }

}


