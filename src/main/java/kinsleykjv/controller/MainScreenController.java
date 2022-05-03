package kinsleykjv.controller;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import kinsleykjv.animatefx.animation.FadeIn;
import kinsleykjv.animatefx.animation.FadeInDown;
import kinsleykjv.animatefx.animation.FadeOut;
import kinsleykjv.animatefx.animation.FadeOutDown;
import kinsleykjv.database.DBManager;
import kinsleykjv.utils.ListViewCell;
import kinsleykjv.utils.SystemRoot;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static kinsleykjv.utils.IOInfo.listFilesInExplorerDirectory;

public class MainScreenController implements Initializable {

    @FXML
    public AnchorPane rootParent;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public MediaView mediaView;
    @FXML
    public Circle circleClose;
    @FXML
    public Circle circleMinimize;
    @FXML
    public VBox controlBox;

    @FXML
    public JFXSlider slider;
    @FXML
    public JFXSlider volumeSeek;
    @FXML
    public VBox vBox;
    @FXML
    public JFXListView listview;
    @FXML
    public Label searchLabel;
    @FXML
    public Label txtHome;
    @FXML
    public TreeView<String> treeView;
    /**
     * The search field.
     */
    private final TextField searchField = TextFields.createClearableTextField();
    private final SystemRoot systemRoot = new SystemRoot();
    @FXML
    public ImageView ImgSpeaker;
    @FXML
    public ImageView ImgPrev;
    @FXML
    public ImageView imgBack;


    @FXML
    public Pane navPane;

    @FXML
    public Pane filesPane;
    @FXML
    public HBox controlls;


    @FXML
    public ImageView ImgPausePlay;
    @FXML
    public ImageView ImgPrevNext;
    private FileChooser fc;
    private MediaPlayer mp;
    private Media me;
    private boolean isPlaying = false;

   private  IntegerProperty currentTime = new SimpleIntegerProperty();
   private IntegerProperty currentPlayPosition = new SimpleIntegerProperty();
    private Stage stage;
    private static final int AUTO_HIDE_DEALY = 5;
    private boolean showsStatus = true;
    private TranslateTransition showStatusVolumeControls;
    private TranslateTransition showStatusMainControls;
    private TranslateTransition hideStatusVolumeControls;
    private TranslateTransition hideStatusMainControls;

    private DBManager dbManager = new DBManager();

    private void showVolumeControls() {
        hideStatusVolumeControls.stop();
        showStatusVolumeControls.play();
    }

    private void hideVolumeControls() {
        showStatusVolumeControls.stop();
        hideStatusVolumeControls.play();
    }

    private void showMainControls() {
        hideStatusMainControls.stop();
        showStatusMainControls.play();
    }

    private void hideMainControls() {
        showStatusMainControls.stop();
        hideStatusMainControls.play();
    }

   /* private void autoHide() {
        Duration duration = Duration.seconds(AUTO_HIDE_DEALY);
        PauseTransition transition = new PauseTransition(duration);
        transition.setOnFinished(evt -> {
            if (showsStatus) {
                hide();
            }
        });
        transition.play();
    }*/

