package draughts.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {
    private final Button button = new Button("Play Draughts");

    OptionsView(OptionsController optionsController) {
        button.setOnAction(optionsController);
        this.getChildren().addAll(button);
    }

    public Button getButton() {
        return button;
    }
}
