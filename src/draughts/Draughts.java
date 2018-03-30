package draughts;

import java.util.List;
import java.util.Map;
import javafx.application.Application;

import draughts.gamecore.*;
import draughts.database.LoadState;
import draughts.database.SaveState;
import draughts.ui.gui.DraughtsGUI;
import draughts.ui.UserInput;
import draughts.ui.tui.textUiInput;
import draughts.ui.tui.Printer;


public class Draughts {
    private static final String FIRST_PLAYER_MESSAGE = "Pick first player";
    private static final String SECOND_PLAYER_MESSAGE = "Pick second player";
    private static final String GUI = "gui";
    private static final String TUI = "text";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase(GUI)) {
            Application.launch(DraughtsGUI.class);

        } else {
            UserInput userInput = new textUiInput();
            Board board = makeBoard(userInput);
            LegalMoves legalMoves = new LegalMoves(board);
            MoveMaker moveMaker = new MoveMaker(board);
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

            Game game = new Game(
                    board,
                    legalMoves,
                    playerOne,
                    playerTwo,
                    moveMaker,
                    saveState,
                    userInput);

            game.playGame();
        }
    }

    private static Board makeBoard(UserInput userInput) {
        Board board;
        LoadState loadState = new LoadState();

        if (userInput.playSavedGame()) {
            List<String> gameNames = loadState.getAllGameNames();
            Printer.printGameNames(gameNames);

            int gameNumber = userInput.selectSavedGame();

            if (gameNumber < gameNames.size()) {
                String gameName = gameNames.get(gameNumber);
                int moveNumber = 0;
                Map<Integer, PieceType> gameState = loadState.loadState(gameName, moveNumber);
                boolean nextMove;

                do {
                    Printer.print(new Board(gameState), "Move " + moveNumber);
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
            player = new HumanPlayer(pieceType, board, legalMoves);
        }

        return player;
    }
}
