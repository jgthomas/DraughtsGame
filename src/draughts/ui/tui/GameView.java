package draughts.ui.tui;

import draughts.gamecore.Board;
import draughts.gamecore.Square;

import java.util.List;

final class GameView {
    private final String whitePieceSymbol = "W";
    private final String blackPieceSymbol = "B";
    private final String whiteKingSymbol = "W!W";
    private final String blackKingSymbol = "B!B";
    private final Board board;

    GameView(Board board) {
        this.board = board;
    }

    void print(String title) {
        System.out.println("\n\n" + title);
        char rowName = 'a';
        printColumnNumbers(board.sideLength());
        for (Square square : board) {
            if (newRow(square)) {
                if (notFirstRow(square)) { System.out.println(); }
                System.out.print(rowName + "   ");
                rowName += 1;
            }
            switch (board.getPieceType(square)) {
                case WHITE_PIECE:
                    System.out.print(" " + whitePieceSymbol + " ");
                    break;
                case BLACK_PIECE:
                    System.out.print(" " + blackPieceSymbol + " ");
                    break;
                case WHITE_KING:
                    System.out.print(whiteKingSymbol);
                    break;
                case BLACK_KING:
                    System.out.print(blackKingSymbol);
                    break;
                case NONE:
                    System.out.print(" - ");
                    break;
            }
        }
        System.out.println();
        System.out.println();
    }

    void clearBoard() {
        System.out.print("\033[H\033[2J");
    }

    private void printColumnNumbers(int numOfCols) {
        System.out.println();
        System.out.print("    ");
        for (int colNum = 0; colNum < numOfCols; colNum += 1) {
            System.out.print(" " + colNum + " ");
        }
        System.out.println("\n");
    }

    private boolean newRow(Square square) {
        return square.col() % board.sideLength() == 0;
    }

    private boolean notFirstRow(Square square) {
        return square.row() > 0;
    }
}

