package draughts.ui.tui;

import draughts.gamecore.Board;
import draughts.gamecore.Square;


final class GameView {
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
            String pieceToken = board.getPiece(square).pieceToken();
            if (pieceToken.length() == 1) {
                System.out.print(" " + pieceToken + " ");
            } else {
                System.out.print(pieceToken);
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

