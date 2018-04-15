package draughts.gamecore;

class WhitePiece implements PieceCalc {

    @Override
    public String pieceString() {
        return "White";
    }

    @Override
    public boolean legalMoveDirection(Square start, Square end) {
        return end.rowHigher(start);
    }

    @Override
    public int takenRow(Square start, Square end) {
        return start.row() + 1;
    }

}