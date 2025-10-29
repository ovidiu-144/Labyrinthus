package com.labyrinthus.window;

import java.sql.*;
import javafx.scene.control.Label;

public class GameSaver {
    private Connection conn;

    public GameSaver(String dbPath) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            createTables();
            createScoreTable();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //close();
    }
    private void createTables() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS SAVE_GAME (
                SAVE_SLOT INTEGER NOT NULL,
                HERO_HP INTEGER NOT NULL,
                LEVEL INTEGER NOT NULL,
                CAMERA_X REAL NOT NULL,
                CAMERA_Y REAL NOT NULL,
                ENEMIES_HP STRING NOT NULL,
                LAST_TIME BIGINT NOT NULL
            );
        """;
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

        System.out.println("Save successfully!");
        stmt.close();
    }

    public void SetTable(int save, int HP, int level, double cameraX, double cameraY, String enemies_hp, long lastTime) throws SQLException {
        String checkSql = "SELECT COUNT(*) AS total FROM SAVE_GAME WHERE SAVE_SLOT = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, save);

        ResultSet rs = checkStmt.executeQuery();
        int count = rs.getInt("total");

        rs.close();
        checkStmt.close();

        if (count > 0){
            System.out.println("Am ajuns in UPDATE la " + save);
            String updateSql = "UPDATE SAVE_GAME SET HERO_HP = ?, LEVEL = ?, CAMERA_X = ?, CAMERA_Y = ?, ENEMIES_HP = ?, LAST_TIME = ? WHERE SAVE_SLOT = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, HP);
            updateStmt.setInt(2, level);
            updateStmt.setDouble(3, cameraX);
            updateStmt.setDouble(4, cameraY);
            updateStmt.setString(5, enemies_hp);
            updateStmt.setLong(6, lastTime);
            updateStmt.setInt(7, save);

            updateStmt.executeUpdate();
            updateStmt.close();
        }
        else {
            String insertSql = "INSERT INTO SAVE_GAME (SAVE_SLOT, HERO_HP, LEVEL, CAMERA_X, CAMERA_Y, ENEMIES_HP, LAST_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, save);
            insertStmt.setInt(2, HP);
            insertStmt.setInt(3, level);
            insertStmt.setDouble(4, cameraX);
            insertStmt.setDouble(5, cameraY);
            insertStmt.setString(6, enemies_hp);
            insertStmt.setLong(7, lastTime);

            insertStmt.executeUpdate();
            insertStmt.close();
        }
    }

    public GameState LoadTable (int save) throws SQLException{
        String sql = "SELECT HERO_HP, LEVEL, CAMERA_X, CAMERA_Y, ENEMIES_HP, LAST_TIME FROM SAVE_GAME ORDER BY SAVE_SLOT LIMIT 1 OFFSET ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, save);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()){
            int heroHP = rs.getInt("HERO_HP");
            int level = rs.getInt("LEVEL");

            double camX = rs.getDouble("CAMERA_X");
            double camY = rs.getDouble("CAMERA_Y");

            String enemiesHp = rs.getString("ENEMIES_HP");

            long lastTime = rs.getLong("LAST_TIME");
            rs.close();
            pstmt.close();

            return new GameState(heroHP, level, camX, camY, enemiesHp, lastTime);
        }
        else {
            rs.close();
            pstmt.close();
            System.out.println("Nu exista Salvarea!");
            return null;
        }
    }

    private void createScoreTable () throws SQLException{
        String sql = """
            CREATE TABLE IF NOT EXISTS SCOREBOARD (
                SCORE INTEGER NOT NULL,
                USER STRING NOT NULL
            );
        """;
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

        System.out.println("Save successfully!");
        stmt.close();
    }

    public void saveScore(int score, String user) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM SCOREBOARD";
        String minScoreSql = "SELECT ROWID, SCORE FROM SCOREBOARD ORDER BY SCORE ASC LIMIT 1";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(countSql);

        rs.next();
        int count = rs.getInt(1);
        rs.close();

        if (count < 3) {
            // Inserăm direct dacă avem mai puțin de 3 scoruri
            String insertSql = "INSERT INTO SCOREBOARD (SCORE, USER) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, score);
            insertStmt.setString(2, user);
            insertStmt.executeUpdate();
            insertStmt.close();
        } else {
            // Verificăm scorul minim
            rs = stmt.executeQuery(minScoreSql);
            if (rs.next()) {
                int minRowId = rs.getInt("ROWID");
                int minScore = rs.getInt("SCORE");

                if (score > minScore) {
                    // Înlocuim scorul cel mai mic
                    String updateSql = "UPDATE SCOREBOARD SET SCORE = ?, USER = ? WHERE ROWID = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, score);
                    updateStmt.setString(2, user);
                    updateStmt.setInt(3, minRowId);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                } else {
                    System.out.println("Scorul e prea mic, nu se salvează.");
                }
            }
            rs.close();
        }

        stmt.close();
    }
    public void updateScoreLabels(Label u1, Label u2, Label u3) throws SQLException {
        String sql = "SELECT USER, SCORE FROM SCOREBOARD ORDER BY SCORE DESC LIMIT 3";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        Label[] labels = {u1, u2, u3};
        int index = 0;

        while (rs.next() && index < labels.length) {
            String user = rs.getString("USER");
            int score = rs.getInt("SCORE");
            labels[index].setText((index + 1) +  "." + user + ": " + score);
            index++;
        }

        // Curățăm celelalte label-uri dacă sunt mai puține scoruri
        while (index < labels.length) {
            labels[index].setText("—");
            index++;
        }

        rs.close();
        stmt.close();
    }


    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
