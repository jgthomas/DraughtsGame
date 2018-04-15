package draughts.gamecore;

import java.util.List;

public interface BoardStateLoader {

    int getPieceCode(Square square);

    List<Square> squares();

    int getBoardSize();

}
