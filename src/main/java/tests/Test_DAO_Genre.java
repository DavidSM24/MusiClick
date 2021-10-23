package tests;

import java.util.List;

import MusiClick.DAO.MDBDAO.GenreDAO;
import MusiClick.models.Genre;
import MusiClick.utils.MDBConexion;

public class Test_DAO_Genre {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MDBConexion.loadServerInfo();
		
		Genre g=new Genre(1,"Rock");
		Genre g2=new Genre(50,"prueba_insert");
		GenreDAO.save(g);GenreDAO.delete(g2);
		
	}

}
