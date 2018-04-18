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
    private int moveNumber;

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        moveNumber = 0;
    }

    @Override
    public void handle(final ActionEvent event) {
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

        Board board = new Board();
        CacheState cacheState = new CacheState(board);

        if (!selectedGame.equals("New Game")) {
            board.setBoardState(loadState.loadState(selectedGame));
            cacheState.setCachedState(loadState.loadGameToCache(selectedGame));
            moveNumber = loadState.totalMoves(selectedGame);
        }

        Game game = new Game(board, cacheState, playerOne, playerTwo, moveNumber);
        BoardController boardController = new BoardController(game);
        new GamePlayView(primaryStage, boardController).show();
    }

    OptionsView getOptionsView() {
        return optionsView;
    }
}
