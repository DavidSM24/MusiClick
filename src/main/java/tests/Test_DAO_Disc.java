package tests;

import MusiClick.MDBDAO.DiscDAO;
import MusiClick.models.Disc;
import MusiClick.utils.MDBConexion;
import javafx.collections.ObservableList;

public class Test_DAO_Disc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		MDBConexion.loadServerInfo();
		ObservableList<Disc> discs=DiscDAO.getAll();
		System.out.println(discs);
	}

}
