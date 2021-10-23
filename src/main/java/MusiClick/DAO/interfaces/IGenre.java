package MusiClick.DAO.interfaces;

import java.util.List;

import MusiClick.models.Genre;

public interface IGenre extends IDAO{
	List<Genre> getByName(String name);
}
