package draughts.ui.tui;


import draughts.database.LoadState;
import draughts.gamecore.*;

import java.util.List;

class OptionsController {
    private final UserInput userInput;

    OptionsController(UserInput userInput) {
        this.userInput = userInput;
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
        Board board;
        LoadState loadState = new LoadState();

        if (userInput.getYesOrNo("Load saved game")) {
            List<String> gameNames = loadState.getAllGameNames();
            printGameNames(gameNames);

            int gameNumber = userInput.getNumber("Pick game number");

            if (gameNumber < gameNames.size()) {
                String gameName = gameNames.get(gameNumber);
                int moveNumber = 0;
                BoardStateLoader gameState = loadState.loadState(gameName, moveNumber);
                boolean nextMove;

                do {
                    new GameView(new Board(gameState)).print("Move " + moveNumber);
                    nextMove = userInput.advanceForward("Press enter to see next move");

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

    private PlayerConfig makePlayer(UserInput userInput,
                                    String msg,
                                    PieceType pieceType) {

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
