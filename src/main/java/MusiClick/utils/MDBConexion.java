package MusiClick.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class MDBConexion {
	
	private static String server=""; //jdbc:mysql://localhost:3307
	private static String database=""; //"musiclick
	private static String username=""; //"root"
	private static String password="";
	
	private static Connection con = null;

	/**
	 * Debe ir cargado desde un xml externo
	 */

	/**
	 * 
	 * @return una conexión a una bbdd
	 */
	public static Connection getConexion() {
		if (con == null) {
			try {
				con = DriverManager.getConnection(server+"/"+database,username,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con = null;
			}
		}
		return con;
	}
	
	/**
	 * Cierra los recursos de una conexión abierta
	 */
	public static void cerrar() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Lee la información para conectarse a una bbdd
	 */
	public static void loadServerInfo() {

		try {
			File file = new File("connection.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			NodeList nList = doc.getElementsByTagName("connection");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nodo = nList.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodo;
					
					server = element.getElementsByTagName("server").item(0).getTextContent();
					database = element.getElementsByTagName("database").item(0).getTextContent();
					username = element.getElementsByTagName("user").item(0).getTextContent();
					password = element.getElementsByTagName("password").item(0).getTextContent();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * guarda un archivo xml con la información de una bbdd
	 */
	public static void saveServerInfo() {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document documento = implementation.createDocument(null, "connection", null);
			documento.setXmlVersion("1.0");

			Element info = documento.createElement("info");

			Element Eserver = documento.createElement("server");
			Text textServer = documento.createTextNode(server + "");
			Eserver.appendChild(textServer);

			Element Edatabase = documento.createElement("database");
			Text textDatabase = documento.createTextNode(database + "");
			Edatabase.appendChild(textDatabase);

			Element Euser = documento.createElement("user");
			Text textUser = documento.createTextNode(username + "");
			Euser.appendChild(textUser);

			Element Epassword = documento.createElement("password");
			Text textPassword = documento.createTextNode(password + "");
			Epassword.appendChild(textPassword);

			info.appendChild(Eserver);
			info.appendChild(Edatabase);
			info.appendChild(Euser);
			info.appendChild(Epassword);

			documento.getDocumentElement().appendChild(info);

			Source source = new DOMSource(documento);
			Result result = new StreamResult(new File("connection.xml"));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerConfigurationException
				| TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
