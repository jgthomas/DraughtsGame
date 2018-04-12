package draughts.ui.tui;


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

        Board board = new Board();

        PlayerConfig playerOne = makePlayer(userInput,
                FIRST_PLAYER_MESSAGE,
                PieceType.WHITE_PIECE);

        PlayerConfig playerTwo = makePlayer(userInput,
                SECOND_PLAYER_MESSAGE,
                PieceType.BLACK_PIECE);

        Game game = new Game(board, playerOne, playerTwo);

        new GameController(game).run();
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

    void printGameNames(List<String> gameNames) {
        int gameNumber = 0;
        for (String gameName : gameNames) {
            System.out.println(gameNumber + ": " + gameName);
            gameNumber += 1;
        }
    }

}
