package chess.moveCalcs;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc {
    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition currPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = currPosition.getRow();
        int col = currPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(currPosition).getTeamColor();

        if (color == ChessGame.TeamColor.WHITE) {
            // white pawn move forward
            ChessPosition newPosition = new ChessPosition(row + 1, col);
            if (board.getPiece(newPosition) == null) {
                if (row != 7) {
                    // normal move forward
                    moves.add(new ChessMove(currPosition, newPosition, null));
                } else {
                    // promotion
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 2 && board.getPiece(new ChessPosition(row + 2, col)) == null) {
                    // initial 2 square move
                    moves.add(new ChessMove(currPosition, new ChessPosition(row + 2, col), null));
                }
            }
            ChessPiece capturePiece;
            // white pawn capture NW
            if (col >= 2) {
                capturePiece = board.getPiece(new ChessPosition(row+1, col-1));
                if (capturePiece != null && capturePiece.getTeamColor() != color) {
                    if (row != 7) {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col-1), null));
                    } else {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col-1), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col-1), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col-1), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col-1), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            // white pawn capture NE
            if (col <= 7) {
                capturePiece = board.getPiece(new ChessPosition(row+1, col+1));
                if (capturePiece != null && capturePiece.getTeamColor() != color) {
                    if (row != 7) {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col+1), null));
                    } else {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col+1), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col+1), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col+1), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row+1, col+1), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
        }

        if (color == ChessGame.TeamColor.BLACK) {
            // black pawn move forward
            ChessPosition newPosition = new ChessPosition(row - 1, col);
            if (board.getPiece(newPosition) == null) {
                if (row != 2) {
                    // normal move forward
                    moves.add(new ChessMove(currPosition, newPosition, null));
                } else {
                    // promotion
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(currPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 7 && board.getPiece(new ChessPosition(row - 2, col)) == null) {
                    // initial 2 square move
                    moves.add(new ChessMove(currPosition, new ChessPosition(row - 2, col), null));
                }
            }
            // black pawn capture SW
            ChessPiece capturePiece;
            if (col >= 2) {
                capturePiece = board.getPiece(new ChessPosition(row-1, col-1));
                if (capturePiece != null && capturePiece.getTeamColor() != color) {
                    if (row != 2) {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col-1), null));
                    } else {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col-1), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col-1), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col-1), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col-1), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            // black pawn capture SE
            if (col <= 7) {
                capturePiece = board.getPiece(new ChessPosition(row-1, col+1));
                if (capturePiece != null && capturePiece.getTeamColor() != color) {
                    if (row != 2) {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col+1), null));
                    } else {
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col+1), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col+1), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col+1), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(currPosition, new ChessPosition(row-1, col+1), ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
        }

        return moves;
    }
}
