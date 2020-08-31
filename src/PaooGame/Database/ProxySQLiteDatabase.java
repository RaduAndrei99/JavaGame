package PaooGame.Database;

import PaooGame.Game;
import PaooGame.RefLinks;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProxySQLiteDatabase extends GameDatabase {

    RealSQLiteDatabase real_database;

    public ProxySQLiteDatabase(String databaseName, RefLinks r) {
        super (r);
        fileName = databaseName;
        url = "jdbc:sqlite:src/PaooGame/Database/";
        databaseFolder = "src/PaooGame/Database/";
    }


    @Override
    public void saveGame(Game game) {
        if (real_database ==null) {
            real_database = new RealSQLiteDatabase(fileName,refs);
        }
        real_database.saveGame(game);
        canYouLoadGame = true;
    }

    @Override
    public void loadGame() {
        if (real_database ==null) {
            real_database = new RealSQLiteDatabase(fileName,refs);
        }
        real_database.loadGame();
    }


    @Override
    public void saveSettings( ) {
        if (real_database ==null) {
            real_database = new RealSQLiteDatabase(fileName,refs);
        }
        real_database.saveSettings();
    }

    @Override
    public void loadSettings() {
        if (real_database ==null) {
            real_database = new RealSQLiteDatabase(fileName,refs);
        }
        if(canYouLoadSettings)
            real_database.loadSettings();
    }


    public void loadDatabase() {
        File tempFile = new File(databaseFolder + fileName);
        if (tempFile.exists()) {
            Connection c;
            Statement stmt;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection(url + fileName);
                stmt = c.createStatement();
                c.setAutoCommit(false);

                ResultSet rs = stmt.executeQuery("SELECT * FROM SaveGame;");
                canYouLoadGame = rs.getString("valid").equals("true");

                rs = stmt.executeQuery("SELECT * FROM Settings;");
                canYouLoadSettings = rs.getString("valid").equals("true");


                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                canYouLoadGame = false;
                canYouSaveGame = true;
            }
        }
        canYouSaveGame = true;
    }
}
