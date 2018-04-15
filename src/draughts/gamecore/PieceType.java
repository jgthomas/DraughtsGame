package draughts.gamecore;

import java.util.HashMap;
import java.util.Map;

public enum PieceType {
    WHITE_PIECE(1,7,Side.WHITE),
    WHITE_KING(3,7,Side.WHITE),
    BLACK_PIECE(2,0,Side.BLACK),
    BLACK_KING(4,0,Side.BLACK),
    NONE(0,0,Side.NONE);

    private static final Map<Integer, PieceType> pieceTypeCodes = new HashMap<>();

    static {
        for (PieceType pieceType : PieceType.values()) {
            pieceTypeCodes.put(pieceType.code, pieceType);
        }
    }

    private final int code;
    private final int kingRow;
    private final Side side;

    PieceType(int code, int kingRow, Side side) {
        this.code = code;
        this.kingRow = kingRow;
        this.side = side;
    }

    public static PieceType valueOf(int code) {
        return pieceTypeCodes.get(code);
    }

    public int value() {
        return code;
    }

    public int kingRow() {
        return kingRow;
    }

    public Side side() {
        return side;
    }

    public boolean isWhite() {
        return this == WHITE_PIECE || this == WHITE_KING;
    }

    public boolean isBlack() {
        return this == BLACK_PIECE || this == BLACK_KING;
    }

    public boolean isKing() {
        return this == WHITE_KING || this == BLACK_KING;
    }
}
