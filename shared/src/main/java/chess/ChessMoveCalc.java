package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessMoveCalc {
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (board.getPiece(myPosition).getPieceType()) {
            case KING -> kingMoves(board, myPosition);
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> bishopMoves(board, myPosition);
            case KNIGHT -> knightMoves(board, myPosition);
            case ROOK -> rookMoves(board, myPosition);
            case PAWN -> pawnMoves(board, myPosition);
        };
    }
    private static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        // NE moves
        for (int i = myPosition.getRow() + 1, j = myPosition.getColumn() + 1; i <= 8 && j <= 8; i++, j++) {
            if (addToMoves(board, myPosition, moves, i, j)) break;
        }

        // NW moves
        for (int i = myPosition.getRow() + 1, j = myPosition.getColumn() - 1; i <= 8 && j >= 1; i++, j--) {
            if (addToMoves(board, myPosition, moves, i, j)) break;
        }

        // SE moves
        for (int i = myPosition.getRow() - 1, j = myPosition.getColumn() + 1; i >= 1 && j <= 8; i--, j++) {
            if (addToMoves(board, myPosition, moves, i, j)) break;
        }

        // SW moves
        for (int i = myPosition.getRow() - 1, j = myPosition.getColumn() - 1; i >= 1 && j >= 1; i--, j--) {
            if (addToMoves(board, myPosition, moves, i, j)) break;
        }

        return moves;
        // throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static boolean addToMoves(ChessBoard board, ChessPosition myPosition, ArrayList<ChessMove> moves, int i, int j) {
        ChessPosition newPosition = new ChessPosition(i, j);
        ChessPiece blockingPiece = board.getPiece(newPosition);
        ChessPiece currentPiece = board.getPiece(myPosition);

        if (board.getPiece(newPosition) != null) {
            if (blockingPiece.getTeamColor() != currentPiece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            return true;
        }
        moves.add(new ChessMove(myPosition, newPosition, null));
        return false;
    }
}


