package draughts.ui.gui;

import draughts.gamecore.PlayerType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class OptionsController implements EventHandler<ActionEvent> {
    private final Stage primaryStage;
    private final OptionsView view = new OptionsView(this);

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(final ActionEvent event) {
        final Object source = event.getSource();
        BoardController boardController;

        if (source.equals(view.getButton())) {
            boardController = new BoardController(PlayerType.HUMAN, PlayerType.HUMAN);
        } else if (source.equals(view.getButton1())) {
            boardController = new BoardController(PlayerType.HUMAN, PlayerType.COMPUTER);
        } else {
            boardController = new BoardController(PlayerType.COMPUTER, PlayerType.HUMAN);
        }
        GamePlayView gamePlayView = new GamePlayView(primaryStage, boardController);
        gamePlayView.getGamePlayView();
    }

    public OptionsView getOptionsView() {
        return view;
    }
}
