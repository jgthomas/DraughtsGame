package draughts.gamecore;

import java.util.List;

public interface BoardStateLoader {

    PieceType getPieceType(Square square);

    List<Square> squares();

    int getBoardSize();

}
