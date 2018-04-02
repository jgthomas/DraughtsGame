package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class GameBarView extends HBox {
    private final Button quitButton = new Button("Quit");
    private final Button undoButton = new Button("Undo");

    GameBarView(GameBarController gameBarController) {
        this.setPrefHeight(100);
        this.setPrefWidth(1200);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        quitButton.setOnAction(gameBarController);
        this.getChildren().add(quitButton);
        undoButton.setOnAction(gameBarController);
        this.getChildren().add(undoButton);
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getUndoButton() {
        return undoButton;
    }
}
