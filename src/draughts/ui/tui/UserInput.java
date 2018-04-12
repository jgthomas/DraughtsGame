package draughts.ui.tui;


import java.util.Scanner;

public final class UserInput {

    private final String PLAYER_PICK_MESSAGE = String.join(System.getProperty("line.separator"),
            "(1) Human",
            "(2) Computer",
            " > "
    );

    private final Scanner scanner = new Scanner(System.in);

    public boolean playSavedGame() {
        return getYesOrNo("Load saved game [y/n]: ");
    }

    public boolean seeNextMove() {
        return getYesOrNo("See next move [y/n]: ");
    }

    public boolean saveGame() {
        return getYesOrNo("Save game [y/n]: ");
    }

    public String setGameName() {
        return getString("Give game a name: ");
    }

    public String getPlayerName() {
        return getString("Pick your player name: ");
    }

    public int selectSavedGame() {
        return getNumber("Pick game number to load: ");
    }

    public int selectSavedMove(int lastMoveNumber) {
        return getNumberInRange(0, lastMoveNumber,"Pick the move to load: ");
    }

    public int pickPlayerType(String message) {
        String fullMessage = String.join(System.getProperty("line.separator"),
                message,
                PLAYER_PICK_MESSAGE
        );
        return getNumber(fullMessage);
    }

    private boolean getYesOrNo(String message) {
        System.out.print(message);
        String answer = scanner.next();
        char response = Character.toLowerCase(answer.charAt(0));
        return response == 'y';
    }

    private int getNumber(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print(message);
            scanner.next();
        }
        return scanner.nextInt();
    }

    private int getNumberInRange(int low, int high, String message) {
        int num = 0;
        while (num < low || num > high) {
            num = getNumber(message);
        }
        return num;
    }

    private String getString(String message) {
        String str = "";
        while (str.length() < 1) {
            System.out.print(message);
            str = scanner.next();
        }
        return str;
    }
}

