package draughts.ai;

import java.util.List;
import java.util.Random;

import draughts.gamecore.*;
import draughts.ui.tui.TextGameView;

public class ComputerPlayer implements Player {
    private static Random rand = new Random();
    private final LegalMoves legalMoves;
    private final PieceType pieceType;
    private final MoveRater moveRater;

    public ComputerPlayer(PieceType pieceType, Board board, LegalMoves legalMoves) {
        this.legalMoves = legalMoves;
        this.pieceType = pieceType;
        moveRater = new RateMoves(board, pieceType);
    }

    @Override
    public Move getMove() {
        List<Move> moveList = legalMoves.legal(pieceType);

        if (moveList.size() == 1) {
            TextGameView.printMoves(moveList);
            return moveList.get(0);
        }

        List<Move> priorityMoves = moveRater.ratedMoves(moveList);
        TextGameView.printMoves(priorityMoves);

        if (priorityMoves.size() == 1) {
            return priorityMoves.get(0);
        }

        return chooseRandomly(priorityMoves);
    }

    private static <T> T chooseRandomly(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }
}
