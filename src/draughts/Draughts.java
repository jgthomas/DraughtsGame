package draughts;

import javafx.application.Application;

import draughts.ui.gui.DraughtsGUI;

public class Draughts {
    private static final String GUI = "gui";
    private static final String TUI = "text";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase(TUI)) {
            System.out.println("TUI");
        } else {
            Application.launch(DraughtsGUI.class);
        }
    }
}
