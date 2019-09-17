package draughts.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class GuiDraughts extends Application {

    @Override
    public void start(Stage primaryStage) {
        final int SCENE_WIDTH = 1000;
        final int SCENE_HEIGHT = 400;

        Scene scene = new Scene(new OptionsController(primaryStage).getOptionsView(), SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Draughts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
