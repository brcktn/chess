package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private ChessPosition _startPos;
    private ChessPosition _endPos;
    private ChessPiece.PieceType _pieceType;

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
}
