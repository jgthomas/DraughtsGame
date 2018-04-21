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
        while (game.hasNotBeenWon()) {
            displayBoard();
            makeMove();
        }
        displayResult();
        playAgain();
    }

    void displayBoard() {
        gameView.clearBoard();
        gameView.print(turnTitle());
    }

    private void makeMove() {
        final int COORDINATE_LEN = 2;
        final String START_MOVE_MSG = "Piece to move (or help for commands)";
        final String END_MOVE_MSG = "Move piece to (or help for commands)";
        final String ILLEGAL_PIECE_MSG = "You cannot move that piece";
        final String ILLEGAL_END_MSG = "You cannot move to there";

        String input = userInput.getCommand(START_MOVE_MSG);
        while(!game.legalStart(buildSquare(input))) {
            if (input.length() > COORDINATE_LEN) {
                commandRunner.execute(input);
            } else {
                System.out.println(ILLEGAL_PIECE_MSG);
            }
            input = userInput.getCommand(START_MOVE_MSG);
        }

        Square start = buildSquare(input);

        input = userInput.getCommand(END_MOVE_MSG);
        while(!game.legalEnd(start, buildSquare(input))) {
            if (input.length() > COORDINATE_LEN) {
                commandRunner.execute(input);
            } else {
                System.out.println(ILLEGAL_END_MSG);
            }
            input = userInput.getCommand(END_MOVE_MSG);
        }
        game.makeMove(new Move(start, buildSquare(input)));
    }

    private void displayResult() {
        final String WHITE_WIN = "White wins!";
        final String BLACK_WIN = "Black wins!";
        final String FINAL_BOARD = "Final Board";

        gameView.print(FINAL_BOARD);

        if (game.getActiveSide() == Side.WHITE) {
            System.out.println(WHITE_WIN);
        } else {
            System.out.println(BLACK_WIN);
        }
    }

    private void playAgain() {
        final String PLAY_AGAIN = "Play another game?";
        if (userInput.getYesOrNo(PLAY_AGAIN)) {
            newGame();
        }
    }

    void saveGame() {
        final String SAVE_PROMPT = "Save game?";
        if (userInput.getYesOrNo(SAVE_PROMPT)) {
            game.saveGame(userInput.getName());
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
