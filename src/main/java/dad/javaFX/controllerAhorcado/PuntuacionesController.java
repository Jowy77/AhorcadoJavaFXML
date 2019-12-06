package dad.javaFX.controllerAhorcado;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import dad.javaFX.ahorcado.utiles.Jugador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PuntuacionesController implements Initializable{
	//VISTA
	@FXML private AnchorPane view;
	
	//TABLA DE USUARIOS
	@FXML private TableView<Jugador> tablaUsuarios;
	@FXML private TableColumn<Jugador, String> columnaJugador;
	@FXML private TableColumn<Jugador, Integer> columnaPuntuacion;
	
	//REFERENCIA AL CONTROLLER PRINCIPAL
	private RootController rootController;
	
	public PuntuacionesController(RootController root) throws IOException {
		rootController=root;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/PuntuacionVista.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//SE ESTABLECEN LAS COLUMNAS
		columnaJugador.setCellValueFactory(new PropertyValueFactory<Jugador,String>("nombre"));
		columnaPuntuacion.setCellValueFactory(new PropertyValueFactory<Jugador, Integer>("puntuacion"));
		//Y AHORA SE CARGAN LOS DATOS DEL ARCHIVO
		tablaUsuarios.setItems(getUsuarios());
		
	}
	
	public AnchorPane getViewPuntuaciones() {
		return view;
	}
	
	public boolean buscarJugador(String usuario) {
		boolean jugadorEncontrado = false;
		
		for(int i=0; i<tablaUsuarios.getItems().size();i++) {
			if(tablaUsuarios.getItems().get(i).getNombre().equalsIgnoreCase(usuario)) {
				jugadorEncontrado=true;
			}
		}
		
		return jugadorEncontrado;
	}
	
	public ObservableList<Jugador> getUsuarios() {
		//LECTOR PARA LOS USUARIOS AHORCADO
		FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader buff = null;
		
		try {
			
			file = new FileInputStream(getClass().getResource("/ficheros/usuariosAhorcado.csv").getFile());
			in = new InputStreamReader(file, StandardCharsets.UTF_8);
			buff = new BufferedReader(in);
			//PARA DEVOLVER LOS JUGADORES;
			
			ObservableList<Jugador> listaJugadores = FXCollections.observableArrayList();
			
			//PARA ALMACENAR LOS USUARIOS QUE SE VAN LEYENDO DEL FICHERO
			String[] usuarioPuntuacion;
			String linea=buff.readLine();
			
			while(linea != null) {
				
				usuarioPuntuacion = linea.split(",");
				listaJugadores.add(new Jugador(usuarioPuntuacion[0],Integer.parseInt(usuarioPuntuacion[1])));
				
				linea=buff.readLine();
			}
			
			buff.close();
			in.close();
			file.close();
			
			return listaJugadores;
			
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR ARCHIVO");
			alerta.setHeaderText("NO SE HA PODIDO ACCEDER AL ARCHIVO DE LOS USUARIOS");
			alerta.setContentText("COMPRUEBE QUE EL ARCHIVO EXISTE O SI ESTA CORRUPTO");
			return null;
		}
	}

}
