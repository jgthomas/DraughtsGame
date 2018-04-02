package draughts.gamecore;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private static final char BASE_CHAR = 'a';
    private static final String ILLEGAL_MOVE_MSG = "Illegal move!";
    private static final String MUST_TAKE_MSG = "You must take if you can";
    private static final String MUST_DO_MOST_TAKES_MSG = "You must do as many takes as possible";
    private static final String START_MOVE_MSG = "Pick piece: ";
    private static final String END_MOVE_MSG = "Pick square: ";
    private final PieceType pieceType;
    private final Board board;
    private final LegalMoves legalMoves;

    public HumanPlayer(PieceType pieceType, Board board, LegalMoves legalMoves) {
        this.pieceType = pieceType;
        this.board = board;
        this.legalMoves = legalMoves;
    }

    @Override
    public Move getMove() {
        List<Move> moveList = legalMoves.legal(pieceType);
        while (true) {
            Move candidate = getInput();
            for (Move move : moveList) {
                if (candidate.equals(move)) {
                    return move;
                }
            }
            if (legalMoves.inPrincipleLegalMove(candidate)) {
                System.out.println(MUST_TAKE_MSG);
            } else if (legalMoves.inPrincipleLegalTake(candidate)) {
                System.out.println(MUST_DO_MOST_TAKES_MSG);
            }
            System.out.println(ILLEGAL_MOVE_MSG);
        }
    }

    private Move getInput() {
        Square start;
        Square end;
        do {
            start = getPosition(START_MOVE_MSG);
        } while (isInvalid(start));

        do {
            end = getPosition(END_MOVE_MSG);
        } while (isInvalid(end));

        return new Move(start, end);
    }

    private Square getPosition(String message) {
        Scanner scanner = new Scanner(System.in);
        String coordinates;
        do  {
            System.out.print(message);
            coordinates = scanner.next();
        } while (coordinates.length() < 2);
        int rowNum = rowCharToCoordinate(coordinates.charAt(0));
        int colNum = Character.getNumericValue(coordinates.charAt(1));
        return new Square(rowNum, colNum);
    }

    private boolean isInvalid(Square square) {
        int rowNum = square.row();
        int colNum  = square.col();
        return (rowNum < 0 || rowNum >= board.max())
                || (colNum < 0 || colNum >= board.max());
    }

    private int rowCharToCoordinate(char c) {
        c = Character.toLowerCase(c);
        return c - BASE_CHAR;
    }
}
