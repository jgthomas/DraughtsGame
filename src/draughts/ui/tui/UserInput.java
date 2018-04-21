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
        return getNumber(message + "\n" + PLAYER_PICK_MESSAGE);
    }

    boolean getYesOrNo(String message) {
        String answer = getString(message + " [y/n]", 1);
        return Character.toLowerCase(answer.charAt(0)) == 'y';
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

    private String getString(String message, int minimumLength) {
        String str = "";
        while (str.length() < minimumLength) {
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

