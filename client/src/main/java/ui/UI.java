package ui;

public interface UI {
    String eval(String cmd, String[] args);
    String help();
    String getPromptText();
}
