package draughts.ui.tui;

public class TextDraughts {

    public void run() {
        new OptionsController(new UserInput()).startGame();
    }
}