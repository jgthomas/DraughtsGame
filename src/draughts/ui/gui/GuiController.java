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
    private List<Rectangle> clickedSquares = new ArrayList<>();
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final BoardView boardView = new BoardView(board, this);
    private PieceType activePieceType = PieceType.WHITE_PIECE;

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (eventSource instanceof Rectangle) {
            Rectangle clickedSquare = ((Rectangle) eventSource);
            Square square = buildSquare(clickedSquare);
            if (clickedSquare.getStroke() == null) {
                if (validSquare(square)) {

                    if (squareCache.size() == 0) {
                        clearBorders();
                    }

                    clickedSquare.setStroke(Color.GREEN);
                    squareCache.add(square);

                    clickedSquares.add(clickedSquare);

                    if (squareCache.size() == 2) {
                        executeMove(buildMove(squareCache.get(0), squareCache.get(1)));
                        squareCache.clear();
                        clearBorders();
                    }
                } else {
                    clickedSquare.setStroke(Color.RED);
                    clickedSquares.add(clickedSquare);
                }

            } else {
                if (squareCache.size() > 0 && clickedSquare.getStroke() == Color.GREEN) {
                    squareCache.remove(squareCache.size()-1);
                }
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

    private boolean validSquare(Square square) {
        List<Move> allMoves = legalMoves.legal(activePieceType);
        List<Square> starts = legalStarts(allMoves);

        if (squareCache.size() == 0) {
            return squareInList(square, starts);
        } else {
            return squareInList(squareCache.get(0), possibleStarts(square, allMoves));
        }
    }

    private List<Square> legalStarts(List<Move> moves) {
        List<Square> legalStarts = new ArrayList<>();
        for (Move move : moves) {
            legalStarts.add(move.startOfMove());
        }
        return legalStarts;
    }

    private boolean squareInList(Square square, List<Square> squareList) {
        for (Square s : squareList) {
            if (s.equals(square)) {
                return true;
            }
        }
        return false;
    }

    private List<Square> possibleStarts(Square end, List<Move> allMoves) {
        List<Square> posStarts = new ArrayList<>();
        for (Move move : allMoves) {
            if (move.endOfMove().equals(end)) {
                posStarts.add(move.startOfMove());
            }
        }
        return posStarts;
    }

    private void executeMove(Move move) {
        List<Move> legal = legalMoves.legal(activePieceType);
        for (Move m : legal) {
            if (m.equals(move)) {
                board.makeMove(m);
                switchPlayers();
                return;
            }
        }
    }

    private void switchPlayers() {
        activePieceType = (activePieceType == PieceType.WHITE_PIECE)
                ? PieceType.BLACK_PIECE
                : PieceType.WHITE_PIECE;
    }

    private void clearBorders() {
        for (Rectangle r : clickedSquares) {
            r.setStroke(null);
        }
        clickedSquares.clear();
    }

}