package dad.javaFX.modelAhorcado;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PalabrasModel {
	
	private ObservableList<String> observableList = FXCollections.observableArrayList();
	private ListProperty<String> listaPalabras = new SimpleListProperty<>(observableList);
	
	private StringProperty palabraSeleccionada = new SimpleStringProperty();
	
	public ListProperty<String> listProperty() {
		return this.listaPalabras;
	}
	
	
	public ObservableList<String> getList(){
		return this.listProperty().get();
	}
	

	public void setList(ObservableList<String> list) {
		this.listProperty().set(list);
	}
	
	public StringProperty AdivinarProperty() {
		return palabraSeleccionada;
	}
	
	public String getPalabraAdivinar() {
		return palabraSeleccionada.get();
	}

}
