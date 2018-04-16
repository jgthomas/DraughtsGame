package draughts.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class SaveGameController implements EventHandler<ActionEvent> {
    private final SaveGameView saveGameView = new SaveGameView(this);

    @Override
    public void handle(ActionEvent event) {
        final Object eventSource = event.getSource();

        if (eventSource.equals(saveGameView.getBackButton())) {
            saveGameView.getBackButton().getScene().getWindow().hide();
        }
    }

    SaveGameView getSaveGameView() {
        return saveGameView;
    }
}
