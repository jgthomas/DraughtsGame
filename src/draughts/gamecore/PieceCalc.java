package draughts.gamecore;

interface PieceCalc {

    String pieceString();

    boolean legalMoveDirection(Square start, Square end);

    int takenRow(Square start, Square end);

    PieceType kingType();

}
