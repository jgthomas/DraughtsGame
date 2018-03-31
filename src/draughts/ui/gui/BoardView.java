package draughts.ui.gui;


import javafx.beans.binding.Bindings;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import draughts.gamecore.Board;
import draughts.gamecore.Square;
import draughts.gamecore.Piece;
import draughts.gamecore.SquareColor;


public class BoardView {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;
    private Board board;

    BoardView(Board board) {
        this.board = board;
    }

    public GridPane makeBoardView() {
        GridPane boardView = new GridPane();
        for (Square square : board) {
            addToBoardView(boardView, square);
        }
        return boardView;
    }

    private void addToBoardView(GridPane boardDisplay, Square square) {
        StackPane squareViewHolder = makeSquareView(square);
        boardDisplay.add(squareViewHolder, square.col(), square.row());
    }

    private StackPane makeSquareView(Square square) {
        StackPane squareViewHolder = new StackPane();
        Rectangle squareView = new Rectangle();
        squareView.setWidth(WIDTH);
        squareView.setHeight(HEIGHT);

        if (square.getSquareColor() == SquareColor.WHITE) {
            squareView.setFill(Color.WHITE);
        } else {
            squareView.setFill(Color.BLACK);
        }

        Piece piece = board.getPiece(square);

        squareViewHolder.getChildren().add(squareView);
        squareViewHolder.getChildren().add(makePieceView(piece));
        squareViewHolder.getChildren().add(makeKingView(piece));

        return squareViewHolder;
    }

    private Circle makePieceView(Piece piece) {
        Circle circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);
        circle.fillProperty().bind(Bindings.createObjectBinding( () -> {
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

        return circle;
    }

    private Text makeKingView(Piece piece) {
        Text kingView = new Text();
        kingView.setText("K");
        kingView.setFont(Font.font("DejaVu Sans", FontWeight.BOLD, 40));
        kingView.fillProperty().bind(Bindings.when(piece.isKingProperty())
                .then(Color.WHITE)
                .otherwise(Color.TRANSPARENT));
        return kingView;
    }
}

