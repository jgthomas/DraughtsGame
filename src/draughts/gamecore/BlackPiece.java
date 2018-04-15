package draughts.gamecore;

public class BlackPiece implements PieceCalc {

    @Override
    public String pieceString() {
        return "Black";
    }

    @Override
    public boolean legalMoveDirection(Square start, Square end) {
        return start.rowHigher(end);
    }

    @Override
    public int takenRow(Square start, Square end) {
        return start.row() - 1;
    }

    @Override
    public PieceType kingType() {
        return PieceType.BLACK_KING;
    }
}
