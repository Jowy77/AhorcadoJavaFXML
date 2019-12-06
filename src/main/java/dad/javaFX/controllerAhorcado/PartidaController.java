package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javaFX.modelAhorcado.PartidaModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
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
	@FXML private Label palabraInvisibleLabel;
	
	//REFERENCIA AL CONTROLADOR PRINCIPAL PARA INICIAR TODAS LAS VISTAS
	private RootController rootController;
	private PalabrasController palabrasController;
	private PuntuacionesController puntuacionesController;
	
	//PALABRA A ADIVINAR
	private String palabraPartida;
	
	//FOTOS AHORCADO INDICE
	private int fotoIndice=1; 
	
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
		partidaModel.intentoAdivinarProperty().bind(palabraIntentoTextField.textProperty());
		this.ahorcadoImage.imageProperty().bind(partidaModel.imagenProperty());
		this.palabraInvisibleLabel.textProperty().bind(partidaModel.palabraSecretaProperty());
		
		this.palabraIntentoTextField.setPromptText("holaaaa");
		//INICIAMOS LA PALABRA PARA ESTA RONDA
		palabraSecretaNueva();
		
		//ESTO ES PARA QUE LA FOTO CAMBIE CUANDO BAJAN LOS PUNTOS
		
		partidaModel.vidasProperty().addListener((v, ov, nv) -> cambiarFoto());
		
		
		//FALTA MIRAR IMAGENES
		//this.ahorcadoImage.imageProperty().bind(partidaModel.imagenProperty());
		partidaModel.setImagen(new Image(getClass().getResource("/Imagenes/"+fotoIndice+".png").toString()));
		
		
		//EVENTOS
		letraButton.setOnAction(e -> OnEnviarLetra());
		resolverButton.setOnAction(e -> OnResolver());
		
	}
	
	private void cambiarFoto() {
		
		fotoIndice++;
		
		if(partidaModel.getVidas()>0) {
			partidaModel.setImagen(new Image(getClass().getResource("/Imagenes/"+fotoIndice+".png").toString()));
		}else {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("GAME OVER");
			alerta.setHeaderText("SE HA ACABADO LA PARTIDA, BIEN JUGADO");
			alerta.setContentText("AHORA SE GUARDARA TU PUNTUACION");
			alerta.showAndWait();
			
			TextInputDialog input = new TextInputDialog();
			input.setTitle("GUARDAR PUNTUACION");
			input.setHeaderText("A CONTINUACION INTRODUCE TU NOMBRE");
			Optional<String> usuario = input.showAndWait();
			
			//if(puntuacionesController.)
		}
		
	}

	public void palabraSecretaNueva() {
		try {
			palabrasController = new PalabrasController();
			this.palabraPartida = palabrasController.getPalabra();
			
			String guiones="";
			for(int i = 0; i < this.palabraPartida.length(); i++) {
				guiones +="-";
			}
			
			this.partidaModel.setPalabraSecreta(guiones);
		} catch (IOException e) {
			System.out.println("NO SE HA PODIDO OBTENER LA PALABRA PARA JUGAR");
		}
		
	}
	
	private void OnResolver() {
		if(palabraPartida.equalsIgnoreCase(partidaModel.getIntentoAdivinar())) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("BIEN HECHO!!!");
			alerta.setHeaderText("HAZ ADIVINADO LA PALABRA ;)");
			alerta.setContentText("PASAMOS A LA SIGUIENTE PALABRA");
			alerta.showAndWait();
			
			partidaModel.setPuntos(partidaModel.getPuntos()+1);
			//VOLVEMOS A CAMBIAR LA PALABRA SECRETA
			String palabraVieja=palabraPartida;
			palabraSecretaNueva();
			
			while(palabraPartida.equalsIgnoreCase(palabraVieja)) {
				palabraSecretaNueva();
			}
		}else {
			partidaModel.setVidas(partidaModel.getVidas()-1);
		}

	}

	private void OnEnviarLetra() {
		if (!partidaModel.getIntentoAdivinar().isEmpty()) {

			char letraIntento = partidaModel.getIntentoAdivinar().toUpperCase().charAt(0);
			String palabraBuscarLetra = palabraPartida;
			boolean letraEncontrada=false;

			char[] nuevoLabelArray = partidaModel.getPalabraSecreta().toCharArray();
			String nuevoLabel = "";

			for (int i = 0; i < palabraBuscarLetra.length(); i++) {
				if (palabraBuscarLetra.charAt(i) == letraIntento) {
					nuevoLabelArray[i] = palabraBuscarLetra.charAt(i);
					letraEncontrada=true;
				}

				nuevoLabel += nuevoLabelArray[i];
			}

			if(!letraEncontrada) {
				partidaModel.setVidas(partidaModel.getVidas()-1);
			}

			partidaModel.setPalabraSecreta(nuevoLabel);

		} else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("NO HAS INTRODUCIDO NADA");
			alerta.setHeaderText("NO HAS INTRODUCIDO NINGUNA LETRA");
			alerta.setContentText("SI INTRODUCES MAS LETRAS SOLO SE COGERA LA PRIMERA");
			alerta.showAndWait();
			}
		}

	public AnchorPane getViewPartida() {
		return view;
	}

}
