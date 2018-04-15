package draughts.gamecore;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class Board implements Iterable<Square> {
    private final SortedMap<Square, Piece> boardMap = new TreeMap<>();
    private final int boardSize;

    public Board(BoardStateLoader boardStateLoader) {
        boardSize = boardStateLoader.getBoardSize();
        for (Square square : boardStateLoader.squares()) {
            boardMap.put(square, new Piece(PieceType.valueOf(boardStateLoader.getPieceCode(square))));
        }
    }

    final void setBoardState(BoardStateLoader boardStateLoader) {
        for (Square square : boardStateLoader.squares()) {
            setPieceType(square, PieceType.valueOf(boardStateLoader.getPieceCode(square)));
        }
    }

    public Piece getPiece(Square square) {
        return boardMap.get(square);
    }

    public PieceType getPieceType(Square square) {
        return boardMap.get(square).getPieceType();
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
        return boardSize;
    }

    @Override
    public Iterator<Square> iterator() {
        return boardMap.keySet().iterator();
    }

    private void setPieceType(Square square, PieceType pieceType) {
        boardMap.get(square).setPieceType(pieceType);
    }
}
