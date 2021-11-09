package MusiClick.models;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Disc {
	
	//id,name,date,photo,reproductions,main_artist,songs
	private int id;
	private String name;
	private LocalDate date;
	private String photo;
	private int reproductions;
	private Artist main_artist;
	private List<Song> songs;
	
	public Disc() {
		this(-1,"",LocalDate.now(),"",0,new Artist(),FXCollections.observableArrayList());
	}
	
	public Disc(int id, String name, LocalDate date, String photo,  int reproductions,Artist main_artist,List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.photo = photo;
		this.songs = songs;
		this.main_artist=main_artist;
		this.reproductions = reproductions;
	}

	public Disc (String name, LocalDate date, String photo,  int reproductions,Artist main_artist,List<Song> songs) {
		super();
		this.name = name;
		this.date = date;
		this.photo = photo;
		this.songs = songs;
		this.main_artist=main_artist;
		this.reproductions = reproductions;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getReproductions() {
		return reproductions;
	}

	public void setReproductions(int reproductions) {
		this.reproductions = reproductions;
	}

	public Artist getMain_artist() {
		return main_artist;
	}

	public void setMain_artist(Artist main_artist) {
		this.main_artist = main_artist;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(ObservableList<Song> songs) {
		this.songs = songs;
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
		if (!(obj instanceof Disc))
			return false;
		Disc other = (Disc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
