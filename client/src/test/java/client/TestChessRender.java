package client;

import chess.ChessGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ui.ChessRender;

public class TestChessRender {
    @Test
    public void testChessRender() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.print(ChessRender.render(new ChessGame().getBoard()));
        });
    }
}
