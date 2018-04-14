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
            boardMap.put(square, new Piece(boardStateLoader.getPieceType(square)));
        }
    }

    final void setBoardState(BoardStateLoader boardStateLoader) {
        for (Square square : boardStateLoader.squares()) {
            setPieceType(square, boardStateLoader.getPieceType(square));
        }
    }

    public Piece getPiece(Square square) {
        return boardMap.get(square);
    }

    public PieceType getPieceType(Square square) {
        return boardMap.get(square).getPieceType();
    }

    void makeMove(Move move) {
        setPieceType(move.startOfMove(), PieceType.NONE);

        if (move.type() == MoveType.TAKE) {
            setPieceType(move.takenSquare(), PieceType.NONE);
        }

        setPieceType(move.endOfMove(), move.getPieceType());

        if (move.getNextTake() != null) {
            makeMove(move.getNextTake());
        }

        if (move.makesKing()) {
            setPieceType(move.endOfMove(), move.getPieceType().getKing());
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
