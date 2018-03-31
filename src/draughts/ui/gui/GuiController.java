package draughts.ui.gui;

import draughts.gamecore.*;
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
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final BoardView boardView = new BoardView(board, this);

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (eventSource instanceof Rectangle) {
            Rectangle clickedSquare = ((Rectangle) eventSource);
            Square square = buildSquare(clickedSquare);
            if (clickedSquare.getStroke() == null) {
                if (validSquare(square)) {
                    clickedSquare.setStroke(Color.GREEN);
                    squareCache.add(square);
                    if (squareCache.size() == 2) {
                        executeMove(buildMove(squareCache.get(0), squareCache.get(1)));
                        squareCache.clear();
                    }
                } else {
                    clickedSquare.setStroke(Color.RED);
                }

            } else {
                clickedSquare.setStroke(null);
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

    private List<Square> legalStarts() {
        List<Square> legalStarts = new ArrayList<>();
        for (Move move : legalMoves.legal(PieceType.WHITE_PIECE)) {
            legalStarts.add(move.startOfMove());
        }
        return legalStarts;
    }

    private List<Square> legalEnds() {
        List<Square> legalEnds = new ArrayList<>();
        for (Move move : legalMoves.legal(PieceType.WHITE_PIECE)) {
            legalEnds.add(move.endOfMove());
        }
        return legalEnds;
    }

    private boolean validSquare(Square square) {
        if (squareCache.size() == 0) {
            return squareInList(square, legalStarts());
        }
        return squareInList(square, legalEnds());
    }

    private boolean squareInList(Square square, List<Square> squareList) {
        for (Square s : squareList) {
            if (s.equals(square)) {
                return true;
            }
        }
        return false;
    }

    private void executeMove(Move move) {
        List<Move> legal = legalMoves.legal(PieceType.WHITE_PIECE);
        System.out.println(move);
        for (Move m : legal) {
            if (m.equals(move)) {
                System.out.println(m);
                board.makeMove(m);
                return;
            }
        }
    }

}