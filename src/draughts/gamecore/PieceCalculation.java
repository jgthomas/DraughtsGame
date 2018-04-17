package draughts.gamecore;

public interface PieceCalculation {

    boolean legalMoveDirection(Square start, Square end);

    int takenRow(Square start, Square end);

}
