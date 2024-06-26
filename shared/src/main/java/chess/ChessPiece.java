package chess;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor _pieceColor;
    private ChessPiece.PieceType _pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this._pieceColor = pieceColor;
        this._pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return _pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return _pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (_pieceType) {
            case KING -> kingMoves(board, myPosition);
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> bishopMoves(board, myPosition);
            case KNIGHT -> knightMoves(board, myPosition);
            case ROOK -> rookMoves(board, myPosition);
            case PAWN -> pawnMoves(board, myPosition);
        };
    }

    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        //NW moves
        for (int i = myPosition.getColumn(), j = myPosition.getRow(); i >= 0 && j < 8; i--, j++) {
            if (board.getPiece(new ChessPosition(i, j)) != null) {
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(i,j), _pieceType));
        }

        //SW moves
        for (int i = myPosition.getColumn(), j = myPosition.getRow(); i >= 0 && j >= 0; i--, j--) {
            if (board.getPiece(new ChessPosition(i, j)) != null) {
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(i,j), _pieceType));
        }

        //NE moves
        for (int i = myPosition.getColumn(), j = myPosition.getRow(); i < 8 && j < 8; i++, j++) {
            if (board.getPiece(new ChessPosition(i, j)) != null) {
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(i,j), _pieceType));
        }

        //SE moves
        for (int i = myPosition.getColumn(), j = myPosition.getRow(); i < 8 && j >= 0; i++, j--) {
            if (board.getPiece(new ChessPosition(i, j)) != null) {
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(i,j), _pieceType));
        }

        return moves;
        // throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }



}
