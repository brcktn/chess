package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor turn;
    private boolean gameOver = false;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        for (ChessMove move : moves) {
            if (isValidMove(move)) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException();
        }

        TeamColor moveTeam = piece.getTeamColor();
        if (moveTeam != getTeamTurn()) {
            throw new InvalidMoveException();
        }

        if (!isValidMove(move)) {
            throw new InvalidMoveException();
        }

        ChessBoard newBoard = getMovedBoard(getBoard(), move);
        setBoard(newBoard);

        // updates team turn
        if (getTeamTurn() == TeamColor.BLACK) {
            setTeamTurn(TeamColor.WHITE);
        } else {
            setTeamTurn(TeamColor.BLACK);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheckHelper(board, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (getTeamTurn() != teamColor || !isInCheck(teamColor)) {
            return false;
        }
        return isUnableToMove(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (getTeamTurn() != teamColor || isInCheck(teamColor)) {
            return false;
        }
        return isUnableToMove(teamColor);
    }

    /**
     * Helper function for checking checkmate and stalemate
     *
     * @param teamColor team in question
     * @return true if team in unable to move, otherwise false
     */
    private boolean isUnableToMove(TeamColor teamColor) {
        Collection<ChessMove> allMoves = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    allMoves.addAll(validMoves(pos));
                }
            }
        }

        return (allMoves.isEmpty());
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param inputBoard the new board to use
     */
    public void setBoard(ChessBoard inputBoard) {
        board = inputBoard;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Determines if given move is allowed
     * takes into account if the move is by the right team,
     * and if the move leaves the team in check.
     *
     * @param move Move made
     * @return True if allowed, otherwise false
     */
    private boolean isValidMove(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) {
            return false;
        }

        Collection<ChessMove> validMoves = piece.pieceMoves(board, move.getStartPosition());
        if (!validMoves.contains(move)) {
            return false;
        }

        TeamColor moveTeam = piece.getTeamColor();
        ChessBoard potentialBoard = getMovedBoard(board, move);
        return !isInCheckHelper(potentialBoard, moveTeam);
    }

    /**
     * Returns chess board with move made without updating game board
     *
     * @param move Move to make
     * @return Board if move was made
     */
    private ChessBoard getMovedBoard(ChessBoard board, ChessMove move) {
        ChessBoard newBoard = new ChessBoard(board);
        ChessPiece movedPiece = newBoard.getPiece(move.getStartPosition());
        if (move.getPromotionPiece() != null) {
            movedPiece.setPieceType(move.getPromotionPiece());
        }
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();

        newBoard.addPiece(endPos, movedPiece);
        newBoard.removePiece(startPos);
        return newBoard;
    }


    /**
     * Checks if a given team is in check on a given board
     *
     * @param color team color
     * @return True if in check, otherwise false
     */
    boolean isInCheckHelper(ChessBoard board, TeamColor color) {
        // iterates through all squares on a potential board
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                // if the piece can capture the king, return true
                if (pieceCanCaptureKing(board, color, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean pieceCanCaptureKing(ChessBoard board, TeamColor color, int i, int j) {
        ChessPiece piece = board.getPiece(new ChessPosition(i, j));
        if (piece != null && piece.getTeamColor() != color) {
            Collection<ChessMove> moves = piece.pieceMoves(board, new ChessPosition(i, j));
            for (ChessMove move : moves) {
                ChessPiece blockingPiece = board.getPiece(move.getEndPosition());
                if (blockingPiece != null
                        && blockingPiece.getPieceType() == ChessPiece.PieceType.KING
                        && blockingPiece.getTeamColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
    }
}

