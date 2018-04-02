package draughts.ui.gui;

import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GamePlayView {
    private final Stage primaryStage;
    private final BoardController boardController;


    GamePlayView(Stage primaryStage, BoardController boardController) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
    }

    public void getGamePlayView() {
        final int SCENE_WIDTH = 1200;
        final int SCENE_HEIGHT = 1250;

        BoardView boardView = boardController.getBoardView();
        GameBarView gameBarView = new GameBarController(boardController).getGameBarView();

        GridPane.setValignment(gameBarView.getQuitButton(), VPos.CENTER);
        boardView.add(gameBarView.getQuitButton(), 0, 10);

        GridPane.setValignment(gameBarView.getUndoButton(), VPos.CENTER);
        boardView.add(gameBarView.getUndoButton(), 1, 10);

        GridPane.setValignment(gameBarView.getResumeButton(), VPos.CENTER);
        boardView.add(gameBarView.getResumeButton(), 2, 10);

        Scene scene = new Scene(boardView, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
        primaryStage.setScene(scene);
    }

}
