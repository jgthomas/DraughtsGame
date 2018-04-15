package draughts.ai;

import java.util.List;
import java.util.Random;

import draughts.gamecore.*;

public class AiPlayer implements Player {
    private static final Random rand = new Random();
    private final LegalMoves legalMoves;
    //private final PieceType pieceType;
    private final Side side;
    private final MoveRater moveRater;

    public AiPlayer(PieceType pieceType, Board board, LegalMoves legalMoves, Side side) {
        this.legalMoves = legalMoves;
        //this.pieceType = pieceType;
        moveRater = new RateMoves(board, side);
        this.side = side;
    }

    @Override
    public Move getMove() {
        List<Move> moveList = legalMoves.legal(side);

        if (moveList.size() == 1) {
            return moveList.get(0);
        }

        List<Move> priorityMoves = moveRater.rateMoves(moveList);

        if (priorityMoves.size() == 1) {
            return priorityMoves.get(0);
        }

        return chooseRandomly(priorityMoves);
    }

    private static <T> T chooseRandomly(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }
}
