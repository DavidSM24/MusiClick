package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.Song;
import MusiClick.utils.Converter;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DiscDAO {

	private static final String GETALL = "SELECT id, name, date,photo, reproductions, id_artist FROM disc;";
	private static final String GETBYID = "SELECT id, name, date,photo, reproductions, id_artist " + "FROM disc "
			+ "WHERE id=?;";
	private final static String GETBYARTIST = "SELECT id, name, date,photo, reproductions, id_artist " + "FROM disc "
			+ "WHERE id_artist=?";
	private static final String GETBYNAME = "SELECT id, name, date,photo,reproductions,id_artist " + "FROM disc "
			+ "WHERE LOWER(name) LIKE ? " + "AND id NOT LIKE(1);";
	private static final String GETBYARTISTNAME = "SELECT d.id,d.name,d.date,d.photo,d.reproductions,d.id_artist "
			+ "FROM disc d " + "INNER JOIN artist a on a.id=d.id_artist " + "WHERE LOWER(a.name) LIKE ? "
			+ "AND d.id NOT LIKE(1);";
	private final static String INSERT_UPDATE = "INSERT INTO disc (id, name, date, photo, reproductions, id_artist) "
			+ "VALUES (?, ?, ?, ?, ?, ?) "
			+ "ON DUPLICATE KEY UPDATE name=?,date=?,photo=?,reproductions=?,id_artist=?;";
	private final static String DELETE = "DELETE FROM disc WHERE id IN ";
	private final static String DELETEALL = "DELETE FROM disc;";
	private final static String UPLOADVIEWS = "UPDATE disc " + "SET reproductions= reproductions+1 "
			+ "WHERE disc.id=?;";

	public static List<Disc> discs = new ArrayList<Disc>();

	private static Connection con = null;

	public static List<Disc> getAll() { // los devuelve vacios

		discs = new ArrayList<Disc>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					// int id, String name, LocalDate date, String photo, int reproductions,Artist
					// main_artist,ObservableList<Song> song

					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					if (ArtistDAO.artists.contains(aaux)) {
						int index = ArtistDAO.artists.indexOf(aaux);
						aaux = ArtistDAO.artists.get(index);
					}

					discs.add(new Disc(rs.getInt("id"), rs.getString("name"), (rs.getDate("date")).toLocalDate(),
							rs.getString("photo"), rs.getInt("reproductions"), aaux,
							FXCollections.observableArrayList()));
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

		return discs;
	}

	public static Disc getById(int id) {
		Disc result = new Disc();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {

					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));

					result = new Disc(rs.getInt("id"), rs.getString("name"), (rs.getDate("date")).toLocalDate(),
							rs.getString("photo"), rs.getInt("reproductions"), aaux,
							FXCollections.observableArrayList());
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

	public static List<Disc> getByName(String name) {
		List<Disc> result = new ArrayList<Disc>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYNAME);
				ps.setString(1, "%" + name.toLowerCase() + "%");
				rs = ps.executeQuery();

				while (rs.next()) {

					Disc aux = new Disc();
					aux.setId(rs.getInt("id"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					List<Song> saux = Converter.song_Converter(SongDAO.getByDisc(aux));

					result.add(new Disc(rs.getInt("id"), rs.getString("name"), (rs.getDate("date")).toLocalDate(),
							rs.getString("photo"), rs.getInt("reproductions"), aaux, saux));
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

	public static List<Disc> getByArtist(Artist a) {
		List<Disc> result = FXCollections.observableArrayList();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYARTIST);
				ps.setInt(1, a.getId());
				rs = ps.executeQuery();
				while (rs.next()) {

					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					if (ArtistDAO.artists.contains(aaux)) {
						int index = ArtistDAO.artists.indexOf(aaux);
						aaux = ArtistDAO.artists.get(index);
					}

					Disc d = new Disc();
					d.setId(rs.getInt("id"));
					List<Song> saux = SongDAO.getByDisc(d);

					result.add(new Disc(rs.getInt("id"), rs.getString("name"), (rs.getDate("date")).toLocalDate(),
							rs.getString("photo"), rs.getInt("reproductions"), aaux, saux));
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

	public static List<Disc> getByArtistName(String name) {
		List<Disc> result = new ArrayList<Disc>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYARTISTNAME);
				ps.setString(1, "%" + name.toLowerCase() + "%");
				rs = ps.executeQuery();

				while (rs.next()) {

					Disc aux = new Disc();
					aux.setId(rs.getInt("id"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					List<Song> saux = Converter.song_Converter(SongDAO.getByDisc(aux));

					result.add(new Disc(rs.getInt("id"), rs.getString("name"), (rs.getDate("date")).toLocalDate(),
							rs.getString("photo"), rs.getInt("reproductions"), aaux, saux));
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

	public static void insertSong(Song s, Disc d) {
		if (s != null && d != null && d.getSongs() != null) {
			d.getSongs().add(s);
		}
	}

	public static void save(Disc d) {
		// INSERT o UPDATE
		// INSERT -> si no existe OK
		// En caso de ERROR -> hago un update
		int rs = 0;
		PreparedStatement ps = null;
		Connection con = MDBConexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT_UPDATE);
				ps.setInt(1, d.getId());
				ps.setString(2, d.getName());
				ps.setDate(3, Date.valueOf(d.getDate()));
				ps.setString(4, d.getPhoto());
				ps.setInt(5, d.getReproductions());
				ps.setInt(6, d.getMain_artist().getId());

				ps.setString(7, d.getName());
				ps.setDate(8, Date.valueOf(d.getDate()));
				ps.setString(9, d.getPhoto());
				ps.setInt(10, d.getReproductions());
				ps.setInt(11, d.getMain_artist().getId());

				rs = ps.executeUpdate();
				if (discs != null && discs.size() > 0 && discs.contains(d)) {
					int i = discs.indexOf(d);
					discs.set(i, d);
				} else {
					discs.add(d);
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

	public static void delete(List<Disc> toDrop) {

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

	public static void upload_Views(Disc d) {
		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(UPLOADVIEWS);
				ps.setInt(1, d.getId());
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
