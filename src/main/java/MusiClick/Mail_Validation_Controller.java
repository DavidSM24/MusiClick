package MusiClick;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

import MusiClick.MDBDAO.SesionDAO;
import MusiClick.MDBDAO.UserDAO;
import MusiClick.models.Sesion;
import MusiClick.models.User;
import MusiClick.utils.CodeGenerator;
import MusiClick.utils.MailSender;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Mail_Validation_Controller {
	
	//variables
	User user=null;
	String code=null;
	
	//buttons
	@FXML
	private Button btn_confirm;
	@FXML
	private Button btn_cancel;
	@FXML
	private Button btn_resend;
	
	//texts
	@FXML
	private Label lab_mail;
	@FXML
	private TextField txt_code;
	
	//methods
	
	/**
	 * Inicializa el controlador
	 * 
	 * @param code código del usuario enviado
	 * @param user usuario que ha recivido el código
	 */
	@FXML
	public void setController(String code, User user) {
		this.code=code;
		this.user=user;
		lab_mail.setText(user.getMail());
	}
	
	/**
	 * Genera un nuevo código y se lo envia al correo del usuario para que lo confirme
	 */
	@FXML
	private void resend() {
		if(code!=null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText(" Se enviará un mensaje a su correo electrónico con \n el código de validación.\n"
			+" ¿Quire continuar?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
					MailSender.sendMail(user.getMail(), "Código de verificación de cuenta.", "Su clave para verificar su cuenta es "+code);
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setHeaderText(null);
					alert2.setTitle("Información");
					alert2.setContentText(" Mensaje enviado correctamente.\n");
					alert2.showAndWait();
				} catch (Exception e) {
					// TODO: handle exception
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setHeaderText(null);
					alert2.setTitle("Información");
					alert2.setContentText(" Error al enviar el mensaje.\n");
					alert2.showAndWait();
				}
			}
		}
	}
	
	/**
	 * Confirma si el codigo escrito es correcto, y si es así, se logea
	 */
	@FXML
	private void confirm() {
		if(code!=null) {
			if(txt_code.getText().matches(code)) { //crear usuario
				System.out.println("correcto");
				
				int newId=UserDAO.getNewId();
				while(UserDAO.getUserByUserCode(code)!=null) {
					code=CodeGenerator.getCode();
				};
				
				user.setConfirmed(true);
				UserDAO.guardar(user);
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Información");
				alert2.setContentText(" Cuenta validada y creada con éxito.\n");
				alert2.showAndWait();	
				
				Stage stage = (Stage) this.btn_confirm.getScene().getWindow();
				stage.close();
				
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
					Parent root = loader.load();
					PrimaryController primary= loader.getController();
					Timestamp ts=new Timestamp(System.currentTimeMillis());
					Sesion s=new Sesion(SesionDAO.getNewId(),user.getId(),ts);
					primary.setController(user,s);
					primary.sendSession();
					Scene scene= new Scene(root);
					Stage stage2= new Stage();
					stage2.setScene(scene);
					//Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
					stage2.setTitle("Final Showdown");
					//stage2.getIcons().add(image);
					stage2.setResizable(false);
					stage2.initModality(Modality.APPLICATION_MODAL);
					stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
					       @Override
					       public void handle(WindowEvent e) {
					          try {
								
							} catch (Exception e2) {
								// TODO: handle exception
							}
					    	  Platform.exit();
					          System.exit(0);
					       }
					    });
					stage2.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			else {
				System.out.println("incorrecto");
			}
		}	
	}
	
	/**
	 * Cancela la validación y regresa al login
	 */
	@FXML
	private void cancel() {
		Stage stage = (Stage) this.btn_cancel.getScene().getWindow();
		stage.close();
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root;
			root = loader.load();
			Scene scene= new Scene(root);
			Stage stage2= new Stage();
			stage2.setScene(scene);
			Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
			stage2.setTitle("Inicio de Sesión");
			stage2.getIcons().add(image);
			stage2.setResizable(false);;
			stage2.initModality(Modality.APPLICATION_MODAL);
			
			stage2.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
