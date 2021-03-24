package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class PostgresApi {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/lab2-registration-of-applications";
    static final String USER = "postgres";
    static final String PASS = "Nadya1234";

    static public Connection getApi() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }

    static public ObservableList<Application> getApplication(Connection connection) {
        ArrayList<Application> applications = new ArrayList<Application>();
        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select app.id, app.description, p.full_name, s.description\n" +
                    "from application as app\n" +
                    "         INNER JOIN \"application-perfomers\" as app_per\n" +
                    "                    ON app.id = app_per.id_application\n" +
                    "inner join performers as p\n" +
                    "    on app_per.id_perfomers = p.id\n" +
                    "inner join statuses as s\n" +
                    "    on app.status_id = s.id\n" +
                    ";");
            while (resultSet.next()) {

                System.out.println(new Application(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3)).getDescription());

                applications.add(new Application(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3)));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        ObservableList<Application> observableList = FXCollections.observableList(applications);
        return observableList;
    }
    static public ObservableList<Application> getStatuses(Connection connection) {
        ArrayList<String> statuses = new ArrayList<String>();
        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select app.id, app.description, p.full_name, s.description\n" +
                    "from application as app\n" +
                    "         INNER JOIN \"application-perfomers\" as app_per\n" +
                    "                    ON app.id = app_per.id_application\n" +
                    "inner join performers as p\n" +
                    "    on app_per.id_perfomers = p.id\n" +
                    "inner join statuses as s\n" +
                    "    on app.status_id = s.id\n" +
                    ";");
            while (resultSet.next()) {

                System.out.println(new Application(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3)).getDescription());

                statuses.add(resultSet.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        ObservableList<Application> observableList = FXCollections.observableList(applications);
        return observableList;
    }

}


