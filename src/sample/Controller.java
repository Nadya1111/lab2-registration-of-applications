package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;

import java.net.URL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Connection connection;
    @FXML
    TableColumn statusCol;
    ObservableList<Application> applications;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = PostgresApi.getApi();
        statusCol.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        applications = PostgresApi.getApplication(connection);

    }

}
