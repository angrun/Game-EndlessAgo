

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.FileNotFoundException;


public class MenuScene {

    //Pane
    @FXML
    StackPane rootPane;

    //Images
    @FXML
    ImageView ago;
    @FXML
    ImageView background1;
    @FXML
    ImageView background2;
    @FXML
    ImageView Heading;
    @FXML
    ImageView startButton;
    @FXML
    ImageView infoButton;
    @FXML
    ImageView soundOff;
    @FXML
    ImageView shopButtonMainMenu;
    @FXML
    ImageView rulesButton;

    private Main main = new Main();

    //Ago animation
    private final static Image JUMP_1 = new Image("/images/agoShop/classic/1Nomove.png");
    private final static Image JUMP_2 = new Image("/images/agoShop/classic/1Jump.png");
    private final static Image JUMP_3 = new Image("/images/agoShop/classic/2Jump.png");

    //Music
    private boolean scaleIsOne;
    private MediaPlayer musicPlayer;
    private boolean canPlayMusic = true;

    //Timeline
    private Timeline timeline;

    {
        Media mp3MusicFile = new Media(getClass().getResource("music/backgroundMusic.mp3").toExternalForm());

        musicPlayer = new MediaPlayer(mp3MusicFile);
        musicPlayer.setAutoPlay(true);
        musicPlayer.setVolume(0.9);


        musicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                musicPlayer.seek(Duration.ZERO);
            }
        });

    }

    public MenuScene() throws FileNotFoundException {
    }


    @FXML
    public void initialize() {

        ParallelTransition parallelTransition = main.backgroundTransition(background1, background2, 10000);
        parallelTransition.play();
        main.makeFadeInTransition(rootPane);
        rootPane.setOpacity(0);
        startRun();
    }


    public void musicOff() {

        if (canPlayMusic) {
            soundOff.setImage(new Image("images/icons/sound/soundOff.png"));
            musicPlayer.pause();
            canPlayMusic = false;

        } else {
            soundOff.setImage(new Image("images/icons/sound/soundOn.png"));
            musicPlayer.play();
            canPlayMusic = true;
        }

    }

    private void startRun() {
        createTimelineForMenuJump().play();
    }

    private Timeline createTimelineForMenuJump() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(300),
                        e -> ago.setImage(JUMP_1)
                )
        );
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(450),
                        e -> ago.setImage(JUMP_2)
                )
        );
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(550),
                        e -> {
                            ago.setImage(JUMP_3);
                            if (scaleIsOne) {
                                ago.setScaleX(-1);
                                scaleIsOne = false;
                            } else {
                                ago.setScaleX(1);
                                scaleIsOne = true;
                            }
                        }
                )
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setOnFinished(event -> {
            this.timeline = createTimelineForMenuJump();
            this.timeline.play();
        });
        return timeline;
    }


    public void infoButtonPressed() {
        main.makeFadeout(rootPane, "Info.fxml");
    }

    public void rulesButtonPressed() {
        main.makeFadeout(rootPane, "Rules.fxml");
    }

    public void startButtonPressed() {
        main.makeFadeout(rootPane, "Selection.fxml");
    }

    public void shopButtonPressed() {
        main.makeFadeout(rootPane, "Shop.fxml");
    }


}
