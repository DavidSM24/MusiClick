package MusiClick;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import MusiClick.MDBDAO.DiscDAO;
import MusiClick.MDBDAO.SesionDAO;
import MusiClick.MDBDAO.SongDAO;
import MusiClick.models.Disc;
import MusiClick.models.Sesion;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.Converter;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrimaryController {

	// PRIMARY

	ObservableList<Song> songs;
	Song song = new Song();
	User u = null;

	@FXML
	Button btn_delete_User;

	@FXML
	private Button btn_user;
	@FXML
	private Pane options_Pane;

	// SONG TAB

	@FXML
	private Pane black_pane;

	@FXML
	private TabPane tabpane;

	@FXML
	private Pane artist_pane;

	@FXML
	private Pane disc_pane;
	@FXML

	private ImageView img_song;
	@FXML
	private Label lab_song_name;
	@FXML
	Label lab_song_artist;
	@FXML
	private Label lab_song_disc;

	// ARTIST INFO
	@FXML
	private TabPane tab_disc_info;
	@FXML
	private TabPane tab_artist_info;

	@FXML
	private ImageView img_artist_info;
	@FXML
	private Label lab_artistinfo_name;
	@FXML
	private Label lab_artistinfo_description;
	@FXML
	private TableView<Disc> table_artist_info_disc;
	@FXML
	private TableColumn<Disc, String> col_artist_info_disc_name;
	@FXML
	private TableColumn<Disc, String> col_artist_info_disc_publication;
	@FXML
	private TableColumn<Disc, String> col_artist_info_disc_reproductions;

	// DISC INFO
	@FXML
	private ImageView img_disc_info;
	@FXML
	private Label lab_discinfo_name;
	@FXML
	private TableView<Song> table_disc_info_song;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_name;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_genre;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_reproductions;

	// REPRODUCTOR

	@FXML

	private TableView<Song> table_song;
	@FXML
	private TableColumn<Song, String> col_song_name;

	private MediaPlayer mp;
	@FXML
	private MediaView mediaView;
	private Duration duration;
	@FXML
	private Slider timeSlider;
	@FXML
	private Label playTime;
	@FXML
	private Slider volumeSlider;

	@FXML
	private Button playButton;

	private final boolean repeat = false;
	private boolean stopRequested = false;
	private boolean atEndOfMedia = false;

	protected static Sesion ss = null;
	@FXML
	private TextField txt_filter;
	@FXML
	private Button btn_managment;

	@FXML
	public void setController(User u, Sesion ss) {
		this.ss = ss;
		this.u = u;
		btn_user.setText(u.getUsername());

		playButton.setText("►");

		lab_song_name.setText("");
		lab_song_artist.setText("");
		lab_song_disc.setText("");

		lab_song_name.setStyle("-fx-text-fill:white;");
		lab_song_artist.setStyle("-fx-text-fill:white;");
		lab_song_disc.setStyle("-fx-text-fill:white;");

		songs = Converter.song_Converter(SongDAO.getAll());
		table_song.setItems(songs);

		setTableAndDetailsInfo();
	}

	public void setSongInPlayer(String url) {

		File filestring = new File(url);
		Media media = new Media(filestring.toURI().toString());
		mp = new MediaPlayer(media);
		mp.stop();
		mediaView.setMediaPlayer(mp);
		mp.setAutoPlay(true);

		mp.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateValues();
			}
		});

		mp.setOnPlaying(new Runnable() {
			public void run() {
				if (stopRequested) {
					mp.pause();
					stopRequested = false;
				} else {
					playButton.setText("||");
				}
			}
		});

		mp.setOnPaused(new Runnable() {
			public void run() {
				System.out.println("onPaused");
				playButton.setText("►");
			}
		});

		mp.setOnReady(new Runnable() {
			public void run() {
				duration = mp.getMedia().getDuration();
				updateValues();
			}
		});

		mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
		mp.setOnEndOfMedia(new Runnable() {
			public void run() {
				if (!repeat) {
					playButton.setText("►");
					stopRequested = true;
					atEndOfMedia = true;
				}
			}
		});

		timeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider position
					mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
				}
			}
		});

		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (volumeSlider.isValueChanging()) {
					mp.setVolume(volumeSlider.getValue() / 100.0);
				}
			}
		});
	}

	public void setTableAndDetailsInfo() {
		// songs

		col_song_name.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getArtist().getName() + " - " + eachsong.getValue().getName());
			return v;
		});

	}

	private void setDetailsAndTableInfoArtist() {
		if (song != null) {
			col_artist_info_disc_name.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getName());
				return v;
			});

			col_artist_info_disc_publication.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getDate().toString());
				return v;
			});

			col_artist_info_disc_reproductions.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getReproductions() + "");
				return v;
			});
		}
	}

	private void setDetailsAndTableInfoDisc() {
		if (song != null) {
			col_disc_info_song_name.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getName());
				return v;
			});

			col_disc_info_song_genre.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getGenre().getName());
				return v;
			});

			col_disc_info_song_reproductions.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getReproductions() + "");
				return v;
			});
		}
	}

	@FXML
	private void goToManagment() throws IOException {
		try {

			Stage stage = (Stage) this.btn_managment.getScene().getWindow();
			stage.close();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("managment.fxml"));
			Parent root = loader.load();
			ManagmentController managment_controller = loader.getController();
			// managment_controller.setController();
			Scene scene = new Scene(root);
			Stage stage2 = new Stage();
			stage2.setScene(scene);
			// Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
			stage2.setTitle("MusiClick Managment Tool");
			// stage2.getIcons().add(image);
			// stage2.setResizable(false);;
			stage2.initModality(Modality.APPLICATION_MODAL);

			stage2.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendSession() {
		System.out.println("entro a sendSession?");
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Timestamp ts = new Timestamp(System.currentTimeMillis());
						ss.setTime(ts);
						SesionDAO.guardar(ss);
						Thread.sleep(1000);
						// System.out.println("hola");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@FXML
	private void play_pause() {

		if (mp != null & song != null) {
			try {
				Status status = mp.getStatus();

				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					// rewind the movie if we're sitting at the end
					if (atEndOfMedia) {
						mp.seek(mp.getStartTime());
						atEndOfMedia = false;
					}
					mp.play();
				} else {
					mp.pause();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	protected void updateValues() {
		if (playTime != null && timeSlider != null && volumeSlider != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					Duration currentTime = mp.getCurrentTime();
					playTime.setText(formatTime(currentTime, duration));
					timeSlider.setDisable(duration.isUnknown());
					if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO)
							&& !timeSlider.isValueChanging()) {
						timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
					}
					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
					}
				}
			});
		}
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

	@FXML
	private void select_Song() {
		if (songs != null && songs.size() > 0 && table_song.getSelectionModel().getSelectedItem() != null
				&& !song.equals(table_song.getSelectionModel().getSelectedItem())) {
			song = table_song.getSelectionModel().getSelectedItem();

			File f = new File(song.getPhoto());
			Image face = new Image("file:" + f.getPath());
			img_song.setImage(face);

			lab_song_name.setText(song.getName());
			lab_song_artist.setText("Artista: " + song.getArtist().getName());
			if (song.getDisc().getId() != 1) {
				lab_song_disc.setText("Disco: " + song.getDisc().getName());
			} else {
				lab_song_disc.setText("");
			}

			try {
				mp.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
			setSongInPlayer(song.getMedia());
		}
	}
	
	@FXML
	private void select_Song(Song s) {
		if (s!=null) {
			song = s;

			File f = new File(song.getPhoto());
			Image face = new Image("file:" + f.getPath());
			img_song.setImage(face);

			lab_song_name.setText(song.getName());
			lab_song_artist.setText("Artista: " + song.getArtist().getName());
			if (song.getDisc().getId() != 1) {
				lab_song_disc.setText("Disco: " + song.getDisc().getName());
			} else {
				lab_song_disc.setText("");
			}

			try {
				mp.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
			setSongInPlayer(song.getMedia());
		}
	}

	@FXML
	public void filter_Songs_ByName() {
		if (!txt_filter.getText().matches("")) {
			ObservableList<Song> filter = FXCollections.observableArrayList();
			ObservableList<Song> filter2 = FXCollections.observableArrayList();
			filter = Converter.song_Converter(SongDAO.getByName(txt_filter.getText()));
			filter2 = Converter.song_Converter(SongDAO.getByArtistName(txt_filter.getText()));
			filter.addAll(filter2);
			table_song.setItems(filter);
			setTableAndDetailsInfo();
		} else {
			table_song.setItems(songs);
			setTableAndDetailsInfo();
		}
	}

	@FXML
	private void open_Artist_Pane() {
		if (song != null) {

			File f2 = new File(song.getArtist().getPhoto());
			Image artist_song = new Image("file:" + f2.getPath());
			img_artist_info.setImage(artist_song);
			lab_artistinfo_name.setText((song.getArtist().getName()));
			lab_artistinfo_description.setText(song.getArtist().getDescription());
			ObservableList<Disc> disc_by_artist = Converter.disc_Converter(DiscDAO.getByArtist(song.getArtist()));
			table_artist_info_disc.setItems(disc_by_artist);
			setDetailsAndTableInfoArtist();
			table_artist_info_disc.getSelectionModel().select(null);

			tab_artist_info.getSelectionModel().select(0);
			black_pane.setVisible(true);
			artist_pane.setVisible(true);
		}

	}

	@FXML
	private void close_Artist_Pane() {
		artist_pane.setVisible(false);
		lab_song_artist.setTextFill(Color.WHITE);
		lab_song_disc.setTextFill(Color.WHITE);
		black_pane.setVisible(false);
	}

	@FXML
	private void open_Disc_Pane() {
		if (song != null) {

			File f2 = new File(song.getDisc().getPhoto());
			Image disc_song = new Image("file:" + f2.getPath());
			img_disc_info.setImage(disc_song);
			lab_discinfo_name.setText((song.getDisc().getName()));
			ObservableList<Song> songs_by_disc = Converter.song_Converter(SongDAO.getByDisc(song.getDisc()));
			table_disc_info_song.setItems(songs_by_disc);
			setDetailsAndTableInfoDisc();			
			table_disc_info_song.getSelectionModel().select(null);
			
			tab_disc_info.getSelectionModel().select(0);
			black_pane.setVisible(true);
			disc_pane.setVisible(true);
		}
	}


	@FXML
	private void open_Disc_Pane(Disc d) {
		
		File f2 = new File(d.getPhoto());
		Image disc_song = new Image("file:" + f2.getPath());
		img_disc_info.setImage(disc_song);
		lab_discinfo_name.setText(d.getName());
		ObservableList<Song> songs_by_disc = Converter.song_Converter(d.getSongs());
		table_disc_info_song.setItems(songs_by_disc);
		setDetailsAndTableInfoDisc();

		tab_disc_info.getSelectionModel().select(0);
		black_pane.setVisible(true);
		disc_pane.setVisible(true);

	}

	@FXML
	private void close_Disc_Pane() {
		disc_pane.setVisible(false);
		lab_song_artist.setTextFill(Color.WHITE);
		lab_song_disc.setTextFill(Color.WHITE);
		black_pane.setVisible(false);
	}

	@FXML
	private void goToDisc() {
		close_Artist_Pane();
		Disc aux = table_artist_info_disc.getSelectionModel().getSelectedItem();
		if(aux!=null) {
			open_Disc_Pane(aux);
		}
		

	}
	
	@FXML
	private void goToPlayFromDisc() {
		Song aux=table_disc_info_song.getSelectionModel().getSelectedItem();
		if (!song.equals(aux)) {
			song = aux;
			
			table_song.getSelectionModel().select(aux);
			select_Song(aux);
			close_Disc_Pane();
		}
	}

	@FXML
	private void open_Options_Pane() {
		options_Pane.setVisible(true);
	}

	@FXML
	private void close_Options_Pane() {

		btn_delete_user.setTextFill(Color.WHITE);
		btn_close_sesion.setTextFill(Color.WHITE);
		options_Pane.setVisible(false);
	}

	@FXML
	private void changeColorUserName() {
		btn_user.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void Default_close_session() {
		btn_close_sesion.setTextFill(Color.WHITE);
	}

	@FXML
	private void Default_delete_user() {
		btn_delete_user.setTextFill(Color.WHITE);
	}

	@FXML
	private void changeColor_close_Session() {
		btn_close_sesion.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void changeColor_delete_user() {
		btn_delete_user.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void changeColorDefaultUserName() {
		btn_user.setTextFill(Color.WHITE);
	}

	@FXML
	private void changeColor_disc_Info() {
		lab_song_disc.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void changeColorDefault_disc_Info() {
		lab_song_disc.setTextFill(Color.WHITE);
	}

	@FXML
	private void changeColor_Artist_Info() {
		lab_song_artist.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void changeColorDefault_Artist_Info() {
		lab_song_artist.setTextFill(Color.WHITE);
	}

	@FXML
	private Button btn_close_sesion;
	@FXML
	private Button btn_delete_user;
}
