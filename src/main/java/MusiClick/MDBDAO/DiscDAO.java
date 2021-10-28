package MusiClick.MDBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.Song;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DiscDAO {
	public static ObservableList<Disc> discs = FXCollections.observableArrayList();

	private static Connection con = null;

	private static final String GETALL = "SELECT id, name, date,photo, reproductions, id_artist FROM disc;";
	private static final String GETBYID = "SELECT id, name, date,photo, reproductions, id_artist "
			+ "FROM disc "
			+ "WHERE id=?;";
//	private static final String GETBYNAME = "SELECT id, name, id_artist,photo, url , duration, reproductions, id_genre "
//			+ "FROM song WHERE name LIKE ?;";
//	private final static String INSERT_UPDATE="INSERT INTO song (id, name,id_artist,photo, url, duration, reproductions,"
//			+ "id_genre) "
//			+ "VALUES (?,?,?,?,?,?,?,?) "
//			+ "ON DUPLICATE KEY UPDATE name=?,id_artist=?,photo=?,url=?,duration=?,reproductions=?,id_genre=?;";
//	private final static String DELETE ="DELETE FROM song WHERE id=?";
//	private final static String DELETEALL ="DELETE FROM song;";
	
	public static ObservableList<Disc> getAll() { //los devuelve vacios
		
		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					//int id, String name, LocalDate date, String photo,  int reproductions,Artist main_artist,ObservableList<Song> songs
					
					Artist aaux=ArtistDAO.getById(rs.getInt("id_artist"));
					if(ArtistDAO.artists.contains(aaux)) {
						int index=ArtistDAO.artists.indexOf(aaux);
						aaux=ArtistDAO.artists.get(index);
					}
					
					discs.add(new Disc(rs.getInt("id"), 
							rs.getString("name"),
							(rs.getDate("date")).toLocalDate() ,
							rs.getString("photo"),
							rs.getInt("reproductions"),
							aaux,
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

					Artist aaux=ArtistDAO.getById(rs.getInt("id_artist"));

					result= new Disc(rs.getInt("id"), 
							rs.getString("name"),
							(rs.getDate("date")).toLocalDate() ,
							rs.getString("photo"),
							rs.getInt("reproductions"),
							aaux,
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
	
	public static void insertSong (Song s, Disc d) {
		if(s!=null&&d!=null&&d.getSongs()!=null) {
			d.getSongs().add(s);
		}
	}
	
}
