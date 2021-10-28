package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import MusiClick.models.Artist;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArtistDAO {

	public static ObservableList<Artist> artists = FXCollections.observableArrayList();

	private static Connection con = null;

	private static final String GETALL = "SELECT id, name, photo FROM artist;";
	private static final String GETBYID = "SELECT id, name, photo FROM artist WHERE id=?;";
	private static final String GETBYNAME = "SELECT id, name, photo FROM artist WHERE name LIKE ?;";
	private final static String INSERT_UPDATE="INSERT INTO artist (id, name, photo) "
			+ "VALUES (?,?, ?) "
			+ "ON DUPLICATE KEY UPDATE name=?, photo=?;";
	private final static String DELETE ="DELETE FROM artist WHERE id=?";
	private final static String DELETEALL ="DELETE FROM artist;";
	
	public static ObservableList<Artist> getAll() {
		// TODO Auto-generated method stub
		artists = FXCollections.observableArrayList();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					artists.add(new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("photo")));
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
					result = new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("photo"));
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
	
	public static ObservableList<Artist> getByName(String name) {
		// TODO Auto-generated method stub
		ObservableList<Artist> result = FXCollections.observableArrayList();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYNAME);
				ps.setString(1, name);
				rs = ps.executeQuery();
				while (rs.next()) {
					result.add(new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("photo")));
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
						ps.setString(3, a.getPhoto());
						
						ps.setString(4, a.getName());
						ps.setString(5, a.getPhoto());
						
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
	
	public static void delete(Artist a) {
		int rs=0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps=null;
		if (con != null) {
			try {
				
				ps=con.prepareStatement(DELETE);
				ps.setInt(1, a.getId());
				rs =ps.executeUpdate();
				if(artists!=null&&artists.size()>0&&artists.contains(a)) {
					artists.remove(a);
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
