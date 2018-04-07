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


class BoardView extends GridPane {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;
    private final BoardController boardController;

    BoardView(Board board, BoardController boardController) {
        this.boardController = boardController;

        for (Square square : board) {
            StackPane squareViewHolder = new StackPane();
            //squareViewHolder.getChildren().add(makeSquareView(square));
            squareViewHolder.getChildren().add(new SquareView(square));
            //squareViewHolder.getChildren().add(makePieceView(board.getPiece(square)));
            squareViewHolder.getChildren().add(new PieceView(board.getPiece(square)));
            //squareViewHolder.getChildren().add(makeKingView(board.getPiece(square)));
            squareViewHolder.getChildren().add(new KingView(board.getPiece(square)));
            this.add(squareViewHolder, square.col(), square.row());
        }
    }

    private Rectangle makeSquareView(Square square) {
        Rectangle squareView = new Rectangle();
        squareView.setWidth(WIDTH);
        squareView.setHeight(HEIGHT);

        if (square.getSquareColor() == SquareColor.WHITE) {
            squareView.setFill(Color.WHITE);
        } else {
            squareView.setFill(Color.BLACK);
        }

        squareView.setStrokeWidth(10);
        squareView.setStrokeType(StrokeType.INSIDE);
        squareView.setOnMouseClicked(boardController.onSquareClick);

        return squareView;
    }

    private class SquareView extends Rectangle {
        private static final int WIDTH = 150;
        private static final int HEIGHT = 150;
        private static final int BORDER_WIDTH = 10;

        SquareView(Square square) {
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

    private Circle makePieceView(Piece piece) {
        Circle pieceView = new Circle();
        pieceView.setCenterX(100.0f);
        pieceView.setCenterY(100.0f);
        pieceView.setRadius(50.0f);
        pieceView.fillProperty().bind(Bindings.createObjectBinding( () -> {
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
        pieceView.setMouseTransparent(true);
        return pieceView;
    }

    private Text makeKingView(Piece piece) {
        Text kingView = new Text();
        kingView.setText("K");
        kingView.setFont(Font.font("DejaVu Sans", FontWeight.BOLD, 40));
        kingView.fillProperty().bind(Bindings.when(piece.isKingProperty())
                .then(Color.WHITE)
                .otherwise(Color.TRANSPARENT));
        kingView.setMouseTransparent(true);
        return kingView;
    }

    private class PieceView extends Circle {

        PieceView(Piece piece) {
            this.setCenterX(100.0f);
            this.setCenterY(100.0f);
            this.setRadius(50.0f);
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

    private class KingView extends Text {
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

