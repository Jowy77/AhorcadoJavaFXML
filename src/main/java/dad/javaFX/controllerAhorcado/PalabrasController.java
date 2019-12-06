package dad.javaFX.controllerAhorcado;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Optional;
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

	// MODEL
	private ObservableList<String> oList = FXCollections.observableArrayList(new ArrayList<String>());
	private ListProperty<String> list = new SimpleListProperty<>(oList);
	private StringProperty palabraSelected = new SimpleStringProperty();
	// ------------------------------------------------------------------------

	public PalabrasController() throws IOException {

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

	// ESTA FUNCION LEE EL FICHERO DE PALABRAS BUCA LA QUE QUIERES BORRAR Y LUEGO
	// ESCIBE EL FICHERO SIN ESA POLABRA
	// AL FINAL LA QUITA TAMBIEN DE LA LISTA DE PALABRAS
	private void OnQuitarPalabra() {

		if (palabraSelected.get() == null) {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("BORRAR PALABRA");
			alerta.setHeaderText("PALABRA NO SELECCIONADA");
			alerta.setContentText("DEBES SELECCIONAR UNA PALABRA DE LA LISTA PARA BORRARLA");
			alerta.showAndWait();
		} else {
			// READER
			FileInputStream file = null;
			InputStreamReader in = null;
			BufferedReader buff = null;
			// WRITER
			FileOutputStream fout = null;

			try {

				file = new FileInputStream(getClass().getResource("/ficheros/palabras.txt").getFile());
				in = new InputStreamReader(file, StandardCharsets.UTF_8);
				buff = new BufferedReader(in);

				String palabraEliminada = palabraSelected.get();
				String linea = buff.readLine();
				String texto = "";

				while (linea != null) {

					if (!linea.equalsIgnoreCase(palabraEliminada)) {
						texto += linea + "\n";
					}

					linea = buff.readLine();
				}

				// CERRAMO LOS LECTORES
				buff.close();
				in.close();
				file.close();
				// --------------------

				// PARA ESCRIBIR
				fout = new FileOutputStream(getClass().getResource("/ficheros/palabras.txt").getFile());
				// ESCRIBIMOS EL FICHERO DE NUEVO SIN LA PALABRA QUE QUEREMOS BORRAR
				fout.write(texto.getBytes());
				// AHORA LA QUITAMOSD E LA LISTA PARA QUE NO APAREZCA
				list.remove(palabraSelected.get());
				// CERRAMOS EL WRITER
				fout.close();
				// ---------------------------------------

			} catch (IOException e) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("ERROR LECTURA FICHERO PALABRAS");
				alerta.setHeaderText("HA OCURRIDO UN ERROR");
				alerta.setContentText("HA OCURRIDO UN ERROR CON EL FICHERO COMPRUEBA QUE ESTE CORRECTO");
				alerta.showAndWait();
			}
		}

	}

	private void OnAgregarPalabra() {
		FileOutputStream fout = null;

		try {
			// SE LE AÑADE TRUE AL FINAL PARA QUE SOBREESCRIBA
			fout = new FileOutputStream(getClass().getResource("/ficheros/palabras.txt").getFile(), true);

			// SACAMOS UN DIALOGO PARA QUE EL USUARIO INTRODUZCA LA PALABRA
			TextInputDialog dialogo = new TextInputDialog();
			dialogo.setTitle("NUEVA PALABRA");
			dialogo.setHeaderText("INTRODUCE UNA PALABRA NUEVA");
			Optional<String> palabra = dialogo.showAndWait();

			if (palabra.get().equals("") || palabra.get().equals(null)) {
				Alert alerta = new Alert(AlertType.WARNING);
				alerta.setTitle("NUEVA PALABRA ERRONEA");
				alerta.setHeaderText("HA OCURRIDO UN ERROR");
				alerta.setContentText("LA PALABRA QUE HAS INTRODUCIDO ES INCORRECTA O ESTA VACIA");
				alerta.showAndWait();

			} else {
				// METO LA PALABRA EN LA LISTA Y LUEGO LA AGREGO AL FICHERO
				listaPalabras.getItems().add(palabra.get().toUpperCase());
				fout.write("\n".getBytes());
				fout.write(palabra.get().toUpperCase().getBytes());
			}

			fout.close();

		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR LECTURA FICHERO PALABRAS");
			alerta.setHeaderText("HA OCURRIDO UN ERROR");
			alerta.setContentText("HA OCURRIDO UN ERROR CON EL FICHERO COMPRUEBA QUE ESTE CORRECTO");
			alerta.showAndWait();

		} catch (NoSuchElementException e) {

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
	
	public String getPalabra() {
		
		int indice = (int)(Math.random()* listaPalabras.getItems().size() - 0) + 0;
		System.out.println(indice);
		String palabra = listaPalabras.getItems().get(indice);
		
		return palabra;
	}

	public SplitPane getViewPalabras() {
		return view;
	}

}
