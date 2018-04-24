package draughts.gamecore;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board implements Iterable<Square> {
    private final SortedMap<Square, Piece> boardMap = new TreeMap<>();
    private final int BOARD_SIZE = 8;

    public Board() {
        BoardInit boardInit = new BoardInit();
        for (Square square : squares()) {
            boardMap.put(square, new Piece(PieceType.valueOf(boardInit.getPieceCode(square))));
        }
    }

    Board(BoardStateLoader boardStateLoader) {
        for (Square square : squares()) {
            boardMap.put(square, new Piece(PieceType.valueOf(boardStateLoader.getPieceCode(square))));
        }
    }

    public final void setBoardState(BoardStateLoader boardStateLoader) {
        for (Square square : squares()) {
            setPieceType(square, PieceType.valueOf(boardStateLoader.getPieceCode(square)));
        }
    }

    public Piece getPiece(Square square) {
        return boardMap.get(square);
    }

    void makeMove(Move move) {
        setPieceType(move.start(), PieceType.NONE);

        if (move.isTake()) {
            setPieceType(move.takenSquare(), PieceType.NONE);
        }

        setPieceType(move.end(), move.getPieceType());

        if (move.getNextTake() != null) {
            makeMove(move.getNextTake());
        }

        if (move.makesKing()) {
            setPieceType(move.end(), move.getKingType());
        }
    }

    public int sideLength() {
        return BOARD_SIZE;
    }

    public boolean validSquare(Square square) {
        return boardMap.containsKey(square);
    }

    @Override
    public Iterator<Square> iterator() {
        return boardMap.keySet().iterator();
    }

    private void setPieceType(Square square, PieceType pieceType) {
        boardMap.get(square).setPieceType(pieceType);
    }

    private List<Square> squares() {
        List<Square> squares = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares.add(new Square(row, col));
            }
        }
        return squares;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Square square : squares()) {
            sb.append(square.toString()).append(" ");
            sb.append(getPiece(square).getPieceType()).append("\n");
            if (square.col() == 7) { sb.append("\n"); }
        }
        return sb.toString();
    }

    private class BoardInit {
        private final Map<Integer, Integer> initialState;

        BoardInit() {
            initialState = zipToMap(squareHashes(), pieceTypeCodes());
        }

        private int getPieceCode(Square square) {
            return initialState.get(square.hashCode());
        }

        private List<Integer> squareHashes() {
            List<Integer> squareHash = new ArrayList<>();
            for (Square square : squares()) {
                squareHash.add(square.hashCode());
            }
            return squareHash;
        }

        private List<Integer> pieceTypeCodes() {
            final int NUM_OF_SQUARES = BOARD_SIZE * BOARD_SIZE;
            List<Integer> codes = new ArrayList<>(Collections.nCopies(NUM_OF_SQUARES, 0));
            for (Integer whitePos : Side.getWhite()) { codes.set(whitePos, PieceType.WHITE_PIECE.value()); }
            for (Integer blackPos : Side.getBlack()) { codes.set(blackPos, PieceType.BLACK_PIECE.value()); }
            return codes;
        }

        private <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
            return IntStream.range(0, keys.size()).boxed()
                    .collect(Collectors.toMap(keys::get, values::get));
        }
    }
}
