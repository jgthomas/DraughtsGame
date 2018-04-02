package draughts.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {
    private final Button button = new Button("Human vs Human");
    private final Button button1 = new Button("Human vs Computer");
    private final Button button2 = new Button("Computer vs Human");

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
