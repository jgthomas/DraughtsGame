package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

class GameBarView extends HBox {
    private static final Button quitButton = new Button("Quit");
    private static final Button backButton = new Button("Back");
    private static final Button forwardButton = new Button("Forward");
    private static final Button resumeButton = new Button("Go");
    private static final Button newGameButton = new Button("Restart");

    private static final List<Button> buttonList = new ArrayList<>();

    static {
        buttonList.add(quitButton);
        buttonList.add(backButton);
        buttonList.add(forwardButton);
        buttonList.add(resumeButton);
        buttonList.add(newGameButton);
    }

    GameBarView(GameBarController gameBarController) {
        this.setPrefHeight(100);
        this.setPrefWidth(1200);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);

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
