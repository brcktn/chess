package ui;

public class LoginUI implements UI {
    @Override
    public String eval(String cmd, String[] args) {
        try {
            return switch (cmd) {
                case "logout" -> login();
                default -> help();
            };
        } catch (Throwable e) {
            return e.getMessage();
        }    }

    @Override
    public String help() {
        return "";
    }
}
