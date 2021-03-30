package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;

public class ComboBoxI<T>  extends ComboBox<T>{
    public int index;

    public ComboBoxI(ObservableList<T> items, int _index) {
        super(items);
        index = _index;
    }
}