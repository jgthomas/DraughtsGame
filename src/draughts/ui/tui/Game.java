package draughts.ui.tui;


import java.util.List;

import draughts.database.SaveState;
import draughts.gamecore.*;
import draughts.ui.UserInput;

public class Game {
    private final Board board;
    private final LegalMoves legalMoves;
    private final Player player1;
    private final Player player2;
    private final SaveState saveState;
    private final UserInput userInput;
    private List<Move> allMovesWhite;
    private List<Move> allMovesBlack;
    private int totalWhitePieces;
    private int totalBlackPieces;
    private boolean whiteWin;
    private boolean blackWin;
    private int move = 0;

    Game(Board board,
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
        totalWhitePieces = board.totalPieces(PieceType.WHITE_PIECE);
        totalBlackPieces = board.totalPieces(PieceType.BLACK_PIECE);
    }

    public void playGame() {
        saveState.cacheState(move);
        while (!whiteWin && !blackWin) {

            if (!blackWin) {
                Printer.print(board, "White's Move");
                board.makeMove(player1.getMove());
                allMovesBlack = legalMoves.legal(PieceType.BLACK_PIECE);
                totalBlackPieces = board.totalPieces(PieceType.BLACK_PIECE);
                if (totalBlackPieces == 0 || allMovesBlack.size() == 0) {
                    whiteWin = true;
                }
                move += 1;
            }

            saveState.cacheState(move);
            Printer.clearBoard();

            if (!whiteWin) {
                Printer.print(board,"Black's Move");
                board.makeMove(player2.getMove());
                allMovesWhite = legalMoves.legal(PieceType.WHITE_PIECE);
                totalWhitePieces = board.totalPieces(PieceType.WHITE_PIECE);
                if (totalWhitePieces == 0 || allMovesWhite.size() == 0) {
                    blackWin = true;
                }
                move += 1;
            }

            saveState.cacheState(move);
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

