package dad.javaFX.controllerAhorcado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Optional;
import dad.javaFX.modelAhorcado.PalabrasModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class PalabrasController implements Initializable {
	// VISTA
	@FXML
	private SplitPane view;

	// PROPIEDADES
	@FXML
	private Button agregar;
	@FXML
	private Button quitar;
	@FXML
	private ListView<String> listaPalabras;

	// REFERECNIA AL CONTROLLER PRINCIPAL
	private RootController rootController;

	/*
	 * MODEL PALABRASMODEL CLASE NO SE QUE LE PASA NO FUNCIONA CON EL BIND
	 */

	// Model
	private ObservableList<String> oList = FXCollections.observableArrayList(new ArrayList<String>());
	private ListProperty<String> list = new SimpleListProperty<>(oList);
	private StringProperty palabraSelected = new SimpleStringProperty();
	// ------------------------------------------------------------------------

	public PalabrasController(RootController root) throws IOException {
		root = rootController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/PalabrasVista.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaPalabras.itemsProperty().bind(list);
		palabraSelected.bind(listaPalabras.getSelectionModel().selectedItemProperty());

		// FUNCION PARA RELLENAR EL LIST VIEW CON LAS PALABRAS DEL FICHERO
		setPalabrasLista();

		// EVENTOS DE LOS BOTONES
		agregar.setOnAction(e -> OnAgregarPalabra());
		quitar.setOnAction(e -> OnQuitarPalabra());

	}

	private Object OnQuitarPalabra() {

		return null;
	}

	private void OnAgregarPalabra() {
		FileOutputStream file = null;
		OutputStreamWriter out = null;
		BufferedWriter buff = null;

		try {
			//SE LE AÑADE TRUE AL FINAL PARA QUE SOBREESCRIBA
			file = new FileOutputStream(getClass().getResource("/ficheros/palabras.txt").getFile(),true);
			out = new OutputStreamWriter(file);
			buff = new BufferedWriter(out);

			// SACAMOS UN DIALOGO PARA QUE EL USUARIO INTRODUZCA LA PALABRA
			TextInputDialog dialogo = new TextInputDialog();
			dialogo.setTitle("NUEVA PALABRA");
			dialogo.setHeaderText("INTRODUCE UNA PALABRA NUEVA");
			Optional<String> palabra = dialogo.showAndWait();

			if (palabra.equals(null)) {
				Alert alerta = new Alert(AlertType.WARNING);
				alerta.setTitle("NUEVA PALABRA ERRONEA");
				alerta.setHeaderText("HA OCURRIDO UN ERROR");
				alerta.setContentText("LA PALABRA QUE HAS INTRODUCIDO ES INCORRECTA O ESTA VACIA");
				alerta.showAndWait();

			} else {
				//METO LA PALABRA EN LA LISTA Y LUEGO LA AGREGO AL FICHERO
				listaPalabras.getItems().add(palabra.get().toUpperCase());
				buff.write(palabra.get().toUpperCase());
			}

		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR LECTURA FICHERO PALABRAS");
			alerta.setHeaderText("HA OCURRIDO UN ERROR");
			alerta.setContentText("HA OCURRIDO UN ERROR CON EL FICHERO COMPRUEBA QUE ESTE CORRECTO");
			alerta.showAndWait();
		}

	}

	public void setPalabrasLista() {

		FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader buff = null;

		try {

			file = new FileInputStream(getClass().getResource("/ficheros/palabras.txt").getFile());
			in = new InputStreamReader(file, StandardCharsets.UTF_8);
			buff = new BufferedReader(in);

			String line;
			while ((line = buff.readLine()) != null) {
				listaPalabras.getItems().add(line);
			}
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR LECTURA FICHERO PALABRAS");
			alerta.setHeaderText("HA OCURRIDO UN ERROR");
			alerta.setContentText("HA OCURRIDO UN ERROR CON EL FICHERO COMPRUEBA QUE ESTE CORRECTO");
			alerta.showAndWait();
		}
	}

	public SplitPane getViewPalabras() {
		return view;
	}

}
