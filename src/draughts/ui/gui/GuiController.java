package draughts.ui.gui;

import draughts.gamecore.Board;
import draughts.gamecore.LegalMoves;
import draughts.gamecore.Move;
import draughts.gamecore.Square;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GuiController {
    private List<Square> moveCache = new ArrayList<>();
    private final Stage primaryStage;
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final BoardView boardView = new BoardView(board, this);

    GuiController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (eventSource instanceof Rectangle) {
            Rectangle clickedSquare = ((Rectangle) eventSource);
            if (clickedSquare.getStroke() == null) {
                clickedSquare.setStroke(Color.GREEN);
                clickedSquare.setStrokeWidth(10);
                clickedSquare.setStrokeType(StrokeType.INSIDE);

                moveCache.add(buildSquare(clickedSquare));

                if (moveCache.size() == 2) {
                    System.out.println(buildMove(moveCache.get(0), moveCache.get(1)));
                    moveCache.clear();
                }

            } else {
                clickedSquare.setStroke(null);
                moveCache.clear();
            }
        }
    };

    public GridPane getBoardView() {
        return boardView.makeBoardView();
    }

    private Square buildSquare(Rectangle square) {
        int row = GridPane.getRowIndex(square.getParent());
        int col = GridPane.getColumnIndex(square.getParent());
        return new Square(row, col);
    }

    private Move buildMove(Square start, Square end) {
        return new Move(start, end);
    }
}