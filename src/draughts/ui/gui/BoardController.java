package draughts.ui.gui;

import draughts.gamecore.Game;
import draughts.gamecore.Square;
import draughts.gamecore.Move;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class BoardController implements EventHandler<MouseEvent> {
    private final Game game;
    private final BoardView boardView;
    private final List<BoardView.SquareView> clickedSquareViews = new ArrayList<>();
    private final Map<MoveSquare, Square> buildMove = new EnumMap<>(MoveSquare.class);

    BoardController(Game game) {
        this.game = game;
        boardView = new BoardView(game.getBoard(), this);
    }

    @Override
    public void handle(MouseEvent event) {
        Object eventSource = event.getSource();
        if (game.hasNotBeenWon() && eventSource instanceof BoardView.SquareView) {
            BoardView.SquareView clickedSquareView = ((BoardView.SquareView) eventSource);
            Square square = buildSquare(clickedSquareView);
            if (clickedSquareView.getStroke() == null) {
                if (buildMove.containsKey(MoveSquare.START) &&
                        game.legalEnd(buildMove.get(MoveSquare.START), square)) {
                    game.makeMove(new Move(buildMove.get(MoveSquare.START), square));
                    clearClickedSquareViews();
                    buildMove.clear();
                } else if (game.legalStart(square)) {
                    clearClickedSquareViews();
                    clickedSquareView.setStroke(Color.GREEN);
                    clickedSquareViews.add(clickedSquareView);
                    buildMove.put(MoveSquare.START, square);
                } else {
                    clickedSquareView.setStroke(Color.RED);
                    clickedSquareViews.add(clickedSquareView);
                }
            } else {
                if (buildMove.size() > 0 && buildMove.get(MoveSquare.START).equals(square)) {
                    buildMove.remove(MoveSquare.START);
                }
                clickedSquareView.setStroke(null);
            }
        }
    }

    BoardView getBoardView() {
        return boardView;
    }

    Game getGame() {
        return game;
    }

    void backOneMove() {
        game.backOneMove();
        clearClickedSquareViews();
        buildMove.clear();
    }

    void forwardOneMove() {
        game.forwardOneMove();
        clearClickedSquareViews();
        buildMove.clear();
    }

    void restartGame() {
        game.restartGame();
        clearClickedSquareViews();
        buildMove.clear();
    }

    void aiResume() {
        game.aiResume();
        clearClickedSquareViews();
        buildMove.clear();
    }

    private Square buildSquare(BoardView.SquareView squareView) {
        int row = BoardView.getRowIndex(squareView.getParent());
        int col = BoardView.getColumnIndex(squareView.getParent());
        return new Square(row, col);
    }

    private void clearClickedSquareViews() {
        for (BoardView.SquareView squareView : clickedSquareViews) {
            squareView.setStroke(null);
        }
        clickedSquareViews.clear();
    }

    private enum MoveSquare {
        START
    }

}