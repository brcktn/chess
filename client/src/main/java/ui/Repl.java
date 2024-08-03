package ui;

import data.ChessClient;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to the chess. Sign in to start.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.println(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private void printPrompt() {
        System.out.print("\n >>> " + SET_BG_COLOR_BLACK + SET_TEXT_COLOR_GREEN);
    }

}
