package draughts.ui.gui;

import javafx.scene.Scene;
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
        final int SCENE_HEIGHT = 1300;

        BoardView boardView = boardController.getBoardView();
        GameBarView gameBarView = new GameBarController().getGameBarView();

        boardView.add(gameBarView, 0, 10);

        Scene scene = new Scene(boardView, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
        primaryStage.setScene(scene);
    }

}
