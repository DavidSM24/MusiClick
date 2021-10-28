package MusiClick;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

	@FXML
	private Button btn_managment;
	
    @FXML
    private void goToManagment() throws IOException {
    	try {

    		
    		Stage stage = (Stage) this.btn_managment.getScene().getWindow();
			stage.close();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("managment.fxml"));    		
    		Parent root = loader.load();
    		ManagmentController managment_controller= loader.getController();
    		//managment_controller.setController();
    		Scene scene= new Scene(root);
    		Stage stage2= new Stage();
    		stage2.setScene(scene);
    		//Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
    		stage2.setTitle("MusiClick Managment Tool");
    		//stage2.getIcons().add(image);
    		//stage2.setResizable(false);;
    		stage2.initModality(Modality.APPLICATION_MODAL);
    		
    		stage2.show();

        } catch (Exception e) {
        }
    }
}
