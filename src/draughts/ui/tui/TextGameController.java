package draughts.ui.tui;


import java.util.List;

import draughts.database.SaveState;
import draughts.gamecore.*;
import draughts.ui.UserInput;

class TextGameController {
    private final Board board;
    private final LegalMoves legalMoves;
    private final Player player1;
    private final Player player2;
    private final SaveState saveState;
    private final UserInput userInput;
    private List<Move> allMovesWhite;
    private List<Move> allMovesBlack;
    private boolean whiteWin;
    private boolean blackWin;
    private int moveNumber = 0;

    TextGameController(Board board,
                       LegalMoves legalMoves,
                       Player player1,
                       Player player2,
                       SaveState saveState,
                       UserInput userInput) {
        whiteWin = false;
        blackWin = false;
        this.board = board;
        this.legalMoves = legalMoves;
        this.player1 = player1;
        this.player2 = player2;
        this.saveState = saveState;
        this.userInput = userInput;
        allMovesWhite = legalMoves.legal(PieceType.WHITE_PIECE);
        allMovesBlack = legalMoves.legal(PieceType.BLACK_PIECE);
    }

    void playGame() {
        saveState.cacheState(moveNumber);
        while (!whiteWin && !blackWin) {

            if (!blackWin) {
                TextGameView.print(board, "White's Move");
                board.makeMove(player1.getMove());
                allMovesBlack = legalMoves.legal(PieceType.BLACK_PIECE);
                if (allMovesBlack.size() == 0) {
                    whiteWin = true;
                }
                moveNumber += 1;
            }

            saveState.cacheState(moveNumber);
            TextGameView.clearBoard();

            if (!whiteWin) {
                TextGameView.print(board,"Black's Move");
                board.makeMove(player2.getMove());
                allMovesWhite = legalMoves.legal(PieceType.WHITE_PIECE);
                if (allMovesWhite.size() == 0) {
                    blackWin = true;
                }
                moveNumber += 1;
            }

            saveState.cacheState(moveNumber);
        }

        if (whiteWin) {
            System.out.println("White wins!");
        } else if (blackWin) {
            System.out.println("Black wins!");
        }

        if (userInput.saveGame()) {
            String name = userInput.setGameName();
            saveState.saveGame(name);
        }
    }
}

