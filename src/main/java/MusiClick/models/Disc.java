package MusiClick.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Disc {
	
	private int id;
	private String name;
	private LocalDate date;
	private String photo;
	private int reproductions;
	private List<Song> songs;
	
	public Disc() {
		this(-1,"",LocalDate.now(),"",0,new ArrayList());
	}
	
	public Disc(int id, String name, LocalDate date, String photo,  int reproductions,List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.photo = photo;
		this.songs = songs;
		this.reproductions = reproductions;
	}

	public Disc (String name, LocalDate date, String photo,  int reproductions,List<Song> songs) {
		super();
		this.name = name;
		this.date = date;
		this.photo = photo;
		this.songs = songs;
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

	public List<Song> getSongs() {
		return songs;
	}

	public void setArtists(List<Song> songs) {
		this.songs = songs;
	}

	public int getReproductions() {
		return reproductions;
	}

	public void setReproductions(int reproductions) {
		this.reproductions = reproductions;
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
		return "Disc [id=" + id + ", name=" + name + ", date=" + date + ", photo=" + photo + ", songs=" + songs
				+ ", reproductions=" + reproductions + "]";
	}
}
