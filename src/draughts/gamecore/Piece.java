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

    boolean reachesKingRow(Square end) {
        return end.row() == pieceType.kingLine();
    }

    boolean isPlayerPiece() {
        return pieceType != PieceType.NONE;
    }

    boolean isBlank() {
        return pieceType == PieceType.NONE;
    }

    boolean pieceIsOpponent(Piece piece) {
        return getColor() != piece.getColor() && piece.getColor() != PieceColor.NONE;
    }

    boolean isColor(PieceColor pieceColor){
        return pieceColor == getColor();
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

    private void setColor(PieceColor pieceColor) {
        color.set(pieceColor);
    }

    private boolean legalKingDirection(Square start, Square end) {
        return start.rowHigher(end) || end.rowHigher(start);
    }
}

