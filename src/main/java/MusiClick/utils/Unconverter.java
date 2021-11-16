package MusiClick.utils;

import java.util.ArrayList;
import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import javafx.collections.ObservableList;

public class Unconverter {

	/**
	 * 
	 * @param list lista de géneros
	 * @return observablelist de géneros
	 */
	public static List<Genre> genre_UnConverter(ObservableList<Genre> list) {

		List<Genre> result = new ArrayList<Genre>();

		if (list != null && list.size() > 0) {
			for (Genre g : list) {
				result.add(g);
			}
		}

		return result;

	}

	/**
	 * 
	 * @param list lista de artistas
	 * @return observablelist de artistas
	 */
	public static List<Artist> artist_UnConverter(ObservableList<Artist> list) {

		List<Artist> result = new ArrayList<Artist>();

		if (list != null && list.size() > 0) {
			for (Artist a : list) {
				result.add(a);
			}
		}

		return result;

	}

	/**
	 * 
	 * @param list lista de canciones
	 * @return observablelist de canciones
	 */
	public static List<Song> song_Converter(ObservableList<Song> list) {

		List<Song> result = new ArrayList<Song>();

		if (list != null && list.size() > 0) {
			for (Song s : list) {
				result.add(s);
			}
		}

		return result;

	}

	/**
	 * 
	 * @param list lista de discos
	 * @return observablelist de discos
	 */
	public static List<Disc> disc_Converter(ObservableList<Disc> list) {

		List<Disc> result = new ArrayList<Disc>();

		if (list != null && list.size() > 0) {
			for (Disc d : list) {
				result.add(d);
			}
		}

		return result;

	}

	/**
	 * 
	 * @param list lista de repros
	 * @return observablelist de repros
	 */
	public static List<ReproductionList> repro_Converter(ObservableList<ReproductionList> list) {

		List<ReproductionList> result = new ArrayList<ReproductionList>();

		if (list != null && list.size() > 0) {
			for (ReproductionList r : list) {
				result.add(r);
			}
		}

		return result;

	}
}
