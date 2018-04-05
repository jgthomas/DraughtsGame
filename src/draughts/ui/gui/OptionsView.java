package draughts.ui.gui;

import draughts.database.LoadState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OptionsView extends HBox {
    private final LoadState loadState = new LoadState();
    private final Button humanHumanButton = new Button("Human vs Human");
    private final Button humanAiButton = new Button("Human vs Computer");
    private final Button aiHumanButton = new Button("Computer vs Human");

    OptionsView(OptionsController optionsController) {
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setPrefWidth(200);
        buttonBox.setPadding(new Insets(10, 50, 50, 50));
        buttonBox.setSpacing(10);

        humanHumanButton.setOnAction(optionsController);
        humanHumanButton.setPrefWidth(buttonBox.getPrefWidth());
        buttonBox.getChildren().addAll(humanHumanButton);

        humanAiButton.setOnAction(optionsController);
        humanAiButton.setPrefWidth(buttonBox.getPrefWidth());
        buttonBox.getChildren().addAll(humanAiButton);

        aiHumanButton.setOnAction(optionsController);
        aiHumanButton.setPrefWidth(buttonBox.getPrefWidth());
        buttonBox.getChildren().addAll(aiHumanButton);

        ListView<String> gamesList = new ListView<>();
        gamesList.getItems().add("New Game");
        gamesList.getItems().addAll(loadState.getAllGameNames());
        HBox gameBox = new HBox(gamesList);
        gameBox.setPrefWidth(300);
        gameBox.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(gameBox);
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
