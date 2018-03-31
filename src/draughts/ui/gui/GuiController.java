package draughts.ui.gui;

import draughts.gamecore.Board;
import draughts.gamecore.LegalMoves;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class GuiController {
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final BoardView boardView = new BoardView(board, this);

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (eventSource instanceof Rectangle) {
            Rectangle clickedSquare = ((Rectangle) eventSource);
            if (clickedSquare.getStroke() == null) {
                clickedSquare.setStroke(Color.GREEN);
                clickedSquare.setStrokeWidth(10);
                clickedSquare.setStrokeType(StrokeType.INSIDE);
            } else {
                clickedSquare.setStroke(null);
            }
        }
    };

    public GridPane getBoardView() {
        return boardView.makeBoardView();
    }
}