package draughts.gamecore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PieceType {
    WHITE_PIECE(1),
    WHITE_KING(3),
    BLACK_PIECE(2),
    BLACK_KING(4),
    NONE(0);

    private static final Map<Integer, PieceType> pieceTypeCodes = new HashMap<>();

    static {
        for (PieceType pieceType : PieceType.values()) {
            pieceTypeCodes.put(pieceType.code, pieceType);
        }
    }

    private final int code;

    PieceType(int code) {
        this.code = code;
    }

    public static PieceType valueOf(int code) {
        return pieceTypeCodes.get(code);
    }

    public int value() {
        return this.code;
    }

    public boolean in(PieceType... pieceTypes) {
        return Arrays.stream(pieceTypes).anyMatch(pieceType -> pieceType == this);
    }

    public boolean isWhite() {
        return this == WHITE_PIECE || this == WHITE_KING;
    }

    public boolean isBlack() {
        return this == BLACK_PIECE || this == BLACK_KING;
    }

    public PieceColor color() {
        PieceColor pieceColor;
        if (this.isWhite()) {
            pieceColor = PieceColor.WHITE;
        } else if (this.isBlack()) {
            pieceColor = PieceColor.BLACK;
        } else {
            pieceColor = PieceColor.NONE;
        }
        return pieceColor;
    }

    public boolean isPlayerPiece() {
        return this != NONE;
    }

    public PieceType getKing() {
        if (this == WHITE_PIECE) { return WHITE_KING; }
        else if (this == BLACK_PIECE) { return BLACK_KING; }
        else { return this; }
    }

    public int kingLine() {
        if (isWhite()) {
            return KingLine.WHITE_KING_LINE.number();
        } else if (isBlack()) {
            return KingLine.BLACK_KING_LINE.number();
        }
        return KingLine.NOT_APPLICABLE.number();
    }

    enum KingLine {
        WHITE_KING_LINE(7),
        BLACK_KING_LINE(0),
        NOT_APPLICABLE(100);

        private int kingLine;

        KingLine(int kingLine) {
            this.kingLine = kingLine;
        }

        public int number() {
            return this.kingLine;
        }
    }
}
