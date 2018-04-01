package draughts.ui.gui;

import draughts.gamecore.PlayerType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OptionsController implements EventHandler<ActionEvent> {
    private final Stage primaryStage;
    private final OptionsView view = new OptionsView(this);

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(final ActionEvent event) {
        final int SCENE_WIDTH = 1500;
        final int SCENE_HEIGHT = 1200;
        final Object source = event.getSource();
        BoardController boardController;

        if (source.equals(view.getButton())) {
            boardController = new BoardController(PlayerType.HUMAN, PlayerType.HUMAN);
        } else if (source.equals(view.getButton1())) {
            boardController = new BoardController(PlayerType.HUMAN, PlayerType.COMPUTER);
        } else {
            boardController = new BoardController(PlayerType.COMPUTER, PlayerType.HUMAN);
        }
        Scene scene = new Scene(boardController.getBoardView(), SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
        primaryStage.setScene(scene);
    }

    public OptionsView getOptionsView() {
        return view;
    }
}
