package draughts.ui.gui;

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

        if (source.equals(view.getButton())) {
            BoardController boardController = new BoardController();
            Scene scene = new Scene(boardController.getBoardView(), SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
            primaryStage.setScene(scene);
        }
    }

    public OptionsView getOptionsView() {
        return view;
    }
}
