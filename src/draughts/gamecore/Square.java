package draughts.gamecore;

import java.util.Objects;

public final class Square implements Comparable<Square> {
    private final int row;
    private final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    boolean rowHigher(Square end) {
        return this.row() > end.row();
    }

    boolean colHigher(Square end) {
        return this.col() > end.col();
    }

    boolean isLegalMoveDistance(Square end) {
        final int MOVE_DIST = 1;
        return Math.abs(this.col() - end.col()) == MOVE_DIST &&
                Math.abs(this.row() - end.row()) == MOVE_DIST;
    }

    boolean isLegalTakeDistance(Square end) {
        final int TAKE_DIST = 2;
        return Math.abs(this.col() - end.col()) == TAKE_DIST &&
                Math.abs(this.row() - end.row()) == TAKE_DIST;
    }

    boolean isWithinRange(Square end) {
        return isLegalMoveDistance(end) || isLegalTakeDistance(end);
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

        return (this.row() == other.row() && this.col() == other.col());
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + row + "," + col + "]";
    }

    @Override
    public int compareTo(Square compareSquare) {
        if (this.row() > compareSquare.row()) { return 1; }
        if (this.row() < compareSquare.row()) { return -1; }
        return Integer.compare(this.col(), compareSquare.col());
    }
}
