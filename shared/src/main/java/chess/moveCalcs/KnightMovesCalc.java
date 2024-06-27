package chess.moveCalcs;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc {
    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition currPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = currPosition.getRow();
        int currCol = currPosition.getColumn();

        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow+1, currCol+2);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow+1, currCol-2);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow+2, currCol+1);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow+2, currCol-1);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow-1, currCol+2);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow-1, currCol-2);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow-2, currCol+1);
        ChessMoveCalc.addIfAllowed(moves, board, currPosition, currRow-2, currCol-1);

        return moves;
    }
}
