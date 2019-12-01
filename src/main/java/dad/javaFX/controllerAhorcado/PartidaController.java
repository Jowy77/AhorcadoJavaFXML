package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class PartidaController implements Initializable{
	
	@FXML private AnchorPane view;
	
	//REFERENCIA AL CONTROLADOR PRINCIPAL PARA INICIAR TODAS LAS VISTAS
	private RootController rootController;
	
	public PartidaController(RootController root) throws IOException {
		root=rootController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/PartidaVista.fxml"));
		loader.setController(this);
		loader.load();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public AnchorPane getViewPartida() {
		return view;
	}

}
