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
}
