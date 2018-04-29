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
        final int LARGE_DECREASE = -10;
        final int SMALL_INCREASE = 1;
        final int MEDIUM_INCREASE = 2;
        final int MIDDLE_INCREASE = 5;
        final int LARGE_INCREASE = 10;
        final List<Move> rated = new ArrayList<>(legalMoves);
        int maxPriority = 0;

        for (Move move : rated) {
            if (isNotGuardingLastRow(move)) { move.raisePriority(MIDDLE_INCREASE); }
            if (move.makesKing()) { move.raisePriority(LARGE_INCREASE); }
            if (staysCentral(move)) { move.raisePriority(MEDIUM_INCREASE); }
            if (movesCentral(move)) { move.raisePriority(MEDIUM_INCREASE); }
            if (noEnemyInFront(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isDefendedLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isDefendedRight(move)) { move.raisePriority(SMALL_INCREASE); }
            if (canDefendLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (canDefendRight(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isNotCurrentlyDefendingLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isNotCurrentlyDefendingRight(move)) { move.raisePriority(SMALL_INCREASE); }
            if (doubleTakeDangerLeft(move)) { move.raisePriority(LARGE_DECREASE); }
            if (doubleTakeDangerRight(move)) { move.raisePriority(LARGE_DECREASE); }
            if (kingCannotBeTaken(move)) { move.raisePriority(MIDDLE_INCREASE); }

            if (move.getPriority() > maxPriority) { maxPriority = move.getPriority(); }

        }

        int topPriority = maxPriority;

        return rated.stream()
                .filter(move -> move.getPriority() == topPriority)
                .collect(Collectors.toList());
    }

    private boolean isNotGuardingLastRow(Move move) {
        return (side.isWhite())
                ? move.start().row() != (board.sideLength() - 1)
                : move.start().row() != 0;
    }

    private boolean staysCentral(Move move) {
        return boardNav.isCentral(move.start()) && boardNav.isCentral(move.end());
    }

    private boolean movesCentral(Move move) {
        return (!boardNav.isCentralCol(move.start()) || !boardNav.isCentralRow(move.start()))
                && (boardNav.isCentralCol(move.end()) || boardNav.isCentralRow(move.end()));
    }

    private boolean noEnemyInFrontLeft(Move move) {
        return board.validSquare(boardNav.toFrontLeftOf(move.end()))
                && (board.getPiece(boardNav.toFrontLeftOf(move.end())).isSameSide(side)
                || board.getPiece(boardNav.toFrontLeftOf(move.end())).isBlank());
    }

    private boolean noEnemyInFrontRight(Move move) {
        return board.validSquare(boardNav.toFrontRightOf(move.end()))
                && (board.getPiece(boardNav.toFrontRightOf(move.end())).isSameSide(side)
                || board.getPiece(boardNav.toFrontRightOf(move.end())).isBlank());
    }

    private boolean noEnemyInFront(Move move) {
        return noEnemyInFrontLeft(move) && noEnemyInFrontRight(move);
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
        return board.validSquare(boardNav.toFrontLeftOf(move.end()))
                && board.getPiece(boardNav.toFrontLeftOf(move.end())).isSameSide(side);
    }

    private boolean canDefendRight(Move move) {
        return board.validSquare(boardNav.toFrontRightOf(move.end()))
                && board.getPiece(boardNav.toFrontRightOf(move.end())).isSameSide(side);
    }

    private boolean isNotCurrentlyDefendingLeft(Move move) {
        return board.validSquare(boardNav.toFrontLeftOf(move.start()))
                && board.getPiece(boardNav.toFrontLeftOf(move.start())).isNotSameSide(side);
    }

    private boolean isNotCurrentlyDefendingRight(Move move) {
        return board.validSquare(boardNav.toFrontRightOf(move.start()))
                && board.getPiece(boardNav.toFrontRightOf(move.start())).isNotSameSide(side);
    }

    private boolean opensUpDoubleTakeLeft(Move move) {
        return (board.validSquare(boardNav.toBackLeftOf(move.start())) &&
                board.getPiece(boardNav.toBackLeftOf(move.start())).isSameSide(side))
                && (board.validSquare(boardNav.twoBackLeftOf(move.start())) &&
                board.getPiece(boardNav.twoBackLeftOf(move.start())).isBlank());
    }

    private boolean opensUpDoubleTakeRight(Move move) {
        return (board.validSquare(boardNav.toBackRightOf(move.start())) &&
                board.getPiece(boardNav.toBackRightOf(move.start())).isSameSide(side))
                && (board.validSquare(boardNav.twoBackRightOf(move.start())) &&
                board.getPiece(boardNav.twoBackRightOf(move.start())).isBlank());
    }

    private boolean doubleTakeDangerLeft(Move move) {
        return opensUpDoubleTakeLeft(move) && !noEnemyInFrontLeft(move);
    }

    private boolean doubleTakeDangerRight(Move move) {
        return opensUpDoubleTakeRight(move) && !noEnemyInFrontRight(move);
    }

    private boolean kingCannotBeTaken(Move move) {
        return board.getPiece(move.start()).isKing() && noEnemyInFront(move);
    }
}

