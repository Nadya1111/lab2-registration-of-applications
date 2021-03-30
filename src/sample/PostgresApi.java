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
                    "                    from application as app\n" +
                    "                    left join performers as p\n" +
                    "                    on app.performer_id = p.id\n" +
                    "                    left join statuses as s\n" +
                    "                      on app.status_id = s.id\n" +
                    "                ;");
            while (resultSet.next()) {
                applications.add(new Application(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(4),
                        resultSet.getString(3))
                );
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        ObservableList<Application> observableList = FXCollections.observableList(applications);
        return observableList;
    }

    static public ObservableList<String> getStatuses(Connection connection) {
        ArrayList<String> statuses = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select statuses.id,  statuses.description as status\n" +
                    "from statuses;");
            while (resultSet.next()) {

                statuses.add(resultSet.getString(2));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        ObservableList<String> observableList = FXCollections.observableList(statuses);
        return observableList;
    }

    static public ObservableList<String> getPerformers(Connection connection) {
        ArrayList<String> statuses = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select performers.id,  performers.full_name \n" +
                    "from performers;");
            while (resultSet.next()) {

                statuses.add(resultSet.getString(2));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        ObservableList<String> observableList = FXCollections.observableList(statuses);
        return observableList;
    }

    public static void updatePerformer(Connection connection, int appId, String performer) {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE application as app\n" +
                            "SET performer_id = (\n" +
                            "        select performers.id\n" +
                            "            from performers\n" +
                            "            where performers.full_name = \'" + performer + "\'\n" +
                            "    )\n" +
                            "    WHERE app.id = " + appId + " ;");
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    public static void updateStatus(Connection connection, int appId, String status) {

        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE application as app\n" +
                    "SET status_id = (\n" +
                    "        select statuses.id\n" +
                    "            from statuses\n" +
                    "            where statuses.description = \'" + status + "\'\n" +
                    "    )\n" +
                    "    WHERE app.id = " + appId + " ;";
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    public static void deleteApp(Connection connection, int id) {
        try {
            Statement statement = connection.createStatement();
            System.out.println(id);
            String query = "DELETE FROM application WHERE id=" + id + ";";
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

    }
}


