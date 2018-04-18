package draughts.gamecore;

interface PieceCalculation {

    boolean legalMoveDirection(Square start, Square end);

    int takenRow(Square start, Square end);

}