    @FXML
    public void nexxtPlayEvent(Event event) {
        System.out.println("next ");
        var vall  = currentPlayPosition.get()+1;
        vall = vall > listview.getItems().size()-1 ? 0 : vall;
        currentPlayPosition.setValue(vall);

    }
    @FXML
    public void prevPlayEvent(Event event) {
        System.out.println("next ");
        var vall  = currentPlayPosition.get()-1;
        vall = Math.max(vall, 0);
        currentPlayPosition.setValue(vall);

    }
    @FXML
    public void playPauseEvent(Event event) {
        
        System.out.println("clicked 22");
        if (isPlaying) {
            System.out.println("clicked 444");

            Image pauseIcon = new Image(getClass().getResourceAsStream("/images/defaul/icons/icons8_play_button_circled_30px_2.png"));
            ImgPausePlay.setImage(pauseIcon);
            mp.pause();
            isPlaying = false;
        } else {
            if (mp == null) {
                System.out.println("clicked 666");
                retrunToHomeScreen();
            } else {
                System.out.println("clicked 888");
                mp.play();
            }

        }
        // compare two arraylist in java and find difference
        //  var diff = listview.getItems().stream().filter(x -> !x.equals(listview.getSelectionModel().getSelectedItem())).collect(Collectors.toList());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbManager.connect();
        treeView.setRoot(systemRoot.getRoot());

        showStatusVolumeControls = new TranslateTransition(Duration.millis(250), controlBox);
        showStatusVolumeControls.setByX(1000.0);
        showStatusVolumeControls.setOnFinished(event -> {
        });
        hideStatusVolumeControls = new TranslateTransition(Duration.millis(250), controlBox);
        hideStatusVolumeControls.setByX(-1000.0);
        hideStatusVolumeControls.setOnFinished(event -> showsStatus = false);


        showStatusMainControls = new TranslateTransition(Duration.millis(250), controlls);
        showStatusMainControls.setByY(1000.0);
        showStatusMainControls.setOnFinished(event -> {
        });
        hideStatusMainControls = new TranslateTransition(Duration.millis(250), controlls);
        hideStatusMainControls.setByY(-1000.0);
        hideStatusMainControls.setOnFinished(event -> showsStatus = false);

        currentPlayPosition.addListener((observable, oldValue, newValue) -> {
            System.out.println("currentPlayPosition: " + currentPlayPosition.get());
            System.out.println("currentPlayPosition: " + oldValue.intValue());
            System.out.println("currentPlayPosition: " + newValue.intValue());
           var file =  VideoFolderFiles.get(newValue.intValue()).getAbsolutePath();
            System.out.println("currentPlayPosition::file:: " + file);
            playChosenFile(file);
        } );


     /*   ImgPrevNext.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {



        });*/
        rootParent.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            System.out.println("drag detected");
           /* hideVolumeControls();
            hideMainControls();*/
            if (isPlaying) {
                new FadeIn(controlBox).play();
                new FadeIn(controlls).play();
            }

        });
        rootParent.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            System.out.println("drag MOUSE_EXITED");

           /* showVolumeControls();
            showMainControls();*/
            if (isPlaying) {
                new FadeOut(controlBox).play();
                new FadeOut(controlls).play();
            }

        });
        /*showVolumeControls();
        showMainControls();*/
        new FadeIn(controlBox).play();
        new FadeIn(controlls).play();

        circleClose.setOnMouseClicked(event -> {
            Platform.exit();

        });
        circleMinimize.setOnMouseClicked(event -> {
            stage = (Stage) rootParent.getScene().getWindow();

            System.out.println("Minimizing");
            if (stage.isMaximized()) {
                stage.setMaximized(false);
            } else {
                stage.setMaximized(true);
            }

        });


        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("(*.mp4)", "*.mp4"), new FileChooser.ExtensionFilter("(*.mkv)", "*.mkv"), new FileChooser.ExtensionFilter("(*.mp3)", "*.mp3"));
        Image imProfile = new Image(getClass().getResourceAsStream("/images/defaul/icons/icons8_play_button_circled_30px_2.png"));

        ImgPausePlay.setImage(imProfile);


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<String> selectedItem = newValue;
            String folder = String.valueOf(selectedItem.getValue());
            System.out.println("Selected Text : " + folder);

            txtHome.setText("Loading files");
            ObservableList<File> result = FXCollections.observableArrayList();

            var cache = dbManager.getAllOfParentFolder(folder);
            if (cache.size() > 0) {
                result.clear();
                cache.forEach(file -> {
                    System.out.println("Cache file : " + file.getSongPath());
                    result.add(new File(file.getSongPath()));
                });
                VideoFolderFiles = result;
                renderListView();
            }

            Task<ObservableList<File>> taskLoadVideos = new Task<>() {
                @Override
                protected ObservableList<File> call() {
                    var fetch = listFilesInExplorerDirectory(folder);
                    // compare two arraylist in java and find difference
                    var diff = VideoFolderFiles.stream().filter(f -> !fetch.contains(f)).collect(Collectors.toList());
                    System.out.println("Difference : "+diff.size());
                    if(diff.size()>0){

                        diff.forEach(f->{ System.out.println("Diff file : "+f.getAbsolutePath());});

                    result.clear();
                    result.addAll(fetch);

                    result.forEach(file -> {
                        dbManager.addSong(new DBManager.AllSongs(file.getName(), file.getPath(), folder, 0));
                    });
                    return result;
                    }
                    return null;
                }
            };
            new FadeIn(progressBar).play();
            // ProgressBar pBar = new ProgressBar();
            progressBar.progressProperty().bind(taskLoadVideos.progressProperty());
            // Label statusLabel = new Label();
            //  statusLabel.textProperty().bind(taskLoadVideos.messageProperty());
            //  VBox root = new VBox(5, statusLabel, progressBar);
            //  Scene scene = new Scene(root);
            // Stage loadingStage =  new Stage();
            //  loadingStage.setScene(scene);
            //  loadingStage.show();
            taskLoadVideos.setOnSucceeded(event -> {
                if(taskLoadVideos.getValue()!=null) {
                    VideoFolderFiles = taskLoadVideos.getValue();
                    renderListView();
                    //    loadingStage.hide();
                    new FadeOut(progressBar).play();
                    txtHome.setText(folder);
                }
            });
            taskLoadVideos.setOnFailed(event -> {
                //    loadingStage.hide();
                new FadeOut(progressBar).play();
                System.err.println("You clicked on -" + taskLoadVideos.getException());
                txtHome.setText("Home: Failed to load Files");
            });
            new Thread(taskLoadVideos).start();



        });
        imgBack.setOnMouseClicked(event -> {
            retrunToHomeScreen();

        });
        new FadeOut(imgBack).play();
        new FadeOut(controlls).play();
        new FadeOut(controlBox).play();
        new FadeOut(progressBar).play();

        filesPane.setOnMouseClicked(event -> {
            System.out.println("You clicked on -" + event.getSource());
        });
    }

    void renderListView() {
        listview.setItems(VideoFolderFiles);
        listview.setCellFactory((Callback<JFXListView<File>, JFXListCell<File>>) param -> {
            var cell = new ListViewCell();
            cell.setOnMouseClicked(event1 -> {
                if (cell != null) {
                    currentPlayPosition.setValue(listview.getSelectionModel().getSelectedIndex());
                   // playChosenFile(cell.getItem().getAbsolutePath());
                    System.out.println("You clicked on -" + cell.getItem().getAbsolutePath());
                    System.out.println("You clicked on -" + cell.getItem().getName());
                    event1.consume();
                }
            });
            return cell;
        });
    }

    private ObservableList<File> VideoFolderFiles = FXCollections.observableArrayList();
    private ObservableList<String> folderFiles = FXCollections.observableArrayList();

    void retrunToHomeScreen() {
        new FadeIn(navPane).play();
        new FadeIn(vBox).play();
        new FadeIn(filesPane).play();
        new FadeOutDown(imgBack).play();
        new FadeOut(controlls).play();
        new FadeOut(controlBox).play();
        Image imProfile = new Image(getClass().getResourceAsStream("/images/defaul/icons/icons8_play_button_circled_30px_2.png"));
        if (mp != null)
            mp.stop();
        isPlaying = false;
        ImgPausePlay.setImage(imProfile);
    }


    void playChosenFile(final String filePath) {
        if (filePath == null) {
            return;
        }
        // new FadeInLeft(navPane).play();
        new FadeOut(navPane).play();
        new FadeOut(vBox).play();

        new FadeOut(filesPane).play();
        new FadeInDown(imgBack).play();
        new FadeInDown(controlls).play();
        new FadeInDown(controlBox).play();

        Image pauseIcon = new Image(getClass().getResourceAsStream("/images/defaul/icons/icons8_pause_24px_1.png"));
        me = new Media(new File(filePath).toURI().toString());
        mp = new MediaPlayer(me);
        mp.setVolume(0.25);
        volumeSeek.setValue(mp.getVolume() * 100);

        mediaView.setMediaPlayer(mp);

        mp.volumeProperty().bindBidirectional(volumeSeek.valueProperty());
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

        final HBox hbox = new HBox(2);
        final int bands = mp.getAudioSpectrumNumBands();
        final Rectangle[] rect = new Rectangle[bands];
        for (int i = 0; i < rect.length; i++) {
            rect[i] = new Rectangle();
            rect[i].setFill(Color.CHARTREUSE);
            hbox.getChildren().add(rect[i]);
        }

        mp.play();
        isPlaying = true;
        ImgPausePlay.setImage(pauseIcon);

        mp.setOnReady(() -> {
            /*
             * int w = mp.getMedia().getWidth(); int bandWidth = w /
             * rect.length;
             *
             * spec.setMinWidth(w); for (Rectangle r : rect) {
             * r.setWidth(bandWidth); r.setHeight(2); }
             */

            slider.setMin(0.0);
            slider.setMax(mp.getTotalDuration().toSeconds());
        });
        mp.setOnError(() -> { // if error while playing the file then show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while playing the file");
            alert.setContentText("Please check the file path");
            alert.showAndWait();

                });
        mp.setOnEndOfMedia(() -> {
            new FadeOut(controlBox).play();
            new FadeOut(controlls).play();
            isPlaying = false;
            if(VideoFolderFiles.size()>0) {
                ImgPrevNext.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, true, true, true, true, true, true,
                        true, true, true, true, null));
            }
        });
        // volume seek increase volume of media player
        volumeSeek.valueProperty().addListener((observable, oldValue, newValue) -> mp.setVolume(newValue.doubleValue() / 100));

                slider.setOnMouseClicked(event1 -> mp.seek(Duration.seconds(slider.getValue())));
        mp.currentTimeProperty().addListener((observable, duration, current) -> slider.setValue(current.toSeconds()));

    }
    // resources/images/defaul/icons/icons8_play_button_circled_30px_2.png


}
