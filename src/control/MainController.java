package control;

import game.map.Map;
import game.map.MapReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resource.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends CommonController{
    public Stage stage;
    public Scene scene;
    ArrayList<Map> mapList;
    private int mapIndex=0;
    public int menuI=0;
    public GameController gameController;
    //public Map

    //FXML panels

    @FXML
    private TextField hField;
    @FXML
    private TextField wField;
    @FXML
    private TextField nField;
    @FXML
    private TextField IDField;
    @FXML
    private TextArea dField;


    @FXML
    private VBox stdMenu;

    @FXML
    private VBox stdMenu2;

    @FXML
    private FlowPane newMapM;

    @FXML
    private FlowPane loadGameM;

    @FXML
    private FlowPane editMapM;

    @FXML
    private ChoiceBox<String> cbox1;

    @FXML
    private ChoiceBox<String> cbox2;
    //


    //events
    @FXML
    private void startGame() throws IOException, ClassNotFoundException {
        start();
    }

    private void start() {

        switch (menuI) {
            //New game
            case 1:

                //Load Game
            case 2:

                gameController.setCurrentMap(mapList.get(mapIndex));
                changeStage();
                return;
            //Map editor
            case 3:
                gameController.setCurrentMap(mapList.get(mapIndex));
                gameController.mapEditor=true;
                changeStage();

                return;
            case 4:
                int w = Integer.parseInt(wField.getText());
                int h = Integer.parseInt(hField.getText());
                gameController.setCurrentMap(Map.createEmptyMap(h,
                        w,
                        nField.getText(),
                        dField.getText(),
                        IDField.getText(),
                        new ResourceLoader()));
                gameController.mapEditor=true;
                changeStage();
                return;
        }

    }

    @FXML
    protected void requestFocus(ActionEvent e){
        super.requestFocus(e);
    }

    @FXML
    private void back() {
    stdMenu.setVisible(true);
    stdMenu2.setVisible(false);
    cbox1.getItems().removeAll();
    cbox2.getItems().removeAll();
    while(mapList!=null&&mapList.isEmpty()==false){
        mapList.remove(0);
        if(cbox1.getItems().isEmpty()==false)
        cbox1.getItems().remove(0);
        if(cbox2.getItems().isEmpty()==false)
        cbox2.getItems().remove(0);
    }
    }

    private void menu2(){
        stdMenu.setVisible(false);
        stdMenu2.setVisible(true);

        loadGameM.setVisible(false);
        editMapM.setVisible(false);
        newMapM.setVisible(false);

    }
    @FXML
    private void newGame(ActionEvent event) throws IOException, ClassNotFoundException {
        MapReader mr = new MapReader();
        mapList=mr.loadMaps(1);
        menu2();
        loadMaps(2);
        editMapM.setVisible(true);
        menuI=1;


    }    @FXML
    private void loadGame(ActionEvent event) throws IOException, ClassNotFoundException {

        MapReader mr = new MapReader();
        mapList=mr.loadMaps(2);
        menu2();
        loadMaps(1);
        loadGameM.setVisible(true);
        menuI=2;

    }
    @FXML
    private void editMap(ActionEvent event) throws IOException, ClassNotFoundException {
        MapReader mr = new MapReader();
        mapList=mr.loadMaps(1);
        menu2();
        loadMaps(2);
        editMapM.setVisible(true);
        menuI=3;

    }    @FXML
    private void newMap(ActionEvent event) throws IOException {

        menu2();
        newMapM.setVisible(true);
        menuI=4;

    }

    private void loadMaps(int i){
        for (Map m: mapList
             ) {
            switch(i) {
                case 1:
                    cbox1.getItems().add(m.mName);
                    break;
                case 2:
                    cbox2.getItems().add(m.mName);
                    break;
            }
        }
    }
    @FXML
    private void quitButton(ActionEvent event) {
        Platform.exit();
    }

    private void changeStage() {
        if(stage!=null&&scene!=null) {
            stage.setScene(scene);
            gameController.setKeyBindings();
            gameController.setMapPainter();
            gameController.mapPainter.mapInitialization();
        }

        else {
            System.out.println("Error");
        }
    }


}
