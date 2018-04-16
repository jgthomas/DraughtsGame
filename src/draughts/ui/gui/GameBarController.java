package draughts.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

class GameBarController implements EventHandler<ActionEvent> {
    private final GameBarView gameBarView;
    private final BoardController boardController;
    private final Stage primaryStage;

    GameBarController(BoardController boardController, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
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
            System.out.println("Hello");
        }
    }

    GameBarView getGameBarView() {
        return gameBarView;
    }
}
