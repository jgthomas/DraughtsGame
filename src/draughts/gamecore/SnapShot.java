package draughts.gamecore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class SnapShot implements Iterable<SnapShot.SquareState> {
    private final List<SquareState> boardState = new ArrayList<>();

    SnapShot(Board board) {
        for (Square square : board) {
            boardState.add(new SquareState(square, board.getPieceType(square)));
        }
    }

    @Override
    public Iterator<SquareState> iterator() {
        return getSquareState();
    }

    private Iterator<SquareState> getSquareState() {
        return boardState.iterator();
    }

    static final class SquareState {
        private final Square square;
        private final PieceType pieceType;

        SquareState(Square square, PieceType pieceType) {
            this.square = square;
            this.pieceType = pieceType;
        }

        Square getSquare() {
            return square;
        }

        PieceType getPieceType() {
            return pieceType;
        }
    }
}
