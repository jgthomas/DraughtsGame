package draughts.ui.gui;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class GuiDraughts extends Application {

    @Override
    public void start(Stage primaryStage) {
        final int SCENE_WIDTH = 1500;
        final int SCENE_HEIGHT = 1200;

        GridPane boardView = new GuiController().getBoardView();

        Scene scene = new Scene(boardView, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTSLATEGREY);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Draughts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
