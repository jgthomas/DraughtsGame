package draughts.gamecore;

import java.util.List;
import java.util.Random;

import draughts.ui.tui.Printer;

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
            Printer.printMoves(moveList);
            return moveList.get(0);
        }

        List<Move> priorityMoves = moveRater.ratedMoves(moveList);
        Printer.printMoves(priorityMoves);

        if (priorityMoves.size() == 1) {
            return priorityMoves.get(0);
        }

        return chooseRandomly(priorityMoves);
    }

    private static <T> T chooseRandomly(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }
}