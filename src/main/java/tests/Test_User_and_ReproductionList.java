package tests;

import java.util.List;

import MusiClick.MDBDAO.ArtistDAO;
import MusiClick.MDBDAO.GenreDAO;
import MusiClick.MDBDAO.ReproductionListDAO;
import MusiClick.MDBDAO.SongDAO;
import MusiClick.MDBDAO.UserDAO;
import MusiClick.models.Artist;
import MusiClick.models.Genre;
import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.MDBConexion;

public class Test_User_and_ReproductionList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MDBConexion.loadServerInfo();
		List<Genre> genres=GenreDAO.getAll();
		List<Artist> artists=ArtistDAO.getAll();
		List<Song> songs=SongDAO.getAll();
		List<User> users=UserDAO.getAllUsersAsList();
		
		List<ReproductionList> repro=ReproductionListDAO.getAll();
		System.out.println(repro);
						
	}
	
}
