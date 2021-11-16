package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import MusiClick.models.ReproductionList;
import MusiClick.models.User;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

	//lista de usuarios
	public static List<User> users= FXCollections.observableArrayList();
	
	//conexión
	private static Connection con = null;
	
	// SENTENCIAS SQL
	private static final String GETALL="SELECT id,mail,username,password,usercode,confirmed FROM user";
	private final static String INSERT_UPDATE="INSERT INTO user (id, mail, username, password, usercode,confirmed) "
			+ "VALUES (?,?,?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE mail=?,username=?,password=?,usercode=?,confirmed=?";
	private final static String GETBYREPROCREATOR="SELECT u.id, u.mail, u.username, u.password, u.usercode, u.confirmed "
			+ "FROM reproductionlist r "
			+ "INNER JOIN user u on u.id=r.id_user "
			+ "WHERE r.id=?";
	private final static String GETBYREPROSUBS="SELECT u.id,u.mail,u.username,u.password,u.usercode,u.confirmed "
			+ "FROM reproductionlist_user ru "
			+ "INNER JOIN reproductionlist r on r.id=ru.id_reproductionList "
			+ "INNER JOIN user u on u.id=ru.id_user "
			+ "WHERE r.id=?";
	
	/**
	 * Añade todos los usuarios de la bbdd
	 */
	public static void loadAllUsers() {
		Connection con = MDBConexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					User aux=new User();
					aux.setId(rs.getInt("id"));
					aux.setMail(rs.getString("mail"));
					aux.setUsername(rs.getString("username"));
					aux.setPassword(rs.getString("password"));
					aux.setUsercode(rs.getString("usercode"));
					if(rs.getString("confirmed").matches("yes")) {
						aux.setConfirmed(true);
					}
					else {
						aux.setConfirmed(false);
					}
					
					users.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @return Devuelve todos los usuarios
	 */
	public static List<User> getAllUsersAsList() {
		List<User> result=new ArrayList();
		
		if(users!=null&&users.size()>0) {
			for(User u: users) {
				result.add(u);
			}
		}		
		return result;
	}
	
	/**
	 * Inserta/Updatea un usuario en la bbdd
	 * @param u el usuario a insertar/updatear
	 */
	public static void guardar(User u) {
		// INSERT o UPDATE
		//INSERT -> si no existe OK
		//En caso de ERROR -> hago un update
		int rs=0;
		Connection con = MDBConexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
				
				String confirmed="no";
				if(u.isConfirmed()) {
					confirmed="yes";
				}
				
				q.setInt(1, u.getId());
				q.setString(2, u.getMail());
				q.setString(3, u.getUsername());
				q.setString(4, u.getPassword());
				q.setString(5, u.getUsercode());	
				q.setString(6, confirmed);		
				
				q.setString(7, u.getMail());
				q.setString(8, u.getUsername());
				q.setString(9, u.getPassword());
				q.setString(10, u.getUsercode());
				q.setString(11, confirmed);	
				
				
				rs =q.executeUpdate();	
				if(users.contains(u)) {
					int i=users.indexOf(u);
					users.set(i, u);
				}
				else {
					users.add(u);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param name un nombre de usuario
	 * @return el usuario con ese único nombre
	 */
	public static User getUserByName(String name){
		User result=null;
		
		for (User u:users) {
			if(u.getUsername().matches(name)) {
				return u;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param mail un email
	 * @return el usuario con ese único email
	 */
	public static User getUserByMail(String mail){
		User result=null;
		
		for (User u:users) {
			if(u.getMail().matches(mail)) {
				return u;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param code un códiºgo
	 * @return el usuario con ese único código
	 */
	public static User getUserByUserCode(String code){
		User result=null;
		
		for (User u:users) {
			if(u.getUsercode().matches(code)) {
				return u;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param aux una lista de reproducción
	 * @return el usuario creador de la lista recivida
	 */
	public static User getUserByReproductionListCreator(ReproductionList aux) {

		User result = new User();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYREPROCREATOR);
				ps.setInt(1, aux.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					
					result.setId(rs.getInt("id"));
					result.setMail(rs.getString("mail"));
					result.setUsername(rs.getString("username"));
					result.setPassword(rs.getString("password"));
					result.setUsercode(rs.getString("usercode"));
					if(rs.getString("confirmed").matches("yes")) {
						result.setConfirmed(true);
					}
					else {
						result.setConfirmed(false);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param f una lista de reproducción
	 * @return lista de usuarios suscritos a esa lista
	 */
	public static List<User> getByReproductionListSusbcribed(ReproductionList f) {

		List<User> result = new ArrayList<User>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYREPROSUBS);
				ps.setInt(1, f.getId());
				rs = ps.executeQuery();
				while (rs.next()) {
					User aux=new User();
					aux.setId(rs.getInt("id"));
					aux.setMail(rs.getString("mail"));
					aux.setUsername(rs.getString("username"));
					aux.setPassword(rs.getString("password"));
					aux.setUsercode(rs.getString("usercode"));
					if(rs.getString("confirmed").matches("yes")) {
						aux.setConfirmed(true);
					}
					else {
						aux.setConfirmed(false);
					}
					
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @return un nuevo id para la bbdd
	 */
	public static int getNewId() {
		//calcula el id mas alto de todos y suma 1
		
		int result=-1;
		if(users!=null&&users.size()>0) {
			for(User u: users) {
				if(u.getId()>result) {
					result=u.getId();
				}
			}
			
			result++;
		}
		else{
			return 0;
		}
		return result;
	}

	

	
	
}
