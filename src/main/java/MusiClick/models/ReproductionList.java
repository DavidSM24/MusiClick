package MusiClick.models;

import java.util.ArrayList;
import java.util.List;

public class ReproductionList {
	
	private int id;
	private String name;
	private List<Song> songs;
	private User creator;
	private List<User> subscribed_users;
	private int type;
	private String image;
	
	public ReproductionList() {
		this(-1,"",new ArrayList<Song>(),new User(),new ArrayList<User>(),0,"");
	}
	
	public ReproductionList(String name, List<Song> songs, User creator, List<User> subscribed_users,int type, String image) {
		super();
		this.name = name;
		this.songs = songs;
		this.creator = creator;
		this.subscribed_users=subscribed_users;
		this.type=type;
		this.image=image;
	}
	public ReproductionList(int id, String name, List<Song> songs, User creator,List<User> subscribed_users,int type,String image) {
		super();
		this.id = id;
		this.name = name;
		this.songs = songs;
		this.creator = creator;
		this.subscribed_users=subscribed_users;
		this.type=type;
		this.image=image;
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
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public List<User> getSubscribed_users() {
		return subscribed_users;
	}

	public void setSubscribed_users(List<User> subscribed_users) {
		this.subscribed_users = subscribed_users;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
		if (!(obj instanceof ReproductionList))
			return false;
		ReproductionList other = (ReproductionList) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReproductionList [id=" + id + ", name=" + name + ", songs=" + songs + ", creator=" + creator
				+ ", subscribed_users=" + subscribed_users + ", type=" + type + ", image=" + image + "]";
	}

	
	
	
	
	
}
