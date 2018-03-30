package draughts.gamecore;

public class MoveMaker {
    private final Board board;

    MoveMaker(Board board) {
        this.board = board;
    }

    public void makeMove(Move move) {
        board.setPieceType(move.startOfMove(), PieceType.NONE);

        if (move.type() == MoveType.TAKE) {
            board.setPieceType(move.takenSquare(), PieceType.NONE);
        }

        board.setPieceType(move.endOfMove(), move.getPieceType());

        if (move.getNextTake() != null) {
            makeMove(move.getNextTake());
        }

        if (move.makesKing()) {
            board.setPieceType(move.endOfMove(), move.getPieceType().getKing());
        }
    }
}

