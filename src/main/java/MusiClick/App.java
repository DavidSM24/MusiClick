package MusiClick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import MusiClick.MDBDAO.SesionDAO;
import MusiClick.MDBDAO.UserDAO;
import MusiClick.utils.MDBConexion;

/**
 * JavaFX App
 */
public class App extends Application {

public void start(Stage stage) throws IOException {
		
		MDBConexion.loadServerInfo();
		
		SesionDAO.activateEvents(); //activa el planificador de eventos de la BBDD en caso de que esté desactivado.									//PD, se desactiva solo cuando pasa mucho tiempo...
		UserDAO.loadAllUsers();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		Scene scene= new Scene(root);
		Stage stage2= new Stage();
		stage2.setScene(scene);
		//Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
		stage2.setTitle("Inicio de Sesión");
		//stage2.getIcons().add(image);
		stage2.setResizable(false);;
		stage2.initModality(Modality.APPLICATION_MODAL);
		
		stage2.show();
	};
	
    public static void main(String[] args) {
        launch();
        
    }

}