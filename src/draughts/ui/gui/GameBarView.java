package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class GameBarView extends HBox {
    private final Button quitButton = new Button("Quit");
    private final Button backButton = new Button("Back");
    private final Button forwardButton = new Button("Forward");
    private final Button resumeButton = new Button("Go");
    private final Button newGameButton = new Button("Restart");

    GameBarView(GameBarController gameBarController) {
        this.setPrefHeight(100);
        this.setPrefWidth(1200);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

        quitButton.setOnAction(gameBarController);
        quitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(quitButton);

        backButton.setOnAction(gameBarController);
        backButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(backButton);

        forwardButton.setOnAction(gameBarController);
        forwardButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(forwardButton);

        resumeButton.setOnAction(gameBarController);
        resumeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(resumeButton);

        newGameButton.setOnAction(gameBarController);
        newGameButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(newGameButton);
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getForwardButton() {
        return forwardButton;
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }
}
