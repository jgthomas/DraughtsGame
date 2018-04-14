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

        setColor(pieceType.color());

        if (pieceType.isKing()) {
            setIsKing(true);
        } else {
            setIsKing(false);
        }
    }

    public BooleanProperty isKingProperty() {
        return isKing;
    }

    public ObjectProperty<PieceColor> colorProperty() {
        return color;
    }

    public PieceColor getColor() {
        return color.get();
    }

    public boolean reachesKingRow(Square end) {
        return end.row() == pieceType.kingLine();
    }

    public boolean legalPieceToTake(Piece piece) {
        return getColor() != piece.getColor() && piece.getColor() != PieceColor.NONE;
    }

    private void setIsKing(boolean b) {
        isKing.set(b);
    }

    private void setColor(PieceColor pieceColor) {
        color.set(pieceColor);
    }
}

