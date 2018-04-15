package draughts.gamecore;

interface PieceCalc {

    String pieceString();

    boolean legalMoveDirection(Square start, Square end);

}
