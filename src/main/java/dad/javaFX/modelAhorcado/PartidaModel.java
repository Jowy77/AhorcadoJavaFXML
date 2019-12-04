package dad.javaFX.modelAhorcado;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class PartidaModel {
	
	private IntegerProperty vidas = new SimpleIntegerProperty();
	private IntegerProperty puntos = new SimpleIntegerProperty();
	private StringProperty palabraSecreta = new SimpleStringProperty();
	private StringProperty intentoAdivinar = new SimpleStringProperty();
	private ObjectProperty<Image> imagen = new SimpleObjectProperty<>();
	
	
	public PartidaModel() {
		setVidas(9);
		setPuntos(0);
	}
	
	//---------------------------------------------------------
	
	public ObjectProperty<Image> imagenProperty() {
		return this.imagen;
	}
	
	public Image getImagen() {
		return this.imagenProperty().get();
	}
	
	public void setImagen(Image imagen) {
		this.imagen.set(imagen);
	}
	
	//------------------------------------------------
	
	public IntegerProperty vidasProperty() {
		return this.vidas;
	}
	
	public int getVidas() {
		return this.vidasProperty().get();
	}
	
	public void setVidas(int vidas) {
		this.vidas.set(vidas);
	}
	
	//---------------------------------------------------
	
	public IntegerProperty puntosProperty() {
		return this.puntos;
	}
	
	public int getPuntos() {
		return this.puntosProperty().get();
	}
	
	public void setPuntos(int puntos) {
		this.puntos.set(puntos);
	}
	
	//-------------------------------------------------
	
	public StringProperty palabraSecretaProperty() {
		return this.palabraSecreta;
	}
	
	public String getPalabraSecreta() {
		return this.palabraSecretaProperty().get();
	}
	
	public void setPalabraSecreta(String palabraSecreta) {
		this.palabraSecreta.set(palabraSecreta);
	}
	
	//--------------------------------------------------------------
	
	public StringProperty intentoAdivinarProperty() {
		return this.intentoAdivinar;
	}
	
	public String getIntentoAdivinar() {
		return this.intentoAdivinarProperty().get();
	}
	
	public void setIntentoAdivinar(String intentoAdivinar) {
		this.intentoAdivinar.set(intentoAdivinar);
	}
	
	//---------------------------------------------------------------

}
