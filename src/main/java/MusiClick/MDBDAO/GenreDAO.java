package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;
import javafx.collections.ObservableList;

public class GenreDAO extends Genre { // extends Sede implements SedeDAO

	//lista de generos
	public static List<Genre> genres = new ArrayList<Genre>();

	//conexión a la bbdd
	private static Connection con = null;

	//SENTENCIAS SQL
	private static final String GETALL = "SELECT id, name FROM genre;";
	private static final String GETBYID = "SELECT id, name FROM genre WHERE id=?;";
	private static final String GETBYNAME = "SELECT id, name FROM genre WHERE name LIKE ?;";
	private final static String INSERT_UPDATE="INSERT INTO genre (id, name) "
			+ "VALUES (?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?;";
	private final static String DELETE ="DELETE FROM genre WHERE id IN ";
	private final static String DELETEALL ="DELETE FROM genre;";

	/**
	 * 
	 * @return Devuelve todos los géneros
	 */
	public static List<Genre> getAll() {
		// TODO Auto-generated method stub
		genres = new ArrayList<Genre>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					genres.add(new Genre(rs.getInt("id"), rs.getString("name")));
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
		return genres;
	}

	/**
	 * 
	 * @param id recive el id para buscar un género en la bbdd
	 * @return devuelve el género con el id recivido
	 */
	public static Genre getById(int id) {
		Genre result = new Genre();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					result = new Genre(rs.getInt("id"), rs.getString("name"));
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
	 * @param name recibe un nombre para buscar todos los géneros con ese nombre
	 * @return todos los nombres con ese género
	 */
	public static List<Genre> getByName(String name) {
		// TODO Auto-generated method stub
		List<Genre> result = new ArrayList<Genre>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYNAME);
				ps.setString(1, name);
				rs = ps.executeQuery();
				while (rs.next()) {
					result.add(new Genre(rs.getInt("id"), rs.getString("name")));
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
	 * Inserta o updatea el género g en la bbdd
	 * 
	 * @param g el género a insertar
	 */
	public static void save(Genre g) {
		// INSERT o UPDATE
				//INSERT -> si no existe OK
				//En caso de ERROR -> hago un update
				int rs=0;
				PreparedStatement ps=null;
				Connection con = MDBConexion.getConexion();
				
				if (con != null) {
					try {
						ps=con.prepareStatement(INSERT_UPDATE);
						ps.setInt(1, g.getId());
						ps.setString(2, g.getName());
						
						ps.setString(3, g.getName());
						
						rs =ps.executeUpdate();	
						if(genres!=null&&genres.size()>0&&genres.contains(g)) {
							int i=genres.indexOf(g);
							genres.set(i, g);
						}
						else {
							genres.add(g);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("entro en el catch de delete_genre????");
					}
					
					finally {
						try {
							ps.close();
						} catch (SQLException e) {
							// TODO: handle exception
						}
					}
					
				}
	}
	
	/**
	 * Elimina los géneros recividos por parámetro
	 * 
	 * @param toDrop recive una lista de géneros a eliminar
	 */
	public static void delete(List<Genre> toDrop) {
		String s="(";
		for(int i=0;i<toDrop.size();i++) {
			s+=toDrop.get(i).getId();
			if(i!=toDrop.size()-1) {
				s+=",";
			}
		}
		s+=");";
		
		int rs=0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps=null;
		if (con != null) {
			try {
				
				ps=con.prepareStatement(DELETE+s);
				rs =ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * Elimina todos los registros de la tabla genre en la bbdd
	 */
	public static void deleteAll() {
		int rs=0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps=null;
		if (con != null) {
			try {
				
				ps=con.prepareStatement(DELETEALL);
				rs =ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
}
