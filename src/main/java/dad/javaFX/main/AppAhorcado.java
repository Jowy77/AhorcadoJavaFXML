package dad.javaFX.main;

import dad.javaFX.controllerAhorcado.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppAhorcado extends Application {
	
	RootController rootController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		rootController = new RootController();
		
		Scene scene = new Scene(rootController.getRootView());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
