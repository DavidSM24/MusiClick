package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReproductionListDAO {

	//lista de repros
	public static List<ReproductionList> repro = new ArrayList<ReproductionList>();

	//conexion
	private static Connection con = null;

	//SENTENCIAS SQL
	private static final String GETALL = "SELECT id, name, id_user,type,image FROM reproductionList;";
	private static final String GETALLDEFAULT = "SELECT id, name, id_user,type,image " + "FROM reproductionList "
			+ "WHERE type=0;";
	private static final String GETBYCREATOR="SELECT id, name, id_user,type,image "
			+ "FROM reproductionList "
			+ "WHERE id_user=?;";
	private static final String INSERT="INSERT INTO reproductionlist (name,id_user,type,image) "
			+ "VALUES (?,?,?,?);";
	private static final String INSERT_UPDATE = "INSERT INTO reproductionlist (id, name, id_user, type, image) "
			+ "VALUES (?,?,?,?,?) " + "ON DUPLICATE KEY UPDATE name=?,id_user=?,type=?,image=?;";
	private static final String DELETEONE="DELETE FROM reproductionlist WHERE id=?";
	private static final String DELETELIST = "DELETE FROM reproductionlist WHERE id IN ";
	private static final String DELETEALL = "DELETE FROM reproductionlist;";
	private static final String SUBSCRIBE="INSERT INTO reproductionlist_user (id_reproductionList, name_reproductionList, id_user, name_user) "
			+ "VALUES (?,?,?,?);";
	private static final String UNSUBSCRIBE= "DELETE FROM reproductionlist_user "
			+ "WHERE id_reproductionList=? "
			+ "AND id_user=?;";
	
	private static final String INSERTREPRODUCTIONLIST_SONG_BY_REPRO = "INSERT INTO reproductionlist_song (id_reproductionList, name_reproductionList, id_song, name_song) "
			+ "VALUES (?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE id_reproductionList=?,name_reproductionList=?,id_song=?,name_song=?;";
	
	private static final String DELETEREPRODUCTIONLIST_SONG_BY_REPRO="DELETE FROM reproductionlist_song WHERE id_reproductionList=?";
	private static final String DELETEREPRODUCTIONLIST_SONG_BY_REPROS = "DELETE FROM reproductionlist_song WHERE id_reproductionList IN ";
	
	private static final String DELETEREPRODUCTIONLIST_USER_BY_REPRO="DELETE FROM reproductionlist_user WHERE id_reproductionList=?;";
	private static final String DELETEREPRODUCTIONLIST_USER_BY_REPROS="DELETE FROM reproductionlist_user WHERE id_reproductionList IN ";
	
	/**
	 * 
	 * @return devuelve todas las listas de reproducción
	 */
	public static List<ReproductionList> getAll() {

		repro = new ArrayList<ReproductionList>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {

					ReproductionList f = new ReproductionList();
					f.setId(rs.getInt("id"));
					System.out.println(f.getId());
					User uaux = UserDAO.getUserByReproductionListCreator(f);
					List<Song> saux = SongDAO.getByReproductionList(f);
					List<User> lsaux = UserDAO.getByReproductionListSusbcribed(f);

					ReproductionList aux = new ReproductionList(f.getId(), rs.getString("name"), saux, uaux, lsaux,
							rs.getInt("type"), rs.getString("image"));

					repro.add(aux);
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
					e.printStackTrace();
				}
			}
		}

		return repro;
	}

	/**
	 * 
	 * @return devuelve todas las listas de reproducción por defecto
	 */
	public static List<ReproductionList> getAllDefault() {

		repro = new ArrayList<ReproductionList>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALLDEFAULT);
				rs = ps.executeQuery();
				while (rs.next()) {

					ReproductionList f = new ReproductionList();
					f.setId(rs.getInt("id"));
					System.out.println(f.getId());
					User uaux = UserDAO.getUserByReproductionListCreator(f);
					List<Song> saux = SongDAO.getByReproductionList(f);

					for (Song x : saux) {
						if (SongDAO.songs.contains(x)) {
							int i = SongDAO.songs.indexOf(x);
							x = SongDAO.songs.get(i);
						}
					}

					List<User> lsaux = UserDAO.getByReproductionListSusbcribed(f);

					ReproductionList aux = new ReproductionList(f.getId(), rs.getString("name"), saux, uaux, lsaux,
							rs.getInt("type"), rs.getString("image"));

					repro.add(aux);
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

		return repro;
	}

	/**
	 * 
	 * @param u el usuario creador de las listas
	 * @return devuelve aquellas listas creadas por el usuario recivido
	 */
	public static List<ReproductionList> getByUserCreator(User u){
		ObservableList<ReproductionList>result = FXCollections.observableArrayList();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYCREATOR);
				ps.setInt(1, u.getId());
				rs = ps.executeQuery();
				while (rs.next()) {

					ReproductionList f = new ReproductionList();
					f.setId(rs.getInt("id"));
					System.out.println(f.getId());
					User uaux = UserDAO.getUserByReproductionListCreator(f);
					List<Song> saux = SongDAO.getByReproductionList(f);
					List<User> lsaux = UserDAO.getByReproductionListSusbcribed(f);

					ReproductionList aux = new ReproductionList(f.getId(), rs.getString("name"), saux, uaux, lsaux,
							rs.getInt("type"), rs.getString("image"));

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
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	/**
	 * 
	 * @param u el usuario suscrito a las listas
	 * @return devuelve aquellas listas a las que está suscrito el usuario
	 */
	public static List<ReproductionList> getByUserSubscriptions(User u) { // <-- return rls if user is subcribed
		List<ReproductionList> result = new ArrayList<ReproductionList>();

		for (ReproductionList r : repro) {
			if (r.getSubscribed_users().contains(u)) {
				result.add(r);
			}
		}

		return result;
	}

	/**
	 * Solo inserta una lista en la bbdd, no updatea, y devuelve el id generado
	 * 
	 * @param r la lista a insertar
	 * @return devuelve el id generado de la lista insertada
	 */
	public static int insert(ReproductionList r) {
		int result=-1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = MDBConexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, r.getName());
				ps.setInt(2, r.getCreator().getId());
				ps.setInt(3, r.getType());
				ps.setString(4, r.getImage());

				ps.executeUpdate();
				
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
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
					e.printStackTrace();
				}
			}

		}
		
		return result;
	}
	
	/**
	 * Inserta/updatea una lista
	 * 
	 * @param r la lista a insertar/updatear
	 */
	public static void save(ReproductionList r) {
		// INSERT o UPDATE
		// INSERT -> si no existe OK
		// En caso de ERROR -> hago un update
		int rs = 0;
		PreparedStatement ps = null;
		Connection con = MDBConexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT_UPDATE);
				ps.setInt(1, r.getId());
				ps.setString(2, r.getName());
				ps.setInt(3, r.getCreator().getId());
				ps.setInt(4, r.getType());
				ps.setString(5, r.getImage());

				ps.setString(6, r.getName());
				ps.setInt(7, r.getCreator().getId());
				ps.setInt(8, r.getType());
				ps.setString(9, r.getImage());

				rs = ps.executeUpdate();

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
	 * Elimina una sola lista de la bbdd
	 * 
	 * @param r la lista a eliminar
	 */
	public static void delete(ReproductionList r) {
		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEONE);
				ps.setInt(1, r.getId());
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * Elimina una serie de listas de la bbdd
	 * 
	 * @param toDrop las listas que se eliminarán
	 */
	public static void delete(List<ReproductionList> toDrop) {
		String s = "(";
		for (int i = 0; i < toDrop.size(); i++) {
			s += toDrop.get(i).getId();
			if (i != toDrop.size() - 1) {
				s += ",";
			}
		}
		s += ");";

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETELIST + s);
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * Elimina todas los los registros de la tabla reproductionlist
	 */
	public static void deleteAll() {

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEALL);
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * Inserta una suscripción en la bbdd para el usuario u en la lista r
	 * 
	 * @param r la lista a la que se suscriben
	 * @param u el usuario que se va a suscribir
	 */
	public static void subscribe(ReproductionList r, User u) {

		int rs = 0;
		PreparedStatement ps = null;
		Connection con = MDBConexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(SUBSCRIBE);
				ps.setInt(1, r.getId());
				ps.setString(2, r.getName());
				ps.setInt(3, u.getId());
				ps.setString(4, u.getUsername());

				rs = ps.executeUpdate();

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
	 * Elimina una suscripción en la bbdd para el usuario u en la lista r
	 * 
	 * @param r la lista a la que se dessuscriben
	 * @param u el usuario que se va a desuscribir
	 */
	public static void unsubscribe(ReproductionList r, User u) {

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(UNSUBSCRIBE);
				ps.setInt(1, r.getId());
				ps.setInt(2, u.getId());
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {	
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * Inserta todas las relaciones entre canciones y una lista
	 * 
	 * @param r la lista a la que se le crearán la relación con sus canciones
	 */
	public static void insert_ReproductionList_Song_By_Repro(ReproductionList r) {

		if (r != null && r.getSongs() != null && r.getSongs().size() > 0) {

			for (Song s : r.getSongs()) {
				int rs = 0;
				PreparedStatement ps = null;
				Connection con = MDBConexion.getConexion();

				if (con != null) {
					try {
						ps = con.prepareStatement(INSERTREPRODUCTIONLIST_SONG_BY_REPRO);
						ps.setInt(1, r.getId());
						ps.setString(2, r.getName());
						ps.setInt(3, s.getId());
						ps.setString(4, s.getName());

						ps.setInt(5, r.getId());
						ps.setString(6, r.getName());
						ps.setInt(7, s.getId());
						ps.setString(8, s.getName());

						rs = ps.executeUpdate();

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

	}

	/**
	 * Dada una lista, elimina todos sus registros de reproductionlist_song
	 * 
	 * @param r la lista que se eliminará
	 */
	public static void delete_ReproductionList_Song_By_Repro(ReproductionList r) {

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEREPRODUCTIONLIST_SONG_BY_REPRO);
				ps.setInt(1, r.getId());
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * Dada una lista de listas de reproducción, se eliminarán todos los registros que contengan
	 * el id de esta lista en la tabla reproductionlist_song
	 * 
	 * @param toDrop la lista que se eliminará
	 */
	public static void delete_ReproductionList_Song_By_Repros(List<ReproductionList> toDrop) {
		String s = "(";
		for (int i = 0; i < toDrop.size(); i++) {
			s += toDrop.get(i).getId();
			if (i != toDrop.size() - 1) {
				s += ",";
			}
		}
		s += ");";

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEREPRODUCTIONLIST_SONG_BY_REPROS + s);
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * Dada una lista se eliminan todos sus registros de reproductionlist_user
	 * 
	 * @param r la lista a eliminar
	 */
	public static void delete_ReproductionList_User_By_Repro(ReproductionList r) {

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEREPRODUCTIONLIST_USER_BY_REPRO);
				ps.setInt(1, r.getId());
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * Dada una lista, se eliminan todos los registros que contengan su id de la tabla
	 * reproductionlist_user
	 * 
	 * @param toDrop la lista de repros a eliminar
	 */
	public static void delete_ReproductionList_User_By_Repros(List<ReproductionList> toDrop) {
		String s = "(";
		for (int i = 0; i < toDrop.size(); i++) {
			s += toDrop.get(i).getId();
			if (i != toDrop.size() - 1) {
				s += ",";
			}
		}
		s += ");";

		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(DELETEREPRODUCTIONLIST_USER_BY_REPROS + s);
				rs = ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}


	
}
