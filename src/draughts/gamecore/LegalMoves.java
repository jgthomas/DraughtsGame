package draughts.gamecore;

import draughts.database.SaveState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LegalMoves {
    private final Board board;
    private final SingleMove singleMove;

    public LegalMoves(Board board) {
        this.board = board;
        this.singleMove = new SingleMove();
    }

    public List<Move> legal(PieceType pieceType) {
        List<Move> legalMoves = new ArrayList<>();
        int maxPriority = MoveType.MOVE.weight();

        for (Square square : board) {
            PieceType pieceToMove = board.getPieceType(square);
            if (pieceToMove.in(pieceType, pieceType.getKing())) {
                for (Move move : legalMoves(square)){
                    maxPriority = (move.getPriority() > maxPriority)
                            ? move.getPriority()
                            : maxPriority;
                    legalMoves.add(move);
                }
            }
        }

        int topPriority = maxPriority;

        return legalMoves.stream()
                .filter(move -> move.getPriority() == topPriority)
                .collect(Collectors.toList());
    }

    List<Square> legalStartingSquares(PieceType pieceType) {
        List<Square> legalStarts = new ArrayList<>();
        for (Move move : legal(pieceType)) {
            legalStarts.add(move.startOfMove());
        }
        return legalStarts;
    }

    List<Square> legalEndingSquares(Square start, PieceType pieceType) {
        List<Square> legalEnds = new ArrayList<>();
        for (Move move : legal(pieceType)) {
            if (move.startOfMove().equals(start)) {
                legalEnds.add(move.endOfMove());
            }
        }
        return legalEnds;
    }

    private List<Move> legalMoves(Square start) {
        List<Move> legalPieceMoves = movesFrom(start);
        for (Move move : legalPieceMoves) {
            if (move.type() == MoveType.TAKE) {
                addFurtherTakes(board, move);
            }
        }

        return legalPieceMoves;
    }

    private List<Move> movesFrom(Square start) {
        List<Move> legalPieceMoves = new ArrayList<>();
        for (Square end : board) {
            if (start.isWithinRange(end)) {
                PieceType pieceType = board.getPieceType(start);
                if (singleMove.isLegalMove(start, end)) {
                    legalPieceMoves.add(new Move(
                            start,
                            end,
                            pieceType,
                            MoveType.MOVE,
                            MoveType.MOVE.weight()));
                } else if (singleMove.isLegalTake(start, end)) {
                    legalPieceMoves.add(new Move(
                            start,
                            end,
                            pieceType,
                            MoveType.TAKE,
                            MoveType.TAKE.weight()));
                }
            }
        }
        return legalPieceMoves;
    }

    private void addFurtherTakes(Board board, Move move) {
        Board testBoard = new Board(new SaveState(board).getCurrentState());
        testBoard.makeMove(move);
        List<Move> furtherMoves = new LegalMoves(testBoard).movesFrom(move.endOfMove());

        if (!move.makesKing()) {
            for (Move nextMove : furtherMoves) {
                if (nextMove.type() == MoveType.TAKE) {
                    move.setNextTake(nextMove);
                    addFurtherTakes(testBoard, nextMove);
                }
            }
        }
        move.updatePriority();
    }

    private class SingleMove {

        private boolean isLegalMove(Square start, Square end) {
            return directionLegal(start, end) && start.isLegalMoveDistance(end);
        }

        private boolean isLegalTake(Square start, Square end) {
            if (directionLegal(start, end) && start.isLegalTakeDistance(end)) {
                Piece potentialTake = board.getPiece(squareInBetween(start, end));
                return board.getPiece(start).pieceIsOpponent(potentialTake);
            }
            return false;
        }

        private boolean directionLegal(Square start, Square end) {
            if (board.getPiece(start).isPlayerPiece() && board.getPiece(end).isBlank()) {
                return board.getPiece(start).legalMoveDirection(start, end);
            }
            return false;
        }

        private Square squareInBetween(Square start, Square end) {
            int row = (start.rowHigher(end)) ? start.row() - 1 : start.row() + 1;
            int col = (start.colHigher(end)) ? start.col() - 1 : start.col() + 1;
            return new Square(row, col);
        }
    }
}