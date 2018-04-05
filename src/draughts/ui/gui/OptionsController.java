package draughts.ui.gui;

import draughts.database.LoadState;
import draughts.gamecore.Board;
import draughts.gamecore.PieceType;
import draughts.gamecore.PlayerConfig;
import draughts.gamecore.PlayerType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class OptionsController implements EventHandler<ActionEvent> {
    private final Stage primaryStage;
    private final OptionsView view = new OptionsView(this);
    private String selectedGame;
    private LoadState loadState = new LoadState();

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(final ActionEvent event) {
        Board board;
        BoardController boardController;
        PlayerConfig playerOne;
        PlayerConfig playerTwo;

        final Object source = event.getSource();

        if (source.equals(view.getHumanHumanButton())) {
            playerOne = new PlayerConfig(PlayerType.HUMAN, PieceType.WHITE_PIECE);
            playerTwo = new PlayerConfig(PlayerType.HUMAN, PieceType.BLACK_PIECE);
        } else if (source.equals(view.getHumanAiButton())) {
            playerOne = new PlayerConfig(PlayerType.HUMAN, PieceType.WHITE_PIECE);
            playerTwo = new PlayerConfig(PlayerType.COMPUTER, PieceType.BLACK_PIECE);
        } else {
            playerOne = new PlayerConfig(PlayerType.COMPUTER, PieceType.WHITE_PIECE);
            playerTwo = new PlayerConfig(PlayerType.HUMAN, PieceType.BLACK_PIECE);
        }

        /*if (selectedGame.equals("New Game")) {
            board = new Board();
        } else {
            board = new Board(loadState.loadState(selectedGame, 0));
        }*/

        board = new Board();

        boardController = new BoardController(board, playerOne, playerTwo);

        GamePlayView gamePlayView = new GamePlayView(primaryStage, boardController);
        gamePlayView.getGamePlayView();
    }

    /*ChangeListener<String> gameSelected = (ObservableValue<? extends String> observable,
                                           String oldName,
                                           String newName) ->
    {
        selectedGame = newName;
    };*/

    public OptionsView getOptionsView() {
        return view;
    }
}
