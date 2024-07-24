package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMoveCalculator {
    public static ArrayList<ChessMove> calculateBishopMoves(ChessBoard board, ChessPosition pos) {
        return MoveCalculatorHelpers.getDiagonalMoves(board, pos);
    }
}
