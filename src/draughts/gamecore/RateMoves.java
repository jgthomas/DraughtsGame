package draughts.gamecore;

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

    RateMoves(Board board, PieceType pieceType) {
        this.board = board;
        this.pieceType = pieceType;
        kingType = pieceType.getKing();
    }

    public List<Move> ratedMoves(List<Move> legalMoves) {
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
        if (pieceType == PieceType.WHITE_PIECE) {
            return move.startOfMove().row() != 0;
        }
        return pieceType == PieceType.BLACK_PIECE
                && move.startOfMove().row() != (board.max() - 1);
    }

    private boolean staysCentral(Move move) {
        return isCentralCol(move.startOfMove()) && isCentralCol(move.endOfMove());
    }

    private boolean movesCentral(Move move) {
        return !isCentralCol(move.startOfMove()) && isCentralCol(move.endOfMove());
    }

    private boolean noEnemyInFront(Move move) {
        Square end = move.endOfMove();
        if (isCentralRow(end) && isCentralCol(end)) {
            PieceType pieceLeftFront = board.getPieceType(leftFront(end));
            PieceType pieceRightFront = board.getPieceType(rightFront(end));
            return pieceLeftFront == PieceType.NONE && pieceRightFront == PieceType.NONE;
        }
        return true;
    }

    private boolean isDefendedLeft(Move move) {
        Square end = move.endOfMove();
        if (isCentralRow(end) && isCentralCol(end)) {
            PieceType pieceLeftBack = board.getPieceType(leftBack(end));
            return pieceLeftBack.in(pieceType, kingType);
        }
        return true;
    }

    private boolean isDefendedRight(Move move) {
        Square end = move.endOfMove();
        if (isCentralRow(end) && isCentralCol(end)) {
            PieceType pieceRightBack = board.getPieceType(rightBack(end));
            return pieceRightBack.in(pieceType, kingType);
        }
        return true;
    }

    private boolean canDefendLeft(Move move) {
        Square end = move.endOfMove();
        if (isCentralRow(end) && isCentralCol(end)) {
            PieceType pieceLeftFront = board.getPieceType(leftFront(end));
            return pieceLeftFront.in(pieceType, kingType);
        }
        return false;
    }

    private boolean canDefendRight(Move move) {
        Square end = move.endOfMove();
        if (isCentralRow(end) && isCentralCol(end)) {
            PieceType pieceRightFront = board.getPieceType(rightFront(end));
            return pieceRightFront.in(pieceType, kingType);
        }
        return false;
    }

    private boolean isNotCurrentlyDefendingLeft(Move move) {
        Square start = move.startOfMove();
        if ((isCentralRow(start) && isCentralCol(start))
                || (start.row() != pieceType.kingLine() && start.col() > 0)) {
            PieceType pieceLeftFront = board.getPieceType(leftFront(start));
            return !(pieceLeftFront == pieceType) || !(pieceLeftFront == kingType);
        }
        return true;
    }

    private boolean isNotCurrentlyDefendingRight(Move move) {
        Square start = move.startOfMove();
        if ((isCentralRow(start) && isCentralCol(start))
                || (start.row() != pieceType.kingLine() && start.col() < board.max() - 1)) {
            PieceType pieceRightFront = board.getPieceType(rightFront(start));
            return !(pieceRightFront == pieceType) || !(pieceRightFront == kingType);
        }
        return true;
    }

    private boolean isCentralCol(Square square) {
        return square.col() > 0 && square.col() < board.max() - 1;
    }

    private boolean isCentralRow(Square square) {
        return square.row() > 0 && square.row() < board.max() - 1;
    }

    private Square leftFront(Square end) {
        if (pieceType == PieceType.WHITE_PIECE) {
            return new Square(end.row()+1, end.col()-1);
        }
        return new Square(end.row()-1, end.col()-1);
    }

    private Square rightFront(Square end) {
        if (pieceType == PieceType.WHITE_PIECE) {
            return new Square(end.row()+1, end.col()+1);
        }
        return new Square(end.row()-1, end.col()+1);
    }

    private Square leftBack(Square end) {
        if (pieceType == PieceType.WHITE_PIECE) {
            return new Square(end.row()-1, end.col()-1);
        }
        return new Square(end.row()+1, end.col()-1);
    }

    private Square rightBack(Square end) {
        if (pieceType == PieceType.WHITE_PIECE) {
            return new Square(end.row()-1, end.col()+1);
        }
        return new Square(end.row()+1, end.col()+1);
    }
}

