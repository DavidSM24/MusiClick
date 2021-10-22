package MusiClick.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Song {
	
	private int id;
	private String name;
	private String photo;
	private List<Artist> artists;
	private int reproductions;
	private Genre genre;
	
	public Song() {
		this(-1,"","",new ArrayList(),0,new Genre());
	}

	public Song(int id, String name, String photo, List<Artist> artists, int reproductions, Genre genre) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.artists = artists;
		this.reproductions = reproductions;
		this.genre = genre;
	}

	public Song(String name, String photo, List<Artist> artists, int reproductions, Genre genre) {
		super();
		this.name = name;
		this.photo = photo;
		this.artists = artists;
		this.reproductions = reproductions;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public int getReproductions() {
		return reproductions;
	}

	public void setReproductions(int reproductions) {
		this.reproductions = reproductions;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Song))
			return false;
		Song other = (Song) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", photo=" + photo + ", artists=" + artists + ", reproductions="
				+ reproductions + ", genre=" + genre.getName() + "]";
	}
	
	
}
