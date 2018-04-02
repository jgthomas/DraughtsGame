package draughts.ui.tui;

import draughts.ai.ComputerPlayer;
import draughts.database.LoadState;
import draughts.database.SaveState;
import draughts.gamecore.*;
import draughts.ui.UserInput;

import java.util.List;
import java.util.Map;


public class TextDraughts {
    private static final String FIRST_PLAYER_MESSAGE = "Pick first player";
    private static final String SECOND_PLAYER_MESSAGE = "Pick second player";

    public void run() {
        UserInput userInput = new TextUserInput();
        Board board = makeBoard(userInput);
        LegalMoves legalMoves = new LegalMoves(board);
        SaveState saveState = new SaveState(board);

        Player playerOne = makePlayer(
                PieceType.WHITE_PIECE,
                board,
                legalMoves,
                FIRST_PLAYER_MESSAGE,
                userInput);

        Player playerTwo = makePlayer(
                PieceType.BLACK_PIECE,
                board,
                legalMoves,
                SECOND_PLAYER_MESSAGE,
                userInput);

        TextGameController textGameController = new TextGameController(
                board,
                legalMoves,
                playerOne,
                playerTwo,
                saveState,
                userInput);

        textGameController.playGame();
    }

    private static Board makeBoard(UserInput userInput) {
        Board board;
        LoadState loadState = new LoadState();

        if (userInput.playSavedGame()) {
            List<String> gameNames = loadState.getAllGameNames();
            TextGameView.printGameNames(gameNames);

            int gameNumber = userInput.selectSavedGame();

            if (gameNumber < gameNames.size()) {
                String gameName = gameNames.get(gameNumber);
                int moveNumber = 0;
                Map<Integer, PieceType> gameState = loadState.loadState(gameName, moveNumber);
                boolean nextMove;

                do {
                    TextGameView.print(new Board(gameState), "Move " + moveNumber);
                    nextMove = userInput.seeNextMove();

                    if (nextMove) {
                        moveNumber += 1;
                        gameState = loadState.loadState(gameName, moveNumber);
                    }

                } while (nextMove);

                board = new Board(gameState);
            } else {
                board = new Board();
            }
        } else {
            board = new Board();
        }
        return board;
    }

    private static Player makePlayer(
            PieceType pieceType,
            Board board,
            LegalMoves legalMoves,
            String message,
            UserInput userInput) {
        Player player;
        int playerCode;

        do {
            playerCode = userInput.pickPlayerType(message);

        } while (playerCode != PlayerType.HUMAN.pick() && playerCode != PlayerType.COMPUTER.pick());

        if (PlayerType.valueOf(playerCode) == PlayerType.COMPUTER) {
            player = new ComputerPlayer(pieceType, board, legalMoves);
        } else {
            player = new TextPlayerController(pieceType, board, legalMoves);
        }

        return player;
    }
}
