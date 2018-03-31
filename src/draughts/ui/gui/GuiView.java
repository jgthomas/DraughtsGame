package draughts.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class GuiView extends Application {

    @Override
    public void start(Stage primaryStage) {
        final int SCENE_WIDTH = 1500;
        final int SCENE_HEIGHT = 1200;

        Scene scene = new Scene(new GuiController().getBoardView(), SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Draughts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
