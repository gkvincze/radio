package com.codecool.database;

import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {
        String mostPlayedSong = "";
        String query = "SELECT song FROM music_broadcast GROUP BY song ORDER BY SUM(times_aired) DESC LIMIT 1;";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mostPlayedSong = resultSet.getString("song");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostPlayedSong;
    }

    public String getMostActiveArtist() {
        String mostActiveArtist = "";
        String query = "SELECT artist FROM music_broadcast GROUP BY artist ORDER BY COUNT(song) DESC LIMIT 1;";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mostActiveArtist = resultSet.getString("artist");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostActiveArtist;
    }
}
