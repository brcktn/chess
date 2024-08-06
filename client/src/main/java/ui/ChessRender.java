package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class ChessRender {
    private final ChessBoard chessBoard;

    ChessRender(ChessGame chessGame) {
        chessBoard = chessGame.getBoard();
    }

    public String render() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }
}
