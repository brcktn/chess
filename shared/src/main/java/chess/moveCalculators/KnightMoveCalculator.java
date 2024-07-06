package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMoveCalculator {
    public static ArrayList<ChessMove> calculateKnightMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 1,2);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 1,-2);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -1,2);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -1,-2);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 2,1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 2,-1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -2,1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -2,-1);

        return moves;
    }
}
