import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameScene {

    //panes
    @FXML
    StackPane rootPane;
    @FXML
    Pane pane;
    @FXML
    Pane infoButtonPane;
    @FXML
    Pane wantToContinuePane;
    @FXML
    Pane rulesPane;
    @FXML
    Pane pauseButtonPane;

    //images
    @FXML
    ImageView background1;
    @FXML
    ImageView background2;
    @FXML
    ImageView life1;
    @FXML
    ImageView life2;
    @FXML
    ImageView life3;
    @FXML
    ImageView infoButton;
    @FXML
    ImageView pauseButton;
    @FXML
    ImageView coins;
    @FXML
    ImageView runningAgo1;
    @FXML
    ImageView timeOut;
    @FXML
    ImageView rules;
    @FXML
    ImageView quit;
    @FXML
    ImageView returnButton1;
    @FXML
    ImageView returnButton2;
    @FXML
    ImageView wantToContinueImage;
    @FXML
    ImageView returnButtonFromPause;

    //Labels
    @FXML
    Label distance;
    @FXML
    Label coinsAmount;
    @FXML
    Label timer;

    //Button
    @FXML
    Button buyLifeButton;


    private Main main = new Main();
    private String fileAgoClassic = main.getAgoSprite();
    private Life livesObj = new Life(3);

    //Ago
    private Image RUN_1 = new Image(main.readFromFile(fileAgoClassic, 1));
    private Image RUN_2 = new Image(main.readFromFile(fileAgoClassic, 2));
    private Image RUN_3 = new Image(main.readFromFile(fileAgoClassic, 3));
    private Image RUN_4 = new Image(main.readFromFile(fileAgoClassic, 4));
    private Image RUN_5 = new Image(main.readFromFile(fileAgoClassic, 5));
    private Image RUN_6 = new Image(main.readFromFile(fileAgoClassic, 6));

    //Birds obstacles
    private final static Image BIRD_FLY_1 = new Image("/images/obstacles/birdFlying1.png");
    private final static Image BIRD_FLY_2 = new Image("/images/obstacles/birdFlying2.png");
    private final static Image MONSTER_FLY_1 = new Image("/images/obstacles/monsterFly1.png");
    private final static Image MONSTER_FLY_2 = new Image("/images/obstacles/monsterFly2.png");

    //Ago animation
    private Image NO_MOVE = new Image(main.readFromFile(fileAgoClassic, 7));
    private Image JUMP_UP = new Image(main.readFromFile(fileAgoClassic, 8));
    private Image JUMP_DOWN = new Image(main.readFromFile(fileAgoClassic, 9));

    //Get ready countdown
    private final static Image TIMEOUT_1 = new Image("/images/titles/getready/one.png");
    private final static Image TIMEOUT_2 = new Image("/images/titles/getready/two.png");
    private final static Image TIMEOUT_3 = new Image("/images/titles/getready/three.png");
    private final static Image TIMEOUT_4 = new Image("/images/titles/getready/go.png");

    //Life
    private final static Image LIFE = new Image("/images/icons/life.png");
    private List<ImageView> listOfLives = new ArrayList();

    //Timelines
    private ParallelTransition parallelTransition;
    private Timeline runTimeLine;
    private Timeline distanceTimeline;
    private Timeline objectAddingTimeline;
    private Timeline countDownForPlayAgainTimeline;
    private List<Timeline> timelineList = new ArrayList<>();

    //Blur
    private GaussianBlur blur;
    private ColorAdjust adj = new ColorAdjust(0, 0, 0, 0);

    //Ago Cords
    private static double AGO_Y_LAYOUT;
    private double agoX;
    private double agoY;

    private int runDistance = 0;

    //FILES
    private String coinsAmountText = main.getCoinsNowTotalAndDistance();
    private String backgroundsText = main.getBackgrounds();


    public GameScene() throws FileNotFoundException {
    }


    @FXML
    public void initialize() throws FileNotFoundException {

        main.makeFadeInTransition(rootPane);

        background1.setImage(new Image(main.readFromFile(backgroundsText, 1)));
        background2.setImage(new Image(main.readFromFile(backgroundsText, 1)));
        runningAgo1.setImage(new Image(main.readFromFile(fileAgoClassic, 7)));
        AGO_Y_LAYOUT = runningAgo1.getLayoutY();
        agoX = runningAgo1.getLayoutX();
        agoY = runningAgo1.getLayoutY();

        listOfLives.addAll(List.of(life1, life2, life3));

        rootPane.getChildren().remove(infoButtonPane);
        rootPane.getChildren().remove(rulesPane);
        rootPane.getChildren().remove(wantToContinuePane);
        rootPane.getChildren().remove(pauseButtonPane);

        Timeline timelineTimeOut = new Timeline();
        timelineTimeOut.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(1000),
                        e -> timeOut.setImage(TIMEOUT_3)
                )
        );
        timelineTimeOut.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(2000),
                        e -> timeOut.setImage(TIMEOUT_2)
                )
        );
        timelineTimeOut.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(3000),
                        e -> timeOut.setImage(TIMEOUT_1)
                )
        );
        timelineTimeOut.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(4000),
                        e -> timeOut.setImage(TIMEOUT_4)
                )
        );

        timelineTimeOut.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(5000),
                        e -> timeOut.setImage(null)
                )
        );


        timelineTimeOut.setCycleCount(1);
        timelineTimeOut.setOnFinished(event -> {
            this.runTimeLine = createTimelineForRun();
            this.runTimeLine.play();
            this.parallelTransition = main.backgroundTransition(background1, background2, 10000);
            this.parallelTransition.play();
            this.distanceTimeline = distanceRun();
            this.objectAddingTimeline = addCoin();
            this.objectAddingTimeline.play();
            distanceRun().play();

        });

        timelineTimeOut.play();


    }

    public void infoButtonPressed() {

        if (parallelTransition.getStatus() == Animation.Status.RUNNING) {
            this.parallelTransition.pause();
            this.runTimeLine.pause();
            this.distanceTimeline.pause();
            this.objectAddingTimeline.pause();

            for (Timeline timeline : timelineList) {
                timeline.pause();
            }

            blur = new GaussianBlur(30);
            adj.setInput(blur);
            pane.setEffect(adj);
            rootPane.getChildren().add(infoButtonPane);

        } else {
            this.parallelTransition.play();
            this.runTimeLine.play();
            this.distanceTimeline.play();
            this.objectAddingTimeline.play();

            for (Timeline timeline : timelineList) {
                timeline.play();
            }

            blur = new GaussianBlur(0);
            adj.setInput(blur);
            rootPane.setEffect(adj);
        }
    }


    public void firstReturnButtonPressed() {
        rootPane.getChildren().remove(infoButtonPane);
        blur = new GaussianBlur(0);
        adj.setInput(blur);
        this.parallelTransition.play();
        this.runTimeLine.play();
        this.distanceTimeline.play();
        this.objectAddingTimeline.play();

        for (Timeline timeline : timelineList) {
            timeline.play();
        }
    }

    public void rulesPressed() {
        rootPane.getChildren().remove(infoButtonPane);
        rootPane.getChildren().add(rulesPane);
    }

    public void secondReturnButtonPressed() {
        rootPane.getChildren().remove(rulesPane);
        rootPane.getChildren().add(infoButtonPane);

    }

    private void removeLife() throws FileNotFoundException {
        if (livesObj.canRemoveLife()) {
            livesObj.setLivesAmount(livesObj.getLivesAmount() - 1);
            listOfLives.get(livesObj.getLivesAmount()).setImage(null);

        } else if (livesObj.getLivesAmount() == 1) {
            this.parallelTransition.pause();
            this.runTimeLine.pause();
            this.distanceTimeline.pause();
            this.objectAddingTimeline.pause();
            for (Timeline timeline : timelineList) {
                timeline.pause();
            }
            livesObj.setLivesAmount(livesObj.getLivesAmount() - 1);
            listOfLives.get(livesObj.getLivesAmount()).setImage(null);

            if (Integer.parseInt(coinsAmount.getText()) >= livesObj.getPrice()) {
                blur = new GaussianBlur(30);
                adj.setInput(blur);
                pane.setEffect(adj);
                rootPane.getChildren().add(wantToContinuePane);
                timer.setText("5");
                buyLifeButton.setText(String.valueOf(livesObj.getPrice()));
                countDownForPlayAgainTimeline = new Timeline(new KeyFrame(
                        Duration.millis(1000),
                        event -> timer.setText(String.valueOf((Integer.parseInt(timer.getText()) - 1)))));
                countDownForPlayAgainTimeline.setOnFinished(e -> {
                    try {
                        showResultStage();
                    } catch (FileNotFoundException ignored) {

                    }
                });
                countDownForPlayAgainTimeline.setCycleCount(5);
                countDownForPlayAgainTimeline.play();

            } else {
                showResultStage();
            }
        }
    }

    private Timeline createTimelineForBirdFly(ImageView bird, Image firstImage, Image secondImage) {
        Timeline timelineFly = new Timeline();
        timelineFly.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(200),
                        e -> bird.setImage(firstImage)
                )
        );
        timelineFly.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(400),
                        e -> bird.setImage(secondImage)
                )
        );
        timelineFly.setCycleCount(Animation.INDEFINITE);
        return timelineFly;
    }

    private Timeline createTimelineForRun() {
        runTimeLine = new Timeline();
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(100),
                        e -> runningAgo1.setImage(RUN_1)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(200),
                        e -> runningAgo1.setImage(RUN_2)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(300),
                        e -> runningAgo1.setImage(RUN_3)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(400),
                        e -> runningAgo1.setImage(RUN_4)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(500),
                        e -> runningAgo1.setImage(RUN_5)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(600),
                        e -> runningAgo1.setImage(RUN_6)
                )
        );

        runTimeLine.setCycleCount(Animation.INDEFINITE);
        return runTimeLine;
    }

    public Timeline createTimelineForJumpDown() {
        this.runTimeLine.stop();
        runTimeLine = new Timeline();
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(0),
                        e -> runningAgo1.setImage(JUMP_DOWN)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(200),
                        e -> {
                            runningAgo1.setImage(JUMP_DOWN);
                            runningAgo1.setLayoutY(runningAgo1.getLayoutY() + (AGO_Y_LAYOUT - runningAgo1.getLayoutY()));
                            agoY = runningAgo1.getLayoutY();
                            agoX = runningAgo1.getLayoutX();
                        }
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(350),
                        e -> runningAgo1.setImage(NO_MOVE)
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(450),
                        e -> runningAgo1.setImage(NO_MOVE)
                )
        );
        runTimeLine.setCycleCount(1);
        runTimeLine.setOnFinished(event -> {
            this.runTimeLine = createTimelineForRun();
            this.runTimeLine.play();
        });
        return runTimeLine;
    }

    public Timeline createTimelineForJumpUp() {
        this.runTimeLine.stop();
        runTimeLine = new Timeline();
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(100),
                        e -> {
                            runningAgo1.setImage(JUMP_UP);
                            runningAgo1.setLayoutY(runningAgo1.getLayoutY() - 200);
                            agoY = runningAgo1.getLayoutY();
                            agoX = runningAgo1.getLayoutX();
                        }
                )
        );
        runTimeLine.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(500),
                        e -> runningAgo1.setImage(JUMP_UP)
                )
        );
        runTimeLine.setCycleCount(1);
        runTimeLine.setOnFinished(event -> {
            runTimeLine.stop();
            runTimeLine = createTimelineForRun();
            runTimeLine.play();
        });
        return runTimeLine;
    }

    private Timeline distanceRun() {
        distanceTimeline = new Timeline();
        distanceTimeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(100),
                        e -> {
                            runDistance += 1;
                            distance.setText(String.valueOf(runDistance));
                        }
                )
        );

        distanceTimeline.setCycleCount(Animation.INDEFINITE);
        return distanceTimeline;
    }

    private static int randInt(int max, int min) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private Timeline addCoin() {

        objectAddingTimeline = new Timeline();
        objectAddingTimeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(randInt(3500, 2000)),
                        e -> {
                            List<String> coinsAndObstacle = List.of("/images/icons/coin.png",
                                    "/images/obstacles/obstacle.png",
                                    "/images/obstacles/birdFlying1.png",
                                    "/images/obstacles/monsterFly1.png");
                            int idx = new Random().nextInt(coinsAndObstacle.size());
                            String image = (coinsAndObstacle.get(idx));
                            ImageView objectToAdd = new ImageView(new Image(image));
                            switch (image) {
                                case "/images/icons/coin.png":
                                    objectToAdd.setFitWidth(50);
                                    objectToAdd.setFitHeight(50);
                                    objectToAdd.setLayoutX(1200);
                                    objectToAdd.setLayoutY(randInt(600, 200));
                                    break;
                                case "/images/obstacles/birdFlying1.png":
                                case "/images/obstacles/monsterFly1.png":
                                    objectToAdd.setFitHeight(100);
                                    objectToAdd.setFitWidth(100);
                                    objectToAdd.setLayoutX(1200);
                                    objectToAdd.setLayoutY(randInt(300, 200));
                                    if (image.equals("/images/obstacles/birdFlying1.png")) {
                                        Timeline flyTimeline = createTimelineForBirdFly(objectToAdd,
                                                BIRD_FLY_1, BIRD_FLY_2);
                                        flyTimeline.play();
                                    } else {
                                        Timeline flyTimeline = createTimelineForBirdFly(objectToAdd,
                                                MONSTER_FLY_1, MONSTER_FLY_2);
                                        flyTimeline.play();
                                    }
                                    break;
                                default:
                                    objectToAdd.setFitWidth(150);
                                    objectToAdd.setFitHeight(150);
                                    objectToAdd.setLayoutX(1200);
                                    objectToAdd.setLayoutY(600);
                                    break;
                            }
                            pane.getChildren().add(objectToAdd);
                            Timeline timelineMove = timelineObjectMove(objectToAdd);
                            timelineList.add(timelineMove);
                            timelineMove.play();

                        }));
        objectAddingTimeline.setCycleCount(1);
        objectAddingTimeline.setOnFinished(event -> {
            this.objectAddingTimeline = addCoin();
            this.objectAddingTimeline.play();
        });
        return objectAddingTimeline;
    }

    private Timeline timelineObjectMove(ImageView object) {

        Timeline timelineMove = new Timeline();
        timelineMove.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(5),
                        event -> {
                            object.setLayoutX(object.getLayoutX() - 1);
                            if (collision(object.getLayoutX(), object.getLayoutY(), object.getFitHeight(),
                                    object.getFitWidth(), runningAgo1.getFitHeight(), runningAgo1.getFitWidth())) {
                                String toCheck = object.getImage().getUrl().substring(object.getImage().getUrl().length() - 5, object.getImage().getUrl().length());
                                timelineMove.stop();
                                timelineList.remove(timelineMove);
                                pane.getChildren().remove(object);
                                if (toCheck.equals("e.png") || toCheck.equals("1.png") || toCheck.equals("2.png")) {
                                    try {
                                        removeLife();
                                    } catch (FileNotFoundException ignored) {

                                    }
                                } else {
                                    coinsAmount.setText(Integer.valueOf(coinsAmount.getText()) + 1 + "");
                                }
                            }
                        }

                )
        );
        timelineMove.setCycleCount(Animation.INDEFINITE);
        return timelineMove;
    }

    private boolean collision(double objectX, double objectY, double objectHeight, double objectWidth, double agoHeight,
                              double agoWidth) {
        return !((objectX > agoWidth + agoX - 40) || (objectWidth + objectX - 40 < agoX)
                || (objectY > agoY + agoHeight - 40)
                || (objectY + objectHeight - 40 < agoY));
    }


    public void showResultStage() throws FileNotFoundException {

        int totalCoins = Integer.parseInt(main.readFromFile(coinsAmountText, 2));

        PrintWriter writer = new PrintWriter(coinsAmountText);
        writer.print(coinsAmount.getText() + "\n" + String.valueOf(totalCoins + Integer.parseInt(coinsAmount.getText())) + "\n" + distance.getText());
        writer.close();
        recordBestDistances(Integer.parseInt(distance.getText()));
        main.makeFadeout(rootPane, "Result.fxml");


    }

    private void recordBestDistances(int distance) throws FileNotFoundException {

        String resultTableText = main.getResultTable();
        Map<Integer, String> readFromFile = new HashMap<>();

        for (int i = 1; i < 6; i++) {
            try {
                String[] str = main.readFromFile(resultTableText, i).split(",");
                System.out.println(str[0] + " " + str[1]);
                readFromFile.put(Integer.parseInt(str[0]), str[1]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        readFromFile.put(distance, String.valueOf(LocalDate.now()));
        List sortedKeys = new ArrayList(readFromFile.keySet());
        sortedKeys.sort(Collections.reverseOrder());
        readFromFile.remove(readFromFile.size() - 1);
        PrintWriter writer1 = new PrintWriter(resultTableText);
        writer1.print(String.valueOf(sortedKeys.get(0) + "," + readFromFile.get(sortedKeys.get(0))) + "\n"
                + String.valueOf(sortedKeys.get(1) + "," + readFromFile.get(sortedKeys.get(1))) + "\n"
                + String.valueOf(sortedKeys.get(2) + "," + readFromFile.get(sortedKeys.get(2))) + "\n"
                + String.valueOf(sortedKeys.get(3) + "," + readFromFile.get(sortedKeys.get(3))) + "\n"
                + String.valueOf(sortedKeys.get(4) + "," + readFromFile.get(sortedKeys.get(4))) + "\n");
        writer1.close();
    }


    public void pauseGame() {
        blur = new GaussianBlur(30);
        adj.setInput(blur);
        pane.setEffect(adj);
        rootPane.getChildren().add(pauseButtonPane);

        this.parallelTransition.pause();
        this.runTimeLine.pause();
        this.distanceTimeline.pause();
        this.objectAddingTimeline.pause();
        for (Timeline timeline : timelineList) {
            timeline.pause();
        }
    }

    public void returnFromPause() {
        rootPane.getChildren().remove(pauseButtonPane);
        blur = new GaussianBlur(0);
        adj.setInput(blur);
        pane.setEffect(adj);

        this.parallelTransition.play();
        this.runTimeLine.play();
        this.distanceTimeline.play();
        this.objectAddingTimeline.play();
        for (Timeline timeline : timelineList) {
            timeline.play();
        }
    }

    public void buyLifeToContinue() {
        this.countDownForPlayAgainTimeline.stop();

        listOfLives.get(livesObj.getLivesAmount()).setImage(LIFE);
        livesObj.addLife();
        coinsAmount.setText(String.valueOf(Integer.valueOf(coinsAmount.getText()) - livesObj.getPriceAndIncrease()));

        blur = new GaussianBlur(0);
        adj.setInput(blur);
        rootPane.getChildren().remove(wantToContinuePane);

        this.parallelTransition.play();
        this.runTimeLine.play();
        this.distanceTimeline.play();
        this.objectAddingTimeline.play();
        for (Timeline timeline : timelineList) {
            timeline.play();
        }
    }
}
