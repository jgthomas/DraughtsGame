package draughts.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class GuiDraughts extends Application {

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(new OptionsController(primaryStage).getOptionsView());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Draughts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
