package draughts.gamecore;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Iterator;

public class Move implements Comparable<Move>, Iterable<Move> {
    private static final int WHITE_KING_LINE = 7;
    private static final int BLACK_KING_LINE = 0;
    private final Square start;
    private final Square end;
    private final List<Move> nextTakes = new ArrayList<>();
    private PieceType pieceType;
    private MoveType moveType;
    private int priority;

    Move(Square start, Square end) {
        this.start = start;
        this.end = end;
    }

    Move(Square start,
         Square end,
         PieceType pieceType,
         MoveType moveType,
         int priority) {
        this.start = start;
        this.end = end;
        this.pieceType = pieceType;
        this.moveType = moveType;
        this.priority = priority;
    }

    Square startOfMove() {
        return start;
    }

    Square endOfMove() {
        return end;
    }

    int getPriority() {
        return priority;
    }

    void raisePriority(int raiseAmount) {
        priority += raiseAmount;
    }

    PieceType getPieceType() {
        return pieceType;
    }

    MoveType type() {
        return moveType;
    }

    void setNextTake(Move move) {
        nextTakes.add(move);
        Collections.sort(nextTakes);
    }

    public Move getNextTake() {
        Collections.sort(nextTakes);
        if (nextTakes.size() < 1) {
            return null;
        }
        return nextTakes.get(0);
    }

    public void printTakes() {
        Collections.sort(nextTakes);
        for (Move take : nextTakes) {
            System.out.println(take.toString());
        }
    }

    void updatePriority() {
        priority += maxPriorityOfNextMove();
    }

    boolean makesKing() {
        return pieceType.in(PieceType.WHITE_PIECE, PieceType.BLACK_PIECE)
                && end.row() == pieceType.kingLine();
    }

    private int maxPriorityOfNextMove() {
        int max = 0;
        for (Move take : nextTakes) {
            if (take.getPriority() > max) {
                max = take.getPriority();
            }
        }
        return max;
    }

    public Square takenSquare() {
        int takenRow;
        int takenCol;
        switch (pieceType) {
            case WHITE_PIECE:
                takenRow = start.row() + 1;
                break;
            case BLACK_PIECE:
                takenRow = start.row() - 1;
                break;
            case BLACK_KING:
            case WHITE_KING:
            default:
                takenRow = (end.rowHigher(start)) ? start.row() + 1 : start.row() - 1;
                break;
        }
        takenCol = (end.colHigher(start)) ? start.col() + 1 : start.col() - 1;
        return new Square(takenRow, takenCol);
    }

    @Override
    public Iterator<Move> iterator() {
        return nextTakes.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!Move.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        Move other = (Move) o;

        return (this.start.equals(other.startOfMove())
                && this.end.equals(other.endOfMove()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public int compareTo(Move compareMove) {
        return compareMove.getPriority() - this.getPriority();
    }

    @Override
    public String toString() {
        String piece = "None";
        switch (pieceType) {
            case WHITE_PIECE:
                piece = "White";
                break;
            case BLACK_PIECE:
                piece = "Black";
                break;
            case WHITE_KING:
                piece = "White King";
                break;
            case BLACK_KING:
                piece = "Black King";
                break;
        }
        String type = "Move";
        if (moveType == MoveType.TAKE) {
            type = "Takes " + takenSquare().toString();
        }
        return String.format("%s [(%s,%s) --> (%s,%s) {Priority: %s} {Piece: %s} {Type: %s}]",
                this.getClass().getSimpleName(),
                start.row(),
                start.col(),
                end.row(),
                end.col(),
                this.getPriority(),
                piece,
                type);
    }
}
