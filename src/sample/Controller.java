package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;

import java.net.URL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.util.Callback;

public class Controller implements Initializable {
    Connection connection;
    @FXML
    TableColumn status;
    ObservableList<Application> applications;
    @FXML
    TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = PostgresApi.getApi();
        ///final ComboBox comboBox = new ComboBox();
        status.setCellValueFactory(new Callback<CellDataFeatures<Application, String>, ObservableValue<String>>() {


            @Override
            public ObservableValue<String> call(CellDataFeatures<Application, String> param) {
                Application application = param.getValue();
                // F,M
                String status = application.getStatus();
               // String gender = Gender.getByCode(genderCode);
                return new SimpleObjectProperty<String>(status);
            }
        });

        status.setCellFactory(ComboBoxTableCell.forTableColumn(PostgresApi.getStatuses(connection)));

        status.setOnEditCommit((EventHandler<CellEditEvent<Application, String> >cellEditEventEventHandler) -> {

        })
                /*(CellEditEvent<Application, String>event) -> {
           TablePosition<Application, String> pos = null;

            String newStatus = event.getNewValue();

            int row = pos.getRow();
            Application application =  event.getTableView().getItems().get(row);
            application.setStatus(newStatus);
        });*/
     //  status.setCellFactory(col -> {

           /* TableCell<Application, StringProperty> c = new TableCell<>();
            final ComboBox<String> comboBox = new ComboBox<>(PostgresApi.getStatuses(connection));
            c.itemProperty().addListener((observable, oldValue, newValue) -> {

                if (oldValue != null) {
                  //  comboBox.valueProperty().bindBidirectional(oldValue.getValue());
                }
                if (newValue != null) {

                    //comboBox.valueProperty().bindBidirectional(newValue.getValue());
                }
            });
            c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(comboBox));
            return c;*/

       // status.setCellFactory(comboBox);
        applications = PostgresApi.getApplication(connection);
        table.setItems(applications);
        table.refresh();
    }

}
