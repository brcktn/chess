package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalc {
    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition currPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

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
            if (addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        // SW moves
        for (int i = currPosition.getRow() - 1, j = currPosition.getColumn() - 1; i >= 1 && j >= 1; i--, j--) {
            if (addToMovesHelper(moves, board, currPosition, i, j)) break;
        }

        return moves;
    }

    private static boolean addToMovesHelper(ArrayList<ChessMove> moveList, ChessBoard board, ChessPosition myPosition, int i, int j) {
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
