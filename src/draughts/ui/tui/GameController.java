package draughts.ui.tui;

import draughts.gamecore.*;

class GameController {
    private final Game game;
    private final GameView gameView;
    private final UserInput userInput;
    private final CommandRunner commandRunner;
    private final OptionsController optionsController;

    GameController(Game game, UserInput userInput, OptionsController optionsController) {
        this.game = game;
        gameView = new GameView(game.getBoard());
        this.userInput = userInput;
        this.optionsController = optionsController;
        commandRunner = new CommandRunner(this);
    }

    void run() {
        final String WHITE_WIN = "White wins!";
        final String BLACK_WIN = "Black wins!";
        final String FINAL_BOARD = "Final Board";

        while (game.hasNotBeenWon()) {
            displayBoard();
            game.makeMove(getInput());
        }

        gameView.print(FINAL_BOARD);

        if (game.getActiveSide() == Side.WHITE) {
            System.out.println(WHITE_WIN);
        } else {
            System.out.println(BLACK_WIN);
        }
    }

    void saveGame() {
        final String SAVE_PROMPT = "Save game?";
        final String GAME_NAME_PROMPT = "Name saved game";
        if (userInput.getYesOrNo(SAVE_PROMPT)) {
            String gameName = userInput.getString(GAME_NAME_PROMPT);
            game.saveGame(gameName);
        }
    }

    void backOneMove() {
        game.backOneMove();
        displayBoard();
    }

    void forwardOneMove() {
        game.forwardOneMove();
        displayBoard();
    }

    void aiResume() {
        game.makeAiMoveIfNeeded();
        displayBoard();
    }

    void restartGame() {
        game.restartGame();
        displayBoard();
    }

    void newGame() {
        gameView.clearBoard();
        optionsController.startGame();
    }

    void displayBoard() {
        gameView.clearBoard();
        gameView.print(turnTitle());
    }

    private Move getInput() {
        final String START_MOVE_MSG = "Piece to move (or help for commands)";
        final String END_MOVE_MSG = "Move piece to (or help for commands)";
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
        String input;
        do  {
            input = userInput.getString(message);
            if (input.length() > NUM_OF_COORDINATES) {
                commandRunner.execute(input);
            }
        } while (input.length() != NUM_OF_COORDINATES);
        return buildSquare(input);
    }

    private Square buildSquare(String input) {
        return new Square(
                rowCharToCoordinate(input.charAt(0)),
                Character.getNumericValue(input.charAt(1)));
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
