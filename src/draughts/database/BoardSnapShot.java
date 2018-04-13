package draughts.database;

import draughts.gamecore.Board;
import draughts.gamecore.BoardStateLoader;
import draughts.gamecore.PieceType;
import draughts.gamecore.Square;

import java.util.*;

public class BoardSnapShot implements BoardStateLoader {
    private final int BOARD_SIZE = 8;
    private final Map<Integer, Integer> boardSnapShot;

    BoardSnapShot(Board board) {
        boardSnapShot = new HashMap<>();
        for (Square square : board) {
            boardSnapShot.put(square.hashCode(), board.getPieceType(square).value());
        }
    }

    BoardSnapShot(Map<Integer, Integer> boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public PieceType getPieceType(Square square) {
        return PieceType.valueOf(boardSnapShot.get(square.hashCode()));
    }

    public List<Square> squares() {
        List<Square> squares = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares.add(new Square(row, col));
            }
        }
        return squares;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    private List<Integer> pieceTypeCodes() {
        final int NUM_OF_SQUARES = BOARD_SIZE * BOARD_SIZE;
        List<Integer> codes = new ArrayList<>(Collections.nCopies(NUM_OF_SQUARES, 0));
        List<Integer> whitePositions = new ArrayList<>(Arrays.asList(0,2,4,6,9,11,13,15,16,18,20,22));
        List<Integer> blackPositions = new ArrayList<>(Arrays.asList(41,43,45,47,48,50,52,54,57,59,61,63));
        for (Integer whitePos : whitePositions) { codes.add(whitePos, PieceType.WHITE_PIECE.value()); }
        for (Integer blackPos : blackPositions) { codes.add(blackPos, PieceType.BLACK_PIECE.value()); }
        return codes;
    }
}
