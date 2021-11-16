package MusiClick.utils;

import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Converter {
	
	/**
	 * 
	 * @param lista de géneros
	 * @return observablelist de géneros
	 */
	public static ObservableList<Genre> genre_Converter (List<Genre> list){
		
		ObservableList<Genre> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Genre g:list) {
				result.add(g);
			}
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param lista de artista
	 * @return observablelist de artista
	 */
	public static ObservableList<Artist> artist_Converter (List<Artist> list){
		
		ObservableList<Artist> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Artist a:list) {
				result.add(a);
			}
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param lista de canciones
	 * @return observablelist de canciones
	 */
	public static ObservableList<Song> song_Converter (List<Song> list){
		
		ObservableList<Song> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Song s:list) {
				result.add(s);
			}
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param lista de discos
	 * @return observablelist de discos
	 */
	public static ObservableList<Disc> disc_Converter (List<Disc> list){
		
		ObservableList<Disc> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(Disc d:list) {
				result.add(d);
			}
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param lista de repros
	 * @return observablelist de repros
	 */
	public static ObservableList<ReproductionList> repro_Converter (List<ReproductionList> list){
		
		ObservableList<ReproductionList> result=FXCollections.observableArrayList();
		
		if(list!=null&&list.size()>0) {
			for(ReproductionList r:list) {
				result.add(r);
			}
		}
		
		return result;
		
	}
	
}
