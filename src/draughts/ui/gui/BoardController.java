package draughts.ui.gui;

import draughts.ai.AiPlayer;
import draughts.database.SaveState;
import draughts.gamecore.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class BoardController {
    private final Board board;
    private final LegalMoves legalMoves;
    private final SaveState saveState;
    private final BoardView boardView;

    private final PlayerConfig playerOne;
    private final PlayerConfig playerTwo;
    private PlayerConfig activePlayer;
    private AiPlayer aiPlayer;

    private boolean gameWon = false;
    private int currentMoveNumber;

    private List<Square> squaresForMove = new ArrayList<>();
    private List<Rectangle> clickedSquareViews = new ArrayList<>();

    BoardController(Board board,
                    PlayerConfig playerOne,
                    PlayerConfig playerTwo) {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        legalMoves = new LegalMoves(board);
        saveState = new SaveState(board);

        saveState.cacheState(currentMoveNumber);

        if (playerOne.isAiPlayer()) {
            aiPlayer = new AiPlayer(PieceType.WHITE_PIECE, board, legalMoves);
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
            activePlayer = playerTwo;
        } else if (playerTwo.isAiPlayer()) {
            aiPlayer = new AiPlayer(PieceType.BLACK_PIECE, board, legalMoves);
            activePlayer = playerOne;
        } else {
            activePlayer = playerOne;
        }

        boardView = new BoardView(board, this);
    }

    EventHandler<MouseEvent> onSquareClick = (event) -> {
        Object eventSource = event.getSource();
        if (!gameWon && eventSource instanceof Rectangle) {
            Rectangle clickedSquareView = ((Rectangle) eventSource);
            Square square = buildSquare(clickedSquareView);
            if (clickedSquareView.getStroke() == null) {
                if (validSquare(square)) {

                    if (squaresForMove.size() == 0) { clearClickedSquareViews(); }

                    clickedSquareView.setStroke(Color.GREEN);
                    clickedSquareViews.add(clickedSquareView);

                    squaresForMove.add(square);

                    if (squaresForMove.size() == 2) {
                        makeHumanMove();
                        if (moveWinsGame()) { gameWon = true; return; }
                        switchActivePlayer();

                        clearClickedSquareViews();
                        squaresForMove.clear();

                        if (activePlayer.isAiPlayer()) {
                            makeAiMove();
                            if (moveWinsGame()) { gameWon = true; return; }
                            switchActivePlayer();
                        }
                    }
                } else {
                    clickedSquareView.setStroke(Color.RED);
                    clickedSquareViews.add(clickedSquareView);
                }

            } else {
                if (squaresForMove.size() > 0 && clickedSquareView.getStroke() == Color.GREEN) {
                    squaresForMove.remove(squaresForMove.size()-1);
                }
                clickedSquareView.setStroke(null);
            }
        }
    };

    public BoardView getBoardView() {
        return boardView;
    }

    public void undoBoard() {
        if (currentMoveNumber > 0 && !gameWon) {
            board.undoBoard(saveState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            resetActivePlayerByTurn();
        }
    }

    public void aiResume() {
        if (!gameWon && activePlayer.isAiPlayer()) {
            makeAiMove();
            switchActivePlayer();
        }
    }

    private void makeAiMove() {
        board.makeMove(aiPlayer.getMove());
        cacheBoardState();
    }

    private void makeHumanMove() {
        executeMove(new Move(squaresForMove.get(0), squaresForMove.get(1)));
        cacheBoardState();
    }

    private void cacheBoardState() {
        currentMoveNumber += 1;
        saveState.cacheState(currentMoveNumber);
    }

    private void resetActivePlayerByTurn() {
        if (currentMoveNumber % 2 == 0) {
            activePlayer = playerOne;
        } else {
            activePlayer = playerTwo;
        }
    }

    private boolean moveWinsGame() {
        PieceType toCheck = (activePlayer == playerOne) ? playerTwo.getPieceType() : playerOne.getPieceType();
        List<Move> allMoves = legalMoves.legal(toCheck);
        int piecesLeft = board.totalPieces(toCheck);
        return piecesLeft == 0 || allMoves.size() == 0;
    }

    private Square buildSquare(Rectangle squareView) {
        int row = GridPane.getRowIndex(squareView.getParent());
        int col = GridPane.getColumnIndex(squareView.getParent());
        return new Square(row, col);
    }

    private boolean validSquare(Square square) {
        List<Move> allMoves = legalMoves.legal(activePlayer.getPieceType());
        List<Square> starts = legalStarts(allMoves);

        if (squaresForMove.size() == 0) {
            return squareInList(square, starts);
        } else {
            return squareInList(squaresForMove.get(0), possibleStartsGivenEnd(square, allMoves));
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

    private List<Square> possibleStartsGivenEnd(Square end, List<Move> allMoves) {
        List<Square> posStarts = new ArrayList<>();
        for (Move move : allMoves) {
            if (move.endOfMove().equals(end)) {
                posStarts.add(move.startOfMove());
            }
        }
        return posStarts;
    }

    private void executeMove(Move move) {
        List<Move> legal = legalMoves.legal(activePlayer.getPieceType());
        for (Move m : legal) {
            if (m.equals(move)) {
                board.makeMove(m);
                return;
            }
        }
    }

    private void switchActivePlayer() {
        activePlayer = (activePlayer == playerOne) ? playerTwo : playerOne;
    }

    private void clearClickedSquareViews() {
        for (Rectangle r : clickedSquareViews) {
            r.setStroke(null);
        }
        clickedSquareViews.clear();
    }

}