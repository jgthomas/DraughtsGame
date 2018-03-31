package draughts.ui.gui;

import draughts.gamecore.Board;
import draughts.gamecore.LegalMoves;
import javafx.scene.layout.GridPane;

public class GuiController {
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);
    private final BoardView boardView = new BoardView(board);

    public GridPane getBoardView() {
        return boardView.makeBoardView();
    }
}