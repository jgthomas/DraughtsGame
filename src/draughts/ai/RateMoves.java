package draughts.ai;

import draughts.gamecore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RateMoves implements MoveRater {
    private final Board board;
    private final Side side;
    private final BoardNav boardNav;

    RateMoves(Board board, Side side) {
        this.board = board;
        this.side = side;
        boardNav = new BoardNav(board, side);
    }

    public List<Move> rateMoves(List<Move> legalMoves) {
        final int SMALL_INCREASE = 1;
        final int MEDIUM_INCREASE = 2;
        final int LARGE_INCREASE = 10;
        final List<Move> rated = new ArrayList<>(legalMoves);
        int maxPriority = 0;

        for (Move move : rated) {
            if (isNotGuardingLastRow(move)) { move.raisePriority(SMALL_INCREASE); }
            if (move.makesKing()) { move.raisePriority(LARGE_INCREASE); }
            if (staysCentral(move)) { move.raisePriority(SMALL_INCREASE); }
            if (movesCentral(move)) { move.raisePriority(MEDIUM_INCREASE); }
            if (noEnemyInFront(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isDefendedLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isDefendedRight(move)) { move.raisePriority(SMALL_INCREASE); }
            if (canDefendLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (canDefendRight(move)) { move.raisePriority(SMALL_INCREASE); }
            //if (isNotCurrentlyDefendingLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            //if (isNotCurrentlyDefendingRight(move)) { move.raisePriority(SMALL_INCREASE); }

            if (move.getPriority() > maxPriority) { maxPriority = move.getPriority(); }

        }

        int topPriority = maxPriority;

        return rated.stream()
                .filter(move -> move.getPriority() == topPriority)
                .collect(Collectors.toList());
    }

    private boolean isNotGuardingLastRow(Move move) {
        return (side.isWhite())
                ? move.start().row() != 0
                : move.start().row() != (board.sideLength() - 1);
    }

    private boolean staysCentral(Move move) {
        return boardNav.isCentralCol(move.start()) && boardNav.isCentralCol(move.end());
    }

    private boolean movesCentral(Move move) {
        return !boardNav.isCentralCol(move.start()) && boardNav.isCentralCol(move.end());
    }

    private boolean noEnemyInFront(Move move) {
        if (boardNav.isCentralRow(move.end()) && boardNav.isCentralCol(move.end())) {
            return board.getPiece(boardNav.toFrontLeftOf(move.end())).isBlank()
                    && board.getPiece(boardNav.toFrontRightOf(move.end())).isBlank();
        }
        return true;
    }

    private boolean isDefendedLeft(Move move) {
        return board.validSquare(boardNav.toBackLeftOf(move.end()))
                && board.getPiece(boardNav.toBackLeftOf(move.end())).isSameSide(side);
    }

    private boolean isDefendedRight(Move move) {
        return board.validSquare(boardNav.toBackRightOf(move.end()))
                && board.getPiece(boardNav.toBackRightOf(move.end())).isSameSide(side);
    }

    private boolean canDefendLeft(Move move) {
        return boardNav.isCentral(move.end())
                && board.getPiece(boardNav.toFrontLeftOf(move.end())).isSameSide(side);
    }

    private boolean canDefendRight(Move move) {
        return boardNav.isCentral(move.end())
                && board.getPiece(boardNav.toFrontRightOf(move.end())).isSameSide(side);
    }

    /*private boolean isNotCurrentlyDefendingLeft(Move move) {
        if (boardNav.isCentral(move.start())
                || (move.getPiece().isNotKingRow(move.start()) && boardNav.isNotAtLeftEdge(move.start()))) {
            return board.getPiece(boardNav.toFrontLeftOf(move.start())).isNotSameSide(side);
        }
        return true;
    }

    private boolean isNotCurrentlyDefendingRight(Move move) {
        if (boardNav.isCentral(move.start())
                || (move.getPiece().isNotKingRow(move.start()) && boardNav.isNotAtRightEdge(move.start()))) {
            return board.getPiece(boardNav.toFrontRightOf(move.start())).isNotSameSide(side);
        }
        return true;
    }*/
}

