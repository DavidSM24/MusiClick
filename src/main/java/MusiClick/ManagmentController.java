package MusiClick;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import MusiClick.MDBDAO.ArtistDAO;
import MusiClick.MDBDAO.DiscDAO;
import MusiClick.MDBDAO.GenreDAO;
import MusiClick.MDBDAO.ReproductionListDAO;
import MusiClick.MDBDAO.SongDAO;
import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.Genre;
import MusiClick.models.ReproductionList;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.Converter;
import MusiClick.utils.FileUtilities;
import MusiClick.utils.MDBConexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class ManagmentController {

	private boolean inizialize = false;

	// ARTIST
	private ObservableList<Artist> artists;
	private Artist artist = null;

	@FXML
	protected Button btn_add_Artist;
	@FXML
	protected Button btn_edit_Artist;
	@FXML
	private Button btn_delete_Artist;
	@FXML
	protected TableView<Artist> table_artist;
	@FXML
	protected TableColumn<Artist, String> col_artist_id;
	@FXML
	protected TableColumn<Artist, String> col_artist_name;
	@FXML
	protected TableColumn<Artist, String> col_artist_photo;
	@FXML
	protected TextField txt_artist_id;
	@FXML
	protected TextField txt_artist_name;
	@FXML
	protected TextField txt_artist_photo;

	// SONG

	private ObservableList<Song> songs;
	private Song song = null;

	@FXML
	protected Button btn_add_Song;
	@FXML
	protected Button btn_edit_Song;
	@FXML
	private Button btn_delete_Song;
	@FXML
	protected TableView<Song> table_song;
	@FXML
	protected TableColumn<Song, String> col_song_id;
	@FXML
	protected TableColumn<Song, String> col_song_name;
	@FXML
	protected TableColumn<Song, String> col_song_artist;
	@FXML
	protected TextField txt_song_id;
	@FXML
	protected TextField txt_song_name;

	@FXML
	protected TextArea txt_description;

	@FXML
	protected TextField txt_song_media;
	@FXML
	protected TextField txt_song_photo;
	@FXML
	protected TextField txt_song_duration;
	@FXML
	protected ComboBox<Genre> com_song_genre;
	@FXML
	protected ComboBox<Artist> com_song_artist;
	@FXML
	protected ComboBox<Disc> com_song_disc;

	// GENRE

	private ObservableList<Genre> genres;
	private Genre genre = null;

	@FXML
	protected Button btn_add_Genre;
	@FXML
	protected Button btn_edit_Genre;
	@FXML
	private Button btn_delete_Genre;
	@FXML
	protected TableView<Genre> table_genre;
	@FXML
	protected TableColumn<Genre, String> col_genre_id;
	@FXML
	protected TableColumn<Genre, String> col_genre_name;
	@FXML
	protected TextField txt_genre_id;
	@FXML
	protected TextField txt_genre_name;

	// DISC

	private ObservableList<Disc> discs;
	private Disc disc = null;
	private Disc d_pred = null;

	@FXML
	protected Button btn_add_Disc;
	@FXML
	protected Button btn_edit_Disc;
	@FXML
	private Button btn_delete_Disc;
	@FXML
	protected TableView<Disc> table_disc;
	@FXML
	protected TableColumn<Disc, String> col_disc_id;
	@FXML
	protected TableColumn<Disc, String> col_disc_name;
	@FXML
	protected TableColumn<Disc, String> col_disc_artist;
	@FXML
	protected TextField txt_disc_id;
	@FXML
	protected TextField txt_disc_name;
	@FXML
	protected TextField txt_disc_photo;
	@FXML
	protected ComboBox<Artist> com_disc_artist;
	@FXML
	protected DatePicker dat_disc_date;

	// REPROS

	private ObservableList<ReproductionList> repros;
	ReproductionList repro;

	@FXML
	private Button btn_add_Repro;
	@FXML
	private Button btn_edit_Repro;
	@FXML
	private Button btn_delete_Repro;
	@FXML
	private Button btn_set_repro_img;

	@FXML
	private TableView<ReproductionList> table_repro;
	@FXML
	private TableColumn<ReproductionList, String> col_repro_id;
	@FXML
	private TableColumn<ReproductionList, String> col_repro_name;

	@FXML
	private TableView<Song> table_repro_song_inside;
	@FXML
	private TableColumn<Song, String> col_repro_song_inside_name_artist;

	@FXML
	private TableView<Song> table_repro_song_outside;
	@FXML
	private TableColumn<Song, String> col_repro_song_outside_name_artist;

	@FXML
	private TextField txt_repro_id;
	@FXML
	private TextField txt_repro_name;
	@FXML
	private TextField txt_repro_img;

	// MANAGER METHODS

	@FXML
	public void setController() { // <----- metodo charge
		MDBConexion.loadServerInfo();

		// set ARTISTS

		artists = Converter.artist_Converter(ArtistDAO.getAll());
		table_artist.setItems(artists);
		setTableAndDetailsInfo();
		btn_add_Artist.setDisable(false);

		// set GENRES

		genres = Converter.genre_Converter(GenreDAO.getAll());
		table_genre.setItems(genres);
		setTableAndDetailsInfo();
		btn_add_Genre.setDisable(false);

		// get Empty Discs
		discs = Converter.disc_Converter(DiscDAO.getAll());

		// set SONGS

		songs = Converter.song_Converter(SongDAO.getAll());
		table_song.setItems(songs);
		setTableAndDetailsInfo();
		com_song_artist.setItems(artists);
		com_song_genre.setItems(genres);
		com_song_disc.setItems(discs);

		btn_add_Song.setDisable(false);

		// set DISCS
		table_disc.setItems(discs);
		setTableAndDetailsInfo();

		com_disc_artist.setItems(artists);

		d_pred = discs.get(0);

		btn_add_Disc.setDisable(false);

		// set Repros

		repros = Converter.repro_Converter(ReproductionListDAO.getAllDefault());
		table_repro.setItems(repros);

		setTableAndDetailsInfo();
		btn_add_Repro.setDisable(false);

		artists.remove(0);

		inizialize = true;

		if (!inizialize) {
			show_Alert(4);
		}

	}

	private void setTableAndDetailsInfo() {

		// artists
		if (artists != null && artists.size() > 0) {
			col_artist_id.setCellValueFactory(eachartist -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachartist.getValue().getId() + "");
				return v;
			});

			col_artist_name.setCellValueFactory(eachartist -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachartist.getValue().getName());
				return v;
			});

			col_artist_photo.setCellValueFactory(eachartist -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachartist.getValue().getPhoto());
				return v;
			});
		}

		// genres
		if (genres != null && genres.size() > 0) {
			col_genre_id.setCellValueFactory(eachgenre -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachgenre.getValue().getId() + "");
				return v;
			});

			col_genre_name.setCellValueFactory(eachgenre -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachgenre.getValue().getName());
				return v;
			});
		}

		// songs
		if (songs != null && songs.size() > 0) {
			col_song_id.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getId() + "");
				return v;
			});

			col_song_name.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getName());
				return v;
			});

			col_song_artist.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getArtist().getName());
				return v;
			});
		}

		// discs
		if (discs != null && discs.size() > 0) {
			col_disc_id.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getId() + "");
				return v;
			});

			col_disc_name.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getName());
				return v;
			});

			col_disc_artist.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getMain_artist().getName());
				return v;
			});
		}

		// repros

		if (repros != null && repros.size() > 0) {
			col_repro_id.setCellValueFactory(eachrepro -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachrepro.getValue().getId() + "");
				return v;
			});

			col_repro_name.setCellValueFactory(eachrepro -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachrepro.getValue().getName());
				return v;
			});
		}
	}

	@FXML
	private void save_to_BD() throws IOException {
		if (inizialize) {

			// SAVE GENRES
			if (genres.size() > 0) {
				// borrar y actualizar

				// borrar no existentes en gernes y sí en bd
				ObservableList<Genre> GinDB = Converter.genre_Converter(GenreDAO.getAll());
				ObservableList<Genre> toDrop = FXCollections.observableArrayList();

				if (GinDB.size() > 0) {
					for (Genre g : genres) {
						for (int i = 0; i < GinDB.size(); i++) {
							if (!genres.contains(GinDB.get(i))) {
								toDrop.add(GinDB.get(i));
							}
						}
					}
				}

				if (toDrop.size() > 0) {
					GenreDAO.delete(toDrop);
				}

				// updatear existentes
				for (Genre g2 : genres) {
					GenreDAO.save(g2);
				}
			} else { // borrar todos
				GenreDAO.deleteAll();
			}

			// SAVE ARTISTS

			if (artists.size() > 0) {
				// borrar y actualizar

				// borrar no existentes en la db
				ObservableList<Artist> AinDB = Converter.artist_Converter(ArtistDAO.getAll());
				ObservableList<Artist> toDrop = FXCollections.observableArrayList();

				if (AinDB.size() > 0) {
					for (Artist a : artists) {
						for (int i = 0; i < AinDB.size(); i++) {
							if (!artists.contains(AinDB.get(i))) {
								toDrop.add(AinDB.get(i));
							}
						}
					}
				}
				if (toDrop.size() > 0) {

					for (Artist a : toDrop) {
						FileUtilities.removeFile(a.getPhoto()); // >------
					}

					ArtistDAO.delete(toDrop);
				}

				// updatear existentes
				for (Artist a2 : artists) {

					if (!a2.getPhoto().matches("[src/main/resources/images/artist/].*")) {
						FileUtilities.saveFile(a2.getPhoto(),"src/main/resources/images/artist/a" + a2.getId() + a2.getName() + ".jpg");

						a2.setPhoto("src/main/resources/images/artist/a" + a2.getId() + a2.getName() + ".jpg");
					}

					ArtistDAO.save(a2);
				}
			} else { // borrar todos
				ArtistDAO.deleteAll();
			}

			// SAVE DISC

			if (discs.size() > 0) {
				// borrar y actualizar

				// borrar no existentes en la db
				ObservableList<Disc> DinDB = Converter.disc_Converter(DiscDAO.getAll());
				ObservableList<Disc> toDrop = FXCollections.observableArrayList();

				if (DinDB.size() > 0) {
					for (Disc d : discs) {
						for (int i = 0; i < DinDB.size(); i++) {
							if (!discs.contains(DinDB.get(i))) {
								toDrop.add(DinDB.get(i));
							}
						}
					}
				}

				if (toDrop.size() > 0) {

					for (Disc d : toDrop) {
						FileUtilities.removeFile(d.getPhoto()); // >------
					}

					DiscDAO.delete(toDrop);
				}

				// updatear existentes
				for (Disc d2 : discs) {

					if (d2.getId() != 1) {
						if (!d2.getPhoto().matches("[src/main/resources/images/disc/].*")) {
							FileUtilities.saveFile(d2.getPhoto(),
									"src/main/resources/images/disc/d" + d2.getId() + d2.getName() + ".jpg");

							d2.setPhoto("src/main/resources/images/disc/d" + d2.getId() + d2.getName() + ".jpg");
						}
					}

					DiscDAO.save(d2);
				}
			} else { // borrar todos
				DiscDAO.deleteAll();
			}

			// SAVE SONGS

			if (songs.size() > 0) {
				// borrar y actualizar

				ObservableList<Song> SinDB = Converter.song_Converter(SongDAO.getAll());
				ObservableList<Song> toDrop = FXCollections.observableArrayList();

				if (SinDB.size() > 0) {
					for (Song s : songs) {
						for (int i = 0; i < SinDB.size(); i++) {
							if (!songs.contains(SinDB.get(i))) {

								toDrop.add(SinDB.get(i));
							}
						}
					}
				}

				if (toDrop.size() > 0) {
					for (Song s : toDrop) {
						FileUtilities.removeFile(s.getMedia());
						FileUtilities.removeFile(s.getPhoto());
					}
					
					SongDAO.delete_ReproductionList_Song_by_Song(toDrop);
					SongDAO.delete(toDrop);
				}

				for (Song s2 : songs) {

					if (!s2.getMedia().matches("[src/main/resources/mp3/].*")) {
						FileUtilities.saveFile(s2.getMedia(),
								"src/main/resources/mp3/s" + s2.getId() + s2.getName() + ".mp3");
						s2.setMedia("src/main/resources/mp3/s" + s2.getId() + s2.getName() + ".mp3");
					}

					if (!s2.getPhoto().matches("[src/main/resources/images/song/].*")) {
						FileUtilities.saveFile(s2.getPhoto(),
								"src/main/resources/images/song/s" + s2.getId() + s2.getName() + ".jpg");
						s2.setPhoto("src/main/resources/images/song/s" + s2.getId() + s2.getName() + ".jpg");
					}
					SongDAO.save(s2);

				}
			} else { // borrar todos
				ObservableList<Song> SinDB = Converter.song_Converter(SongDAO.getAll());
				for (Song s : SinDB) {
					FileUtilities.removeFile(s.getMedia());
					FileUtilities.removeFile(s.getPhoto());
				}
				SongDAO.deleteAll();
			}

			// SAVE REPROS
			
			if (repros.size() > 0) {
				// borrar y actualizar

				// borrar no existentes en la db
				ObservableList<ReproductionList> RinDB = Converter.repro_Converter(ReproductionListDAO.getAll());
				ObservableList<ReproductionList> toDrop = FXCollections.observableArrayList();

				if (RinDB.size() > 0) {
					for (ReproductionList r : repros) {
						for (int i = 0; i < RinDB.size(); i++) {
							if (!repros.contains(RinDB.get(i))) {
								toDrop.add(RinDB.get(i));
							}
						}
					}
				}

				if (toDrop.size() > 0) {

					for (ReproductionList r : toDrop) {
						FileUtilities.removeFile(r.getImage()); // >------
					}
					
					ReproductionListDAO.delete_ReproductionList_Song_By_Repros(toDrop);
					ReproductionListDAO.delete_ReproductionList_User_By_Repros(toDrop);
					ReproductionListDAO.delete(toDrop);
				}

				// updatear existentes
				for (ReproductionList r2 : repros) {

					if (r2.getId() != 1) {
						if (!r2.getImage().matches("[src/main/resources/images/default/].*")) {
							FileUtilities.saveFile(r2.getImage(),
									"src/main/resources/images/default/r" + r2.getId() + ".jpg");

							r2.setImage("src/main/resources/images/default/r" + r2.getId() + ".jpg");
						}
					}

					ReproductionListDAO.save(r2);
					ObservableList<ReproductionList> aux= FXCollections.observableArrayList();
					aux.add(r2);
					ReproductionListDAO.delete_ReproductionList_Song_By_Repros(aux);
					ReproductionListDAO.insert_ReproductionList_Song_By_Repro(r2);
				}
			} else { // borrar todos
				ReproductionListDAO.deleteAll();
			}
			
			show_Alert(5);
			setController();
		} else {
			show_Alert(0);
		}
	}

	private void refresh() {
		table_song.getColumns().get(0).setVisible(false);
		table_song.getColumns().get(0).setVisible(true);

		table_artist.getColumns().get(0).setVisible(false);
		table_artist.getColumns().get(0).setVisible(true);

		table_genre.getColumns().get(0).setVisible(false);
		table_genre.getColumns().get(0).setVisible(true);
		
		if(table_repro_song_inside.getColumns().size()>0) {
			table_repro_song_inside.getColumns().get(0).setVisible(false);
			table_repro_song_inside.getColumns().get(0).setVisible(true);
		}
		
		
		if(table_repro_song_outside.getColumns().size()>0) {
			table_repro_song_outside.getColumns().get(0).setVisible(false);
			table_repro_song_outside.getColumns().get(0).setVisible(true);
		}
	}

	private void show_Alert(int type) {
		String f = "";
		switch (type) {
		case 0:
			f = "Necesita cargar los datos de la BD.";
			break;
		case 1:
			f = "Operación incorrecta, este id ya está en uso.";
			break;
		case 3:
			f = "No hay datos para operar.";
			break;
		case 4:
			f = "Datos cargados correctamente.";
			break;
		case 5:
			f = "Se ha actualizado su base de datos correctamente";
			break;
		}
		Alert alert2 = new Alert(AlertType.INFORMATION);
		alert2.setHeaderText(null);
		alert2.setTitle("Información");
		alert2.setContentText(f);
		alert2.showAndWait();
	}

	// ARTIST METHODS

	@FXML
	public void set_artist_Photo() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.png") || file.getPath().matches(".+\\.jpg")) {
				txt_artist_photo.setText(file.getPath());
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
		}
	}

	@FXML
	private void select_Artist() {
		if (inizialize && artists.size() > 0) {
			if (table_artist.getSelectionModel().getSelectedItem() != null) {
				artist = this.table_artist.getSelectionModel().getSelectedItem();
				btn_edit_Artist.setDisable(false);
				btn_delete_Artist.setDisable(false);
				txt_artist_id.setText(table_artist.getSelectionModel().getSelectedItem().getId() + "");
				txt_artist_name.setText(table_artist.getSelectionModel().getSelectedItem().getName());
				txt_description.setText(table_artist.getSelectionModel().getSelectedItem().getDescription());
				txt_artist_photo.setText(table_artist.getSelectionModel().getSelectedItem().getPhoto());
			} else {
				artist = null;
				btn_edit_Artist.setDisable(true);
				btn_delete_Artist.setDisable(true);
				txt_artist_id.setText("");
				txt_artist_name.setText("");
				txt_description.setText("");
				txt_artist_photo.setText("");
			}

		} else {
			artist = null;
			btn_edit_Artist.setDisable(true);
			btn_delete_Artist.setDisable(true);
			txt_artist_id.setText("");
			txt_artist_name.setText("");
			txt_description.setText("");
			txt_artist_photo.setText("");

		}
	}

	@FXML
	private void add_Artist() {
		if (inizialize) {
			if (txt_artist_id.getText().matches("[0-9]+") && !txt_artist_name.getText().matches("")
					&& !txt_description.getText().matches("")) {
				boolean same_Id = false;
				for (int i = 0; i < artists.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_artist_id.getText()) == artists.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id) {
					artist = new Artist(Integer.parseInt(txt_artist_id.getText() + ""), txt_artist_name.getText(),
							txt_description.getText(), txt_artist_photo.getText());
					artists.add(artist);

					setTableAndDetailsInfo();
					refresh();

				} else {
					show_Alert(1);
				}
			}
		} else {
			show_Alert(0);
		}
	}

	@FXML
	private void edit_Artist() {
		try {
			if (artist != null && !txt_artist_id.getText().matches("") && txt_artist_id.getText().matches("[0-9]+")
					&& !txt_artist_name.getText().matches("") && !txt_description.getText().matches("")
					&& !txt_artist_photo.getText().matches("")) {

				boolean same_Id = false;
				for (int i = 0; i < artists.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_artist_id.getText()) == artists.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id | Integer.parseInt(txt_artist_id.getText()) == artist.getId()) {
					int index = artists.indexOf(artist);
					table_artist.getSelectionModel().getSelectedItem().setId(Integer.parseInt(txt_artist_id.getText()));
					table_artist.getSelectionModel().getSelectedItem().setName(txt_artist_name.getText());
					table_artist.getSelectionModel().getSelectedItem().setDescription(txt_description.getText());
					table_artist.getSelectionModel().getSelectedItem().setPhoto(txt_artist_photo.getText());

					artist = table_artist.getSelectionModel().getSelectedItem();
					artists.set(index, artist);

					refresh();
				} else {
					show_Alert(1);
				}
				table_song.getColumns().get(0).setVisible(false);
				table_song.getColumns().get(0).setVisible(true);

			}
		} catch (Exception e) {

		}

	}

	@FXML
	private void delete_Artist() {

		if (inizialize) {
			if (artist != null) {
				ObservableList<Song> toDrop = Converter.song_Converter(SongDAO.getAll());
				if (toDrop.size() > 0) {

					// preguntar si eliminar las canciones
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setTitle("Confirmación");
					alert.setContentText("Se eleminarán todas las canciones de este Artista, ¿desea continuar?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						// eliminar todos las las canciones de ese artist

						for (Song s : toDrop) {
							if (songs.contains(s)) {
								song = s;
								delete_Song();
							}
						}

						artists.remove(artist);
						refresh();
					}

				} else {
					artists.remove(artist);
					refresh();
				}

			} else {
				show_Alert(3);
			}
			if (artists.size() <= 0) {
				artist = null;
				btn_edit_Artist.setDisable(true);
				btn_delete_Artist.setDisable(true);
				txt_artist_id.setText("");
				txt_artist_name.setText("");
				txt_artist_photo.setText("");
			} else {
				artist = artists.get(0);
				btn_edit_Artist.setDisable(false);
				btn_delete_Artist.setDisable(false);
				txt_artist_id.setText(artists.get(0).getId() + "");
				txt_artist_name.setText(artists.get(0).getName());
				txt_artist_photo.setText(artists.get(0).getPhoto());
			}
		} else {
			show_Alert(0);
		}
	}

	// GENRE METHODS

	@FXML
	private void select_Genre() {
		if (inizialize && genres.size() > 0) {
			if (table_genre.getSelectionModel().getSelectedItem() != null) {
				genre = this.table_genre.getSelectionModel().getSelectedItem();
				btn_edit_Genre.setDisable(false);
				btn_delete_Genre.setDisable(false);
				txt_genre_id.setText(table_genre.getSelectionModel().getSelectedItem().getId() + "");
				txt_genre_name.setText(table_genre.getSelectionModel().getSelectedItem().getName());
			} else {
				genre = null;
				btn_edit_Genre.setDisable(true);
				btn_delete_Genre.setDisable(true);
				txt_genre_id.setText("");
				txt_genre_name.setText("");
			}

		} else {
			genre = null;
			btn_edit_Genre.setDisable(true);
			btn_delete_Genre.setDisable(true);
			txt_genre_id.setText("");
			txt_genre_name.setText("");

		}
	}

	@FXML
	private void add_Genre() {
		if (inizialize) {
			if (txt_genre_id.getText().matches("[0-9]+") && !txt_genre_name.getText().matches("")) {
				boolean same_Id = false;
				for (int i = 0; i < genres.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_genre_id.getText()) == genres.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id) {
					genre = new Genre(Integer.parseInt(txt_genre_id.getText() + ""), txt_genre_name.getText());
					genres.add(genre);

					setTableAndDetailsInfo();
					refresh();

				} else {
					show_Alert(1);
				}
			}
		} else {
			show_Alert(0);
		}
	}

	@FXML
	private void edit_Genre() {

		if (genre != null && !txt_genre_id.getText().matches("") && txt_genre_id.getText().matches("[0-9]+")
				&& !txt_genre_name.getText().matches("")) {

			boolean same_Id = false;
			for (int i = 0; i < genres.size() && !same_Id; i++) {
				if (Integer.parseInt(txt_genre_id.getText()) == genres.get(i).getId()) {
					same_Id = true;
				}
			}
			if (!same_Id | Integer.parseInt(txt_genre_id.getText()) == genre.getId()) {
				int index = genres.indexOf(genre);
				table_genre.getSelectionModel().getSelectedItem().setId(Integer.parseInt(txt_genre_id.getText()));
				table_genre.getSelectionModel().getSelectedItem().setName(txt_genre_name.getText());

				genre = table_genre.getSelectionModel().getSelectedItem();
				genres.set(index, genre);

				refresh();
			} else {
				show_Alert(1);
			}
		}
	}

	@FXML
	private void delete_Genre() {

		if (inizialize) {
			ObservableList<Song> toDrop = Converter.song_Converter(SongDAO.getAll());
			if (toDrop.size() <= 0) { // no hay canciones con este género
				if (genres.size() > 1) {
					for (Song s : songs) {
						if (s.getGenre().getId() == genre.getId() && genres.get(0) != genre) {
							s.setGenre(genres.get(0));
						} else {
							if (s.getGenre().getId() == genre.getId()) {
								s.setGenre(genres.get(1));
							}
						}
					}

					genres.remove(genre);
					refresh();
				} else {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setHeaderText(null);
					alert2.setTitle("Confirmación");
					alert2.setContentText("Deben existir al menos 2 géneros para editar uno.");
					alert2.showAndWait();
				}
			}

			else if (toDrop.size() > 0) { // hay cnaciones con este género

				// preguntar si eliminar las canciones
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText("Se editarán todas las canciones con este género, ¿desea continuar?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					// eliminar todos las las canciones de genero
					if (genres.size() > 1) {
						for (Song s : songs) {
							if (s.getGenre().getId() == genre.getId() && genres.get(0) != genre) {
								s.setGenre(genres.get(0));
							} else {
								if (s.getGenre().getId() == genre.getId()) {
									s.setGenre(genres.get(1));
								}
							}
						}

						genres.remove(genre);
						refresh();
					} else {
						Alert alert2 = new Alert(AlertType.ERROR);
						alert2.setHeaderText(null);
						alert2.setTitle("Confirmación");
						alert2.setContentText("Deben existir al menos 2 géneros para editar uno.");
						alert2.showAndWait();
					}

				}
			}

			if (genres.size() <= 0) {
				genre = null;
				btn_edit_Genre.setDisable(true);
				btn_delete_Genre.setDisable(true);
				txt_genre_id.setText("");
				txt_genre_name.setText("");
			} else {
				genre = genres.get(0);
				btn_edit_Genre.setDisable(false);
				btn_delete_Genre.setDisable(false);
				txt_genre_id.setText(genres.get(0).getId() + "");
				txt_genre_name.setText(genres.get(0).getName());
			}
		} else {
			show_Alert(0);
		}
	}

	// SONG METHODS

	@FXML
	public void set_song_Photo() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.png") || file.getPath().matches(".+\\.jpg")) {
				txt_song_photo.setText(file.getPath());
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
		}
	}

	@FXML
	public void set_song_Media() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar archivo mp3...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.mp3")) {
				txt_song_media.setText(file.getPath());
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir archivo de tipo mp3.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
		}
	}

	@FXML
	private void select_Song() {
		if (inizialize && songs.size() > 0) {
			if (table_song.getSelectionModel().getSelectedItem() != null) {
				song = this.table_song.getSelectionModel().getSelectedItem();
				btn_edit_Song.setDisable(false);
				btn_delete_Song.setDisable(false);

				txt_song_id.setText(table_song.getSelectionModel().getSelectedItem().getId() + "");
				txt_song_name.setText(table_song.getSelectionModel().getSelectedItem().getName());
				txt_song_photo.setText(table_song.getSelectionModel().getSelectedItem().getPhoto());
				txt_song_media.setText(table_song.getSelectionModel().getSelectedItem().getMedia());
				txt_song_duration.setText(table_song.getSelectionModel().getSelectedItem().getDuration() + "");

				com_song_genre.setItems(genres);
				com_song_genre.setValue(table_song.getSelectionModel().getSelectedItem().getGenre());

				com_song_artist.setItems(artists);
				com_song_artist.setValue(table_song.getSelectionModel().getSelectedItem().getArtist());

				com_song_disc.setItems(discs);
				com_song_disc.setValue(table_song.getSelectionModel().getSelectedItem().getDisc());

			} else {
				song = null;
				btn_edit_Song.setDisable(true);
				btn_delete_Song.setDisable(true);
				txt_song_id.setText("");
				txt_song_name.setText("");
				txt_song_photo.setText("");
				txt_song_media.setText("");
				txt_song_duration.setText("");

				com_song_genre.setItems(genres);
				com_song_genre.setValue(null);

				com_song_artist.setItems(artists);
				com_song_artist.setValue(null);
				com_song_disc.setValue(discs.get(0));
			}

		} else {
			song = null;
			btn_edit_Song.setDisable(true);
			btn_delete_Song.setDisable(true);
			txt_song_id.setText("");
			txt_song_name.setText("");
			txt_song_photo.setText("");
			txt_song_media.setText("");
			txt_song_duration.setText("");

			com_song_genre.setItems(genres);
			com_song_genre.setValue(null);

			com_song_artist.setItems(artists);
			com_song_artist.setValue(null);
			com_song_disc.setValue(discs.get(0));

		}
	}

	@FXML
	private void add_Song() {
		if (inizialize) {
			if (!txt_song_id.getText().matches("") && txt_song_id.getText().matches("[0-9]+")
					&& !txt_song_name.getText().matches("") && !txt_song_photo.getText().matches("")
					&& !txt_song_media.getText().matches("") && !txt_song_duration.getText().matches("")
					&& (txt_song_duration.getText().matches("[0-9]+" + "\\." + "[0-9]{1,}")
							| txt_song_duration.getText().matches("[0-9]+"))
					&& com_song_artist.getSelectionModel().getSelectedItem() != null
					&& com_song_disc.getSelectionModel().getSelectedItem() != null) {

				boolean same_Id = false;
				for (int i = 0; i < songs.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_song_id.getText()) == songs.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id) {

					song = new Song(Integer.parseInt(txt_song_id.getText() + ""), txt_song_name.getText(),
							txt_song_photo.getText(), txt_song_media.getText(),
							com_song_artist.getSelectionModel().getSelectedItem(),
							Double.parseDouble(txt_song_duration.getText()), 0,
							com_song_genre.getSelectionModel().getSelectedItem(),
							com_song_disc.getSelectionModel().getSelectedItem());

					songs.add(song);
					setTableAndDetailsInfo();
					refresh();

				} else {
					show_Alert(1);
				}
			}
		} else {
			show_Alert(0);
		}
	}

	@FXML
	private void edit_Song() {

		if (song != null && !txt_song_id.getText().matches("") && txt_song_id.getText().matches("[0-9]+")
				&& !txt_song_name.getText().matches("") && !txt_song_photo.getText().matches("")
				&& !txt_song_media.getText().matches("") && !txt_song_duration.getText().matches("")
				&& (txt_song_duration.getText().matches("[0-9]+" + "\\." + "[0-9]{1,}")
						| txt_song_duration.getText().matches("[0-9]+"))) {

			boolean same_Id = false;
			for (int i = 0; i < songs.size() && !same_Id; i++) {
				if (Integer.parseInt(txt_song_id.getText()) == songs.get(i).getId()) {
					same_Id = true;
				}
			}
			if (!same_Id | Integer.parseInt(txt_song_id.getText()) == song.getId()) {
				int index = songs.indexOf(song);
				table_song.getSelectionModel().getSelectedItem().setId(Integer.parseInt(txt_song_id.getText()));
				table_song.getSelectionModel().getSelectedItem().setName(txt_song_name.getText());
				table_song.getSelectionModel().getSelectedItem().setPhoto(txt_song_photo.getText());
				table_song.getSelectionModel().getSelectedItem().setMedia(txt_song_media.getText());
				table_song.getSelectionModel().getSelectedItem()
						.setDuration(Double.parseDouble(txt_song_duration.getText()));
				table_song.getSelectionModel().getSelectedItem()
						.setArtist(com_song_artist.getSelectionModel().getSelectedItem());
				table_song.getSelectionModel().getSelectedItem()
						.setGenre(com_song_genre.getSelectionModel().getSelectedItem());
				table_song.getSelectionModel().getSelectedItem()
						.setDisc(com_song_disc.getSelectionModel().getSelectedItem());

				song = table_song.getSelectionModel().getSelectedItem();
				songs.set(index, song);

				refresh();

			} else {
				show_Alert(1);
			}
		}
	}

	@FXML
	private void delete_Song() {
		if (inizialize) {
			if (song != null) {
				
				if(repros!=null&&repros.size()>0) {
					for(ReproductionList r:repros) {
						if(r.getSongs()!=null&&r.getSongs().size()>0) {
							if(r.getSongs().contains(song)) {
								r.getSongs().remove(song);
							}
						}
						
					}
				}
				
				songs.remove(song);
				refresh();
			} else {
				show_Alert(3);
			}
			if (songs.size() <= 0) {
				song = null;
				btn_edit_Song.setDisable(true);
				btn_delete_Song.setDisable(true);
				txt_song_id.setText("");
				txt_song_name.setText("");
				txt_song_photo.setText("");
				txt_song_media.setText("");
				txt_song_duration.setText("");
				com_song_artist.setValue(artists.get(0));
				com_song_genre.setValue(genres.get(0));
				
				if(repros!=null&&table_repro.getSelectionModel().getSelectedItem()!=null) {
					repro=table_repro.getSelectionModel().getSelectedItem();
					table_repro.getSelectionModel().select(repro);
					select_Repro();
				}
				else {
					if(table_repro.getItems().size()>0) {
						repro=table_repro.getItems().get(0);
						table_repro.getSelectionModel().select(repro);
						select_Repro();
					}
				}
			} else {
				song = songs.get(0);
				btn_edit_Song.setDisable(false);
				btn_delete_Song.setDisable(false);
				txt_song_id.setText(songs.get(0).getId() + "");
				txt_song_name.setText(songs.get(0).getName());
				txt_song_photo.setText(songs.get(0).getPhoto());
				txt_song_media.setText(songs.get(0).getMedia());
				txt_song_duration.setText(songs.get(0).getDuration() + "");
				com_song_artist.setValue(artists.get(0));
				com_song_genre.setValue(genres.get(0));
				
				if(repros!=null&&table_repro.getSelectionModel().getSelectedItem()!=null) {
					repro=table_repro.getSelectionModel().getSelectedItem();
					table_repro.getSelectionModel().select(repro);
					select_Repro();
				}
				else {
					if(table_repro.getItems().size()>0) {
						repro=table_repro.getItems().get(0);
						table_repro.getSelectionModel().select(repro);
						select_Repro();
					}
				}
			}
		} else {
			show_Alert(0);
		}
	}

	// DISC METHODS

	@FXML
	public void set_disc_Photo() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.png") || file.getPath().matches(".+\\.jpg")) {
				txt_disc_photo.setText(file.getPath());
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
		}
	}

	@FXML
	private void select_Disc() {
		if (inizialize && discs.size() > 0) {
			if (table_disc.getSelectionModel().getSelectedItem() != null
					&& table_disc.getSelectionModel().getSelectedItem() != d_pred) {
				disc = this.table_disc.getSelectionModel().getSelectedItem();
				btn_edit_Disc.setDisable(false);
				btn_delete_Disc.setDisable(false);

				txt_disc_id.setText(table_disc.getSelectionModel().getSelectedItem().getId() + "");
				txt_disc_name.setText(table_disc.getSelectionModel().getSelectedItem().getName());
				txt_disc_photo.setText(table_disc.getSelectionModel().getSelectedItem().getPhoto());

				com_disc_artist.setItems(artists);
				com_disc_artist.setValue(table_disc.getSelectionModel().getSelectedItem().getMain_artist());

				dat_disc_date.setValue(table_disc.getSelectionModel().getSelectedItem().getDate());

			} else {
				disc = null;
				btn_edit_Disc.setDisable(true);
				btn_delete_Disc.setDisable(true);
				txt_disc_id.setText("");
				txt_disc_name.setText("");
				txt_disc_photo.setText("");

				com_disc_artist.setItems(artists);
				com_disc_artist.setValue(null);

				dat_disc_date.setValue(null);
			}

		} else {
			disc = null;
			btn_edit_Disc.setDisable(true);
			btn_delete_Disc.setDisable(true);
			txt_song_id.setText("");
			txt_song_name.setText("");
			txt_disc_photo.setText("");

			com_disc_artist.setItems(artists);
			com_disc_artist.setValue(null);

			dat_disc_date.setValue(null);

		}
	}

	@FXML
	private void add_Disc() {
		if (inizialize) {
			if (!txt_disc_id.getText().matches("") && txt_disc_id.getText().matches("[0-9]+")
					&& !txt_disc_name.getText().matches("") && !txt_disc_photo.getText().matches("")
					&& com_disc_artist.getSelectionModel().getSelectedItem() != null
					&& dat_disc_date.getValue() != null) {

				boolean same_Id = false;
				for (int i = 0; i < discs.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_disc_id.getText()) == discs.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id) {

					disc = new Disc(Integer.parseInt(txt_disc_id.getText() + ""), txt_disc_name.getText(),
							dat_disc_date.getValue(), txt_disc_photo.getText(), 0,
							com_disc_artist.getSelectionModel().getSelectedItem(), FXCollections.observableArrayList());

					discs.add(disc);
					setTableAndDetailsInfo();
					refresh();

				} else {
					show_Alert(1);
				}
			}
		} else {
			show_Alert(0);
		}
	}

	@FXML
	private void edit_Disc() {
		if (disc != null && !txt_disc_id.getText().matches("") && txt_disc_id.getText().matches("[0-9]+")
				&& !txt_disc_name.getText().matches("") && !txt_disc_photo.getText().matches("")
				&& dat_disc_date.getValue() != null && com_disc_artist.getSelectionModel().getSelectedItem() != null) {

			boolean same_Id = false;
			for (int i = 0; i < discs.size() && !same_Id; i++) {
				if (Integer.parseInt(txt_disc_id.getText()) == discs.get(i).getId()) {
					same_Id = true;
				}
			}
			if (!same_Id | Integer.parseInt(txt_disc_id.getText()) == disc.getId()) {
				int index = discs.indexOf(disc);
				table_disc.getSelectionModel().getSelectedItem().setId(Integer.parseInt(txt_disc_id.getText()));
				table_disc.getSelectionModel().getSelectedItem().setName(txt_disc_name.getText());
				table_disc.getSelectionModel().getSelectedItem().setPhoto(txt_disc_photo.getText());
				table_disc.getSelectionModel().getSelectedItem()
						.setMain_artist(com_disc_artist.getSelectionModel().getSelectedItem());
				table_disc.getSelectionModel().getSelectedItem().setDate(dat_disc_date.getValue());

				disc = table_disc.getSelectionModel().getSelectedItem();
				discs.set(index, disc);

				refresh();

			} else {
				show_Alert(1);
			}
		}
	}

	@FXML
	private void delte_Disc() {
		if (inizialize) {
			if (disc != null) {
				if (disc.getSongs().size() > 0) {
					for (Song s : songs) {
						if (s.getDisc().getId() == disc.getId()) {
							s.setDisc(discs.get(0));
						}
					}
				}
				discs.remove(disc);
				refresh();
			} else {
				show_Alert(3);
			}
			if (discs.size() <= 0) {
				disc = null;
				btn_edit_Disc.setDisable(true);
				btn_delete_Disc.setDisable(true);
				txt_disc_id.setText("");
				txt_disc_name.setText("");
				txt_disc_photo.setText("");
				com_disc_artist.setValue(null);
				dat_disc_date.setValue(null);
			} else {
				disc = discs.get(0);
				btn_edit_Disc.setDisable(false);
				btn_delete_Disc.setDisable(false);
				txt_disc_id.setText(discs.get(0).getId() + "");
				txt_disc_name.setText(discs.get(0).getName());
				txt_disc_photo.setText(discs.get(0).getPhoto());
				com_disc_artist.setValue(discs.get(0).getMain_artist());
				dat_disc_date.setValue(discs.get(0).getDate());

				btn_edit_Disc.setDisable(true);
				btn_delete_Disc.setDisable(true);
			}
		} else {
			show_Alert(0);
		}
	}

	// REPRO METHODS

	@FXML
	public void set_repro_Photo() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.png") || file.getPath().matches(".+\\.jpg")) {
				txt_repro_img.setText(file.getPath());
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
		}
	}
	
	@FXML
	private void select_Repro() {
		if (inizialize && repros.size() > 0) {
			if (table_repro.getSelectionModel().getSelectedItem() != null) {

				repro = this.table_repro.getSelectionModel().getSelectedItem();
				btn_edit_Repro.setDisable(false);
				btn_delete_Repro.setDisable(false);

				txt_repro_id.setText(repro.getId() + "");
				txt_repro_name.setText(repro.getName());
				txt_repro_img.setText(repro.getImage());

				table_repro_song_inside.setItems(Converter.song_Converter(repro.getSongs()));

				// meter outside

				ObservableList<Song> saux = Converter.song_Converter(SongDAO.getAll());
				saux.removeAll(repro.getSongs());

				table_repro_song_outside.setItems(saux);
				
				insert_Repro_Songs_Info();

			} else {
				repro = null;
				btn_edit_Repro.setDisable(true);
				btn_delete_Repro.setDisable(true);
				txt_repro_id.setText("");
				txt_repro_name.setText("");
				txt_repro_img.setText("");
				table_repro_song_inside.setItems(FXCollections.observableArrayList());
				table_repro_song_outside.setItems(FXCollections.observableArrayList());
				
				insert_Repro_Songs_Info();

			}

		} else {
			repro = null;
			btn_edit_Repro.setDisable(true);
			btn_delete_Repro.setDisable(true);
			txt_repro_id.setText("");
			txt_repro_name.setText("");
			txt_repro_img.setText("");
			table_repro_song_inside.setItems(FXCollections.observableArrayList());
			table_repro_song_outside.setItems(FXCollections.observableArrayList());
			
			insert_Repro_Songs_Info();

		}
	}
	
	@FXML
	private void add_Repro() {
		if (inizialize) {
			if (!txt_repro_id.getText().matches("")
					&& txt_repro_id.getText().matches("[0-9]+")
					&& !txt_repro_name.getText().matches("")
					&& !txt_repro_img.getText().matches("")) {

				boolean same_Id = false;
				for (int i = 0; i < repros.size() && !same_Id; i++) {
					if (Integer.parseInt(txt_repro_id.getText()) == repros.get(i).getId()) {
						same_Id = true;
					}
				}
				if (!same_Id) {
					
					User aux=new User();
					aux.setId(1);
					
					repro = new ReproductionList(
							Integer.parseInt(txt_repro_id.getText() + ""), 
							txt_repro_name.getText(),
							table_repro_song_inside.getItems(), 
							aux,
							FXCollections.observableArrayList(), 
							0,
							txt_repro_img.getText());

					repros.add(repro);
					refresh();
					
				} else {
					show_Alert(1);
				}
			}
		} else {
			show_Alert(0);
		}
	}
	
	@FXML
	private void edit_Repo() {
		if (repros != null && !txt_repro_id.getText().matches("") && txt_repro_id.getText().matches("[0-9]+")
				&& !txt_repro_name.getText().matches("") && !txt_repro_img.getText().matches("")) {

			boolean same_Id = false;
			for (int i = 0; i < repros.size() && !same_Id; i++) {
				if (Integer.parseInt(txt_repro_id.getText()) == repros.get(i).getId()) {
					same_Id = true;
				}
			}
			if (!same_Id | Integer.parseInt(txt_repro_id.getText()) == repro.getId()) {
				int index = repros.indexOf(repro);
				table_repro.getSelectionModel().getSelectedItem().setId(Integer.parseInt(txt_repro_id.getText()));
				table_repro.getSelectionModel().getSelectedItem().setName(txt_repro_name.getText());
				table_repro.getSelectionModel().getSelectedItem().setImage(txt_repro_img.getText());
				table_repro.getSelectionModel().getSelectedItem().setSongs(table_repro_song_inside.getItems());

				repro = table_repro.getSelectionModel().getSelectedItem();
				repros.set(index, repro);

				refresh();
			} else {
				show_Alert(1);
			}
			table_song.getColumns().get(0).setVisible(false);
			table_song.getColumns().get(0).setVisible(true);

		}
	}
	
	@FXML
	private void delete_Repro() {
		if (inizialize) {
			
			if (repros.size() <= 0) {
				repros.remove(table_repro.getSelectionModel().getSelectedItem());
				repro = null;
				btn_edit_Repro.setDisable(true);
				btn_delete_Repro.setDisable(true);
				txt_repro_id.setText("");
				txt_repro_name.setText("");
				txt_repro_img.setText("");
				
				table_repro_song_inside.setItems(FXCollections.observableArrayList());
				table_repro_song_outside.setItems(FXCollections.observableArrayList());
				insert_Repro_Songs_Info();

			} else {
				repros.remove(table_repro.getSelectionModel().getSelectedItem());
				table_repro.getSelectionModel().select(0);
				select_Repro();

				btn_edit_Disc.setDisable(true);
				btn_delete_Disc.setDisable(true);
			}
		} else {
			show_Alert(0);
		}
	}
	
	@FXML
	private void insert_Repro_Songs_Info() {

		// INSIDE

		if (repro!=null&&repro.getSongs() != null) {
			col_repro_song_inside_name_artist.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getArtist().getName() + " / " + eachsong.getValue().getName());
				return v;
			});
		}

		if(songs!=null&&songs.size()>0) {
			col_repro_song_outside_name_artist.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getArtist().getName() + " / " + eachsong.getValue().getName());
				return v;
			});
		}
		
	}
	
	@FXML
	private void update_Repro_Songs_info() {
		if(table_repro_song_inside.getColumns().size()>0) {
			table_repro_song_inside.getColumns().get(0).setVisible(false);
			table_repro_song_inside.getColumns().get(0).setVisible(true);
		}
		
		
		if(table_repro_song_outside.getColumns().size()>0) {
			table_repro_song_outside.getColumns().get(0).setVisible(false);
			table_repro_song_outside.getColumns().get(0).setVisible(true);
		}
	}

	@FXML
	private void toOut() {
		if(table_repro_song_inside.getSelectionModel().getSelectedItem()!=null) {
			Song aux=table_repro_song_inside.getSelectionModel().getSelectedItem();
			table_repro_song_inside.getItems().remove(aux);
			if(!table_repro_song_outside.getItems().contains(aux)) {
				table_repro_song_outside.getItems().add(aux);
			}
			
			update_Repro_Songs_info();
			
		}
	}
	
	@FXML
	private void toIn() {
		if(table_repro_song_outside.getSelectionModel().getSelectedItem()!=null) {
			Song aux=table_repro_song_outside.getSelectionModel().getSelectedItem();
			table_repro_song_outside.getItems().remove(aux);
			if(!table_repro_song_inside.getItems().contains(aux)) {
				table_repro_song_inside.getItems().add(aux);
			}
			
			update_Repro_Songs_info();
			
		}
	}
}