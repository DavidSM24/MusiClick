package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SongDAO {

	public static List<Song> songs = new ArrayList<Song>();

	private static Connection con = null;

	private static final String GETALL = "SELECT id, name, id_artist,photo, url , duration, reproductions, id_genre, id_disc "
			+ "FROM song;";
	private static final String GETBYID = "SELECT id, name, id_artist,photo, url , duration, reproductions, id_genre, id_disc "
			+ "FROM song WHERE id=?;";
	private static final String GETBYNAME = "SELECT id, name, id_artist,photo, url , duration, reproductions, id_genre, id_disc "
			+ "FROM song WHERE LOWER(name) LIKE ?;";
	private static final String GETBYDISC = "SELECT s.id, s.name, s.id_artist, s.photo, s.url, s.duration, s.reproductions, s.id_genre , s.id_disc "
			+ "FROM song s " + "WHERE id_disc=?";
	private static final String GETBYDISCNAME = "SELECT s.id,s.name,s.id_artist,s.photo,s.url,s.duration,s.reproductions,s.id_genre,s.id_disc "
			+ "FROM song s " + "INNER JOIN disc d ON d.id=s.id_disc " + "WHERE LOWER(d.name) LIKE ?;";
	private final static String GETBYREPROSONGS = "SELECT s.id, s.name, s.id_artist,s.photo,s.url,s.duration,s.reproductions,s.id_genre,s.id_disc "
			+ "FROM reproductionList_song rs " + "INNER JOIN song s on s.id=rs.id_song "
			+ "INNER JOIN reproductionList r on r.id=rs.id_reproductionList " + "WHERE r.id=?";
	private final static String GETRANDOMLY ="SELECT id,name,id_artist,photo,url,duration,reproductions,id_genre,id_disc "
			+ "FROM song "
			+ "ORDER BY RAND() "
			+ "LIMIT 10;";
	private final static String INSERT_UPDATE = "INSERT INTO song (id, name,id_artist,photo, url, duration, reproductions,"
			+ "id_genre,id_disc) " + "VALUES (?,?,?,?,?,?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?,id_artist=?,photo=?,url=?,duration=?,reproductions=?,id_genre=?,id_disc=?;";
	private final static String DELETE = "DELETE FROM song WHERE id IN ";
	private final static String DELETEALL = "DELETE FROM song;";
	private final static String UPLOADVIEWS = "UPDATE song " + "SET reproductions= reproductions+1 "
			+ "WHERE song.id=?;";

	private final static String DELETEREPRO_SONG_BY_SONGS= "DELETE FROM reproductionlist_song WHERE id_song IN ";
	
	public static List<Song> getAll() {
		// TODO Auto-generated method stub
		songs = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					if (GenreDAO.genres.contains(gaux)) {
						int index = GenreDAO.genres.indexOf(gaux);
						gaux = GenreDAO.genres.get(index);
					}

					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					if (ArtistDAO.artists.contains(aaux)) {
						int index = ArtistDAO.artists.indexOf(aaux);
						aaux = ArtistDAO.artists.get(index);
					}

					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					if (DiscDAO.discs.contains(daux)) {
						int index = DiscDAO.discs.indexOf(daux);
						daux = DiscDAO.discs.get(index);
					}

					Song aux = new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"),
							rs.getString("url"), aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux,
							daux);

					songs.add(aux);
					DiscDAO.insertSong(aux, daux);
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
		return songs;
	}

	public static Song getById(int id) {
		Song result = new Song();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result = new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
							aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux);
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

	public static List<Song> getByArtistId(Artist a) {
		List<Song> result = new ArrayList<Song>();
		if (songs != null && songs.size() > 0) {
			for (Song s : songs) {
				if (s.getArtist().equals(a)) {
					result.add(s);
				}
			}
		}
		return result;

	}

	public static List<Song> getByArtistName(String name) {
		List<Song> result = new ArrayList<Song>();
		if (songs != null && songs.size() > 0) {
			for (Song s : songs) {
				if (s.getArtist().getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(s);
				}
			}
		}
		return result;

	}

	public static List<Song> getByGenre(Genre g) {
		List<Song> result = new ArrayList<Song>();
		if (songs != null && songs.size() > 0) {
			for (Song s : songs) {
				if (s.getGenre().equals(g)) {
					result.add(s);
				}
			}
		}
		return result;

	}

	public static List<Song> getByName(String name) {
		// TODO Auto-generated method stub
		List<Song> result = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYNAME);
				ps.setString(1, "%" + name.toLowerCase() + "%");
				rs = ps.executeQuery();

				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result.add(
							new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
									aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux));
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

	public static List<Song> getByDisc(Disc d) {

		List<Song> result = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYDISC);
				ps.setInt(1, d.getId());
				rs = ps.executeQuery();

				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result.add(
							new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
									aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux));
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

	public static List<Song> getByDisc(String name) {
		List<Song> result = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYDISCNAME);
				ps.setString(1, "%" + name.toLowerCase() + "%");
				rs = ps.executeQuery();

				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result.add(
							new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
									aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux));
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

	public static List<Song> getByReproductionList(ReproductionList r) {
		List<Song> result = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYREPROSONGS);
				ps.setInt(1, r.getId());
				rs = ps.executeQuery();

				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result.add(
							new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
									aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux));
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

	public static List<Song> getRandomly() {
		List<Song> result = new ArrayList<Song>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETRANDOMLY);
				rs = ps.executeQuery();

				while (rs.next()) {
					Genre gaux = GenreDAO.getById(rs.getInt("id_genre"));
					Artist aaux = ArtistDAO.getById(rs.getInt("id_artist"));
					Disc daux = DiscDAO.getById(rs.getInt("id_disc"));
					result.add(
							new Song(rs.getInt("id"), rs.getString("name"), rs.getString("photo"), rs.getString("url"),
									aaux, rs.getDouble("duration"), rs.getInt("reproductions"), gaux, daux));
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
	
	public static void save(Song s) {
		// INSERT o UPDATE
		// INSERT -> si no existe OK
		// En caso de ERROR -> hago un update
		int rs = 0;
		PreparedStatement ps = null;
		Connection con = MDBConexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT_UPDATE);
				ps.setInt(1, s.getId());
				ps.setString(2, s.getName());
				ps.setInt(3, s.getArtist().getId());
				ps.setString(4, s.getPhoto());
				ps.setString(5, s.getMedia());
				ps.setDouble(6, s.getDuration());
				ps.setInt(7, s.getReproductions());
				ps.setInt(8, s.getGenre().getId());
				ps.setInt(9, s.getDisc().getId());

				ps.setString(10, s.getName());
				ps.setInt(11, s.getArtist().getId());
				ps.setString(12, s.getPhoto());
				ps.setString(13, s.getMedia());
				ps.setDouble(14, s.getDuration());
				ps.setInt(15, s.getReproductions());
				ps.setInt(16, s.getGenre().getId());
				ps.setInt(17, s.getDisc().getId());

				rs = ps.executeUpdate();
				if (songs != null && songs.size() > 0 && songs.contains(songs)) {
					int i = songs.indexOf(songs);
					songs.set(i, s);
				} else {
					songs.add(s);
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

	public static void delete(ObservableList<Song> toDrop) {

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
	
	//cuando se elimina una cancion
	public static void delete_ReproductionList_Song_by_Song(ObservableList<Song> toDrop) {

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

				ps = con.prepareStatement(DELETEREPRO_SONG_BY_SONGS + s);
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

	public static void deleteByArtist(Artist a) {
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i).getArtist().getId() == a.getId()) {
				songs.remove(songs.get(i));
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

	public static void upload_Views(Song s) {
		int rs = 0;
		Connection con = MDBConexion.getConexion();
		PreparedStatement ps = null;
		if (con != null) {
			try {

				ps = con.prepareStatement(UPLOADVIEWS);
				ps.setInt(1, s.getId());
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
