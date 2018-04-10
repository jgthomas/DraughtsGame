package draughts.gamecore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Board implements Iterable<Square> {
    private final List <Square> squares = new Squares().squares;
    private final Map<Square, Piece> boardMap = new HashMap<>();
    private final int SIDE_LENGTH = 8;

    public Board() {
        for (Square square : squares) {
            boardMap.put(square, new Piece(square.getInitialPieceType()));
        }
    }

    public Board(Map<Integer, PieceType> gameState) {
        for (Square square : squares) {
            boardMap.put(square, new Piece(gameState.get(square.hashCode())));
        }
    }

    public Board(SnapShot snapShot) {
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

    private void setPieceType(Square square, PieceType pieceType) {
        boardMap.get(square).setPieceType(pieceType);
    }

    public void makeMove(Move move) {
        setPieceType(move.startOfMove(), PieceType.NONE);

        if (move.type() == MoveType.TAKE) {
            setPieceType(move.takenSquare(), PieceType.NONE);
        }

        setPieceType(move.endOfMove(), move.getPieceType());

        if (move.getNextTake() != null) {
            makeMove(move.getNextTake());
        }

        if (move.makesKing()) {
            setPieceType(move.endOfMove(), move.getPieceType().getKing());
        }
    }

    void setBoardState(Map<Integer, Integer> boardState) {
        for (Square square : squares) {
            setPieceType(square, PieceType.valueOf(boardState.get(square.hashCode())));
        }
    }

    public int sideLength() {
        return SIDE_LENGTH;
    }

    @Override
    public Iterator<Square> iterator() {
        return squares.iterator();
    }

    private final class Squares {
        private final List <Square> squares = new ArrayList<>();
        private final int LAST_WHITE_ROW = 2;
        private final int FIRST_BLACK_ROW = 5;

        private Squares() {
            for (int row = 0; row < SIDE_LENGTH; row++) {
                for (int col = 0; col < SIDE_LENGTH; col++) {
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

        private boolean bothEven(int row, int col) {
            return row %2 == 0 && col %2 == 0;
        }

        private boolean bothOdd(int row, int col) {
            return row %2 != 0 && col %2 != 0;
        }

        private boolean isWhiteRow(int row) {
            return row <= LAST_WHITE_ROW;
        }

        private boolean isBlackRow(int row) {
            return row >= FIRST_BLACK_ROW;
        }

        private boolean isWhiteSquare(int row, int col) {
            if (row % 2 == 0) {
                return col % 2 == 0;
            }
            return col % 2 != 0;
        }
    }
}
