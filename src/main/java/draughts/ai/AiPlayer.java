package draughts.ai;

import java.util.List;
import java.util.Random;

import draughts.gamecore.*;

public class AiPlayer implements Player {
    private static final Random rand = new Random();
    private final LegalMoves legalMoves;
    private final Side side;
    private final MoveRater moveRater;

    public AiPlayer(Board board, LegalMoves legalMoves, Side side) {
        this.legalMoves = legalMoves;
        moveRater = new RateMoves(board, side);
        this.side = side;
    }

    @Override
    public Move getMove() {
        List<Move> moveList = legalMoves.legal(side);

        if (moveList.size() == 1) {
            printMoves(moveList);
            return moveList.get(0);
        }

        List<Move> priorityMoves = moveRater.rateMoves(moveList);

        printMoves(priorityMoves);

        if (priorityMoves.size() == 1) {
            return priorityMoves.get(0);
        }

        return chooseRandomly(priorityMoves);
    }

    private static <T> T chooseRandomly(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    private static void printMoves(List<Move> moves) {
        for (Move m : moves) {
            System.out.println(m.toString());
            for (Move nTake: m) {
                System.out.println("START FOLLOW UP SEQUENCE");
                System.out.println(nTake.toString());
                nTake.printTakes();
                while (nTake.getNextTake() != null) {
                    nTake.getNextTake().printTakes();
                    nTake = nTake.getNextTake();
                }
                System.out.println("END FOLLOW UP SEQUENCE");
            }
        }
        System.out.println("###############");
    }

}
