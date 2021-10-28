package tests;

import java.util.List;

import MusiClick.MDBDAO.ArtistDAO;
import MusiClick.models.Artist;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;
import javafx.collections.ObservableList;

public class Test_DAO_Artist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MDBConexion.loadServerInfo();
		ObservableList<Artist> artists=ArtistDAO.getAll();;
		
		System.out.println(artists);
		ArtistDAO.delete(new Artist(3,"Fito y Fitipaldis","photo3"));
		ArtistDAO.deleteAll();
		System.out.println("done");
		
	}

}
