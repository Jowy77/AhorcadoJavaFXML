package dad.javaFX.ahorcado.utiles;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Jugador {
	
	private SimpleStringProperty nombre = new SimpleStringProperty();
	private SimpleIntegerProperty puntuacion = new SimpleIntegerProperty();
	
	public Jugador(String nombre,int puntuacion) {
		
		this.nombre = new SimpleStringProperty(nombre);
		this.puntuacion = new SimpleIntegerProperty(puntuacion);
	}
	
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}
	public int getPuntuacion() {
		return puntuacion.get();
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = new SimpleIntegerProperty(puntuacion);
	}
	
}
