package draughts;

import draughts.ui.gui.GuiDraughts;
import draughts.ui.tui.TextDraughts;
import javafx.application.Application;


class Draughts {
    private static final String GUI = "gui";
    private static final String TUI = "tui";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase(TUI)) {
            new TextDraughts().run();

        } else {
            Application.launch(GuiDraughts.class);
        }
    }
}
