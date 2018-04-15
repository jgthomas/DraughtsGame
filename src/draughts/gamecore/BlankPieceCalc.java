package draughts.gamecore;

class BlankPieceCalc implements PieceCalc {

    @Override
    public String pieceString() {
        return "None";
    }

    @Override
    public boolean legalMoveDirection(Square start, Square end) {
        return false;
    }

    @Override
    public int takenRow(Square start, Square end) {
        return start.row();
    }

    @Override
    public PieceType kingType() {
        return PieceType.NONE;
    }
}
