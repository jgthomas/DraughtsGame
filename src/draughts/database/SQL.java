package draughts.database;

final class SQL {

    static final String CREATE_TABLE = String.join(System.getProperty("line.separator"),
            "CREATE TABLE IF NOT EXISTS games (",
            " name   string  NOT NULL,",
            " move   integer NOT NULL,",
            " square integer NOT NULL,",
            " piece  integer NOT NULL",
            ");"
    );

    static final String INSERT_STATE = String.join(System.getProperty("line.separator"),
            "INSERT INTO games (name,move,square,piece)",
            "VALUES (?,?,?,?)"
    );

    static final String SELECT_GAME_POSITION = String.join(System.getProperty("line.separator"),
            "SELECT *",
            "  FROM games",
            " WHERE name=?",
            "   AND move=?"
    );

    static final String SELECT_ALL_GAMES = String.join(System.getProperty("line.separator"),
            "SELECT DISTINCT name",
            "FROM games"
    );

    static final String SELECT_MAX_MOVE = String.join(System.getProperty("line.separator"),
            "SELECT MAX(move)",
            "        AS totalMoves",
            "      FROM games",
            "     WHERE name=?"
    );

    static final String CHECK_NAME_EXISTS = String.join(System.getProperty("line.separator"),
            "SELECT 1",
            "  FROM games",
            " WHERE name=?"
            );
}
