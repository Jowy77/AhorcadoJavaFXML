package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

public class PalabrasController implements Initializable{
	
	@FXML private SplitPane view;
	
	
	private RootController rootController;
	
	public PalabrasController(RootController root) throws IOException {
		root=rootController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/PalabrasVista.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public SplitPane getViewPalabras() {
		return view;
	}

}
