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
            case KING -> kingMoves(board, myPosition);
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> BishopMoveCalc.bishopMoves(board, myPosition);
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



    private static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    public static boolean addToMoves(ArrayList<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, ArrayList<ChessMove> moves, int i, int j) {
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
}


