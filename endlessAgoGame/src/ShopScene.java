import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class ShopScene {
    
    //Pane
    @FXML
    StackPane root;
    @FXML
    Pane pane;

    //Images
    @FXML
    ImageView bow;
    @FXML
    ImageView flowers;
    @FXML
    ImageView glasses;
    @FXML
    ImageView hat;
    @FXML
    ImageView ago;
    @FXML
    ImageView background1;
    @FXML
    ImageView background2;
    @FXML
    ImageView buyHat;
    @FXML
    ImageView buyGlasses;
    @FXML
    ImageView buyFlower;
    @FXML
    ImageView buyBow;
    @FXML
    ImageView buyClassic;
    @FXML
    ImageView returnButton;
    
    //Labels
    @FXML
    Label coinsTotal;
    @FXML
    Label hatPrice;
    @FXML
    Label bowPrice;
    @FXML
    Label glassesPrice;
    @FXML
    Label flowersPrice;
    
    private Main main = new Main();
    private String coinsNowTotalAndDistanceFile = main.getCoinsNowTotalAndDistance();
    private String agoSpriteFile = main.getAgoSprite();
    
    public ShopScene() throws FileNotFoundException {
    }
    
    public void initialize() throws FileNotFoundException {
        main.makeFadeInTransition(root);
        main.backgroundTransition(background1, background2, 20000).play();
        coinsTotal.setText(main.readFromFile(main.getCoinsNowTotalAndDistance(), 2));
    }

    
    public String buyHat() throws FileNotFoundException {
        
        if (Integer.parseInt(coinsTotal.getText()) >= Integer.parseInt(hatPrice.getText())) {

            PrintWriter writer1 = new PrintWriter(coinsNowTotalAndDistanceFile);
            int readCoins = Integer.parseInt(coinsTotal.getText());
            int moneyLeft = readCoins - Integer.parseInt(hatPrice.getText());

            writer1.print(0 + "\n" + String.valueOf(moneyLeft) + "\n" + 0);
            coinsTotal.setText(String.valueOf(moneyLeft));
            writer1.close();
            addTextAnimation("You bought the hat", pane);
            PrintWriter writer = new PrintWriter(agoSpriteFile);
            writer.print("/images/agoShop/hat/1RunHat.png\n" +
                    "/images/agoShop/hat/2RunHat.png\n" +
                    "/images/agoShop/hat/3RunHat.png\n" +
                    "/images/agoShop/hat/4RunHat.png\n" +
                    "/images/agoShop/hat/5RunHat.png\n" +
                    "/images/agoShop/hat/6RunHat.png\n" +
                    "/images/agoShop/hat/1NomoveHat.png\n" +
                    "/images/agoShop/hat/1JumpHat.png\n" +
                    "/images/agoShop/hat/2JumpHat.png");
            writer.close();
            setAgoHat();
        }
        return agoSpriteFile;
    }
    
    public String buyFlower() throws FileNotFoundException {

        if (Integer.parseInt(coinsTotal.getText()) >= Integer.parseInt(flowersPrice.getText())) {

            PrintWriter writer1 = new PrintWriter(coinsNowTotalAndDistanceFile);
            int readcoins = Integer.parseInt(coinsTotal.getText());
            int moneyLeft = readcoins - Integer.parseInt(flowersPrice.getText());
            writer1.print(0 + "\n" + String.valueOf(moneyLeft) + "\n" + 0);
            coinsTotal.setText(String.valueOf(moneyLeft));
            writer1.close();
            
            addTextAnimation("You bought the flowers", pane);
            PrintWriter writer = new PrintWriter(agoSpriteFile);
            writer.print("/images/agoShop/flowers/1RunFlowers.png\n" +
                    "/images/agoShop/flowers/2RunFlowers.png\n" +
                    "/images/agoShop/flowers/3RunFlowers.png\n" +
                    "/images/agoShop/flowers/4RunFlowers.png\n" +
                    "/images/agoShop/flowers/5RunFlowers.png\n" +
                    "/images/agoShop/flowers/6RunFlowers.png\n" +
                    "/images/agoShop/flowers/1NomoveFlowers.png\n" +
                    "/images/agoShop/flowers/1JumpFlowers.png\n" +
                    "/images/agoShop/flowers/2JumpFlowers.png");
            writer.close();
            setAgoFlowers();
        }

        return agoSpriteFile;
    }

    public String buyGlasses() throws FileNotFoundException {

        if (Integer.parseInt(coinsTotal.getText()) >= Integer.parseInt(glassesPrice.getText())) {

            PrintWriter writer1 = new PrintWriter(coinsNowTotalAndDistanceFile);
            int readCoins = Integer.parseInt(coinsTotal.getText());
            int moneyLeft = readCoins - Integer.parseInt(glassesPrice.getText());
            writer1.print(0 + "\n" + String.valueOf(moneyLeft) + "\n" + 0);
            coinsTotal.setText(String.valueOf(moneyLeft));
            writer1.close();
            
            addTextAnimation("You bought the glasses", pane);
            PrintWriter writer = new PrintWriter(agoSpriteFile);
            writer.print("/images/agoShop/glasses/1RunGlasses.png\n" +
                    "/images/agoShop/glasses/2RunGlasses.png\n" +
                    "/images/agoShop/glasses/3RunGlasses.png\n" +
                    "/images/agoShop/glasses/4RunGlasses.png\n" +
                    "/images/agoShop/glasses/5RunGlasses.png\n" +
                    "/images/agoShop/glasses/6RunGlasses.png\n" +
                    "/images/agoShop/glasses/image/glasses/1NomoveGlasses.png\n" +
                    "/images/agoShop/glasses/1JumpGlasses.png\n" +
                    "/images/agoShop/glasses/2JumpGlasses.png");
            writer.close();
            setAgoGlasses();
        }

        return agoSpriteFile;
    }

    public String buyBow() throws FileNotFoundException {

        if (Integer.parseInt(coinsTotal.getText()) >= Integer.parseInt(bowPrice.getText())) {

            PrintWriter writer1 = new PrintWriter(coinsNowTotalAndDistanceFile);
            int readCoins = Integer.parseInt(coinsTotal.getText());
            int moneyLeft = readCoins - Integer.parseInt(bowPrice.getText());
            writer1.print(0 + "\n" + String.valueOf(moneyLeft) + "\n" + 0);
            coinsTotal.setText(String.valueOf(moneyLeft));
            writer1.close();
            addTextAnimation("You bought the bow", pane);

            PrintWriter writer = new PrintWriter(agoSpriteFile);
            writer.print("/images/agoShop/bow/1RunBow.png\n" +
                    "/images/agoShop/bow/2RunBow.png\n" +
                    "/images/agoShop/bow/3RunBow.png\n" +
                    "/images/agoShop/bow/4RunBow.png\n" +
                    "/images/agoShop/bow/5RunBow.png\n" +
                    "/images/agoShop/bow/6RunBow.png\n" +
                    "/images/agoShop/bow/1NomoveBow.png\n" +
                    "/images/agoShop/bow/1JumpBow.png\n" +
                    "/images/agoShop/bow/2JumpBow.png");
            writer.close();
            setAgoBow();
        }

        return agoSpriteFile;
    }


    public String buyClassic() throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(agoSpriteFile);
        writer.print("/images/agoShop/classic/1Run.png\n" +
                "/images/agoShop/classic/2Run.png\n" +
                "/images/agoShop/classic/3Run.png\n" +
                "/images/agoShop/classic/4Run.png\n" +
                "/images/agoShop/classic/5Run.png\n" +
                "/images/agoShop/classic/6Run.png\n" +
                "/images/agoShop/classic/1Nomove.png\n" +
                "/images/agoShop/classic/1Jump.png\n" +
                "/images/agoShop/classic/2Jump.png");
        addTextAnimation("You've chosen to stay classic", pane);
        writer.close();

        return agoSpriteFile;
    }
    
    private void addTextAnimation(String message, Pane pane) {

        Text msg = new Text(message);
        msg.setStyle("    -fx-font-weight: bold;\n" +
                "    -fx-color: #000000;\n" +
                "    -fx-letter-spacing: 2pt;\n" +
                "    -fx-word-spacing: 2pt;\n" +
                "    -fx-font-size: 40px;\n" +
                "    -fx-text-align: left;\n" +
                "    -fx-font-family: tahoma, verdana, arial, sans-serif;\n" +
                "    -fx-line-height: 1px;");
        
        msg.setTextOrigin(VPos.TOP);
        pane.getChildren().add(msg);
        KeyValue initKeyValue = new KeyValue(msg.translateXProperty(), 800);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
        KeyValue endKeyValue = new KeyValue(msg.translateXProperty(), -1.0
                * 600);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(4), endKeyValue);
        Timeline timeline = new Timeline(initFrame, endFrame);
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void setAgoClassic() {
        ago.setImage(new Image("/images/agoShop/classic/1Nomove.png"));
    }

    public void setAgoBow() {
        ago.setImage(new Image("/images/agoShop/bow/1NomoveBow.png"));
    }

    public void setAgoFlowers() {
        ago.setImage(new Image("/images/agoShop/flowers/1NomoveFlowers.png"));
    }

    public void setAgoGlasses() {
        ago.setImage(new Image("/images/agoShop/glasses/1NomoveGlasses.png"));
    }

    public void setAgoHat() {
        ago.setImage(new Image("/images/agoShop/hat/1NomoveHat.png"));
    }

    public void returnButton() {
        main.makeFadeout(root, "Menu.fxml");
    }

}
