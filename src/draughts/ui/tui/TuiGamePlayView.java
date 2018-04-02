package draughts.ui.tui;

import java.util.List;

import draughts.gamecore.Board;
import draughts.gamecore.Move;
import draughts.gamecore.PieceType;
import draughts.gamecore.Square;

public final class TuiGamePlayView {
    private static final String whitePieceSymbol = "W";
    private static final String blackPieceSymbol = "B";
    private static final String whiteKingSymbol = "W!W";
    private static final String blackKingSymbol = "B!B";

    public static void print(Board board, String title) {
        System.out.println("\n" + title);
        char rowName = 'a';
        printColumnNumbers(board.max());
        for (Square square : board) {
            if (newRow(board, square)) {
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

    public static void list(Board board) {
        System.out.println();
        for (Square square : board) {
            switch (board.getPieceType(square)) {
                case WHITE_PIECE:
                    System.out.print(whitePieceSymbol);
                    break;
                case BLACK_PIECE:
                    System.out.print(blackPieceSymbol);
                    break;
                case WHITE_KING:
                    System.out.print(whiteKingSymbol);
                    break;
                case BLACK_KING:
                    System.out.print(blackKingSymbol);
                    break;
            }

            if (board.getPieceType(square) != PieceType.NONE) {
                System.out.print(" " + square.toString());
                System.out.println();
            }
        }
    }

    public static void printMoves(List<Move> moves) {
        for (Move m : moves) {
            System.out.println(m.toString());
            for (Move nTake: m) {
                System.out.println("START FOLLOW UP SEQUENCE");
                System.out.println(nTake.toString());
                nTake.printTakes();
                while (nTake.getNextTake() != null) {
                    nTake.getNextTake().printTakes();
                    nTake = nTake.getNextTake();
                }
                System.out.println("END FOLLOW UP SEQUENCE");
            }
            System.out.println();
        }
    }

    public static void printGameNames(List<String> gameNames) {
        int gameNumber = 0;
        for (String gameName : gameNames) {
            System.out.println(gameNumber + ": " + gameName);
            gameNumber += 1;
        }
    }

    public static void clearBoard() {
        System.out.print("\033[H\033[2J");
    }

    private static void printColumnNumbers(int numOfCols) {
        System.out.println();
        System.out.print("    ");
        for (int colNum = 0; colNum < numOfCols; colNum += 1) {
            System.out.print(" " + colNum + " ");
        }
        System.out.println("\n");
    }

    private static boolean newRow(Board board, Square square) {
        return square.col() % board.max() == 0;
    }

    private static boolean notFirstRow(Square square) {
        return square.row() > 0;
    }
}

