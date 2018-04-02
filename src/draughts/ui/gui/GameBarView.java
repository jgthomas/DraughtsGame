package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class GameBarView extends HBox {
    private final Button quitButton = new Button("Quit");
    private final Button undoButton = new Button("Undo");
    private final Button resumeButton = new Button("Resume");

    GameBarView(GameBarController gameBarController) {
        this.setPrefHeight(100);
        this.setPrefWidth(1200);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

        quitButton.setOnAction(gameBarController);
        quitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(quitButton);

        undoButton.setOnAction(gameBarController);
        undoButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(undoButton);

        resumeButton.setOnAction(gameBarController);
        resumeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(resumeButton);
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public Button getResumeButton() {
        return resumeButton;
    }
}
