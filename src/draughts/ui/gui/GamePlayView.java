package draughts.ui.gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


class GamePlayView {
    private final Stage primaryStage;
    private final BoardController boardController;

    GamePlayView(Stage primaryStage, BoardController boardController) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
    }

    void show() {
        final int SCENE_WIDTH = 1400;
        final int SCENE_HEIGHT = 1300;

        BoardView boardView = boardController.getBoardView();
        GameBarView gameBarView = new GameBarController(boardController, primaryStage).getGameBarView();
        InfoBarView infoBarView = new InfoBarView(boardController.getGame());

        BorderPane gameUI = new BorderPane();
        gameUI.setCenter(boardView);
        gameUI.setBottom(gameBarView);
        gameUI.setRight(infoBarView);
        gameUI.setLeft(null);

        Scene scene = new Scene(gameUI, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
        primaryStage.setScene(scene);
    }

}
