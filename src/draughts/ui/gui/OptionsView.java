package draughts.ui.gui;

import draughts.database.LoadState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class OptionsView extends HBox {
    private final Button humanHumanButton = new Button("Human vs Human");
    private final Button humanAiButton = new Button("Human vs Computer");
    private final Button aiHumanButton = new Button("Computer vs Human");

    private final ObservableList<String> gameNames = FXCollections.observableArrayList();
    private final ListView<String> gamesList = new ListView<>(gameNames);

    OptionsView(OptionsController optionsController) {
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setPrefWidth(300);
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

        LoadState loadState = new LoadState();


        gameNames.add("New Game");
        gameNames.addAll(loadState.getAllGameNames());


        gamesList.getSelectionModel().select(0);
        HBox gameBox = new HBox(gamesList);
        gameBox.setPrefWidth(300);
        gameBox.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(gameBox);
        this.getChildren().add(buttonBox);
    }

    Button getHumanHumanButton() {
        return humanHumanButton;
    }

    Button getHumanAiButton() {
        return humanAiButton;
    }

    Button getAiHumanButton() {
        return aiHumanButton;
    }

    String getSelectedGame() {
        return gamesList.getSelectionModel().getSelectedItem();
    }
}
