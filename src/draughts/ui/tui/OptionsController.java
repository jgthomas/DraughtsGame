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

    void startGame() {
        final String FIRST_PLAYER = "Pick first player";
        final String SECOND_PLAYER = "Pick second player";
        final String HUMAN_PLAYER_SET = "You are player two";
        PlayerConfig playerOne;
        PlayerConfig playerTwo;

        playerOne = makePlayer(userInput, FIRST_PLAYER, PieceType.WHITE_PIECE);
        if (playerOne.isAiPlayer()) {
            playerTwo = new PlayerConfig(PlayerType.HUMAN, PieceType.BLACK_PIECE);
            System.out.println(HUMAN_PLAYER_SET);
        } else {
            playerTwo = makePlayer(userInput, SECOND_PLAYER, PieceType.BLACK_PIECE);
        }
        Board board= makeBoard(userInput);
        Game game = new Game(board, playerOne, playerTwo);
        new GameController(game, userInput).run();
    }

    private Board makeBoard(UserInput userInput) {
        final String LOAD_GAME = "Load saved game";
        final String PICK_GAME = "Pick game number";
        Board board;

        if (userInput.getYesOrNo(LOAD_GAME)) {
            List<String> gameNames = loadState.getAllGameNames();
            printGameNames(gameNames);

            int gameNumber = userInput.getNumber(PICK_GAME);

            if (gameNumber < gameNames.size()) {
                board = pickStartingMove(gameNames.get(gameNumber));
            } else {
                board = new Board();
            }
        } else {
            board = new Board();
        }
        return board;
    }

    private Board pickStartingMove(String gameName) {
        final String NEXT_MOVE = "Press enter to see next move";
        boolean nextMove;
        int moveNumber = 0;
        BoardStateLoader boardStateLoader = loadState.loadState(gameName, moveNumber);
        do {
            new GameView(new Board(boardStateLoader)).print(boardTitle(moveNumber));
            nextMove = userInput.advanceForward(NEXT_MOVE);
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

    private String boardTitle(int moveNumber) {
        String baseMove = "Move " + moveNumber;
        if (moveNumber % 2 == 0) {
            return baseMove + " (white)";
        }
        return baseMove + " (black)";
    }

}
