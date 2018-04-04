package draughts.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {
    private final Button humanHumanButton = new Button("Human vs Human");
    private final Button humanAiButton = new Button("Human vs Computer");
    private final Button aiHumanButton = new Button("Computer vs Human");

    OptionsView(OptionsController optionsController) {
        humanHumanButton.setOnAction(optionsController);
        this.getChildren().addAll(humanHumanButton);
        humanAiButton.setOnAction(optionsController);
        this.getChildren().addAll(humanAiButton);
        aiHumanButton.setOnAction(optionsController);
        this.getChildren().addAll(aiHumanButton);
    }

    public Button getHumanHumanButton() {
        return humanHumanButton;
    }

    public Button getHumanAiButton() {
        return humanAiButton;
    }

    public Button getAiHumanButton() {
        return aiHumanButton;
    }
}
