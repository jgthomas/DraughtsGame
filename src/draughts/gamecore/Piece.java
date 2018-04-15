package draughts.gamecore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Piece {
    private final WhitePieceCalc whitePieceCalc = new WhitePieceCalc();
    private final BlackPieceCalc blackPieceCalc = new BlackPieceCalc();
    private final BlankPieceCalc blankPieceCalc = new BlankPieceCalc();
    private final BooleanProperty isKing;
    private final ObjectProperty<Side> side;
    private PieceType pieceType;
    private PieceCalc pieceCalc;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = new SimpleBooleanProperty(false);
        side = new SimpleObjectProperty<>(pieceType.side());
        setPieceCalc();
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
        return pieceType == PieceType.NONE;
    }

    public boolean isSameSide(Side side){
        return side == getSide();
    }

    public int value() {
        return pieceType.value();
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

        setPieceCalc();
    }

    boolean isPlayerPiece() {
        return pieceType != PieceType.NONE;
    }

    boolean pieceIsOpponent(Piece piece) {
        return getSide() != piece.getSide() && piece.getSide() != Side.NONE;
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

    private void setPieceCalc() {
        if (pieceType.isWhite()) {
            pieceCalc = whitePieceCalc;
        } else if (pieceType.isBlack()) {
            pieceCalc = blackPieceCalc;
        } else {
            pieceCalc = blankPieceCalc;
        }
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

