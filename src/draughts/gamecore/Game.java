package draughts.gamecore;

import draughts.ai.AiPlayer;
import draughts.database.SaveState;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class Game {
    private final Board board;
    private final PlayerConfig playerOne;
    private final PlayerConfig playerTwo;
    private final LegalMoves legalMoves;
    private final SaveState saveState;

    private final ObjectProperty<Side> activeSide;
    private final BooleanProperty gameIsWon;

    private final int firstMoveNumber;
    private int currentMoveNumber;
    private PlayerConfig activePlayer;
    private Player aiPlayer;

    public Game(Board board,
                PlayerConfig playerOne,
                PlayerConfig playerTwo)
    {
        this(board, playerOne, playerTwo, 0);
    }

    public Game(Board board,
                PlayerConfig playerOne,
                PlayerConfig playerTwo,
                int firstMoveNumber)
    {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.legalMoves = new LegalMoves(board);
        this.saveState = new SaveState(board);
        this.firstMoveNumber = firstMoveNumber;
        this.currentMoveNumber = firstMoveNumber;
        this.activePlayer = playerOne;

        saveState.cacheState(firstMoveNumber);

        if (playerOne.isAiPlayer()) {
            aiPlayer = new AiPlayer(playerOne.getPieceType(), board, legalMoves, playerOne.getSide());
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
            activePlayer = playerTwo;
        } else if (playerTwo.isAiPlayer()) {
            aiPlayer = new AiPlayer(playerTwo.getPieceType(), board, legalMoves, playerTwo.getSide());
        }

        activeSide = new SimpleObjectProperty<>(activePlayer.getSide());
        gameIsWon = new SimpleBooleanProperty(false);
    }

    public void makeMove(Move move) {
        executeMove(move);
        cacheBoardState();
        if (moveWinsGame()) { setGameIsWon(true); return; }
        switchActivePlayer();

        if (activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            cacheBoardState();
            if (moveWinsGame()) { setGameIsWon(true); return; }
            switchActivePlayer();
        }
    }

    public void restartGame() {
        board.setBoardState(saveState.getCachedState(firstMoveNumber));
        currentMoveNumber = firstMoveNumber;
        saveState.clearCachedMoves();
        switchActivePlayer();
        setGameIsWon(false);
        saveState.cacheState(currentMoveNumber);
        aiResume();
    }

    public void backOneMove() {
        if (!getGameIsWon() && currentMoveNumber > firstMoveNumber) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            switchActivePlayer();
        }
    }

    public void forwardOneMove() {
        if (!getGameIsWon() && currentMoveNumber < saveState.numberOfCachedMoves() - 1) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber + 1));
            currentMoveNumber += 1;
            switchActivePlayer();
        }
    }

    public void aiResume() {
        if (!getGameIsWon() && activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            cacheBoardState();
            switchActivePlayer();
        }
    }

    public boolean legalStart(Square start) {
        return squareInList(start, legalMoves.legalStartingSquares(activePlayer.getSide()));
    }

    public boolean legalEnd(Square start, Square end) {
        return squareInList(end, legalMoves.legalEndingSquares(start, activePlayer.getSide()));
    }

    public Board getBoard() {
        return board;
    }

    public boolean won() {
        return getGameIsWon();
    }

    public int getMoveNumber() {
        return currentMoveNumber;
    }

    public void saveGame(String gameName) {
        saveState.saveGame(gameName);
    }

    public BooleanProperty gameIsWonProperty() {
        return gameIsWon;
    }

    private boolean getGameIsWon() {
        return gameIsWon.get();
    }

    private void setGameIsWon(boolean won) {
        gameIsWon.set(won);
    }

    public ObjectProperty<Side> activeSideProperty() {
        return activeSide;
    }

    public Side getActiveSide() {
        return activeSide.get();
    }

    private void setActiveSide(Side side) {
        activeSide.set(side);
    }

    private void cacheBoardState() {
        currentMoveNumber += 1;
        saveState.cacheState(currentMoveNumber);
    }

    private boolean moveWinsGame() {
        Side opponent = (activePlayer == playerOne)
                ? playerTwo.getSide()
                : playerOne.getSide();
        return legalMoves.legal(opponent).size() == 0;
    }

    private void switchActivePlayer() {
        activePlayer = (currentMoveNumber % 2 == 0) ? playerOne : playerTwo;
        setActiveSide(activePlayer.getSide());
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
        for (Move m : legalMoves.legal(activePlayer.getSide())) {
            if (m.equals(move)) {
                board.makeMove(m);
                return;
            }
        }
    }
}
