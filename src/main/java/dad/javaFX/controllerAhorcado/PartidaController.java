package dad.javaFX.controllerAhorcado;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javaFX.ahorcado.utiles.Jugador;
import dad.javaFX.modelAhorcado.PartidaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	public PartidaController(RootController root,PuntuacionesController puntController) throws IOException {
		
		this.rootController=root;
		this.puntuacionesController=puntController;
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
			
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("GAME OVER");
			alerta.setHeaderText("SE HA ACABADO LA PARTIDA, BIEN JUGADO");
			alerta.setContentText("QUIERES GUARDAR LA PARTIDA?");
			
			Optional<ButtonType> resultGuardar = alerta.showAndWait();
			
			if(resultGuardar.get() == ButtonType.OK) {
			
			TextInputDialog input = new TextInputDialog();
			input.setTitle("GUARDAR PUNTUACION");
			input.setHeaderText("A CONTINUACION INTRODUCE TU NOMBRE");
			Optional<String> usuario = input.showAndWait();
			
			while(usuario.get().equals("")) {
				input.setHeaderText("INTRODUCE ALGO EL USUARIO NO PUEDE ESTAR EN BLANCO");
				usuario = input.showAndWait();
			}
			
			int playerOne= puntuacionesController.getIndexJugador(usuario.get());
	
			if(playerOne != -1) {
				Alert alertUsuarioEncontrado = new Alert(AlertType.CONFIRMATION);
				alertUsuarioEncontrado.setTitle("USUARIO ENCONTRADO");
				alertUsuarioEncontrado.setHeaderText("ESTA A PUNTO DE SOBREESCRIBIR SU PUNTUACION ANTERIOR");
				alertUsuarioEncontrado.setContentText("QUIERES SOBREESCRIBIR TU PUNTUACION ANTERIOR?");

				Optional<ButtonType> result = alertUsuarioEncontrado.showAndWait();
				
				if(result.get() == ButtonType.OK) {
					ActionEvent e = new ActionEvent();
					this.rootController.cambiarPuntuacion(e,playerOne,this.partidaModel.getPuntos());
					Alert alertPuntuacionCambiada = new Alert(AlertType.CONFIRMATION);
					alertPuntuacionCambiada.setTitle("PUNTUACION");
					alertPuntuacionCambiada.setHeaderText("SE HA CAMBIADO LA PUNTUACION");
					alertPuntuacionCambiada.setContentText("PUEDES VERLA EN LA PESTAÑA CORRESPODIENTE");
				}
			}else {
				Alert alertUsuarioNuevo = new Alert(AlertType.CONFIRMATION);
				alertUsuarioNuevo.setTitle("JUGADOR NO ENCONTRADO");
				alertUsuarioNuevo.setHeaderText("SE VA A AÑADIR EL NUEVO USUARIO AL REGISTRO");
				alertUsuarioNuevo.setContentText("QUIERES AÑADIR EL NUEVO USUARIO JUNTO CON SU PUNTUACION?");
				
				Optional<ButtonType> result = alertUsuarioNuevo.showAndWait();
				
				if(result.get() == ButtonType.OK) {
					Jugador nuevoJugador = new Jugador(usuario.get(), this.partidaModel.getPuntos());
					//ActionEvent e = new ActionEvent();
					this.rootController.anadirUsuario(nuevoJugador);
				}else {
					Alert alertPartidaNoGuardada = new Alert(AlertType.CONFIRMATION);
					alertPartidaNoGuardada.setTitle("PARTIDA SIN REGISTRAR");
					alertPartidaNoGuardada.setHeaderText("ESTA PARTIDA NO SE HA REGISTRADO");
				}
			}
			
			}
			
			//EMPEZAR OTRA VEZ
			fotoIndice=0;
			partidaModel.setVidas(9);
			partidaModel.setPuntos(0);
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
	
	public void setPalabraPartida(String palabra) {
		this.palabraPartida=palabra;
	}

	public AnchorPane getViewPartida() {
		return view;
	}

}
