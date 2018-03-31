package draughts.gamecore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Piece {
    private PieceType pieceType;
    private final BooleanProperty isKing;
    private final ObjectProperty<PieceColor> color;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = new SimpleBooleanProperty(false);
        color = new SimpleObjectProperty<>(pieceType.color());
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;

        switch (pieceType) {
            case WHITE_KING:
            case BLACK_KING:
                setIsKing(true);
                break;
            case WHITE_PIECE:
                setColor(PieceColor.WHITE);
                setIsKing(false);
                break;
            case BLACK_PIECE:
                setColor(PieceColor.BLACK);
                setIsKing(false);
                break;
            case NONE:
                setColor(PieceColor.NONE);
                setIsKing(false);
                break;
        }
    }

    public BooleanProperty isKingProperty() {
        return isKing;
    }

    public boolean getIsKing() {
        return isKing.get();
    }

    private void setIsKing(boolean b) {
        isKing.set(b);
    }

    public ObjectProperty<PieceColor> colorProperty() {
        return color;
    }

    public PieceColor getColor() {
        return color.get();
    }

    private void setColor(PieceColor pieceColor) {
        color.set(pieceColor);
    }
}

