package draughts.gamecore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Piece {
    private PieceType pieceType;
    private PieceCalc pieceCalc;
    private final BooleanProperty isKing;
    private final ObjectProperty<Side> side;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = new SimpleBooleanProperty(false);
        side = new SimpleObjectProperty<>(pieceType.side());
        setPieceCalc(pieceType);
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

        setPieceCalc(pieceType);
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

    boolean isPlayerPiece() {
        return pieceType != PieceType.NONE;
    }

    public boolean isBlank() {
        return pieceType == PieceType.NONE;
    }

    boolean pieceIsOpponent(Piece piece) {
        return getSide() != piece.getSide() && piece.getSide() != Side.NONE;
    }

    public boolean isSameSide(Side side){
        return side == getSide();
    }

    PieceType getKingType() {
        return pieceCalc.kingType();
    }

    boolean legalMoveDirection(Square start, Square end) {
        if (pieceType.isKing()) {
            return legalKingDirection(start, end);
        }
        return pieceCalc.legalMoveDirection(start, end);
    }

    int takenRow(Square start, Square end) {
        if (pieceType.isKing()) {
            return takenRowByKing(start, end);
        }
        return pieceCalc.takenRow(start, end);
    }

    private void setPieceCalc(PieceType pieceType) {
        if (pieceType.isWhite()) { this.pieceCalc = new WhitePiece(); }
        else if (pieceType.isBlack()) { this.pieceCalc = new BlackPiece(); }
        else { this.pieceCalc = new BlankPiece(); }
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

    private int takenRowByKing(Square start, Square end) {
        return (end.rowHigher(start)) ? start.row() + 1 : start.row() - 1;
    }

    @Override
    public String toString() {
        if (pieceType.isKing()) {
            return pieceCalc.pieceString() + " King";
        }
        return pieceCalc.pieceString();
    }
}

