package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMoveCalculator {
    public static ArrayList<ChessMove> calculateQueenMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        moves.addAll(MoveCalculatorHelpers.getLateralMoves(board, pos));
        moves.addAll(MoveCalculatorHelpers.getDiagonalMoves(board, pos));
        return moves;
    }
}
