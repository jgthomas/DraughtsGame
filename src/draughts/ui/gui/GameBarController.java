package draughts.ui.gui;

import draughts.database.LoadState;
import draughts.gamecore.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

class GameBarController implements EventHandler<ActionEvent> {
    private final GameBarView gameBarView;
    private final BoardController boardController;
    private final Stage primaryStage;
    private final Game game;
    private final LoadState loadState = new LoadState();

    GameBarController(BoardController boardController, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
        this.game = boardController.getGame();
        gameBarView = GameBarView.newInstance(this);
    }

    @Override
    public void handle(ActionEvent event) {
        final Object eventSource = event.getSource();

        if (eventSource.equals(gameBarView.getQuitButton())) {
            System.exit(0);
        } else if (eventSource.equals(gameBarView.getBackButton())) {
            boardController.backOneMove();
        } else if (eventSource.equals(gameBarView.getForwardButton())) {
            boardController.forwardOneMove();
        } else if (eventSource.equals(gameBarView.getResumeButton())) {
            boardController.aiResume();
        } else if (eventSource.equals(gameBarView.getRestartGameButton())) {
            boardController.restartGame();
        } else if (eventSource.equals(gameBarView.getNewGameButton())) {
            final int SCENE_WIDTH = 1000;
            final int SCENE_HEIGHT = 400;
            Scene scene = new Scene(new OptionsController(primaryStage).getOptionsView(), SCENE_WIDTH, SCENE_HEIGHT);
            primaryStage.setScene(scene);
        } else if (eventSource.equals(gameBarView.getSaveGameButton())) {
            String defaultName = "Game" + loadState.getAllGameNames().size();
            TextInputDialog dialog = new TextInputDialog(defaultName);
            dialog.setTitle("Save Game");
            dialog.setHeaderText("Save Game");
            dialog.setContentText("Give game a name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(game::saveGame);
        }
    }

    GameBarView getGameBarView() {
        return gameBarView;
    }
}
