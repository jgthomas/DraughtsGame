package draughts.gamecore;

public interface PieceCalculation {

    String pieceString();

    String shortPieceString();

    boolean legalMoveDirection(Square start, Square end);

    int takenRow(Square start, Square end);

}
