package draughts.gamecore;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Iterator;

public class Move implements Comparable<Move>, Iterable<Move> {
    private final Square start;
    private final Square end;
    private final List<Move> nextTakes = new ArrayList<>();
    private final Piece piece;
    private final PieceType pieceType;
    private final PieceType kingType;
    private final MoveType moveType;
    private Square takenSquare;
    private int priority;

    public Move(Square start, Square end) {
        this(start, end, new Piece(PieceType.NONE), MoveType.MOVE, MoveType.MOVE.weight());
    }

    Move(Square start,
         Square end,
         Piece piece,
         MoveType moveType,
         int priority) {
        this.start = start;
        this.end = end;
        this.piece = piece;
        this.pieceType = piece.getPieceType();
        this.kingType = piece.getKingType();
        this.moveType = moveType;
        this.priority = priority;

        if (moveType == MoveType.TAKE) {
            takenSquare = new Square(piece.takenRow(start, end), takenColumn());
        }
    }

    public Square start() {
        return start;
    }

    public Square end() {
        return end;
    }

    public int getPriority() {
        return priority;
    }

    public void raisePriority(int raiseAmount) {
        priority += raiseAmount;
    }

    public void printTakes() {
        Collections.sort(nextTakes);
        for (Move take : nextTakes) {
            System.out.println(take.toString());
        }
    }

    PieceType getPieceType() {
        return pieceType;
    }

    PieceType getKingType() {
        return kingType;
    }

    boolean isTake() {
        return moveType == MoveType.TAKE;
    }

    void setNextTake(Move move) {
        nextTakes.add(move);
        Collections.sort(nextTakes);
    }

    Move getNextTake() {
        Collections.sort(nextTakes);
        if (nextTakes.size() < 1) {
            return null;
        }
        return nextTakes.get(0);
    }

    void updatePriority() {
        priority += maxPriorityOfNextMove();
    }

    public boolean makesKing() {
        return !pieceType.isKing() && end.row() == pieceType.kingRow();
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

    Square takenSquare() {
        return takenSquare;
    }

    private int takenColumn() {
        return (end.colHigher(start)) ? start.col() + 1 : start.col() - 1;
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

        return (this.start.equals(other.start())
                && this.end.equals(other.end()));
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
        String type = "Move";
        if (moveType == MoveType.TAKE) {
            type = "Takes " + takenSquare.toString();
        }
        return String.format("%s [(%s,%s) --> (%s,%s) {Priority: %s} {Piece: %s} {Type: %s}]",
                this.getClass().getSimpleName(),
                start.row(),
                start.col(),
                end.row(),
                end.col(),
                this.getPriority(),
                piece.toString(),
                type);
    }
}
