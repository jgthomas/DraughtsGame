package draughts.gamecore;

import java.util.Objects;

public final class Square {
    private static final int MOVE_DIST = 1;
    private static final int TAKE_DIST = 2;
    private final int rowNum;
    private final int colNum;
    private final PieceType initialPieceType;
    private final SquareColor squareColor;

    public Square(int row, int col) {
        this(row, col, PieceType.NONE, SquareColor.NONE);
    }

    Square(int row, int col, PieceType initialPieceType, SquareColor squareColor) {
        rowNum = row;
        colNum = col;
        this.initialPieceType = initialPieceType;
        this.squareColor = squareColor;
    }

    public int row() {
        return rowNum;
    }

    public int col() {
        return colNum;
    }

    boolean rowHigher(Square end) {
        return this.row() > end.row();
    }

    boolean colHigher(Square end) {
        return this.col() > end.col();
    }

    PieceType getInitialPieceType() {
        return initialPieceType;
    }

    boolean isLegalMoveDistance(Square end) {
        return Math.abs(this.col() - end.col()) == MOVE_DIST &&
                Math.abs(this.row() - end.row()) == MOVE_DIST;
    }

    boolean isLegalTakeDistance(Square end) {
        return Math.abs(this.col() - end.col()) == TAKE_DIST &&
                Math.abs(this.row() - end.row()) == TAKE_DIST;
    }

    boolean isWithinRange(Square end) {
        return isLegalMoveDistance(end) || isLegalTakeDistance(end);
    }

    public SquareColor getSquareColor() {
        return squareColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!Square.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        Square other = (Square) o;

        return (this.rowNum == other.rowNum && this.colNum == other.colNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, colNum);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + rowNum + "," + colNum + "]";
    }
}
