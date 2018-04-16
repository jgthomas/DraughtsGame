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
        turnInfo.fillProperty().bind(Bindings
                .when(game.gameIsNotWonProperty())
                .then(Color.BLACK)
                .otherwise(Color.TRANSPARENT));


        Circle turnImage = new Circle();
        turnImage.setCenterX(50);
        turnImage.setCenterY(50);
        turnImage.setRadius(25);
        turnImage.fillProperty().bind(Bindings.createObjectBinding( () -> {
                    switch (game.getActiveSide()) {
                        case WHITE:
                            return Color.WHITE;
                        case BLACK:
                            return Color.BLACK;
                        default:
                            return Color.TRANSPARENT;
                    }
                }, game.activeSideProperty()
        ));

        Text winInfo = new Text("WINS!");
        winInfo.setFont(new Font(45));
        winInfo.fillProperty().bind(Bindings
                .when(game.gameIsNotWonProperty())
                .then(Color.TRANSPARENT)
                .otherwise(Color.LAWNGREEN));

        this.getChildren().add(turnInfo);
        this.getChildren().add(turnImage);
        this.getChildren().add(winInfo);
    }
}
