package draughts.ui.tui;


import draughts.database.LoadState;
import draughts.gamecore.*;

import java.util.List;

class OptionsController {
    private final UserInput userInput;
    private final LoadState loadState;

    OptionsController(UserInput userInput) {
        this.userInput = userInput;
        loadState = new LoadState();
    }

    void startGameController() {
        final String FIRST_PLAYER_MESSAGE = "Pick first player";
        final String SECOND_PLAYER_MESSAGE = "Pick second player";

        PlayerConfig playerOne = makePlayer(userInput,
                FIRST_PLAYER_MESSAGE,
                PieceType.WHITE_PIECE);

        PlayerConfig playerTwo = makePlayer(userInput,
                SECOND_PLAYER_MESSAGE,
                PieceType.BLACK_PIECE);

        Board board= makeBoard(userInput);

        Game game = new Game(board, playerOne, playerTwo);

        new GameController(game, userInput).run();
    }

    private Board makeBoard(UserInput userInput) {
        final String LOAD_SAVED_GAME_PROMPT = "Load saved game";
        final String PICK_GAME_NUMBER_PROMPT = "Pick game number";

        Board board;

        if (userInput.getYesOrNo(LOAD_SAVED_GAME_PROMPT)) {
            List<String> gameNames = loadState.getAllGameNames();
            printGameNames(gameNames);

            int gameNumber = userInput.getNumber(PICK_GAME_NUMBER_PROMPT);

            if (gameNumber < gameNames.size()) {
                board = moveNumberFromGame(gameNames.get(gameNumber));
            } else {
                board = new Board();
            }
        } else {
            board = new Board();
        }
        return board;
    }

    private Board moveNumberFromGame(String gameName) {
        final String NEXT_MOVE_PROMPT = "Press enter to see next move";
        boolean nextMove;
        int moveNumber = 0;
        BoardStateLoader boardStateLoader = loadState.loadState(gameName, moveNumber);
        do {
            new GameView(new Board(boardStateLoader)).print("Move " + moveNumber);
            nextMove = userInput.advanceForward(NEXT_MOVE_PROMPT);
            if (nextMove) {
                moveNumber += 1;
                boardStateLoader = loadState.loadState(gameName, moveNumber);
            }
        } while (nextMove);
        return new Board(boardStateLoader);
    }

    private PlayerConfig makePlayer(
            UserInput userInput,
            String msg,
            PieceType pieceType)
    {
        int playerCode;
        do {
            playerCode = userInput.pickPlayerType(msg);
        } while (playerCode != PlayerType.HUMAN.pick()
                && playerCode != PlayerType.COMPUTER.pick());
        return new PlayerConfig(PlayerType.valueOf(playerCode), pieceType);
    }

    private void printGameNames(List<String> gameNames) {
        int gameNumber = 0;
        for (String gameName : gameNames) {
            System.out.println(gameNumber + ": " + gameName);
            gameNumber += 1;
        }
    }

}
