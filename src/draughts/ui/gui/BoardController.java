package draughts.ui.gui;

import draughts.ai.AiPlayer;
import draughts.database.SaveState;
import draughts.gamecore.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


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

    private List<Rectangle> clickedSquareViews = new ArrayList<>();
    private Map<MoveSquare, Square> buildMove = new EnumMap<>(MoveSquare.class);

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
                if (valid(square)) {

                    if (buildMove.containsKey(MoveSquare.START)) {
                        executeMove(new Move(buildMove.get(MoveSquare.START), square));
                        cacheBoardState();
                        if (moveWinsGame()) { gameWon = true;return; }
                        clearClickedSquareViews();
                        buildMove.clear();
                        switchActivePlayer();
                    } else {
                        clearClickedSquareViews();
                        clickedSquareView.setStroke(Color.GREEN);
                        clickedSquareViews.add(clickedSquareView);
                        buildMove.put(MoveSquare.START, square);
                    }

                    if (activePlayer.isAiPlayer()) {
                        makeAiMove();
                        if (moveWinsGame()) { gameWon = true; return; }
                        switchActivePlayer();
                    }
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
    };

    public BoardView getBoardView() {
        return boardView;
    }

    public void backOneMove() {
        if (!gameWon && currentMoveNumber > 0) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            resetActivePlayerByTurn();
            clearClickedSquareViews();
        }
    }

    public void forwardOneMove() {
        if (!gameWon && currentMoveNumber < saveState.numberOfCachedMoves() - 1) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber + 1));
            currentMoveNumber += 1;
            resetActivePlayerByTurn();
            clearClickedSquareViews();
        }
    }

    public void newGame() {
        board.setBoardState(saveState.getCachedState(0));
        saveState.clearCachedMoves();
        currentMoveNumber = 0;
        resetActivePlayerByTurn();
        gameWon = false;
        aiResume();
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
        PieceType opponentPieceType = (activePlayer == playerOne)
                ? playerTwo.getPieceType()
                : playerOne.getPieceType();
        return board.totalPieces(opponentPieceType) == 0 || legalMoves.legal(opponentPieceType).size() == 0;
    }

    private Square buildSquare(Rectangle squareView) {
        int row = BoardView.getRowIndex(squareView.getParent());
        int col = BoardView.getColumnIndex(squareView.getParent());
        return new Square(row, col);
    }

    private boolean valid(Square square) {
        List<Move> allMoves = legalMoves.legal(activePlayer.getPieceType());

        if (buildMove.containsKey(MoveSquare.START)) {
            return squareInList(square, legalMoves.legalEndingSquares(
                    buildMove.get(MoveSquare.START), activePlayer.getPieceType()));
        }
        return squareInList(square, legalMoves.legalStartingSquares(activePlayer.getPieceType()));
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
        for (Move m : legalMoves.legal(activePlayer.getPieceType())) {
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

    private enum MoveSquare {
        START
    }

}