package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;

public class MoveCalculatorHelpers {
    public static ArrayList<ChessMove> getMovesInDirection(ChessBoard board, ChessPosition pos, int NSdirection, int EWdirection) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        boolean keepMoving = true;
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        int row = pos.getRow();
        int col = pos.getColumn();

        while (keepMoving) {
            row += NSdirection;
            col += EWdirection;

            if (row < 1 || col < 1 || row > 8 || col > 8) {
                break;
            }

            ChessPiece blockingPiece = board.getPiece(new ChessPosition(row, col));

            if (blockingPiece == null || blockingPiece.getTeamColor() != team) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                if (blockingPiece != null) {
                    keepMoving = false;
                }
            } else {
                keepMoving = false;
            }
        }

        return moves;
    }

    public static ArrayList<ChessMove> getDiagonalMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(getMovesInDirection(board, pos, 1, 1));
        moves.addAll(getMovesInDirection(board, pos, -1, 1));
        moves.addAll(getMovesInDirection(board, pos, 1, -1));
        moves.addAll(getMovesInDirection(board, pos, -1, -1));

        return moves;
    }

    public static ArrayList<ChessMove> getLateralMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.addAll(getMovesInDirection(board, pos, 1, 0));
        moves.addAll(getMovesInDirection(board, pos, -1, 0));
        moves.addAll(getMovesInDirection(board, pos, 0, 1));
        moves.addAll(getMovesInDirection(board, pos, 0, -1));

        return moves;
    }

    public static void addRelativeMove(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition pos, int relRow, int relCol) {
        int row = pos.getRow() + relRow;
        int col = pos.getColumn() + relCol;

        if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {
            ChessPiece blockingPiece = board.getPiece(new ChessPosition(row, col));
            if (blockingPiece == null || blockingPiece.getTeamColor() != board.getPiece(pos).getTeamColor() ) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }

    }
}
