
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    private Pane root = new Pane();
    private GameScene gameScene;

    private File agoSprite = createFile("agoSpriteTest.txt", "/images/agoShop/classic/1Run.png\n" +
            "/images/agoShop/classic/2Run.png\n" +
            "/images/agoShop/classic/3Run.png\n" +
            "/images/agoShop/classic/4Run.png\n" +
            "/images/agoShop/classic/5Run.png\n" +
            "/images/agoShop/classic/6Run.png\n" +
            "/images/agoShop/classic/1Nomove.png\n" +
            "/images/agoShop/classic/1Jump.png\n" +
            "/images/agoShop/classic/2Jump.png");
    private File backgrounds = createFile("backgroundsTest.txt", "");
    private File coinsNowTotalAndDistance = createFile("coinsNowTotalAndDistanceTest.txt", "0\n0\n0");
    private File resultTable = createFile("resultTableTest.txt", "0,0\n-1,0\n-2,0\n-3,0\n-4,0\n");
    Scene scene = new Scene(root);


    public Main() throws FileNotFoundException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader menu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent parent = menu.load();

        FXMLLoader gameScene = new FXMLLoader(getClass().getResource("Game.fxml"));
        this.gameScene = gameScene.getController();
        Scene scene = new Scene(parent);
        primaryStage.setResizable(false);

        primaryStage.setTitle("ENDLESS AGO");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void makeFadeInTransition(StackPane pane) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void makeFadeout(StackPane pane, String fxml) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                Parent secondView = loader.load();
                scene.setRoot(secondView);
                if (fxml.equals("Game.fxml")) {
                    gameScene = loader.getController();
                    scene.setOnKeyPressed(keyListenerJumpUp);
                    scene.setOnKeyReleased(keyListenerJumpDown);

                }
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(scene);

            } catch (IOException ignored) {
            }

        });
        fadeTransition.play();

    }

    private EventHandler<KeyEvent> keyListenerJumpUp = event -> {
        if (event.getCode() == KeyCode.W) {
            gameScene.createTimelineForJumpUp().play();
        }
        event.consume();
    };

    private EventHandler<KeyEvent> keyListenerJumpDown = event -> {
        if (event.getCode() == KeyCode.W) {
            gameScene.createTimelineForJumpDown().play();
        }
        event.consume();
    };


    public ParallelTransition backgroundTransition(ImageView background1, ImageView background2, int speed) {

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(speed), background1);
        translateTransition.setFromX(0);
        int BACKGROUND_WIDTH = 2000;
        translateTransition.setToX(-1 * BACKGROUND_WIDTH);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(speed), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);

        return parallelTransition;

    }


    public String readFromFile(String file, int line) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(file));
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.next());
        }
        scanner.close();

        return list.get(line - 1);


    }

    private File createFile(String name, String textInFile) throws FileNotFoundException {
        File file = new File(name);
        try {
            file.createNewFile();
        } catch (IOException ignored) {
        }
        if (file.exists() && file.length() == 0) {
            PrintWriter writer = new PrintWriter(file.getPath());
            writer.print(textInFile);
            writer.close();
        }
        return file;
    }

    public String getAgoSprite() {
        return agoSprite.getPath();
    }

    public String getBackgrounds() {
        return backgrounds.getPath();
    }

    public String getCoinsNowTotalAndDistance() {
        return coinsNowTotalAndDistance.getPath();
    }

    public String getResultTable() {
        return resultTable.getPath();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

