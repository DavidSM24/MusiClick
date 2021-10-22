package tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.Song;

public class Test_models {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Genre> genres = new ArrayList<Genre>() {
			{
				add(new Genre(1, "Rock"));
				add(new Genre(2, "Pop"));
				add(new Genre(3, "Latina"));
			}
		};

		List<Artist> artists = new ArrayList<Artist>() {
			{
				add(new Artist(1, "Pedro", "url1"));
				add(new Artist(2, "Juan", "url2"));
				add(new Artist(3, "Sara", "url3"));
			}
		};

		List<Song> songs = new ArrayList<Song>() {
			{
				add(new Song(1, "Slide Away", "photo1", artists, 0, genres.get(0)));
			}
		};

		List<Disc> disc = new ArrayList<Disc>() {
			{
				add(new Disc(1, "Morning Glory", LocalDate.now(), "photo1", 0, songs));
			}
		};
		
		System.out.println(disc);
	}

}
