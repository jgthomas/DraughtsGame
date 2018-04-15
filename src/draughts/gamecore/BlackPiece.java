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

}
