package draughts.ui.gui;

import draughts.gamecore.PieceType;
import draughts.gamecore.PlayerConfig;
import draughts.gamecore.PlayerType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class OptionsController implements EventHandler<ActionEvent> {
    private final Stage primaryStage;
    private final OptionsView view = new OptionsView(this);

    OptionsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(final ActionEvent event) {
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

        boardController = new BoardController(playerOne, playerTwo);

        GamePlayView gamePlayView = new GamePlayView(primaryStage, boardController);
        gamePlayView.getGamePlayView();
    }

    public OptionsView getOptionsView() {
        return view;
    }
}
