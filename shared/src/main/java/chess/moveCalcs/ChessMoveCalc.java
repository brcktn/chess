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
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> BishopMoveCalc.bishopMoves(board, myPosition);
            case KNIGHT -> KnightMovesCalc.knightMoves(board, myPosition);
            case ROOK -> rookMoves(board, myPosition);
            case PAWN -> pawnMoves(board, myPosition);
        };
    }

    private static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
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
}


