package com.disruption.EventListeners.utility;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DatabaseManager {
    String dbloc = new ConfigReader().getConfigEntryByKey("db");
    Connection conn;

    public DatabaseManager(){
        try {
            File file = new File(dbloc);
            if (!file.exists()) {
                file.createNewFile();
            }
            conn = DriverManager.getConnection(dbloc);
            createDatabaseLayout();
        } catch (SQLException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.ERROR,"The Drivermanager had an unexpected error: " + e.getMessage());
        } catch (IOException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.ERROR,"Couldn't create a connection: " + e.getMessage());
        }
    }
    public String getEntryByUser(String userID) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM DB WHERE USERID=?");
        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();
        return rs.getString("USER");
    }

    public String getEntryByID(String id){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM DB WHERE ID=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.getString("ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEntry(String score, String userid){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO DB (?, ?)");
            ps.setString(1, score);
            ps.setString(2, userid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatabaseLayout(){
        Statement st;
        try {
            st = conn.createStatement();
            st.execute("CREATE TABLE DB (VARCHAR(256) SCORE, VARCHAR(256) USER, INT PRIMARY KEY AUTOINCREMENT)");
        } catch (SQLException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.ERROR,"The Database layout creation had an unexpected error: " + e.getMessage());
        }
    }

}
