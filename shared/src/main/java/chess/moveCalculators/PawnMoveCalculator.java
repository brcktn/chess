package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;

public class PawnMoveCalculator {
    public static ArrayList<ChessMove> calculatePawnMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(pos);
        int row = pos.getRow();
        int col = pos.getColumn();

        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (row != 7) {
                if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                    moves.add(new ChessMove(pos, new ChessPosition(row +1, col), null));
                    if (row == 2 && board.getPiece(new ChessPosition(row + 2, col)) == null) {
                        moves.add(new ChessMove(pos, new ChessPosition(row + 2, col), null));
                    }
                }

                if (col > 1 && board.getPiece(new ChessPosition(row + 1, col - 1)) != null && board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), null));
                }

                if (col < 8 && board.getPiece(new ChessPosition(row + 1, col + 1)) != null && board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), null));
                }


            } else {
                if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col), ChessPiece.PieceType.KNIGHT));
                }

                if (col > 1 && board.getPiece(new ChessPosition(row + 1, col - 1)) != null && board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.KNIGHT));
                }

                if (col < 8 && board.getPiece(new ChessPosition(row + 1, col + 1)) != null && board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.KNIGHT));
                }
            }
        } else {
            if (row != 2) {
                if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), null));
                    if (row == 7 && board.getPiece(new ChessPosition(row - 2, col)) == null) {
                        moves.add(new ChessMove(pos, new ChessPosition(row - 2, col), null));
                    }
                }

                if (col > 1 && board.getPiece(new ChessPosition(row - 1, col - 1)) != null && board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), null));
                }

                if (col < 8 && board.getPiece(new ChessPosition(row - 1, col + 1)) != null && board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), null));
                }


            } else {
                if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), ChessPiece.PieceType.KNIGHT));
                }

                if (col > 1 && board.getPiece(new ChessPosition(row - 1, col - 1)) != null && board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.KNIGHT));
                }

                if (col < 8 && board.getPiece(new ChessPosition(row - 1, col + 1)) != null && board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.KNIGHT));
                }
            }
        }

        return moves;
    }
}
