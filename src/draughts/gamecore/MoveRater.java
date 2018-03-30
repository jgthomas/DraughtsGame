package draughts.gamecore;

import java.util.List;

public interface MoveRater {

    List<Move> ratedMoves(List<Move> legalMoves);

}
