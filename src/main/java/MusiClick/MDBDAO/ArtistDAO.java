package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArtistDAO {

	//lista estatica de artistas
	public static List<Artist> artists = new ArrayList<Artist>();

	//conexion al servidor
	private static Connection con = null;

	//SENTENCIAS SQL PARA LA GESTIÓN DE LA BBDD
	private static final String GETALL = "SELECT id, name, description,photo FROM artist;";
	private static final String GETBYID = "SELECT id, name,description, photo FROM artist WHERE id=?;";
	private static final String GETBYNAME = "SELECT id, name,description, photo FROM artist WHERE name LIKE ?;";
	private final static String INSERT_UPDATE="INSERT INTO artist (id, name,description, photo) "
			+ "VALUES (?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?,description=?, photo=?;";
	private final static String DELETE ="DELETE FROM artist WHERE id IN ";
	//DELETE FROM `artist` WHERE id NOT LIKE (0);
	private final static String DELETEALL ="DELETE FROM artist;";
	
	/**
	 * 
	 * @return la lista completa de artistas
	 */
	public static List<Artist> getAll() {
		// TODO Auto-generated method stub
		artists = new ArrayList<Artist>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					artists.add(new Artist(rs.getInt("id"), rs.getString("name"),rs.getString("description"), rs.getString("photo")));
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
		return artists;
	}

	/**
	 * 
	 * @param id recibe el id para buscar
	 * @return devuelve el artista con ese id
	 */
	public static Artist getById(int id) {
		Artist result = new Artist();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					result = new Artist(rs.getInt("id"), rs.getString("name"),rs.getString("description"), rs.getString("photo"));
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
	 * @param id recibe el name para buscar
	 * @return devuelve lista de artistas con ese nombre
	 */
	public static List<Artist> getByName(String name) {
		// TODO Auto-generated method stub
		List<Artist> result = new ArrayList<Artist>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYNAME);
				ps.setString(1, name);
				rs = ps.executeQuery();
				while (rs.next()) {
					result.add(new Artist(rs.getInt("id"), rs.getString("name"),rs.getString("description"), rs.getString("photo")));
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
	 * @param a recive el artista para insertar/updatear
	 */
	public static void save(Artist a) {
		// INSERT o UPDATE
				//INSERT -> si no existe OK
				//En caso de ERROR -> hago un update
				int rs=0;
				PreparedStatement ps=null;
				Connection con = MDBConexion.getConexion();
				
				if (con != null) {
					try {
						ps=con.prepareStatement(INSERT_UPDATE);
						ps.setInt(1, a.getId());
						ps.setString(2, a.getName());
						ps.setString(3, a.getDescription());
						ps.setString(4, a.getPhoto());
						
						ps.setString(5, a.getName());
						ps.setString(6, a.getDescription());
						ps.setString(7, a.getPhoto());
						
						rs =ps.executeUpdate();	
						if(artists!=null&&artists.size()>0&&artists.contains(a)) {
							int i=artists.indexOf(a);
							artists.set(i, a);
						}
						else {
							artists.add(a);
						}
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
	 * 
	 * @param toDrop recive la lista de artistas a eliminar
	 */
	public static void delete(List<Artist> toDrop) {
		
		String s="(";
		for(int i=0;i<toDrop.size();i++) {
			s+=toDrop.get(i).getId();
			if(i!=toDrop.size()-1) {
				s+=",";
			}
		}
		s+=") AND id NOT LIKE (0);";
		
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
	 * elimina todos los registros de artista
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
