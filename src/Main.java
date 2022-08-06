import control.GameController;
import control.MainController;
import game.map.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resource.ResourceLoader;

public class Main extends Application {
    public Map dummyMap;
     public ResourceLoader resourceLoader;

    @Override
    public void start(Stage stage) throws Exception{
        resourceLoader=new ResourceLoader();
        dummyMap=Map.createEmptyMap(100,100,"Dummy","Desc","dum",resourceLoader);
        //before
        stage.setTitle("Creeper World");
        //resource loading
        FXMLLoader mainFXML = new FXMLLoader(getClass().getResource("fx/fxml/MainMenu.fxml"));
        Parent mainParent = mainFXML.load();

        Scene mainScene = new Scene(mainParent, 1920, 1080);


        FXMLLoader gameFXML = new FXMLLoader(getClass().getResource("fx/fxml/Game.fxml"));
        Parent gameParent = gameFXML.load();
        Scene gameScene = new Scene(gameParent, 1920, 1080);

        MainController mc = (MainController) mainFXML.getController();
        mc.gameController= (GameController) gameFXML.getController();
        mc.gameController.resourceLoader=this.resourceLoader;
        mc.gameController.scene=gameScene;
        mc.scene=gameScene;
        mc.stage=stage;
        //after
        stage.setScene(mainScene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
