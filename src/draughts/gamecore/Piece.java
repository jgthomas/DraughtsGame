package draughts.gamecore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Piece {
    private PieceType pieceType;
    private final BooleanProperty isKing;
    private final ObjectProperty<Side> side;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = new SimpleBooleanProperty(false);
        side = new SimpleObjectProperty<>(pieceType.side());
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;

        setSide(pieceType.side());

        if (pieceType.isKing()) {
            setIsKing(true);
        } else {
            setIsKing(false);
        }
    }

    public BooleanProperty isKingProperty() {
        return isKing;
    }

    public ObjectProperty<Side> sideProperty() {
        return side;
    }

    public Side getSide() {
        return side.get();
    }

    boolean reachesKingRow(Square end) {
        return end.row() == pieceType.kingLine();
    }

    boolean isPlayerPiece() {
        return pieceType != PieceType.NONE;
    }

    public boolean isBlank() {
        return pieceType == PieceType.NONE;
    }

    boolean pieceIsOpponent(Piece piece) {
        return getSide() != piece.getSide() && piece.getSide() != Side.NONE;
    }

    boolean isSameSide(Side side){
        return side == getSide();
    }

    boolean legalMoveDirection(Square start, Square end) {
        if (pieceType.isKing()) {
            return legalKingDirection(start, end);
        } else if (pieceType.isWhite()) {
            return end.rowHigher(start);
        } else if (pieceType.isBlack()) {
            return start.rowHigher(end);
        }
        return false;
    }

    private void setIsKing(boolean b) {
        isKing.set(b);
    }

    private void setSide(Side side) {
        this.side.set(side);
    }

    private boolean legalKingDirection(Square start, Square end) {
        return start.rowHigher(end) || end.rowHigher(start);
    }
}

