package draughts.ui.tui;


import java.util.Scanner;

final class UserInput {

    private final String PLAYER_PICK_MESSAGE = String.join(System.getProperty("line.separator"),
            "(1) Human",
            "(2) Computer",
            " >>>"
    );

    private final Scanner scanner = new Scanner(System.in);

    int pickPlayerType(String message) {
        String fullMessage = String.join(System.getProperty("line.separator"),
                message,
                PLAYER_PICK_MESSAGE
        );
        return getNumber(fullMessage);
    }

    boolean getYesOrNo(String message) {
        message = message + " [y/n]";
        String answer = getString(message, 1);
        char response = Character.toLowerCase(answer.charAt(0));
        return response == 'y';
    }

    String getCommand(String message) {
        return getString(message, 2);
    }

    String getName() {
        final String GAME_NAME_PROMPT = "Name saved game";
        return getString(GAME_NAME_PROMPT, 1);
    }

    int pickSavedGame(int high) {
        final String PICK_GAME = "Pick game number";
        int num = -1;
        while (num < 0 || num > high) {
            num = getNumber(PICK_GAME);
        }
        return num;
    }

    private String getString(String message, int length) {
        String str = "";
        while (str.length() < length) {
            System.out.print(message + " : ");
            str = scanner.next();
        }
        return str;
    }

    private int getNumber(String message) {
        System.out.print(message + " : ");
        while (!scanner.hasNextInt()) {
            System.out.print(message + " : ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}

