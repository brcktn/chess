package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc {
    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition currPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = currPosition.getRow();
        int currCol = currPosition.getColumn();

        addIfAllowed(moves, board, currPosition, currRow+1, currCol);
        addIfAllowed(moves, board, currPosition, currRow-1, currCol);
        addIfAllowed(moves, board, currPosition, currRow, currCol+1);
        addIfAllowed(moves, board, currPosition, currRow, currCol-1);
        addIfAllowed(moves, board, currPosition, currRow+1, currCol+1);
        addIfAllowed(moves, board, currPosition, currRow-1, currCol+1);
        addIfAllowed(moves, board, currPosition, currRow+1, currCol-1);
        addIfAllowed(moves, board, currPosition, currRow-1, currCol-1);

        return moves;
    }

    private static void addIfAllowed(Collection<ChessMove> moves, ChessBoard board, ChessPosition currPosition, int i, int j) {
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
