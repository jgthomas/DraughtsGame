package draughts.ui.gui;

import draughts.database.LoadState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

class OptionsView extends HBox {
    private final Button humanHumanButton = new Button("Human vs Human");
    private final Button humanAiButton = new Button("Human vs Computer");
    private final Button aiHumanButton = new Button("Computer vs Human");

    private final ObservableList<String> gameNames = FXCollections.observableArrayList();
    private final ListView<String> gameNamesDisplay = new ListView<>(gameNames);

    OptionsView(OptionsController optionsController) {
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setPrefWidth(600);
        buttonBox.setPadding(new Insets(10, 50, 50, 50));
        buttonBox.setSpacing(10);

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(humanHumanButton);
        buttonList.add(humanAiButton);
        buttonList.add(aiHumanButton);

        for (Button button : buttonList) {
            button.setOnAction(optionsController);
            button.setPrefWidth(buttonBox.getPrefWidth());
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setMinSize(200, 33);
            button.setFont(new Font(30));
            button.setTextFill(Color.BLACK);
            buttonBox.getChildren().add(button);
        }

        LoadState loadState = new LoadState();

        gameNames.add("New Game");
        gameNames.addAll(loadState.getAllGameNames());

        gameNamesDisplay.getSelectionModel().select(0);
        gameNamesDisplay.setCellFactory( (cell) -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setFont(Font.font(25));
                }
            }
        });

        HBox savedGameNameBox = new HBox(gameNamesDisplay);
        savedGameNameBox.setPrefWidth(300);
        savedGameNameBox.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(savedGameNameBox);
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
        return gameNamesDisplay.getSelectionModel().getSelectedItem();
    }
}
