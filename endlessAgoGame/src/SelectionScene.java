
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SelectionScene {

    @FXML
    StackPane rootPane;

    @FXML
    ImageView candyCrushButton;
    @FXML
    ImageView halloweenButton;
    @FXML
    ImageView cityLifeButton;
    @FXML
    ImageView backToMainMenuButton;
    @FXML
    ImageView background1;
    @FXML
    ImageView background2;

    private Main main = new Main();

    //ToolTips
    private ImageView candyBackgroundSmall = new ImageView(new Image("images/backgrounds/candyBackgroundSmall.png"));
    private ImageView halloweenSmall = new ImageView(new Image("images/backgrounds/halloweenBackgroundSmall.png"));
    private ImageView cityLifeSmall = new ImageView(new Image("images/backgrounds/cityDaySmall.png"));

    public SelectionScene() throws FileNotFoundException {
    }

    public void halloweenButton() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(main.getBackgrounds());
        writer.print("images/backgrounds/halloweenBackground.png");
        writer.close();
        main.makeFadeout(rootPane, "Game.fxml");
    }

    public void candyWorldButton() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(main.getBackgrounds());
        writer.print("images/backgrounds/candyBackground.png");
        writer.close();
        main.makeFadeout(rootPane, "Game.fxml");
    }

    public void cityLifeButton() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(main.getBackgrounds());
        writer.print("images/backgrounds/cityDay.png");
        writer.close();
        main.makeFadeout(rootPane, "Game.fxml");
    }

    public void initialize() {

        main.backgroundTransition(background1, background2, 10000).play();
        rootPane.setOpacity(0);
        main.makeFadeInTransition(rootPane);

        final Tooltip tooltipSafari = new Tooltip();
        tooltipSafari.setGraphic(candyBackgroundSmall);
        Tooltip.install(candyCrushButton, tooltipSafari);

        final Tooltip tooltipForest = new Tooltip();
        tooltipForest.setGraphic(halloweenSmall);
        Tooltip.install(halloweenButton, tooltipForest);

        final Tooltip tooltipAntarctica = new Tooltip();
        tooltipAntarctica.setGraphic(cityLifeSmall);
        Tooltip.install(cityLifeButton, tooltipAntarctica);
    }

    public void shadingTransition() {
        main.makeFadeout(rootPane, "Menu.fxml");
    }

}
