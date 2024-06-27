package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalc {
    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        // NE moves
        for (int i = myPosition.getRow() + 1, j = myPosition.getColumn() + 1; i <= 8 && j <= 8; i++, j++) {
            if (ChessMoveCalc.addToMoves(moves, board, myPosition, moves, i, j)) break;
        }

        // NW moves
        for (int i = myPosition.getRow() + 1, j = myPosition.getColumn() - 1; i <= 8 && j >= 1; i++, j--) {
            if (ChessMoveCalc.addToMoves(moves, board, myPosition, moves, i, j)) break;
        }

        // SE moves
        for (int i = myPosition.getRow() - 1, j = myPosition.getColumn() + 1; i >= 1 && j <= 8; i--, j++) {
            if (ChessMoveCalc.addToMoves(moves, board, myPosition, moves, i, j)) break;
        }

        // SW moves
        for (int i = myPosition.getRow() - 1, j = myPosition.getColumn() - 1; i >= 1 && j >= 1; i--, j--) {
            if (ChessMoveCalc.addToMoves(moves, board, myPosition, moves, i, j)) break;
        }

        return moves;
    }
}
