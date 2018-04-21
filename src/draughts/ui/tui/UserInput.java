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

    int getNumberInRange(int low, int high, String message) {
        int num = -1;
        while (num < low || num > high) {
            num = getNumber(message);
        }
        return num;
    }

    String getString(String message, int length) {
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

