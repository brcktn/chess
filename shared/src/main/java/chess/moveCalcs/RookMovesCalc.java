package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc {
    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition currPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessMoveCalc.straightMoves(moves, board, currPosition);
        return moves;
    }
}
