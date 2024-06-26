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
        ChessMoveCalc.diagonalMoves(moves, board, currPosition);
        return moves;
    }
}
