package draughts.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {
    private final Button button = new Button("H vs H");
    private final Button button1 = new Button("H vs C");
    private final Button button2 = new Button("C vs H");

    OptionsView(OptionsController optionsController) {
        button.setOnAction(optionsController);
        this.getChildren().addAll(button);
        button1.setOnAction(optionsController);
        this.getChildren().addAll(button1);
        button2.setOnAction(optionsController);
        this.getChildren().addAll(button2);
    }

    public Button getButton() {
        return button;
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }
}
