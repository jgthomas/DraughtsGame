package draughts.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {
    private final Button humanHumanButton = new Button("Human vs Human");
    private final Button humanAiButton = new Button("Human vs Computer");
    private final Button aiHumanButton = new Button("Computer vs Human");

    OptionsView(OptionsController optionsController) {
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setPrefWidth(800);
        buttonBox.setPadding(new Insets(10, 50, 50, 50));
        buttonBox.setSpacing(10);

        humanHumanButton.setOnAction(optionsController);
        humanHumanButton.setPrefWidth(buttonBox.getPrefWidth() / 3);
        buttonBox.getChildren().addAll(humanHumanButton);

        humanAiButton.setOnAction(optionsController);
        humanAiButton.setPrefWidth(buttonBox.getPrefWidth() / 3);
        buttonBox.getChildren().addAll(humanAiButton);

        aiHumanButton.setOnAction(optionsController);
        aiHumanButton.setPrefWidth(buttonBox.getPrefWidth() / 3);
        buttonBox.getChildren().addAll(aiHumanButton);

        this.getChildren().add(buttonBox);
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
