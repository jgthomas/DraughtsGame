package draughts.gamecore;

public class BlankPiece implements PieceCalc {

    @Override
    public String pieceString() {
        return "None";
    }

    @Override
    public boolean legalMoveDirection(Square start, Square end) {
        return false;
    }

}
