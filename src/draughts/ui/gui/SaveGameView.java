package draughts.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

class SaveGameView extends HBox {
    private final Button saveGameButton = new Button("Save");
    private final Button backButton = new Button("Back");
    private final SaveGameController saveGameController;

    SaveGameView(SaveGameController saveGameController) {
        this.saveGameController = saveGameController;
        this.getChildren().add(buildButtonBox());
    }

    Button getSaveGameButton() {
        return saveGameButton;
    }

    Button getBackButton() {
        return backButton;
    }

    private HBox buildButtonBox() {
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setPadding(new Insets(10, 50, 50, 50));
        buttonBox.setSpacing(10);

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(saveGameButton);
        buttonList.add(backButton);

        for (Button button : buttonList) {
            button.setOnAction(saveGameController);
            button.setTextFill(Color.BLACK);
            buttonBox.getChildren().add(button);
        }
        return buttonBox;
    }
}
