package draughts.gamecore;

class WhitePieceCalc implements PieceCalc {

    @Override
    public String pieceString() {
        return "White";
    }

    @Override
    public String shortPieceString() {
        return "W";
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
        return PieceType.WHITE_KING;
    }
}
