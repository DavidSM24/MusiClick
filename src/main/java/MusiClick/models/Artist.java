package MusiClick.models;

public class Artist {
	private int id;
	private String name;
	private String photo;
	
	public Artist() {
		this(-1,"","");
	}
	
	public Artist(int id, String name, String photo) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

	public Artist(String name, String photo) {
		super();
		this.id=-1;
		this.name = name;
		this.photo = photo;
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
		if (!(obj instanceof Artist))
			return false;
		Artist other = (Artist) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", photo=" + photo + "]";
	}
}
