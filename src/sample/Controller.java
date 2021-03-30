package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public class ControlIndex extends ComboBox {
        public int index;
        public Control ctrl;

        public ControlIndex(Control _ctrl, int _index) {
            ctrl = _ctrl;
            index = _index;
        }
    }

    Connection connection;
    @FXML
    TableColumn status;
    ObservableList<Application> applications;
    @FXML
    TableColumn performers;
    @FXML
    TableView table;

    ArrayList<ControlIndex> cbStatuses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = PostgresApi.getApi();
        cbStatuses = new ArrayList<ControlIndex>();
        updateTable();
        EventHandler deleteHandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String kc = e.getCode().toString();
                if (kc == "DELETE") {
                    System.out.println(kc);
                    ObservableList<TablePosition> tablePositions = table.getSelectionModel().getSelectedCells();
                    if (tablePositions.size() != 0) {
                        PostgresApi.deleteApp(
                                connection,
                                ((Application) table.getItems().get(tablePositions.get(0).getRow())).getId()

                        );
                        updateTable();
                    }
                }

            }
        };
        table.addEventHandler(KeyEvent.KEY_RELEASED, deleteHandler);

    }


    private void updateTable() {
        applications = PostgresApi.getApplication(connection);
        for (int i = 0; i < applications.size(); i++) {
            Application app = applications.get(i);
            app.cbStatus.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    onStatusChanged(app.cbStatus.getValue(), app.cbStatus.index);
                }
            });
            app.cbPerformers.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    onPerformerChanged(app.cbPerformers.getValue(), app.cbPerformers.index);
                }
            });
            applications.set(i, app);

        }

        table.setItems(applications);
    }

    public void onStatusChanged(String newStatus, int appId) {
        PostgresApi.updateStatus(
                PostgresApi.getApi(),
                appId, newStatus);
    }

    public void onPerformerChanged(String performer, int appId) {
        PostgresApi.updatePerformer(
                PostgresApi.getApi(),
                appId, performer);

    }

    public static void cl(Object obj) {
        System.out.println(obj.toString());
    }
}
