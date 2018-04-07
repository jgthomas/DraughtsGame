package draughts.gamecore;

import draughts.ai.AiPlayer;
import draughts.database.SaveState;

import java.util.List;

public class Game {
    private final Board board;
    private final PlayerConfig playerOne;
    private final PlayerConfig playerTwo;
    private final LegalMoves legalMoves;
    private final SaveState saveState;

    //private int firstMoveNumber;
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
        //this.firstMoveNumber = firstMoveNumber;
        this.currentMoveNumber = firstMoveNumber;
        this.activePlayer = playerOne;
        this.gameWon = false;

        saveState.cacheState(currentMoveNumber);

        if (playerOne.isAiPlayer()) {
            aiPlayer = new AiPlayer(playerOne.getPieceType(), board, legalMoves);
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
            activePlayer = playerTwo;
        } else if (playerTwo.isAiPlayer()) {
            aiPlayer = new AiPlayer(playerTwo.getPieceType(), board, legalMoves);
        }
    }

    public void makeMove(Move move) {
        executeMove(move);
        currentMoveNumber += 1;
        saveState.cacheState(currentMoveNumber);
        if (moveWinsGame()) { gameWon = true; return; }
        switchActivePlayer();

        if (activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
            if (moveWinsGame()) { gameWon = true; return; }
            switchActivePlayer();
        }
    }

    public void resetGame() {
        board.setBoardState(saveState.getCachedState(0));
        currentMoveNumber = 0;
        resetActivePlayerByTurn();
        gameWon = false;
        aiResume();
    }

    public void backOneMove() {
        if (!gameWon && currentMoveNumber > 0) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber - 1));
            currentMoveNumber -= 1;
            resetActivePlayerByTurn();
        }
    }

    public void forwardOneMove() {
        if (!gameWon && currentMoveNumber < saveState.numberOfCachedMoves() - 1) {
            board.setBoardState(saveState.getCachedState(currentMoveNumber + 1));
            currentMoveNumber += 1;
            resetActivePlayerByTurn();
        }
    }

    public void aiResume() {
        if (!gameWon && activePlayer.isAiPlayer()) {
            board.makeMove(aiPlayer.getMove());
            currentMoveNumber += 1;
            saveState.cacheState(currentMoveNumber);
            switchActivePlayer();
        }
    }

    public boolean legalStart(Square start) {
        return squareInList(start, legalMoves.legalStartingSquares(activePlayer.getPieceType()));
    }

    public boolean legalEnd(Square start, Square end) {
        return squareInList(end, legalMoves.legalEndingSquares(start, activePlayer.getPieceType()));
    }

    public Board getBoard() {
        return board;
    }

    public boolean won() {
        return gameWon;
    }

    /*private void cacheBoardState() {
        currentMoveNumber += 1;
        saveState.cacheState(currentMoveNumber);
    }*/

    private void switchActivePlayer() {
        activePlayer = (activePlayer == playerOne) ? playerTwo : playerOne;
    }

    private boolean moveWinsGame() {
        PieceType opponentPieceType = (activePlayer == playerOne)
                ? playerTwo.getPieceType()
                : playerOne.getPieceType();
        return board.totalPieces(opponentPieceType) == 0 || legalMoves.legal(opponentPieceType).size() == 0;
    }

    private void resetActivePlayerByTurn() {
        if (currentMoveNumber % 2 == 0) {
            activePlayer = playerOne;
        } else {
            activePlayer = playerTwo;
        }
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
}
