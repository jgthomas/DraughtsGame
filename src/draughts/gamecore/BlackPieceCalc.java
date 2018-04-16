package draughts.gamecore;

class BlackPieceCalc implements PieceCalc {

    @Override
    public String pieceString() {
        return "Black";
    }

    @Override
    public String shortPieceString() {
        return "B";
    }

    @Override
    public boolean legalMoveDirection(Square start, Square end) {
        return end.rowHigher(start);
    }

    @Override
    public int takenRow(Square start, Square end) {
        return start.row() + 1;
    }

    @Override
    public PieceType kingType() {
        return PieceType.BLACK_KING;
    }
}
