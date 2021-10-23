package MusiClick.DAO.interfaces;

import java.util.List;

public interface IDAO {
	void guardar(Object o);
	void editar(Object o);
	void borrar(Object o);
	static List<Object> mostrarTodos() {
		return null;
	}
	Object mostrar(int id);
}
