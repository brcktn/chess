package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition _startPos;
    private final ChessPosition _endPos;
    private final ChessPiece.PieceType _pieceType;

    public ChessMove(ChessPosition startPosition,
                     ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this._startPos = startPosition;
        this._endPos = endPosition;
        this._pieceType = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return _startPos;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return _endPos;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return _pieceType;
    }

    public String toString() {
        return "[" + _startPos + " -> " + _endPos + ", " + _pieceType + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(_startPos, chessMove._startPos) && Objects.equals(_endPos, chessMove._endPos) && _pieceType == chessMove._pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_startPos, _endPos, _pieceType);
    }
}
