package draughts.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class SaveGameView extends HBox {
    private final Button saveGameButton = new Button("Save");
    private final Button backButton = new Button("Back");
    private final SaveGameController saveGameController;

    SaveGameView(SaveGameController saveGameController) {
        this.saveGameController = saveGameController;
    }

    Button getSaveGameButton() {
        return saveGameButton;
    }

    Button getBackButton() {
        return backButton;
    }
}
