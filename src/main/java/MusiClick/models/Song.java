package MusiClick.models;

public class Song {
	
	private int id;
	private String name;
	private Artist artist;
	private String photo;
	private String media;
	private double duration;	
	private int reproductions;
	private Genre genre;
	private Disc disc;
	
	public Song() {
		this(-1,"",new Artist(),"","",0f,0,new Genre());
	}

	public Song(int id, String name,Artist artists, String photo,String media,double duration,  int reproductions, Genre genre) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.media=media;
		this.duration=duration;
		this.artist = artists;
		this.reproductions = reproductions;
		this.genre = genre;
		this.disc=new Disc();
	}

	public Song(String name, String photo,String media, Artist artists,double duration, int reproductions, Genre genre) {
		super();
		this.name = name;
		this.photo = photo;
		this.media=media;
		this.duration=duration;
		this.artist = artists;
		this.reproductions = reproductions;
		this.genre = genre;
		this.disc=new Disc();
	}
	
	public Song(int id,String name, String photo,String media, Artist artists,double duration, int reproductions, Genre genre, Disc disc) {
		super();
		this.id=id;
		this.name = name;
		this.photo = photo;
		this.media=media;
		this.duration=duration;
		this.artist = artists;
		this.reproductions = reproductions;
		this.genre = genre;
		this.disc=disc;
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

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artists) {
		this.artist = artists;
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
	
	public Disc getDisc() {
		return disc;
	}

	public void setDisc(Disc disc) {
		this.disc = disc;
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
		return "Song ["+name + "--> " +getDisc().getName() + "]";
	}
	
	
}
