package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMoveCalculator {
    public static ArrayList<ChessMove> calculateRookMoves(ChessBoard board, ChessPosition pos) {
        return MoveCalculatorHelpers.getLateralMoves(board, pos);
    }
}
