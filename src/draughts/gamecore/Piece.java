package draughts.gamecore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Piece {
    private final BooleanProperty isKing;
    private final ObjectProperty<Side> side;
    private PieceType pieceType;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = new SimpleBooleanProperty(false);
        side = new SimpleObjectProperty<>(pieceType.side());
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

    public boolean isBlank() {
        return pieceType.isBlank();
    }

    public boolean isSameSide(Side side){
        return side == getSide();
    }

    public int value() {
        return pieceType.value();
    }

    public String pieceToken() {
        return pieceType.getShortPieceString();
    }

    PieceType getPieceType() {
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

    boolean isPlayerPiece() {
        return pieceType.isPlayerPiece();
    }

    boolean pieceIsOpponent(Piece piece) {
        return getSide() != piece.getSide() && piece.getSide() != Side.NONE;
    }

    PieceType getKingType() {
        return pieceType.kingType();
    }

    boolean legalMoveDirection(Square start, Square end) {
        return pieceType.legalMoveDirection(start, end);
    }

    int takenRow(Square start, Square end) {
        return pieceType.takenRow(start, end);
    }

    private void setIsKing(boolean b) {
        isKing.set(b);
    }

    private void setSide(Side side) {
        this.side.set(side);
    }

    @Override
    public String toString() {
        return pieceType.getPieceString();
    }
}

