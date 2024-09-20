package models;

import models.datastructures.DataScore;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * See klass tegeleb andmebaasi ühenduse ja "igasuguste" päringutega tabelitest.
 * Alguses on ainult ühenduse jaoks funktsionaalsus
 */
public class Database {
    /**
     * Algselt ühendust pole
     */
    private Connection connection = null;
    /**
     * Andmebaasi ühenduse string
     */
    private String databaseUrl;
    /**
     * Loodud mudel
     */
    private Model model;

    /**
     * Klassi andmebaas konstruktor
     * @param model loodud mudel
     */
    public Database(Model model) {
        this.model = model;
        this.databaseUrl = "jdbc:sqlite:" + model.getDatabaseFile();
        // kõik andmebaasiga seotud päringud. Kõik mida on vaja lugeda ja kirjutada on selles failis
        this.selectUniqueCategories();
    }

    private void selectUniqueCategories() {
        String sql = "SELECT DISTINCT(category) as category FROM words ORDER BY category";
        List<String> categories = new ArrayList<>(); //Tühi kategooriate list
        try {
            Connection connection = this.dbConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }
            categories.add(0,model.getChooseCategory()); // Esimeseks "Kõik kategooriad"
            // list teha stringi massiiviks. Konverteerida ühest teise
            String[] result = categories.toArray(new String[0]); // List<String> => String[]
            model.setCbmCategories(result); // Seadista kategoorias mudelisse
            // System.out.println(categories);

            connection.close(); // Andmebaasi ühendus sulgeda

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loob andmebaasiga ühenduse
     * @return andmebaasi ühenduse
     */
    private Connection dbConnection() throws SQLException {
        // https://stackoverflow.com/questions/13891006/
        if(connection != null) {
            connection.close();
        }
        connection = DriverManager.getConnection(databaseUrl);
        return connection;
    }

    public void selectScores() {
        String sql = "SELECT * FROM scores ORDER BY gametime, playertime DESC, playername;";
        List<DataScore> data = new ArrayList<>();
        try {
            Connection connection = this.dbConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            model.getDataScores().clear(); //Tühjenda mudelis listi sisu

            while (rs.next()) {
                String datetime = rs.getString("playertime");
                LocalDateTime playerTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String playerName = rs.getString("playername");
                String guessWord = rs.getString("guessword");
                String wrongChar = rs.getString("wrongcharacters");
                int timeSeconds = rs.getInt("gametime");

                // Lisa listi kirje
                data.add(new DataScore(playerTime, playerName, guessWord, wrongChar, timeSeconds));


                // System.out.println(datetime + " | " + playerTime);

            }
            model.setDataScores(data); // Muuda andmed mudelis
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
