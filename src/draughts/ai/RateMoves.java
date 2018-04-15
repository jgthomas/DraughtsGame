package draughts.ai;

import draughts.gamecore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RateMoves implements MoveRater {
    private static final int SMALL_INCREASE = 1;
    private static final int MEDIUM_INCREASE = 2;
    private static final int LARGE_INCREASE = 10;
    private final Board board;
    private final PieceType pieceType;
    private final PieceType kingType;
    private final Side side;

    RateMoves(Board board, PieceType pieceType, Side side) {
        this.board = board;
        this.pieceType = pieceType;
        kingType = pieceType.getKing();
        this.side = side;
    }

    public List<Move> rateMoves(List<Move> legalMoves) {
        List<Move> rated = new ArrayList<>(legalMoves);
        int maxPriority = MoveType.MOVE.weight();

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
            if (isNotCurrentlyDefendingLeft(move)) { move.raisePriority(SMALL_INCREASE); }
            if (isNotCurrentlyDefendingRight(move)) { move.raisePriority(SMALL_INCREASE); }

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
        return isCentralCol(move.start()) && isCentralCol(move.end());
    }

    private boolean movesCentral(Move move) {
        return !isCentralCol(move.start()) && isCentralCol(move.end());
    }

    private boolean noEnemyInFront(Move move) {
        if (isCentralRow(move.end()) && isCentralCol(move.end())) {
            return board.getPiece(toFrontLeftOf(move.end())).isBlank()
                    && board.getPiece(toFrontRightOf(move.end())).isBlank();
        }
        return true;
    }

    private boolean isDefendedLeft(Move move) {
        return isCentral(move.end()) && board.getPiece(toBackLeftOf(move.end())).isSameSide(side);
    }

    private boolean isDefendedRight(Move move) {
        return isCentral(move.end()) && board.getPiece(toBackRightOf(move.end())).isSameSide(side);
    }

    private boolean canDefendLeft(Move move) {
        return isCentral(move.end()) && board.getPiece(toFrontLeftOf(move.end())).isSameSide(side);
    }

    private boolean canDefendRight(Move move) {
        return isCentral(move.end()) && board.getPiece(toFrontRightOf(move.end())).isSameSide(side);
    }

    private boolean isNotCurrentlyDefendingLeft(Move move) {
        if (isCentral(move.start()) || (move.getPiece().isNotKingRow(move.start()) && isNotAtLeftEdge(move.start()))) {
            return board.getPiece(toFrontLeftOf(move.start())).isNotSameSide(side);
        }
        return true;
    }

    private boolean isNotCurrentlyDefendingRight(Move move) {
        if (isCentral(move.start()) || (move.getPiece().isNotKingRow(move.start()) && isNotAtRightEdge(move.start()))) {
            return board.getPiece(toFrontRightOf(move.start())).isNotSameSide(side);
        }
        return true;
    }

    private boolean isCentral(Square square) {
        return isCentralCol(square) && isCentralRow(square);
    }

    private boolean isCentralCol(Square square) {
        return square.col() > 0 && square.col() < board.sideLength() - 1;
    }

    private boolean isCentralRow(Square square) {
        return square.row() > 0 && square.row() < board.sideLength() - 1;
    }

    private boolean isNotAtRightEdge(Square square) {
        return square.col() < board.sideLength() - 1;
    }

    private boolean isNotAtLeftEdge(Square square) {
        return square.col() > 0;
    }

    private Square toFrontLeftOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()+1, end.col()-1)
                : new Square(end.row()-1, end.col()-1);
    }

    private Square toFrontRightOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()+1, end.col()+1)
                : new Square(end.row()-1, end.col()+1);
    }

    private Square toBackLeftOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()-1, end.col()-1)
                : new Square(end.row()+1, end.col()-1);
    }

    private Square toBackRightOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()-1, end.col()+1)
                : new Square(end.row()+1, end.col()+1);
    }
}

