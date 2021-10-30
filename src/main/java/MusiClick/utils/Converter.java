package MusiClick.utils;

import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Converter {
	
	public static ObservableList<Genre> genre_Converter (List<Genre> list){
		
		ObservableList<Genre> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Genre g:list) {
				result.add(g);
			}
		}
		
		return result;
		
	}
	
	public static ObservableList<Artist> artist_Converter (List<Artist> list){
		
		ObservableList<Artist> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Artist a:list) {
				result.add(a);
			}
		}
		
		return result;
		
	}
	
	public static ObservableList<Song> song_Converter (List<Song> list){
		
		ObservableList<Song> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Song s:list) {
				result.add(s);
			}
		}
		
		return result;
		
	}
	
	public static ObservableList<Disc> disc_Converter (List<Disc> list){
		
		ObservableList<Disc> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Disc d:list) {
				result.add(d);
			}
		}
		
		return result;
		
	}
	
}
