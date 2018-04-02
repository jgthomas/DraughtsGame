package draughts.ui.gui;

import draughts.ai.ComputerPlayer;
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
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final SaveState saveState = new SaveState(board);

    private final BoardView boardView;

    private final PlayerType playerOne;
    private final PlayerType playerTwo;
    private ComputerPlayer aiPlayer;

    private PlayerType activePlayer;
    private PieceType activePieceType;
    private boolean gameWon = false;
    private int moveNumber;

    private List<Square> squaresForMove = new ArrayList<>();
    private List<Rectangle> clickedSquareViews = new ArrayList<>();

    BoardController(PlayerType playerOne, PlayerType playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.moveNumber = 0;

        saveState.cacheState(moveNumber);

        if (playerOne == PlayerType.COMPUTER) {
            aiPlayer = new ComputerPlayer(PieceType.WHITE_PIECE, board, legalMoves);
            board.makeMove(aiPlayer.getMove());
            moveNumber += 1;
            saveState.cacheState(moveNumber);
            activePieceType = PieceType.BLACK_PIECE;
            activePlayer = playerTwo;
        } else if (playerTwo == PlayerType.COMPUTER) {
            aiPlayer = new ComputerPlayer(PieceType.BLACK_PIECE, board, legalMoves);
            activePieceType = PieceType.WHITE_PIECE;
            activePlayer = playerOne;
        } else {
            activePlayer = playerOne;
            activePieceType = PieceType.WHITE_PIECE;
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

                    if (squaresForMove.size() == 0) {
                        clearClickedSquareViews();
                    }

                    clickedSquareView.setStroke(Color.GREEN);
                    clickedSquareViews.add(clickedSquareView);

                    squaresForMove.add(square);

                    if (squaresForMove.size() == 2) {
                        makeHumanMove();
                        if (moveWinsGame()) { gameWon = true; return; }
                        switchActivePlayer();

                        clearClickedSquareViews();
                        squaresForMove.clear();

                        if (activePlayer == PlayerType.COMPUTER) {
                            makeAiMove();
                            if (moveWinsGame()) { gameWon = true; return; }
                            switchActivePlayer();
                        } else {
                            switchActivePieceType();
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
        if (moveNumber > 0 && !gameWon) {
            board.undoBoard(saveState.getCachedState(moveNumber - 1));
            moveNumber -= 1;
            if (moveNumber % 2 == 0) {
                activePlayer = playerOne;
                activePieceType = PieceType.WHITE_PIECE;
            } else {
                activePlayer = playerTwo;
                activePieceType = PieceType.BLACK_PIECE;
            }
        }
    }

    public void aiResume() {
        if (activePlayer == PlayerType.COMPUTER) {
            makeAiMove();
            switchActivePlayer();
            switchActivePieceType();
        }
    }

    private void makeAiMove() {
        board.makeMove(aiPlayer.getMove());
        moveNumber += 1;
        saveState.cacheState(moveNumber);
    }

    private void makeHumanMove() {
        executeMove(new Move(squaresForMove.get(0), squaresForMove.get(1)));
        moveNumber += 1;
        saveState.cacheState(moveNumber);
    }

    private boolean moveWinsGame() {
        List<Move> allMoves = legalMoves.legal(opponentsPieceType());
        int piecesLeft = board.totalPieces(opponentsPieceType());
        return piecesLeft == 0 || allMoves.size() == 0;
    }

    private PieceType opponentsPieceType() {
        return (activePieceType == PieceType.WHITE_PIECE)
                ? PieceType.BLACK_PIECE
                : PieceType.WHITE_PIECE;
    }

    private Square buildSquare(Rectangle squareView) {
        int row = GridPane.getRowIndex(squareView.getParent());
        int col = GridPane.getColumnIndex(squareView.getParent());
        return new Square(row, col);
    }

    private boolean validSquare(Square square) {
        List<Move> allMoves = legalMoves.legal(activePieceType);
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
        List<Move> legal = legalMoves.legal(activePieceType);
        for (Move m : legal) {
            if (m.equals(move)) {
                board.makeMove(m);
                return;
            }
        }
    }

    private void switchActivePieceType() {
        activePieceType = (activePieceType == PieceType.WHITE_PIECE)
                ? PieceType.BLACK_PIECE
                : PieceType.WHITE_PIECE;
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