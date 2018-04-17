package draughts.gamecore;

import java.util.HashMap;
import java.util.Map;

enum PieceType implements PieceCalculation {
    WHITE_PIECE(1,0,Side.WHITE) {
        @Override
        public String pieceString() { return "White"; }

        @Override
        public String shortPieceString() { return "W"; }

        @Override
        public boolean legalMoveDirection(Square start, Square end) {
            return start.rowHigher(end);
        }

        @Override
        public int takenRow(Square start, Square end) {
            return start.row() - 1;
        }
    },

    WHITE_KING(3,0,Side.WHITE) {
        @Override
        public String pieceString() { return "White King"; }

        @Override
        public String shortPieceString() { return "W!K"; }

        @Override
        public boolean legalMoveDirection(Square start, Square end) {
            return start.rowHigher(end) || end.rowHigher(start);
        }

        @Override
        public int takenRow(Square start, Square end) {
            return (end.rowHigher(start)) ? start.row() + 1 : start.row() - 1;
        }
    },

    BLACK_PIECE(2,7,Side.BLACK) {
        @Override
        public String pieceString() { return "Black King"; }

        @Override
        public String shortPieceString() { return "B"; }

        @Override
        public boolean legalMoveDirection(Square start, Square end) {
            return end.rowHigher(start);
        }

        @Override
        public int takenRow(Square start, Square end) {
            return start.row() + 1;
        }
    },

    BLACK_KING(4,7,Side.BLACK) {
        @Override
        public String pieceString() { return "Black"; }

        @Override
        public String shortPieceString() { return "B!K"; }

        @Override
        public boolean legalMoveDirection(Square start, Square end) {
            return start.rowHigher(end) || end.rowHigher(start);
        }

        @Override
        public int takenRow(Square start, Square end) {
            return (end.rowHigher(start)) ? start.row() + 1 : start.row() - 1;
        }
    },

    NONE(0,0,Side.NONE) {
        @Override
        public String pieceString() { return "None"; }

        @Override
        public String shortPieceString() { return "-"; }

        @Override
        public boolean legalMoveDirection(Square start, Square end) {
            return false;
        }

        @Override
        public int takenRow(Square start, Square end) {
            return start.row();
        }
    };

    private static final Map<Integer, PieceType> pieceTypeCodes = new HashMap<>();
    private PieceType kingType;

    static {
        for (PieceType pieceType : PieceType.values()) {
            pieceTypeCodes.put(pieceType.code, pieceType);
        }

        WHITE_PIECE.kingType = WHITE_KING;
        WHITE_KING.kingType = WHITE_KING;
        BLACK_PIECE.kingType = BLACK_KING;
        BLACK_KING.kingType = BLACK_KING;
        NONE.kingType = NONE;
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

    public PieceType kingType() {
        return kingType;
    }

    public int kingRow() {
        return kingRow;
    }

    public Side side() {
        return side;
    }

    public boolean isKing() {
        return this == WHITE_KING || this == BLACK_KING;
    }

    public boolean isBlank() {
        return this == NONE;
    }

    public boolean isPlayerPiece() {
        return this != NONE;
    }
}
