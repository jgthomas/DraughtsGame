package draughts.ui.tui;

import draughts.gamecore.*;

class GameController {
    private final Game game;
    private final GameView gameView;
    private final UserInput userInput;
    private final CommandRunner commandRunner;

    GameController(Game game, UserInput userInput) {
        this.game = game;
        gameView = new GameView(game.getBoard());
        this.userInput = userInput;
        commandRunner = new CommandRunner(this);
    }

    void run() {
        final String WHITE_WIN = "White wins!";
        final String BLACK_WIN = "Black wins!";
        final String FINAL_BOARD = "Final Board";

        gameView.clearBoard();

        while (game.hasNotBeenWon()) {
            displayBoard();
            game.makeMove(getInput());
            gameView.clearBoard();
        }

        gameView.print(FINAL_BOARD);

        if (game.getActiveSide() == Side.WHITE) {
            System.out.println(WHITE_WIN);
        } else {
            System.out.println(BLACK_WIN);
        }

        offerToSaveGame();
    }

    void offerToSaveGame() {
        final String SAVE_PROMPT = "Save game?";
        final String GAME_NAME_PROMPT = "Name saved game";
        if (userInput.getYesOrNo(SAVE_PROMPT)) {
            String gameName = userInput.getString(GAME_NAME_PROMPT);
            game.saveGame(gameName);
        }
    }

    void displayBoard() {
        gameView.clearBoard();
        gameView.print(turnTitle());
    }

    void backOneMove() {
        game.backOneMove();
    }

    void forwardOneMove() {
        game.forwardOneMove();
    }

    private Move getInput() {
        final String START_MOVE_MSG = "Piece to move";
        final String END_MOVE_MSG = "Move piece to";
        final String ILLEGAL_PIECE_MSG = "You cannot move that piece";
        final String ILLEGAL_END_MSG = "You cannot move to there";

        Square start = getPosition(START_MOVE_MSG);

        while (!game.legalStart(start)) {
            System.out.println(ILLEGAL_PIECE_MSG);
            start = getPosition(START_MOVE_MSG);
        }

        Square end = getPosition(END_MOVE_MSG);

        while (!game.legalEnd(start, end)) {
            System.out.println(ILLEGAL_END_MSG);
            end = getPosition(END_MOVE_MSG);
        }

        return new Move(start, end);
    }

    private Square getPosition(String message) {
        final int NUM_OF_COORDINATES = 2;
        final int COMMAND_LEN = 4;
        String input;
        do  {
            input = userInput.getString(message);
            if (input.length() >= COMMAND_LEN) {
                commandRunner.execute(input);
            }
        } while (input.length() != NUM_OF_COORDINATES);
        int rowNum = rowCharToCoordinate(input.charAt(0));
        int colNum = Character.getNumericValue(input.charAt(1));
        return new Square(rowNum, colNum);
    }

    private int rowCharToCoordinate(char c) {
        final char BASE_CHAR = 'a';
        return Character.toLowerCase(c) - BASE_CHAR;
    }

    private String turnTitle() {
        final String WHITE_MOVE_TITLE = "White's Move";
        final String BLACK_MOVE_TITLE = "Black's Move";

        if (game.getMoveNumber() % 2 == 0) {
            return WHITE_MOVE_TITLE;
        }
        return BLACK_MOVE_TITLE;
    }
}
