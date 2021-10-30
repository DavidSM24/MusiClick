package tests;

import java.util.List;

import MusiClick.MDBDAO.GenreDAO;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test_DAO_Genre {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MDBConexion.loadServerInfo();
		
		Genre g=new Genre(1,"Rock");
		Genre g2=new Genre(2,"prueba_insert");
		Genre g3=new Genre(3,"prueba_insert");
		Genre g4=new Genre(4,"prueba_insert");
		ObservableList<Genre>genres=FXCollections.observableArrayList();
		genres.add(g);genres.add(g2);genres.add(g3);genres.add(g4);
		GenreDAO.save(g);GenreDAO.delete(genres);
		
	}

}
