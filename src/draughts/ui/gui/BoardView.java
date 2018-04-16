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


final class BoardView extends GridPane {
    /*private final ObservableList<Contents> contentsOfSquares = FXCollections.observableList(new ArrayList<>(),
            (Contents c) -> new Observable[]{c.pieceProperty(), c.getPiece().sideProperty(), c.getPiece().isKingProperty()});*/

    BoardView(Board board, BoardController boardController) {
        for (Square square : board) {
            SquareViewHolder squareViewHolder = new SquareViewHolder(square, board.getPiece(square), boardController);
            this.add(squareViewHolder, square.col(), square.row());
        }
    }

    private class SquareViewHolder extends StackPane {
        SquareViewHolder(Square square, Piece piece, BoardController boardController) {
            this.getChildren().add(new SquareView(square, boardController));
            this.getChildren().add(new PieceView(piece));
            this.getChildren().add(new KingView(piece));
        }
    }

    class SquareView extends Rectangle {
        private final int WIDTH = 150;
        private final int HEIGHT = 150;
        private final int BORDER_WIDTH = 10;

        SquareView(Square square, BoardController boardController) {
            this.setWidth(WIDTH);
            this.setHeight(HEIGHT);

            if(isWhiteSquare(square)) {
                this.setFill(Color.LIGHTGRAY);
            } else {
                this.setFill(Color.DARKGREY);
            }

            this.setStrokeWidth(BORDER_WIDTH);
            this.setStrokeType(StrokeType.INSIDE);
            this.setOnMouseClicked(boardController);
        }

        private boolean isWhiteSquare(Square square) {
            if (square.row() % 2 == 0) {
                return square.col() % 2 == 0;
            }
            return square.col() % 2 != 0;
        }
    }

    private class PieceView extends Circle {
        private final int MIDPOINT = 100;
        private final int RADIUS = 50;

        PieceView(Piece piece) {
            this.setCenterX(MIDPOINT);
            this.setCenterY(MIDPOINT);
            this.setRadius(RADIUS);
            this.fillProperty().bind(Bindings.createObjectBinding( () -> {
                        switch (piece.getSide()) {
                            case WHITE:
                                return Color.WHITE;
                            case BLACK:
                                return Color.RED;
                            default:
                                return Color.TRANSPARENT;
                        }
                    }, piece.sideProperty()
            ));
            this.setMouseTransparent(true);
        }
    }

    private class KingView extends Text {
        private final String kingLetter = "K";
        private final String kingFont = "DejaVu Sans";
        private final int kingFontSize = 40;

        KingView(Piece piece) {
            this.setText(kingLetter);
            this.setFont(Font.font(kingFont, FontWeight.BOLD, kingFontSize));
            this.fillProperty().bind(Bindings.when(piece.isKingProperty())
                    .then(Color.BLACK)
                    .otherwise(Color.TRANSPARENT));
            this.setMouseTransparent(true);
        }
    }
}

