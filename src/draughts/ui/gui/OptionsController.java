package draughts.ui.gui;

import draughts.database.CacheState;
import draughts.database.LoadState;
import draughts.gamecore.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

class OptionsController implements EventHandler<ActionEvent> {
    private final Stage primaryStage;
    private final OptionsView optionsView = OptionsView.newInstance(this);
    private final LoadState loadState = new LoadState();

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(final ActionEvent event) {
        Board board;
        CacheState cacheState;
        BoardController boardController;
        Game game;
        PlayerConfig playerOne;
        PlayerConfig playerTwo;

        final Object source = event.getSource();

        if (source.equals(optionsView.getHumanHumanButton())) {
            playerOne = new PlayerConfig(PlayerType.HUMAN, Side.WHITE);
            playerTwo = new PlayerConfig(PlayerType.HUMAN, Side.BLACK);
        } else if (source.equals(optionsView.getHumanAiButton())) {
            playerOne = new PlayerConfig(PlayerType.HUMAN, Side.WHITE);
            playerTwo = new PlayerConfig(PlayerType.COMPUTER, Side.BLACK);
        } else {
            playerOne = new PlayerConfig(PlayerType.COMPUTER, Side.WHITE);
            playerTwo = new PlayerConfig(PlayerType.HUMAN, Side.BLACK);
        }

        String selectedGame = optionsView.getSelectedGame();

        if (selectedGame.equals("New Game")) {
            board = new Board();
            cacheState = new CacheState(board);
            game = new Game(board, cacheState, playerOne, playerTwo);
        } else {
            board = new Board(loadState.loadState(selectedGame));
            cacheState = new CacheState(board, loadState.loadGameToCache(selectedGame));
            game = new Game(board, cacheState, playerOne, playerTwo, loadState.totalMoves(selectedGame));
        }

        boardController = new BoardController(game);

        GamePlayView gamePlayView = new GamePlayView(primaryStage, boardController);
        gamePlayView.show();
    }

    OptionsView getOptionsView() {
        return optionsView;
    }
}
