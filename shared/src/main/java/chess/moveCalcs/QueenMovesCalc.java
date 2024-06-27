package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc {
    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition currPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessMoveCalc.straightMoves(moves, board, currPosition);
        ChessMoveCalc.diagonalMoves(moves, board, currPosition);
        return moves;
    }
}
