package draughts.ui.tui;


import draughts.gamecore.Game;
import draughts.gamecore.Move;
import draughts.gamecore.PieceType;
import draughts.gamecore.Square;

import java.util.Scanner;

public class GameController {
    private final char BASE_CHAR = 'a';
    private final int NUM_OF_COORDINATES = 2;
    private final String ILLEGAL_PIECE_MSG = "You cannot move that piece";
    private final String ILLEGAL_END_MSG = "You cannot move to there";
    private final String START_MOVE_MSG = "Piece to move: ";
    private final String END_MOVE_MSG = "Move piece to: ";
    private final String WHITE_MOVE_TITLE = "White's Move";
    private final String BLACK_MOVE_TITLE = "Black's Move";
    private final String WHITE_WIN_MESSAGE = "White wins!";
    private final String BLACK_WIN_MESSAGE = "Black wins!";
    private final String FINAL_BOARD_MESSAGE = "Final Board";
    private final Game game;
    private final GameView gameView;

    GameController(Game game) {
        this.game = game;
        gameView = new GameView(game.getBoard());
    }

    void run() {
        while (!game.won()) {
            gameView.print(turnTitle());
            game.makeMove(getInput());
        }

        gameView.print(FINAL_BOARD_MESSAGE);

        if (game.getActivePieceType() == PieceType.WHITE_PIECE) {
            System.out.println(WHITE_WIN_MESSAGE);
        } else {
            System.out.println(BLACK_WIN_MESSAGE);
        }
    }

    private Move getInput() {
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
        Scanner scanner = new Scanner(System.in);
        String coordinates;
        do  {
            System.out.print(message);
            coordinates = scanner.next();
        } while (coordinates.length() < NUM_OF_COORDINATES);
        int rowNum = rowCharToCoordinate(coordinates.charAt(0));
        int colNum = Character.getNumericValue(coordinates.charAt(1));
        return new Square(rowNum, colNum);
    }

    private int rowCharToCoordinate(char c) {
        return Character.toLowerCase(c) - BASE_CHAR;
    }

    private String turnTitle() {
        if (game.getMoveNumber() % 2 == 0) {
            return WHITE_MOVE_TITLE;
        }
        return BLACK_MOVE_TITLE;
    }
}
