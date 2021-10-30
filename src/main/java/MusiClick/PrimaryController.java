package MusiClick;

import java.io.IOException;
import java.sql.Timestamp;

import MusiClick.MDBDAO.SesionDAO;
import MusiClick.models.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

	protected static Sesion ss=null;
	
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
    
    public void sendSession() {
		System.out.println("entro a sendSession?");
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Timestamp ts=new Timestamp(System.currentTimeMillis());
						ss.setTime(ts);
						SesionDAO.guardar(ss);
						Thread.sleep(1000);
						System.out.println("hola");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
}
