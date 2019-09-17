package draughts.gamecore;

import draughts.ai.AiPlayer;
import draughts.database.CacheState;

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
    private final CacheState cacheState;
    private final ObjectProperty<Side> activeSide;
    private final BooleanProperty gameIsNotWon;
    private final int firstMoveNumber;
    private int currentMoveNumber;
    private int maxMoveNumber;
    private PlayerConfig activePlayer;
    private Player aiPlayer;

    public Game(Board board,
                CacheState cacheState,
                PlayerConfig playerOne,
                PlayerConfig playerTwo,
                int firstMoveNumber)
    {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.legalMoves = new LegalMoves(board);
        this.cacheState = cacheState;
        this.firstMoveNumber = firstMoveNumber;
        this.currentMoveNumber = firstMoveNumber;
        this.maxMoveNumber = firstMoveNumber;
        this.activePlayer = setActivePlayer();
        activeSide = new SimpleObjectProperty<>(activePlayer.getSide());
        gameIsNotWon = new SimpleBooleanProperty(true);

        cacheState.cacheState(firstMoveNumber);

        if (playerOne.isAiPlayer()) {
            aiPlayer = new AiPlayer(board, legalMoves, playerOne.getSide());
        } else if (playerTwo.isAiPlayer()) {
            aiPlayer = new AiPlayer(board, legalMoves, playerTwo.getSide());
        }

        makeAiMoveIfNeeded();
    }

    public void makeMove(Move move) {
        executeMove(move);
        cacheBoardState();
        if (moveWinsGame()) {
            setGameIsNotWon(false);
            return;
        }
        switchActivePlayer();
        makeAiMoveIfNeeded();
    }

    public void backOneMove() {
        if (getGameIsNotWon() && notAtStartOfGame()) {
            board.setBoardState(cacheState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            switchActivePlayer();
        }
    }

    public void forwardOneMove() {
        if (getGameIsNotWon() && currentMoveNumber < maxMoveNumber) {
            board.setBoardState(cacheState.getCachedState(currentMoveNumber + 1));
            currentMoveNumber += 1;
            switchActivePlayer();
        }
    }

    public final void makeAiMoveIfNeeded() {
        if (getGameIsNotWon() && activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            cacheBoardState();
            if (moveWinsGame()) { setGameIsNotWon(false); return; }
            switchActivePlayer();
        }
    }

    public void restartGame() {
        board.setBoardState(cacheState.getCachedState(firstMoveNumber));
        currentMoveNumber = firstMoveNumber;
        if (firstMoveNumber == 0) {
            cacheState.clearCachedMoves();
        } else {
            cacheState.clearCacheBetween(firstMoveNumber, maxMoveNumber);
        }
        maxMoveNumber = firstMoveNumber;
        switchActivePlayer();
        setGameIsNotWon(true);
        cacheState.cacheState(currentMoveNumber);
        makeAiMoveIfNeeded();
    }

    public void saveGame(String gameName) {
        cacheState.saveCachedState(gameName);
    }

    public boolean legalStart(Square start) {
        return squareInList(start, legalMoves.legalStartingSquares(activePlayer.getSide()));
    }

    public boolean legalEnd(Square start, Square end) {
        return squareInList(end, legalMoves.legalEndingSquares(start, activePlayer.getSide()));
    }

    private void cacheBoardState() {
        currentMoveNumber += 1;
        maxMoveNumber += 1;
        cacheState.cacheState(currentMoveNumber);
    }

    private boolean moveWinsGame() {
        Side opponent = (activePlayer == playerOne)
                ? playerTwo.getSide()
                : playerOne.getSide();
        return legalMoves.legal(opponent).size() == 0;
    }

    private void switchActivePlayer() {
        activePlayer = setActivePlayer();
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

    private boolean notAtStartOfGame() {
        return currentMoveNumber > firstMoveNumber || (firstMoveNumber > 0 && currentMoveNumber > 0);
    }

    @Override
    public String toString() {
        String gameString = "First loaded move number: " + firstMoveNumber + "\n";
        gameString += "Current move number: " + currentMoveNumber + "\n";
        gameString += "Last move number: " + maxMoveNumber + "\n";
        gameString += "Player One: " + playerOne.toString() + "\n";
        gameString += "Player Two: " + playerTwo.toString() + "\n";
        gameString += "Active Player: " + activePlayer.toString() + "\n";
        gameString += board.toString();
        return gameString;
    }

    private PlayerConfig setActivePlayer() {
        return (currentMoveNumber % 2 == 0) ? playerOne : playerTwo;
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasNotBeenWon() {
        return getGameIsNotWon();
    }

    public int getMoveNumber() {
        return currentMoveNumber;
    }

    public BooleanProperty gameIsNotWonProperty() {
        return gameIsNotWon;
    }

    private boolean getGameIsNotWon() {
        return gameIsNotWon.get();
    }

    private void setGameIsNotWon(boolean won) {
        gameIsNotWon.set(won);
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
}
