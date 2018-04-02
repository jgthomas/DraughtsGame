package draughts.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GamePlayView {
    private final Stage primaryStage;
    private final BoardController boardController;


    GamePlayView(Stage primaryStage, BoardController boardController) {
        this.primaryStage = primaryStage;
        this.boardController = boardController;
    }

    public void getGamePlayView() {
        final String title = "Draughts";
        final int SCENE_WIDTH = 1200;
        final int SCENE_HEIGHT = 1300;

        GridPane boardView = boardController.getBoardView();

        HBox controlBar = makeControlBar();
        controlBar.getChildren().add(makeTitle(title));

        boardView.add(controlBar, 0, 10);

        Scene scene = new Scene(boardView, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);
        primaryStage.setScene(scene);
    }

    private Text makeTitle(String title) {
        Text titleNode = new Text();
        titleNode.setText(title);
        titleNode.setFont(new Font(20));
        return titleNode;
    }

    private HBox makeControlBar() {
        HBox controlBar = new HBox();
        controlBar.setPrefHeight(100);
        controlBar.setPrefWidth(1200);
        controlBar.setAlignment(Pos.CENTER);
        return controlBar;
    }

}
