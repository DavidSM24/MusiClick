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

public class ReproductionListDAO {

	public static List<ReproductionList> repro = new ArrayList<ReproductionList>();

	private static Connection con = null;

	private static final String GETALL = "SELECT id, name, id_user FROM reproductionList;";
	private static final String GETBYID = "SELECT id, name, photo FROM artist WHERE id=?;";
	private static final String GETBYNAME = "SELECT id, name, photo FROM artist WHERE name LIKE ?;";
	private final static String INSERT_UPDATE="INSERT INTO artist (id, name, photo) "
			+ "VALUES (?,?, ?) "
			+ "ON DUPLICATE KEY UPDATE name=?, photo=?;";
	private final static String DELETE ="DELETE FROM artist WHERE id IN ";
	//DELETE FROM `artist` WHERE id NOT LIKE (0);
	private final static String DELETEALL ="DELETE FROM artist;";
	
	public static List<ReproductionList> getAll(){
		
		repro = new ArrayList<ReproductionList>();

		con = MDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					
					ReproductionList f=new ReproductionList();
					f.setId(rs.getInt("id"));
					System.out.println(f.getId());
					User uaux=UserDAO.getUserByReproductionListCreator(f);
					List<Song> saux=SongDAO.getByReproductionList(f);
					List<User> lsaux=UserDAO.getByReproductionListSusbcribed(f);
					
					ReproductionList aux=new ReproductionList(
							f.getId(), 
							rs.getString("name"),
							saux,
							uaux,
							lsaux);
					
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
	
}
