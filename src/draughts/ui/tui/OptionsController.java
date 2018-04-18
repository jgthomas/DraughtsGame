package draughts.ui.tui;


import draughts.database.CacheState;
import draughts.database.LoadState;
import draughts.gamecore.*;

import java.util.List;

class OptionsController {
    private final UserInput userInput;
    private final LoadState loadState;
    private int moveNumber;

    OptionsController(UserInput userInput) {
        this.userInput = userInput;
        loadState = new LoadState();
        moveNumber = 0;
    }

    void startGame() {
        final String FIRST_PLAYER = "Pick first player";
        final String SECOND_PLAYER = "Pick second player";
        final String HUMAN_PLAYER_SET = "You are player two";
        final String LOAD_GAME = "Load saved game";
        final String PICK_GAME = "Pick game number";
        PlayerConfig playerOne;
        PlayerConfig playerTwo;
        Board board;
        CacheState cacheState;

        playerOne = makePlayer(userInput, FIRST_PLAYER, Side.WHITE);
        if (playerOne.isAiPlayer()) {
            playerTwo = new PlayerConfig(PlayerType.HUMAN, Side.BLACK);
            System.out.println(HUMAN_PLAYER_SET);
        } else {
            playerTwo = makePlayer(userInput, SECOND_PLAYER, Side.BLACK);
        }

        if (userInput.getYesOrNo(LOAD_GAME)) {
            List<String> gameNames = loadState.getAllGameNames();
            printGameNames(gameNames);
            int gameNumber = userInput.getNumberInRange(0, gameNames.size(), PICK_GAME);
            board = new Board(loadState.loadState(gameNames.get(gameNumber)));
            cacheState = new CacheState(board, loadState.loadGameToCache(gameNames.get(gameNumber)));
        } else {
            board = new Board();
            cacheState = new CacheState(board);
        }

        Game game = new Game(board, cacheState, playerOne, playerTwo, moveNumber);
        new GameController(game, userInput).run();
    }

    private PlayerConfig makePlayer(UserInput userInput, String msg, Side side) {
        int playerCode;
        do {
            playerCode = userInput.pickPlayerType(msg);
        } while (playerCode != PlayerType.HUMAN.pick()
                && playerCode != PlayerType.COMPUTER.pick());
        return new PlayerConfig(PlayerType.valueOf(playerCode), side);
    }

    private void printGameNames(List<String> gameNames) {
        int gameNumber = 0;
        for (String gameName : gameNames) {
            System.out.println(gameNumber + ": " + gameName);
            gameNumber += 1;
        }
    }
}
