package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

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
        this.setSpacing(50);

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(quitButton);
        buttonList.add(backButton);
        buttonList.add(forwardButton);
        buttonList.add(resumeButton);
        buttonList.add(newGameButton);

        for (Button button : buttonList) {
            button.setOnAction(gameBarController);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.getChildren().add(button);
        }
    }

    Button getQuitButton() {
        return quitButton;
    }

    Button getBackButton() {
        return backButton;
    }

    Button getForwardButton() {
        return forwardButton;
    }

    Button getResumeButton() {
        return resumeButton;
    }

    Button getNewGameButton() {
        return newGameButton;
    }
}
