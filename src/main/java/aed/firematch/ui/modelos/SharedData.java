package aed.firematch.ui.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    private static final SharedData instance = new SharedData();
    private final ObservableList<String> likedUsuarios = FXCollections.observableArrayList();

    private SharedData() {}

    public static SharedData getInstance() {
        return instance;
    }

    public ObservableList<String> getLikedUsuarios() {
        return likedUsuarios;
    }
}