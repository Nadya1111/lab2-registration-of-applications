package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class Application {
    private int id;
    private String description;
    private String status;
    private String performers;
    public ComboBoxI<String> cbPerformers;
    public ComboBoxI<String> cbStatus;

    public Application(int id,
                       String description,
                       String status,
                       String performers) {
        this.description = description;
        this.id = id;
        this.cbPerformers = new ComboBoxI(PostgresApi.getPerformers(PostgresApi.getApi()), id);;
        this.status = status;
        cbStatus = new ComboBoxI(PostgresApi.getStatuses(PostgresApi.getApi()), id);
        cbStatus.setValue(status);
        cbPerformers.setValue(performers);
    }

    public ComboBoxI<String>getPerformers() {
        return cbPerformers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public ComboBoxI<String> getStatus() {
        return cbStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
