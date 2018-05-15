import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ResultScene {

    //Pane
    @FXML
    StackPane rootpane;

    //Images
    @FXML
    ImageView background1;
    @FXML
    ImageView background2;
    @FXML
    ImageView mainMenuButton;
    @FXML
    ImageView playAgainButton;

    //Labels
    @FXML
    Label distanceRun;
    @FXML
    Label coinsCollected;
    @FXML
    Label coinsTotal;
    @FXML
    Label distanceResult1;
    @FXML
    Label distanceResult2;
    @FXML
    Label distanceResult3;
    @FXML
    Label distanceResult4;
    @FXML
    Label distanceResult5;
    @FXML
    Label timeResult1;
    @FXML
    Label timeResult2;
    @FXML
    Label timeResult3;
    @FXML
    Label timeResult4;
    @FXML
    Label timeResult5;

    private List<Label> listOfResultLabels = new ArrayList<>();
    private List<Label> listOfTimeResultLabels = new ArrayList<>();


    private void addLabelsToList() {

        listOfResultLabels.add(distanceResult1);
        listOfResultLabels.add(distanceResult2);
        listOfResultLabels.add(distanceResult3);
        listOfResultLabels.add(distanceResult4);
        listOfResultLabels.add(distanceResult5);

        listOfTimeResultLabels.add(timeResult1);
        listOfTimeResultLabels.add(timeResult2);
        listOfTimeResultLabels.add(timeResult3);
        listOfTimeResultLabels.add(timeResult4);
        listOfTimeResultLabels.add(timeResult5);
    }

    private Main main = new Main();

    public ResultScene() throws FileNotFoundException {
    }

    public void initialize() throws FileNotFoundException {
        System.out.println(LocalDate.now());
        addLabelsToList();
        main.makeFadeInTransition(rootpane);
        main.backgroundTransition(background1, background2, 10000);

        coinsCollected.setText(main.readFromFile(main.getCoinsNowTotalAndDistance(), 1));
        coinsTotal.setText(main.readFromFile(main.getCoinsNowTotalAndDistance(), 2));
        distanceRun.setText(main.readFromFile(main.getCoinsNowTotalAndDistance(), 3));

        for (int i = 0; i < listOfResultLabels.size(); i++) {
            String distance = main.readFromFile(main.getResultTable(), i + 1).split(",")[0];

            if (Integer.parseInt(distance) <= 0) {
                distance = "";
            }
            listOfResultLabels.get(i).setText(distance);
        }

        for (int i = 0; i < listOfTimeResultLabels.size(); i++) {
            String date = main.readFromFile(main.getResultTable(), i + 1).split(",")[1];
            if (date.equals("0")) {
                date = "";
            }
            listOfTimeResultLabels.get(i).setText(date);
        }
    }

    public void playAgain() {
        main.makeFadeout(rootpane, "Game.fxml");
    }

    public void returnToMainMenu() {
        main.makeFadeout(rootpane, "Menu.fxml");
    }
}
