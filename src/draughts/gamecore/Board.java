package draughts.gamecore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Board implements Iterable<Square> {
    private static final int MAX = 8;
    private static final List <Square> squares = new ArrayList<>();

    static {
        new Squares();
    }

    private final Map<Square, Piece> boardMap = new HashMap<>();

    Board() {
        for (Square square : squares) {
            boardMap.put(square, new Piece(square.getInitialPieceType()));
        }
    }

    Board(Map<Integer, PieceType> gameState) {
        for (Square square : squares) {
            boardMap.put(square, new Piece(gameState.get(square.hashCode())));
        }
    }

    Board(SnapShot snapShot) {
        for (SnapShot.SquareState squareState : snapShot){
            boardMap.put(squareState.getSquare(), new Piece(squareState.getPieceType()));
        }
    }

    public Piece getPiece(Square square) {
        return boardMap.get(square);
    }

    public PieceType getPieceType(Square square) {
        return boardMap.get(square).getPieceType();
    }

    public void setPieceType(Square square, PieceType pieceType) {
        boardMap.get(square).setPieceType(pieceType);
    }

    public int max() {
        return MAX;
    }

    public int totalPieces(PieceType pieceType) {
        int pieceCount = 0;
        if (pieceType.isWhite()) {
            pieceCount = countOf(PieceType.WHITE_PIECE) + countOf(PieceType.WHITE_KING);
        } else if (pieceType.isBlack()) {
            pieceCount = countOf(PieceType.BLACK_PIECE) + countOf(PieceType.BLACK_KING);
        }
        return pieceCount;
    }

    private int countOf(PieceType pieceType) {
        int pieceCount = 0;
        for (Square square : squares) {
            if (getPieceType(square) == pieceType) {
                pieceCount += 1;
            }
        }
        return pieceCount;
    }

    @Override
    public Iterator<Square> iterator() {
        return squares.iterator();
    }

    private static final class Squares {
        private static final int LAST_WHITE_ROW = 2;
        private static final int FIRST_BLACK_ROW = 5;

        private Squares() {
            for (int row = 0; row < MAX; row++) {
                for (int col = 0; col < MAX; col++) {
                    PieceType pieceType = PieceType.NONE;
                    SquareColor squareColor = SquareColor.BLACK;
                    if (isWhiteSquare(row, col)) {
                        squareColor = SquareColor.WHITE;
                    }
                    if (bothEven(row, col) || bothOdd(row, col)) {
                        if (isWhiteRow(row)) {
                            pieceType = PieceType.WHITE_PIECE;
                        } else if (isBlackRow(row)) {
                            pieceType = PieceType.BLACK_PIECE;
                        }
                    }
                    squares.add(new Square(row, col, pieceType, squareColor));
                }
            }
        }

        private static boolean bothEven(int row, int col) {
            return row %2 == 0 && col %2 == 0;
        }

        private static boolean bothOdd(int row, int col) {
            return row %2 != 0 && col %2 != 0;
        }

        private static boolean isWhiteRow(int row) {
            return row <= LAST_WHITE_ROW;
        }

        private static boolean isBlackRow(int row) {
            return row >= FIRST_BLACK_ROW;
        }

        private static boolean isWhiteSquare(int row, int col) {
            if (row % 2 == 0) {
                return col % 2 == 0;
            }
            return col % 2 != 0;
        }
    }
}
