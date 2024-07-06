package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMoveCalculator {
    public static ArrayList<ChessMove> calculateKingMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 1,1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 1,0);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 1,-1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 0,-1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -1,-1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -1,0);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, -1,1);
        MoveCalculatorHelpers.addRelativeMove(moves, board, pos, 0,1);

        return moves;
    }
}
