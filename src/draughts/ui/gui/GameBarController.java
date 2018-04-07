package draughts.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class GameBarController implements EventHandler<ActionEvent> {
    private final GameBarView gameBarView = new GameBarView(this);
    private final BoardController boardController;

    GameBarController(BoardController boardController) {
        this.boardController = boardController;
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
        } else if (eventSource.equals(gameBarView.getNewGameButton())) {
            boardController.restartGame();
        }
    }

    GameBarView getGameBarView() {
        return gameBarView;
    }
}
