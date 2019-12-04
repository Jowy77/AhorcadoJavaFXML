package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RootController implements Initializable{
	
	private TabPane view;
	private PartidaController partidaController;
	private PalabrasController palabrasController;
	private PuntuacionesController puntuacionController;
	
	public RootController() throws IOException {
		view = new TabPane();
		partidaController = new PartidaController(this);
		palabrasController = new PalabrasController(this);
		puntuacionController = new PuntuacionesController(this);
		
		Tab partida = new Tab("Partida", partidaController.getViewPartida());
		Tab palabras = new Tab("Palabras", palabrasController.getViewPalabras());
		Tab puntuaciones = new Tab("Puntuaciones", puntuacionController.getViewPuntuaciones());
		view.getTabs().addAll(partida,palabras,puntuaciones);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public TabPane getRootView() {
		return view;
	}

}
