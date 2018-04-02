package draughts.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GameBarController implements EventHandler<ActionEvent> {
    private final GameBarView gameBarView = new GameBarView(this);

    @Override
    public void handle(ActionEvent event) {
        final Object eventSource = event.getSource();

        if (eventSource.equals(gameBarView.getQuitButton())) {
            System.exit(0);
        }
    }

    public GameBarView getGameBarView() {
        return gameBarView;
    }
}
