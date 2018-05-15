import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;


public class RulesScene {

    @FXML
    StackPane rootPane;

    @FXML
    ImageView background1;
    @FXML
    ImageView background2;
    @FXML
    ImageView returnButton;

    private Main main = new Main();

    public RulesScene() throws FileNotFoundException {
    }

    public void initialize() {
        main.backgroundTransition(background1, background2, 10000).play();
    }

    public void returnToMainMenu() {
        main.makeFadeout(rootPane, "Menu.fxml");
    }

}
