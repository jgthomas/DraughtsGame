package draughts.gamecore;

import draughts.ai.AiPlayer;
import draughts.database.SaveState;

public class Game {
    private final Board board;
    private final PlayerConfig playerOne;
    private final PlayerConfig playerTwo;
    private final LegalMoves legalMoves;
    private final SaveState saveState;

    private int firstMoveNumber;
    private int currentMoveNumber;
    private PlayerConfig activePlayer;
    private boolean gameWon;
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
        this.gameWon = false;

        saveState.cacheState(firstMoveNumber);

        if (playerOne.isAiPlayer()) {
            aiPlayer = new AiPlayer(activePlayer.getPieceType(), board, legalMoves);
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
        } else if (playerTwo.isAiPlayer()) {
            aiPlayer = new AiPlayer(activePlayer.getPieceType(), board, legalMoves);
        }
    }

    public void makeMove(Move move) {
        board.makeMove(move);
        cacheBoardState();
        if (moveWinsGame()) { gameWon = true; return; }
        switchActivePlayer();

        if (activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            cacheBoardState();
            if (moveWinsGame()) { gameWon = true; return; }
            switchActivePlayer();
        }
    }

    private void cacheBoardState() {
        currentMoveNumber += 1;
        saveState.cacheState(currentMoveNumber);
    }

    private void switchActivePlayer() {
        activePlayer = (activePlayer == playerOne) ? playerTwo : playerOne;
    }

    private boolean moveWinsGame() {
        PieceType opponentPieceType = (activePlayer == playerOne)
                ? playerTwo.getPieceType()
                : playerOne.getPieceType();
        return board.totalPieces(opponentPieceType) == 0 || legalMoves.legal(opponentPieceType).size() == 0;
    }

    public void resetGame() {
        board.setBoardState(saveState.getCachedState(firstMoveNumber));
        saveState.clearCachedMoves();
        currentMoveNumber = firstMoveNumber;
        resetActivePlayerByTurn();
        gameWon = false;
        aiResumeIfNeeded();
    }

    public void backOneMove() {
        if (!gameWon && currentMoveNumber > 0) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            resetActivePlayerByTurn();
            aiResumeIfNeeded();
        }
    }

    public void forwardOneMove() {
        if (!gameWon && currentMoveNumber < saveState.numberOfCachedMoves() - 1) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber + 1));
            currentMoveNumber += 1;
            resetActivePlayerByTurn();
            aiResumeIfNeeded();
        }
    }

    private void resetActivePlayerByTurn() {
        if (currentMoveNumber % 2 == 0) {
            activePlayer = playerOne;
        } else {
            activePlayer = playerTwo;
        }
    }

    private void aiResumeIfNeeded() {
        if (!gameWon && activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            cacheBoardState();
            switchActivePlayer();
        }
    }
}
