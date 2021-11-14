package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.MDBConexion;
import javafx.collections.ObservableList;

public class ReproductionListDAO {

	public static List<ReproductionList> repro = new ArrayList<ReproductionList>();

	private static Connection con = null;

	private static final String GETALL = "SELECT id, name, id_user,type,image FROM reproductionList;";
	private static final String GETALLDEFAULT = "SELECT id, name, id_user,type,image " + "FROM reproductionList "
			+ "WHERE type=0;";
	
	private static final String INSERT_UPDATE = "INSERT INTO reproductionlist (id, name, id_user, type, image) "
			+ "VALUES (?,?,?,?,?) " + "ON DUPLICATE KEY UPDATE name=?,id_user=?,type=?,image=?;";
	private static final String DELETE = "DELETE FROM reproductionlist WHERE id IN ";
	private static final String DELETEALL = "DELETE FROM reproductionlist;";
	private static final String SUBSCRIBE="INSERT INTO reproductionlist_user (id_reproductionList, name_reproductionList, id_user, name_user) "
			+ "VALUES (?,?,?,?);";
	private static final String UNSUBSCRIBE= "DELETE FROM reproductionlist_user "
			+ "WHERE id_reproductionList=? "
			+ "AND id_user=?;";
	private static final String INSERTREPRODUCTIONLIST_SONG_BY_REPRO = "INSERT INTO reproductionlist_song (id_reproductionList, name_reproductionList, id_song, name_song) "
			+ "VALUES (?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE id_reproductionList=?,name_reproductionList=?,id_song=?,name_song=?;";
	private static final String DELETEREPRODUCTIONLIST_SONG_BY_REPRO = "DELETE FROM reproductionlist_song WHERE id_reproductionList IN ";

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
				}
			}
		}

		return repro;
	}

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

	public static List<ReproductionList> getByUserCreator(User u){
		List<ReproductionList> result = ReproductionListDAO.getAll();
		
		for (ReproductionList r : repro) {
			if (!r.getCreator().equals(u)) {
				result.remove(r);
			}
		}

		return result;
	}
	
	public static List<ReproductionList> getByUserSubscriptions(User u) { // <-- return rls if user is subcribed
		List<ReproductionList> result = new ArrayList<ReproductionList>();

		for (ReproductionList r : repro) {
			if (r.getSubscribed_users().contains(u)) {
				result.add(r);
			}
		}

		return result;
	}

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

	public static void delete(ObservableList<ReproductionList> toDrop) {
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

				ps = con.prepareStatement(DELETE + s);
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

	// cuando se elimina un repro
	public static void delete_ReproductionList_Song_By_Repros(ObservableList<ReproductionList> toDrop) {
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

				ps = con.prepareStatement(DELETEREPRODUCTIONLIST_SONG_BY_REPRO + s);
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
