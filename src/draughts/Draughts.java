package draughts;

import draughts.ui.gui.DraughtsGUI;
import draughts.ui.tui.DraughtsTUI;
import javafx.application.Application;


public class Draughts {
    private static final String GUI = "gui";
    private static final String TUI = "text";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase(GUI)) {
            Application.launch(DraughtsGUI.class);

        } else {
            new DraughtsTUI().run();
        }
    }
}
