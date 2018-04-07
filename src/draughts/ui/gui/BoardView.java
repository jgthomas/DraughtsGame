package draughts.ui.gui;


import javafx.beans.binding.Bindings;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import draughts.gamecore.Board;
import draughts.gamecore.Square;
import draughts.gamecore.Piece;
import draughts.gamecore.SquareColor;


final class BoardView extends GridPane {

    BoardView(Board board, BoardController boardController) {
        for (Square square : board) {
            SquareViewHolder squareViewHolder = new SquareViewHolder(square, board.getPiece(square), boardController);
            this.add(squareViewHolder, square.col(), square.row());
        }
    }

    private static class SquareViewHolder extends StackPane {

        SquareViewHolder(Square square, Piece piece, BoardController boardController) {
            this.getChildren().add(new SquareView(square, boardController));
            this.getChildren().add(new PieceView(piece));
            this.getChildren().add(new KingView(piece));
        }
    }

    static class SquareView extends Rectangle {
        private static final int WIDTH = 150;
        private static final int HEIGHT = 150;
        private static final int BORDER_WIDTH = 10;

        SquareView(Square square, BoardController boardController) {
            this.setWidth(WIDTH);
            this.setHeight(HEIGHT);

            if (square.getSquareColor() == SquareColor.WHITE) {
                this.setFill(Color.WHITE);
            } else {
                this.setFill(Color.BLACK);
            }

            this.setStrokeWidth(BORDER_WIDTH);
            this.setStrokeType(StrokeType.INSIDE);
            this.setOnMouseClicked(boardController.onSquareClick);
        }
    }

    private static class PieceView extends Circle {
        private static final int MIDPOINT = 100;
        private static final int RADIUS = 50;

        PieceView(Piece piece) {
            this.setCenterX(MIDPOINT);
            this.setCenterY(MIDPOINT);
            this.setRadius(RADIUS);
            this.fillProperty().bind(Bindings.createObjectBinding( () -> {
                        switch (piece.getColor()) {
                            case WHITE:
                                return Color.RED;
                            case BLACK:
                                return Color.BLACK;
                            default:
                                return Color.TRANSPARENT;
                        }
                    }, piece.colorProperty()
            ));
            this.setMouseTransparent(true);
        }
    }

    private static class KingView extends Text {
        private static final String kingLetter = "K";
        private static final String kingFont = "DejaVu Sans";
        private static final int kingFontSize = 40;

        KingView(Piece piece) {
            this.setText(kingLetter);
            this.setFont(Font.font(kingFont, FontWeight.BOLD, kingFontSize));
            this.fillProperty().bind(Bindings.when(piece.isKingProperty())
                    .then(Color.WHITE)
                    .otherwise(Color.TRANSPARENT));
            this.setMouseTransparent(true);
        }
    }
}

