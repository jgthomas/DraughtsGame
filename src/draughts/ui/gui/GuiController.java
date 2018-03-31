package draughts.ui.gui;

import draughts.gamecore.Board;
import draughts.gamecore.Move;
import draughts.gamecore.Square;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class GuiController {
    private List<Square> squareCache = new ArrayList<>();
    private final Board board = new Board();
    private final BoardView boardView = new BoardView(board, this);

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (eventSource instanceof Rectangle) {
            Rectangle clickedSquare = ((Rectangle) eventSource);
            if (clickedSquare.getStroke() == null) {
                clickedSquare.setStroke(Color.GREEN);

                squareCache.add(buildSquare(clickedSquare));

                if (squareCache.size() == 2) {
                    System.out.println(buildMove(squareCache.get(0), squareCache.get(1)));
                    squareCache.clear();
                }

            } else {
                clickedSquare.setStroke(null);
                if (squareCache.size() > 0) {
                    squareCache.remove(squareCache.size()-1);
                }
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