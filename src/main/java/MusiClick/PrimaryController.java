package MusiClick;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import MusiClick.MDBDAO.DiscDAO;
import MusiClick.MDBDAO.ReproductionListDAO;
import MusiClick.MDBDAO.SesionDAO;
import MusiClick.MDBDAO.SongDAO;
import MusiClick.models.Artist;
import MusiClick.models.Disc;
import MusiClick.models.ReproductionList;
import MusiClick.models.Sesion;
import MusiClick.models.Song;
import MusiClick.models.User;
import MusiClick.utils.Converter;
import MusiClick.utils.FileUtilities;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class PrimaryController {

	// PRIMARY
	int istr;
	ObservableList<Song> songsToReproduce;
	ObservableList<Song> songsToReproduceListened;
	ObservableList<Song> listened;

	ObservableList<Song> songs;
	ObservableList<Disc> discs;

	Song song = new Song();
	Disc disc = new Disc();
	Disc discInPane = new Disc();

	User u = null;

	@FXML
	private Button btn_close_sesion;
	@FXML
	private Button btn_delete_user;
	@FXML
	private Button btn_delete_User;

	@FXML
	private Button btn_user;
	@FXML
	private Pane options_Pane;

	// LIST TAB

	ReproductionList repro;

	ObservableList<ReproductionList> repros;
	ObservableList<ReproductionList> userRepros;

	@FXML
	private Pane black_pane2;
	@FXML
	private Pane list_pane;

	@FXML
	private Label lab_lt_1;
	@FXML
	private Label lab_lt_2;
	@FXML
	private Label lab_lt_3;
	@FXML
	private Label lab_lt_4;
	@FXML
	private Label lab_lt_5;
	@FXML
	private Label lab_lt_6;
	@FXML
	private Label lab_lt_7;
	@FXML
	private Label lab_lt_8;
	@FXML
	private Label lab_lt_9;
	@FXML
	private Label lab_lt_10;

	@FXML
	private ImageView img_lt_1;
	@FXML
	private ImageView img_lt_2;
	@FXML
	private ImageView img_lt_3;
	@FXML
	private ImageView img_lt_4;
	@FXML
	private ImageView img_lt_5;
	@FXML
	private ImageView img_lt_6;
	@FXML
	private ImageView img_lt_7;
	@FXML
	private ImageView img_lt_8;
	@FXML
	private ImageView img_lt_9;
	@FXML
	private ImageView img_lt_10;

	@FXML
	private TableView<ReproductionList> table_repros;
	@FXML
	private TableColumn<ReproductionList, String> col_repros_name;

	@FXML
	private TableView<ReproductionList> table_userRepros;
	@FXML
	private TableColumn<ReproductionList, String> col_userRepros_name;

	// list_pane

	@FXML
	private Button btn_subs;
	@FXML
	private Button btn_unsubs;

	@FXML
	private ImageView img_list_info;
	@FXML
	private Label lab_listinfo_name;
	@FXML
	private TableView<Song> table_list_info_song;
	@FXML
	private TableColumn<Song, String> col_list_song_name;
	@FXML
	private TableColumn<Song, String> col_list_song_genre;
	@FXML
	private TableColumn<Song, String> col_list_song_reproductions;

	// creation_pane

	ObservableList<Song> songs_creation_inside;
	ObservableList<Song> songs_creation_outside;
	String str_image;

	@FXML
	private CheckBox che_public;
	@FXML
	private Pane creation_pane;
	@FXML
	private TextField txt_creation_name;
	@FXML
	private ImageView img_creation;
	@FXML
	private TableView<Song> table_creation_inside;
	@FXML
	private TableColumn<Song, String> col_creation_inside_name;
	@FXML
	private TableView<Song> table_creation_outside;
	@FXML
	private TableColumn<Song, String> col_creation_outside_name;

	// SEARCHER TAB

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
	private TableView<Song> table_song;
	@FXML
	private TableColumn<Song, String> col_song_name;

	@FXML
	private TableView<Disc> table_disc;
	@FXML
	private TableColumn<Disc, String> col_disc_name;

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
	private Label lab_discinfo_artist;
	@FXML
	private TableView<Song> table_disc_info_song;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_name;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_genre;
	@FXML
	private TableColumn<Song, String> col_disc_info_song_reproductions;

	// REPRODUCTOR

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

		// set SONG TAB

		listened = FXCollections.observableArrayList();

		playButton.setText("►");

		lab_song_name.setText("");
		lab_song_artist.setText("");
		lab_song_disc.setText("");

		lab_song_name.setStyle("-fx-text-fill:white;");
		lab_song_artist.setStyle("-fx-text-fill:white;");
		lab_song_disc.setStyle("-fx-text-fill:white;");

		discs = Converter.disc_Converter(DiscDAO.getAll());
		discs.remove(0);
		songs = Converter.song_Converter(SongDAO.getAll());
		table_song.setItems(songs);
		table_disc.setItems(discs);

		// set LIST TAB

		repros = Converter.repro_Converter(ReproductionListDAO.getAll());
		userRepros = Converter.repro_Converter(ReproductionListDAO.getByUserSubscriptions(u));

		table_repros.setItems(repros);
		table_userRepros.setItems(userRepros);

		setTableAndDetailsInfo();

		if (repros.size() < 1) {
			table_repros.setVisible(false);
		}
		if (userRepros.size() < 1) {
			table_userRepros.setVisible(false);
		}

		for (int i = 0; i < 10; i++) {
			switch (i) {
			case 0: {
				lab_lt_1.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_1.setImage(img);
			}
				break;
			case 1: {
				lab_lt_2.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_2.setImage(img);
			}
				break;
			case 2: {
				lab_lt_3.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_3.setImage(img);
			}
				break;
			case 3: {
				lab_lt_4.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_4.setImage(img);
			}
				break;
			case 4: {
				lab_lt_5.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_5.setImage(img);
			}
				break;
			case 5: {
				lab_lt_6.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_6.setImage(img);
			}
				break;
			case 6: {
				lab_lt_7.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_7.setImage(img);
			}
				break;
			case 7: {
				lab_lt_8.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_8.setImage(img);
			}
				break;
			case 8: {
				lab_lt_9.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_9.setImage(img);
			}
				break;
			case 9: {
				lab_lt_10.setText(repros.get(i).getName());
				File f = new File(repros.get(i).getImage());
				Image img = new Image("file:" + f.getPath());
				img_lt_10.setImage(img);
			}
				break;
			}
			List<ReproductionList> toRemove = new ArrayList<ReproductionList>();
			for (ReproductionList r : repros) {
				if (r.getCreator().equals(u) && r.getType() != 0) {
					toRemove.add(r);
				}
			}
			repros.removeAll(toRemove);
		}
	}

	public void setSongInPlayer() {

		try {

			song = songsToReproduce.get(istr);

			File filestring = new File(song.getMedia());
			Media media = new Media(filestring.toURI().toString());
			mp = new MediaPlayer(media);
			mp.stop();

			if (!listened.contains(song)) {
				listened.add(song);
				SongDAO.upload_Views(song);
				if (song.getDisc() != null && song.getDisc().getId() != 1) {
					Disc aux = song.getDisc();
					DiscDAO.upload_Views(aux);
				}
			}

			if (table_song.getItems().contains(song)) {
				table_song.getSelectionModel().select(song);
			} else {
				table_song.getSelectionModel().select(null);
			}

			if (table_list_info_song.getItems().contains(song)) {
				table_list_info_song.getSelectionModel().select(song);
			} else {
				table_list_info_song.getSelectionModel().select(null);

			}
			if (table_disc_info_song.getItems().contains(song)) {
				table_disc_info_song.getSelectionModel().select(song);
			} else {
				table_disc_info_song.getSelectionModel().select(null);

			}

			show_Song_Info(song);

			songsToReproduceListened.remove(song);

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

			mp.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					if (songsToReproduce.size() > 0) {
						istr++;
						setSongInPlayer();
					}

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void setTableAndDetailsInfo() {
		// songs

		col_song_name.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getArtist().getName() + " - " + eachsong.getValue().getName());
			return v;
		});

		// discs

		if (discs != null && discs.size() > 0) {
			col_disc_name.setCellValueFactory(eachdisc -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachdisc.getValue().getMain_artist().getName() + " - " + eachdisc.getValue().getName());
				return v;
			});
		}

		// repros

		if (repros != null && repros.size() > 0) {
			col_repros_name.setCellValueFactory(eachrepro -> {
				SimpleStringProperty v = new SimpleStringProperty();
				if (eachrepro.getValue().getType() == 1) {
					v.setValue(eachrepro.getValue().getName() + " (by "
							+ eachrepro.getValue().getCreator().getUsername() + ")");
				} else {
					v.setValue(eachrepro.getValue().getName());
				}

				return v;
			});
		}

		// userRepros

		if (userRepros != null && userRepros.size() > 0) {
			col_userRepros_name.setCellValueFactory(eachUseRepro -> {
				SimpleStringProperty v = new SimpleStringProperty();
				if (eachUseRepro.getValue().getType() == 1) {
					v.setValue(eachUseRepro.getValue().getName() + " (by "
							+ eachUseRepro.getValue().getCreator().getUsername() + ")");
				} else {
					v.setValue(eachUseRepro.getValue().getName());
				}

				return v;
			});
		}

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

	private void setDetailsAndTableInfoList() {
		if (repro != null) {
			col_list_song_name.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getName());
				return v;
			});

			col_list_song_genre.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getGenre().getName());
				return v;
			});

			col_list_song_reproductions.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getReproductions() + "");
				return v;
			});
		}
	}

	private void setDetailsAndTableInfoCreation() {
		if (songs_creation_inside != null) {
			col_creation_inside_name.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getArtist().getName() + " - " + eachsong.getValue().getName());
				return v;
			});
		}

		if (songs_creation_outside != null) {
			col_creation_outside_name.setCellValueFactory(eachsong -> {
				SimpleStringProperty v = new SimpleStringProperty();
				v.setValue(eachsong.getValue().getArtist().getName() + " - " + eachsong.getValue().getName());
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
			stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					try {

					} catch (Exception e2) {
						// TODO: handle exception
					}
					Platform.exit();
					System.exit(0);
				}
			});

			stage2.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendSession() {
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

			show_Song_Info(song);

			try {
				mp.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}

			songsToReproduce = FXCollections.observableArrayList();

			List<Song> filter_1 = SongDAO.getByDisc(song.getDisc());
			List<Song> filter_2 = SongDAO.getByArtistId(song.getArtist());
			List<Song> filter_3 = SongDAO.getByGenre(song.getGenre());
			songsToReproduce = FXCollections.observableArrayList();

			List<Song> duplicated_filter = new ArrayList<Song>();
			duplicated_filter.addAll(filter_1);
			duplicated_filter.addAll(filter_2);
			duplicated_filter.addAll(filter_3);

			duplicated_filter = new ArrayList<>(new HashSet<>(duplicated_filter));

			if (filter_1.size() > 0) {
				songsToReproduce.addAll(filter_1);
			}
			if (filter_2.size() > 0) {
				songsToReproduce.addAll(filter_2);
			}
			if (filter_3.size() > 0) {
				songsToReproduce.addAll(filter_3);
			}

			songsToReproduce = Converter.song_Converter(duplicated_filter);
			if (songsToReproduce.contains(song)) {
				songsToReproduce.remove(song);
			}
			songsToReproduce.add(0, song);
			songsToReproduceListened = FXCollections.observableArrayList();
			songsToReproduceListened.addAll(songsToReproduce);

			istr = 0;
			setSongInPlayer();
		}
	}

	@FXML
	private void select_Song_From_List_Pane() {
		if (table_list_info_song.getSelectionModel().getSelectedItem() != null) {

			if (table_list_info_song.getSelectionModel().getSelectedItem().getId() != song.getId()) {
				song = table_list_info_song.getSelectionModel().getSelectedItem();
				select_Song(song);
			}

		}
	}

	@FXML
	private void select_Song(Song s) {
		if (s != null) {
			song = s;

			show_Song_Info(song);

			try {
				mp.stop();
				mp = null;
			} catch (Exception e) {
				// TODO: handle exception
			}

			songsToReproduce = FXCollections.observableArrayList();

			List<Song> filter_1 = SongDAO.getByDisc(song.getDisc());
			List<Song> filter_2 = SongDAO.getByArtistId(song.getArtist());
			List<Song> filter_3 = SongDAO.getByGenre(song.getGenre());
			songsToReproduce = FXCollections.observableArrayList();

			List<Song> duplicated_filter = new ArrayList<Song>();

			duplicated_filter.addAll(filter_1);
			duplicated_filter.addAll(filter_2);
			duplicated_filter.addAll(filter_3);

			duplicated_filter = new ArrayList<>(new HashSet<>(duplicated_filter));

			if (filter_1.size() > 0) {
				songsToReproduce.addAll(filter_1);
			}
			if (filter_2.size() > 0) {
				songsToReproduce.addAll(filter_2);
			}
			if (filter_3.size() > 0) {
				songsToReproduce.addAll(filter_3);
			}

			songsToReproduce = Converter.song_Converter(duplicated_filter);

			if (songsToReproduce.contains(song)) {
				songsToReproduce.remove(song);
			}
			songsToReproduce.add(0, song);
			songsToReproduceListened = FXCollections.observableArrayList();
			songsToReproduceListened.addAll(songsToReproduce);

			istr = 0;
			setSongInPlayer();
		}
	}

	private void show_Song_Info(Song s) {
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
	}

	@FXML
	private void select_List_of_All() {
		if (table_repros.getSelectionModel().getSelectedItem() != null) {

			table_list_info_song.setVisible(true);

			repro = table_repros.getSelectionModel().getSelectedItem();

			File f = new File(repro.getImage());
			Image image = new Image("file:" + f.getPath());
			img_list_info.setImage(image);

			if (repro.getType() == 0) {
				lab_listinfo_name.setText(repro.getName());

			} else {
				lab_listinfo_name.setText(repro.getName() + " (by " + repro.getCreator().getUsername() + ")");
			}

			ObservableList<Song> saux = Converter.song_Converter(repro.getSongs());
			table_list_info_song.setItems(saux);
			if (saux.size() < 1) {
				table_list_info_song.setVisible(false);
			} else {
				setDetailsAndTableInfoList();
			}

			if (table_list_info_song.getItems().contains(song)) {
				table_list_info_song.getSelectionModel().select(song);
			} else {
				table_list_info_song.getSelectionModel().select(null);
			}

			if (!repro.getCreator().equals(u)) {
				if (!repro.getSubscribed_users().contains(u)) {
					btn_subs.setVisible(true);
					btn_unsubs.setVisible(false);
				} else {
					btn_subs.setVisible(false);
					btn_unsubs.setVisible(true);
				}
			} else {
				btn_unsubs.setVisible(false);
				btn_subs.setVisible(false);
			}

			open_List_Pane();
		}
	}

	@FXML
	private void select_List_of_User() {

		if (table_userRepros.getSelectionModel().getSelectedItem() != null) {

			table_list_info_song.setVisible(true);

			repro = table_userRepros.getSelectionModel().getSelectedItem();

			File f = new File(repro.getImage());
			Image image = new Image("file:" + f.getPath());
			img_list_info.setImage(image);

			if (repro.getType() == 0) {
				lab_listinfo_name.setText(repro.getName());

			} else {
				lab_listinfo_name.setText(repro.getName() + " (by " + repro.getCreator().getUsername() + ")");
			}

			ObservableList<Song> saux = Converter.song_Converter(repro.getSongs());
			table_list_info_song.setItems(saux);
			if (saux.size() < 1) {
				table_list_info_song.setVisible(false);
			} else {
				setDetailsAndTableInfoList();
			}

			if (table_list_info_song.getItems().contains(song)) {
				table_list_info_song.getSelectionModel().select(song);
			} else {
				table_list_info_song.getSelectionModel().select(null);
			}

			if (!repro.getCreator().equals(u)) {
				if (!repro.getSubscribed_users().contains(u)) {
					btn_subs.setVisible(true);
					btn_unsubs.setVisible(false);
				} else {
					btn_subs.setVisible(false);
					btn_unsubs.setVisible(true);
				}
			} else {
				btn_unsubs.setVisible(false);
				btn_subs.setVisible(false);
			}

			open_List_Pane();
		}
	}

	@FXML
	private void filter_Songs_Discs_ByName() {
		if (!txt_filter.getText().matches("")) {
			ObservableList<Song> filter = FXCollections.observableArrayList();
			ObservableList<Song> filter2 = FXCollections.observableArrayList();
			ObservableList<Song> filter3 = FXCollections.observableArrayList();

			ObservableList<Disc> filter4 = FXCollections.observableArrayList();
			ObservableList<Disc> filter5 = FXCollections.observableArrayList();

			filter = Converter.song_Converter(SongDAO.getByName(txt_filter.getText()));
			filter2 = Converter.song_Converter(SongDAO.getByArtistName(txt_filter.getText()));
			filter3 = Converter.song_Converter(SongDAO.getByDisc(txt_filter.getText()));
			filter.addAll(filter2);
			filter.addAll(filter3);

			filter4 = Converter.disc_Converter(DiscDAO.getByName(txt_filter.getText()));
			filter5 = Converter.disc_Converter(DiscDAO.getByArtistName(txt_filter.getText()));
			filter4.addAll(filter5);

			table_song.setItems(filter);

			table_disc.setItems(filter4);

			if (filter.size() < 1) {
				table_song.setVisible(false);
			} else {
				table_song.setVisible(true);
			}

			if (filter4.size() < 1) {
				table_disc.setVisible(false);
			} else {
				table_disc.setVisible(true);
			}

			setTableAndDetailsInfo();
		} else {
			table_song.setItems(songs);
			table_disc.setItems(discs);
			table_song.setVisible(true);
			table_disc.setVisible(true);
			setTableAndDetailsInfo();
		}
	}

	@FXML
	private void open_Artist_Pane() {
		if (song != null) {

			table_artist_info_disc.setVisible(true);

			File f2 = new File(song.getArtist().getPhoto());
			Image artist_song = new Image("file:" + f2.getPath());
			img_artist_info.setImage(artist_song);
			lab_artistinfo_name.setText((song.getArtist().getName()));
			lab_artistinfo_description.setText(song.getArtist().getDescription());
			ObservableList<Disc> disc_by_artist = Converter.disc_Converter(DiscDAO.getByArtist(song.getArtist()));
			table_artist_info_disc.setItems(disc_by_artist);
			setDetailsAndTableInfoArtist();
			table_artist_info_disc.getSelectionModel().select(null);

			if (disc_by_artist.size() < 1) {
				table_artist_info_disc.setVisible(false);
			}

			tab_artist_info.getSelectionModel().select(0);
			black_pane.setVisible(true);
			artist_pane.setVisible(true);
		}

	}

	@FXML
	private void open_Artist_Pane(Artist a) { // from discs

		close_Disc_Pane();

		table_artist_info_disc.setVisible(true);

		File f2 = new File(a.getPhoto());
		Image artist_song = new Image("file:" + f2.getPath());
		img_artist_info.setImage(artist_song);
		lab_artistinfo_name.setText((a.getName()));
		lab_artistinfo_description.setText(a.getDescription());
		ObservableList<Disc> disc_by_artist = Converter.disc_Converter(DiscDAO.getByArtist(a));
		table_artist_info_disc.setItems(disc_by_artist);
		setDetailsAndTableInfoArtist();
		table_artist_info_disc.getSelectionModel().select(null);

		if (disc_by_artist.size() < 1) {
			table_artist_info_disc.setVisible(false);
		}

		tab_artist_info.getSelectionModel().select(0);
		black_pane.setVisible(true);
		artist_pane.setVisible(true);
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

			discInPane = song.getDisc();

			File f2 = new File(song.getDisc().getPhoto());
			Image disc_song = new Image("file:" + f2.getPath());
			img_disc_info.setImage(disc_song);
			lab_discinfo_name.setText((song.getDisc().getName()));
			lab_discinfo_artist.setText((song.getDisc().getMain_artist().getName()));
			ObservableList<Song> songs_by_disc = Converter.song_Converter(SongDAO.getByDisc(song.getDisc()));
			table_disc_info_song.setItems(songs_by_disc);
			setDetailsAndTableInfoDisc();
			table_disc_info_song.getSelectionModel().select(null);

			if (table_disc_info_song.getItems().contains(song)) {
				table_disc_info_song.getSelectionModel().select(song);
			} else {
				table_list_info_song.getSelectionModel().select(null);
			}

			tab_disc_info.getSelectionModel().select(0);
			black_pane.setVisible(true);
			disc_pane.setVisible(true);
		}
	}

	@FXML
	private void open_Disc_Pane(Disc d) {

		discInPane = d;

		disc = d;

		File f2 = new File(d.getPhoto());
		Image disc_song = new Image("file:" + f2.getPath());
		img_disc_info.setImage(disc_song);
		lab_discinfo_name.setText(d.getName());
		lab_discinfo_artist.setText(d.getMain_artist().getName());
		ObservableList<Song> songs_by_disc = Converter.song_Converter(d.getSongs());
		table_disc_info_song.setItems(songs_by_disc);
		setDetailsAndTableInfoDisc();

		if (table_disc_info_song.getItems().contains(song)) {
			table_disc_info_song.getSelectionModel().select(song);
		} else {
			table_list_info_song.getSelectionModel().select(null);
		}

		tab_disc_info.getSelectionModel().select(0);
		black_pane.setVisible(true);
		disc_pane.setVisible(true);

	}

	@FXML
	private void close_Disc_Pane() {
		table_disc.getSelectionModel().select(null);
		disc_pane.setVisible(false);
		lab_song_artist.setTextFill(Color.WHITE);
		lab_song_disc.setTextFill(Color.WHITE);
		black_pane.setVisible(false);
	}

	private void open_List_Pane() {

		if (repro != null) {

			black_pane2.setVisible(true);
			list_pane.setVisible(true);
		}

	}

	@FXML
	private void close_List_Pane() {

		table_repros.getSelectionModel().select(null);
		table_userRepros.getSelectionModel().select(null);
		list_pane.setVisible(false);
		black_pane2.setVisible(false);
	}

	@FXML
	private void goToDisc() {
		close_Artist_Pane();
		Disc aux = table_artist_info_disc.getSelectionModel().getSelectedItem();
		if (aux != null) {
			open_Disc_Pane(aux);
		}

	}

	@FXML
	private void goToDiscFromDiscs() {
		Disc aux = table_disc.getSelectionModel().getSelectedItem();
		if (aux != null) {
			open_Disc_Pane(aux);
		}
	}

	@FXML
	private void goToArtistFromDiscs() {
		open_Artist_Pane(disc.getMain_artist());

	}

	@FXML
	private void goToArtistFromDiscInPane() {
		open_Artist_Pane(discInPane.getMain_artist());
	}

	@FXML
	private void goToPlayFromDisc() {
		Song aux = table_disc_info_song.getSelectionModel().getSelectedItem();
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
	private void open_Cration_Pane() {

		txt_creation_name.setText("");
		songs_creation_inside = FXCollections.observableArrayList();
		songs_creation_outside = Converter.song_Converter(SongDAO.getAll());

		table_creation_inside.setItems(songs_creation_inside);
		table_creation_outside.setItems(songs_creation_outside);

		setDetailsAndTableInfoCreation();

		str_image = "";

		File f = new File("src/main/resources/images/default/default.jpg");
		Image img = new Image("file:" + f.getPath());
		img_creation.setImage(img);

		che_public.setSelected(false);

		black_pane2.setVisible(true);

		table_creation_inside.setVisible(false);
		if (songs_creation_outside.size() < 1) {
			table_creation_outside.setVisible(false);
		} else {
			table_creation_outside.setVisible(true);
		}

		creation_pane.setVisible(true);
	}

	@FXML
	private void close_Creation_Pane() {
		black_pane2.setVisible(false);
		creation_pane.setVisible(false);
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
	private void changeColor_Disc_Info_Artist() {
		lab_discinfo_artist.setTextFill(Color.CORNFLOWERBLUE);
	}

	@FXML
	private void changeColorDefault_Disc_Info_Artist() {
		lab_discinfo_artist.setTextFill(Color.WHITE);
	}

	@FXML
	private void select_List_1() {
		repro = repros.get(0);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_2() {
		repro = repros.get(1);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_3() {
		repro = repros.get(2);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_4() {
		repro = repros.get(3);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_5() {
		repro = repros.get(4);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_6() {
		repro = repros.get(5);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_7() {
		repro = repros.get(6);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_8() {
		repro = repros.get(7);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_9() {
		repro = repros.get(8);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void select_List_10() {
		repro = repros.get(9);
		table_repros.getSelectionModel().select(repro);
		select_List_of_All();
	}

	@FXML
	private void goNext() {

		if (songsToReproduceListened.size() < 1) {
			mp.stop();
			mp = null;
			songsToReproduce = Converter.song_Converter(SongDAO.getRandomly());
			songsToReproduceListened.addAll(songsToReproduce);
			istr = 0;
			setSongInPlayer();
		}

		else {

			istr++;
			if (istr >= songsToReproduce.size()) {
				istr = 0;
			}
			mp.stop();
			mp = null;
			setSongInPlayer();
		}
	}

	@FXML
	private void goPrev() {

		if (songsToReproduceListened.size() < 1) {
			mp.stop();
			mp = null;
			songsToReproduce = Converter.song_Converter(SongDAO.getRandomly());
			songsToReproduceListened.addAll(songsToReproduce);
			istr = 0;
			setSongInPlayer();
		}

		else {

			istr--;
			if (istr < 0) {
				istr = songsToReproduce.size() - 1;
			}
			mp.stop();
			mp = null;
			setSongInPlayer();
		}
	}

	@FXML
	private void subs() {
		btn_subs.setVisible(false);

		repro.getSubscribed_users().add(u);
		table_userRepros.getItems().add(repro);
		ReproductionListDAO.subscribe(repro, u);
		btn_unsubs.setVisible(true);

		if (table_userRepros.getItems().size() > 0) {
			setTableAndDetailsInfo();
			table_userRepros.setVisible(true);

		}
	}

	@FXML
	private void unsubs() {
		btn_unsubs.setVisible(false);

		repro.getSubscribed_users().remove(u);
		table_userRepros.getItems().remove(repro);
		ReproductionListDAO.unsubscribe(repro, u);
		btn_subs.setVisible(true);

		if (table_userRepros.getItems().size() < 1) {
			setTableAndDetailsInfo();
			table_userRepros.setVisible(false);
		}
	}

	@FXML
	private void create_List() throws IOException {

		List<User> subscribed = new ArrayList<User>();
		subscribed.add(u);
		boolean create = false;

		if (!txt_creation_name.getText().matches("")) {
			if (str_image.matches("")) {
				// alerta default

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText(" ¿Desea generar y guardar la lista con una imagen por defecto?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					create = true;
				}

			} else {
				create = true;
			}
		}

		if (create) {

			int type = 1;
			int id = -1;
			if (che_public.isSelected()) {
				type = 2;
			}
			// guardar y sacar url real

			ReproductionList r = new ReproductionList(txt_creation_name.getText(), songs_creation_inside, u, subscribed,
					type, "");

			r.setId(ReproductionListDAO.insert(r));

			if (str_image.matches("")) {
				str_image = "src/main/resources/images/default/default.jpg";
				r.setImage(str_image);
			} else {
				r.setImage("src/main/resources/images/repro/r" + r.getId() + txt_creation_name.getText() + ".jpg");
				FileUtilities.saveFile(str_image, r.getImage());
			}
			ReproductionListDAO.save(r);
			userRepros.add(r);
			
			ReproductionListDAO.subscribe(r, u);
			
			setTableAndDetailsInfo();
			
			//guardar relacion

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Información");
			alert.setContentText(" Se ha creado su Lista de Reproducción correctamente.");
			alert.show();

			close_Creation_Pane();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Información");
			alert.setContentText(" El campo nombre es obligatorio.");
			alert.show();
		}
	}

	@FXML
	public void createToOut() {
		if(table_creation_inside.getSelectionModel().getSelectedItem()!=null) {
			Song aux = table_creation_inside.getSelectionModel().getSelectedItem();
			table_creation_inside.getItems().remove(aux);
			if (!table_creation_outside.getItems().contains(aux)) {
				table_creation_outside.getItems().add(aux);
			}

			
			if(table_creation_inside.getItems().size()>0) {
				table_creation_inside.setVisible(true);
			}
			else {
				table_creation_inside.setVisible(false);
			}
			if(table_creation_outside.getItems().size()>0) {
				table_creation_outside.setVisible(true);
			}
			else {
				table_creation_outside.setVisible(false);
			}
		}
		
	}

	@FXML
	private void createToIn() {
		if (table_creation_outside.getSelectionModel().getSelectedItem() != null) {
			Song aux = table_creation_outside.getSelectionModel().getSelectedItem();
			table_creation_outside.getItems().remove(aux);
			if (!table_creation_inside.getItems().contains(aux)) {
				table_creation_inside.getItems().add(aux);
			}


			if(table_creation_inside.getItems().size()>0) {
				table_creation_inside.setVisible(true);
			}
			else {
				table_creation_inside.setVisible(false);
			}
			if(table_creation_outside.getItems().size()>0) {
				table_creation_outside.setVisible(true);
			}
			else {
				table_creation_outside.setVisible(false);
			}
		}
	}
	
	@FXML
	private void select_Image_Creator() {
		File file = null;
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file = filechooser.showOpenDialog(null);
			if (file != null && file.getPath().matches(".+\\.png") || file.getPath().matches(".+\\.jpg")) {
				str_image=file.getPath();
				
				Image i= new Image("file:"+file.getPath());
				img_creation.setImage(i);
			} else { // extension incorrecta
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception;
			str_image="src/main/resources/images/default/default.jpg";
			File f=new File(str_image);
			Image i= new Image("file:"+f.getPath());
			img_creation.setImage(i);
		}
	}
	
	private void update_Inside_Outside_Info(){
		if(table_creation_inside.getColumns().size()>0) {
			table_creation_inside.getColumns().get(0).setVisible(false);
			table_creation_inside.getColumns().get(0).setVisible(true);
		}
		
		
		if(table_creation_outside.getColumns()	.size()>0) {
			table_creation_outside.getColumns().get(0).setVisible(false);
			table_creation_outside.getColumns().get(0).setVisible(true);
		}
	}

	
}
