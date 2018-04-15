package draughts.gamecore;

interface PieceCalc {

    String pieceString();

    String shortPieceString();

    boolean legalMoveDirection(Square start, Square end);

    int takenRow(Square start, Square end);

    PieceType kingType();

}
