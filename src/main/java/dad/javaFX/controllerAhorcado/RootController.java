package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javaFX.ahorcado.utiles.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RootController implements Initializable{
	
	private TabPane view;
	private PartidaController partidaController;
	private PalabrasController palabrasController;
	public PuntuacionesController puntuacionController;
	
	public RootController() throws IOException {
		view = new TabPane();
		palabrasController = new PalabrasController();
		puntuacionController = new PuntuacionesController();
		partidaController = new PartidaController(this,puntuacionController);
		
		
		Tab partida = new Tab("Partida", partidaController.getViewPartida());
		Tab palabras = new Tab("Palabras", palabrasController.getViewPalabras());
		Tab puntuaciones = new Tab("Puntuaciones", puntuacionController.getViewPuntuaciones());
		view.getTabs().addAll(partida,palabras,puntuaciones);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	public void cambiarPuntuacion(ActionEvent e,int jugador,int puntuacion) {
		puntuacionController.getListaJugador().get(jugador).setPuntuacion(puntuacion);
	}
	
	public void anadirUsuario(Jugador jugador) {
		puntuacionController.getListaJugador().add(jugador);
	}
	
	public TabPane getRootView() {
		return view;
	}

}
