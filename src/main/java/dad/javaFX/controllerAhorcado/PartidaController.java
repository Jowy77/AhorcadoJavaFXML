package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javaFX.modelAhorcado.PartidaModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;

public class PartidaController implements Initializable{
	
	//VISTA
	@FXML private AnchorPane view;
	
	@FXML private Label vidasLabel;
	@FXML private Label puntosLabel;
	@FXML private TextField palabraIntentoTextField;
	@FXML private Button letraButton;
	@FXML private Button resolverButton;
	@FXML private ImageView ahorcadoImage;
	
	//REFERENCIA AL CONTROLADOR PRINCIPAL PARA INICIAR TODAS LAS VISTAS
	private RootController rootController;
	
	//MODEL DE ESTE CONTROLADOR
	private PartidaModel partidaModel = new PartidaModel();
	
	public PartidaController(RootController root) throws IOException {
		root=rootController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/PartidaVista.fxml"));
		loader.setController(this);
		loader.load();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.vidasLabel.textProperty().bindBidirectional(partidaModel.vidasProperty(), new NumberStringConverter());
		this.puntosLabel.textProperty().bindBidirectional(partidaModel.puntosProperty(), new NumberStringConverter());
		this.palabraIntentoTextField.textProperty().bind(partidaModel.intentoAdivinarProperty());
		this.ahorcadoImage.imageProperty().bind(partidaModel.imagenProperty());
		//this.ahorcadoImage.imageProperty().bind(partidaModel.imagenProperty());
		
		partidaModel.setImagen(new Image(getClass().getResource("/Imagenes/9.png").toString()));
		
		
		//EVENTOS
		letraButton.setOnAction(e -> OnEnviarLetra());
		resolverButton.setOnAction(e -> OnResolver());
		
	}
	
	private Object OnResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	private void OnEnviarLetra() {
		// TODO Auto-generated method stub
	}
	
	private void cargarImagen() {
		
	}

	public AnchorPane getViewPartida() {
		return view;
	}

}
