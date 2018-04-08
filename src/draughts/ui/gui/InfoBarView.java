package draughts.ui.gui;

import draughts.gamecore.Game;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class InfoBarView extends VBox {

    InfoBarView(Game game) {
        this.setPrefWidth(200);
        this.setPrefHeight(1300);
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20);

        Text turnInfo = new Text("To Play");
        turnInfo.setFont(new Font(35));
        this.getChildren().add(turnInfo);

        Circle turnImage = new Circle();
        turnImage.setCenterX(50);
        turnImage.setCenterY(50);
        turnImage.setRadius(25);
        turnImage.fillProperty().bind(Bindings.createObjectBinding( () -> {
                    switch (game.getActivePieceType()) {
                        case WHITE_PIECE:
                            return Color.RED;
                        case BLACK_PIECE:
                            return Color.BLACK;
                        default:
                            return Color.TRANSPARENT;
                    }
                }, game.activePieceTypeProperty()
        ));
        this.getChildren().add(turnImage);
    }
}
