package tests;

import MusiClick.MDBDAO.SongDAO;
import MusiClick.models.Artist;
import MusiClick.models.Genre;
import MusiClick.models.Song;
import MusiClick.utils.Converter;
import MusiClick.utils.MDBConexion;
import javafx.collections.ObservableList;

public class Test_DAO_Song {
	
	public static void main(String[] args) {
		MDBConexion.loadServerInfo();
		ObservableList<Song> songs=Converter.song_Converter(SongDAO.getByName("Slide Away"));
//		System.out.println(songs);
//		Song s=new Song(1,"Slide Away",new Artist(1,"",""),"photo1","url1",7.5,1000,new Genre(3,"Britpop"));
//		Song s2=new Song(2,"Wonderwall",new Artist(1,"",""),"photo2","url2",6.5,1000,new Genre(3,"Britpop"));
//		Song s3=new Song(3,"El roce de tu cuerpo",new Artist(2,"",""),"photo3","url3",3.5,1000,new Genre(1,"Britpop"));
//		SongDAO.save(s);SongDAO.save(s2);SongDAO.save(s3);
		
	}
}
