package draughts.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


class GamePlayView {
    private final Stage primaryStage;
    private final BoardController boardController;
    private final SaveGameController saveGameController;

    GamePlayView(Stage primaryStage, BoardController boardController, SaveGameController saveGameController) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
        this.saveGameController = saveGameController;
    }

    void show() {
        final int SCENE_WIDTH = 1400;
        final int SCENE_HEIGHT = 1300;

        BoardView boardView = boardController.getBoardView();
        GameBarView gameBarView = new GameBarController(boardController, saveGameController, primaryStage).getGameBarView();
        InfoBarView infoBarView = new InfoBarView(boardController.getGame());

        BorderPane gameUI = new BorderPane();
        gameUI.setCenter(boardView);
        gameUI.setBottom(gameBarView);
        gameUI.setRight(infoBarView);
        gameUI.setLeft(null);
        gameUI.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(gameUI, SCENE_WIDTH, SCENE_HEIGHT, Color.BEIGE);
        primaryStage.setScene(scene);
    }

}
