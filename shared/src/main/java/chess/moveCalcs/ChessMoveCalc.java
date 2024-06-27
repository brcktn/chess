package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class ChessMoveCalc {
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

    public static void diagonalMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition currPosition) {
        // NE moves
        for (int i = currPosition.getRow() + 1, j = currPosition.getColumn() + 1; i <= 8 && j <= 8; i++, j++) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // NW moves
        for (int i = currPosition.getRow() + 1, j = currPosition.getColumn() - 1; i <= 8 && j >= 1; i++, j--) {
            if (ChessMoveCalc.addToMovesHelper(moves, board, currPosition, i, j)) break;
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


