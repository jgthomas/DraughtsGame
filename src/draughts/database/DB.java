package draughts.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class DB {
    private static final String DB_URL = "jdbc:sqlite:test.db";

    void insertGame(String name, List<SaveState.State> game) {
        createNewTable();
        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(SQL.INSERT_STATE)) {
            conn.setAutoCommit(false);
            for (SaveState.State state : game) {
                for (Integer squareKey : state.state.keySet()) {
                    ps.setString(1, name);
                    ps.setInt(2, state.moveNumber);
                    ps.setInt(3, squareKey);
                    ps.setInt(4, state.state.get(squareKey));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    Map<Integer, Integer> selectGame(String gameName, int moveNumber) {
        Map<Integer, Integer> state = new HashMap<>();
        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(SQL.SELECT_GAME_POSITION)) {
            ps.setString(1, gameName);
            ps.setInt(2, moveNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int square = rs.getInt("square");
                    int piece = rs.getInt("piece");
                    state.put(square, piece);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return state;
    }

    List<String> selectAllGameNames() {
        List<String> gameNames = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(SQL.SELECT_ALL_GAMES)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    gameNames.add(rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gameNames;
    }

    private void createNewTable() {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(SQL.CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

