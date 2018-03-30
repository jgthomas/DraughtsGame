package draughts.ui;

public interface UserInput {

    boolean playSavedGame();

    boolean seeNextMove();

    boolean saveGame();

    String setGameName();

    int selectSavedGame();

    int pickPlayerType(String message);
}
