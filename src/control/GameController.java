package control;

import control.eventHandlers.KeyBinding;
import game.Grids.LevelLayout;
import game.Structures.Enemy.Emitter;
import game.Structures.Enemy.Tower;
import game.Structures.EnemyStructure;
import game.Structures.Friendly.*;
import game.Structures.Friendly.Weapon.Beam;
import game.Structures.Friendly.Weapon.Bomber;
import game.Structures.Friendly.Weapon.Cannon;
import game.Structures.Friendly.Weapon.Mortar;
import game.Structures.FriendlyWeapon;
import game.Structures.Neutral.Totem;
import game.lib.Connectable;
import game.lib.Pair;
import game.lib.Point;
import game.map.Map;
import game.map.MapPainter;
import game.map.MapWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resource.ResourceLoader;
import resource.Sprite;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends CommonController {

    //@FXML objects
    //Updating objects
    @FXML
    private ProgressBar energyBar;
    @FXML
    private ProgressBar collectionBar;
    @FXML
    private ProgressBar depletionBar;
    @FXML
    private ProgressBar saturationBar;
    @FXML
    private Label PB_Enr;
    @FXML
    private Label PB_Col;
    @FXML
    private Label PB_Dep;
    @FXML
    private Label PB_Dif;
    @FXML
    private Label PB_Timer;
    //Other
    @FXML
    private AnchorPane anchor;

    @FXML
    private AnchorPane bgL;

    @FXML
    private AnchorPane gridL;
    @FXML
    private AnchorPane conL;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label labelLVL;

    @FXML
    private ProgressBar pbLVL;

    @FXML
    private Slider levelSlider;

    @FXML
    private TextField fName;

    @FXML
    private TextArea fDesc;

    @FXML
    private TextField fCost;

    @FXML
    private TextField fHP;

    @FXML
    private TextField fExtra;

    @FXML
    private HBox buildB;
    @FXML
    private HBox selectB;

    @FXML
    private HBox mapEditY;
    @FXML
    private HBox mapEditN;



    public int hint=0;
    //selection objects

    public Sprite current;

    public Rectangle selectedR;
    public Rectangle selectedL;
    public Rectangle selectedStr;

    //
    public static GameController gc;
    public Scene scene;
    public ResourceLoader resourceLoader;
    public Map currentMap;

    public MapPainter mapPainter;

    //logic
    boolean isBuilding=false;
    public boolean mapEditor=false;
    public boolean levelEditing=false;



    @FXML
    private void SaveMap(ActionEvent e) throws IOException {
        if(!mapEditor)
        currentMap.calc.setPeriod(0,mapEditor);
        MapWriter m = new MapWriter();
        m.saveMap(this.currentMap,mapEditor);
    }
    @FXML
    private void GetStructure(ActionEvent event) {
        if(! levelEditing) {
            if(current==null) {
            Button b = (Button) event.getSource();
            String s = b.getAccessibleText();
            isBuilding = true;
            switch (s) {

                case "Base":
                    current= Base.makeEmpty(resourceLoader,currentMap);
                    break;
                //Friendly Structure
                case "Collector":
                    current = (Collector.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Relay":
                    current = (Relay.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Storage":
                    current = (Storage.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Speed":
                    current = (Accelerator.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Reactor":
                    current = (Reactor.makeEmpty(resourceLoader,mapEditor,currentMap));

                    break;

                    //Weapon
                case "Cannon":
                    current = (Cannon.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Mortar":
                    current = (Mortar.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Beam":
                    current = (Beam.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;
                case "Bomber":
                    current = (Bomber.makeEmpty(resourceLoader,mapEditor,currentMap));
                    break;

                    //Neutral
                case "Totem":
                    current = Totem.makeEmpty(resourceLoader);
                    anchor.getChildren().add(current.texture);
                    return;
                //Enemy
                case "Emitter":
                    current = Emitter.makeEmpty(resourceLoader);
                    break;
                case "Tower":
                    current = Tower.makeEmpty(resourceLoader);
                    break;
            }
                anchor.getChildren().add(current.texture);
                current.text[0]=s;
                return;

            }
            else {
            System.out.println("DESELECT LEVEL");
            }
        }
        else {
            System.out.println("DESELECT FIRST");
        }

    }


    //Buttons
    @FXML
    private void quitButton(ActionEvent event) {
        if(!mapEditor) {
        }
        Platform.exit();

        System.exit(1);
    }

    @FXML
    private void setSpeed(ActionEvent e) {
        Button a = (Button)e.getSource();
        int b = Integer.parseInt(a.getAccessibleText());
        //System.out.println(b);
        currentMap.calc.setPeriod(b,mapEditor);
    }

    @FXML
    private void Disarm(ActionEvent e) {
    if (current instanceof FriendlyWeapon) {
        ((FriendlyWeapon) current).swapArmed();
        fExtra.setText(((FriendlyWeapon) current).text[3]);
    }

    }

    @FXML
    private void Destroy(ActionEvent e) {

            anchor.getChildren().remove(current.texture);
        if(current instanceof Base) {
            currentMap.removeObject((Base)current,mapEditor);
        }
        else {
            currentMap.removeObject(current);
        }
        if(current instanceof Connectable){
            ((Connectable) current).disconnect(currentMap);
        }

        current = null;
        swapBot(true);

        anchor.getChildren().remove(selectedStr);
        selectedStr = null;

    }


    //Edit level
    @FXML
    private void editLevel(ActionEvent event) {
        CheckBox s = (CheckBox) event.getSource();
        levelEditing=s.isSelected();
        if(levelEditing) {
            System.out.println("SELECTED");
            selectedL=new Rectangle(0,0,0,0);
            anchor.getChildren().add(selectedL);
        }
        else {
            System.out.println("DESELECTED");
            anchor.getChildren().remove(selectedL);
            selectedL=null;
        }
    }


    //// MOUSE CONTROL
    @FXML
    private void mouseMoved(MouseEvent event){
        double x=(int)(event.getX())-((int)(event.getX())%16);
        double y=(int)(event.getY())-((int)(event.getY())%16);
        //GRID UPDATE, ALWAYS
        if(checkBorders((event.getX()),event.getY())){
            int lvl=currentMap.levelGrid.getLevel((int)x/16-4,(int)y/16-4);
            labelLVL.setText(""+lvl);
            pbLVL.setProgress((double)lvl/ LevelLayout.maxLVL);
        }

        //MOVING LEVEL RECT
        if(levelEditing) {
            selectedL.setStroke(Color.YELLOW);
            selectedL.setStrokeWidth(4);
            selectedL.setFill(null);
            selectedL.setX(x-2);
            selectedL.setY(y-2);
            selectedL.setWidth(levelSlider.getValue()
                    *16
                    +2);
            selectedL.setHeight(levelSlider.getValue()
                    *16+2);
        }

        //MOVING CURRENT AND RECT
        if (current!=null) {
            if(checkBorders((event.getX()),event.getY())) {
                if(isBuilding)
                current.setPos(new Point(x, y));
                if(selectedR==null&& (current instanceof FriendlyWeapon  || current instanceof Base ||mapEditor) ) {
                    selectedR= new Rectangle(x-2,y-2,40,40);
                    selectedR.setStrokeWidth(4);
                    selectedR.setFill(null);
                    anchor.getChildren().add(selectedR);
                }
                // anchor.getChildren().add(selectedR);
                if(selectedR!=null) {
                    selectedR.setX(x - 2);
                    selectedR.setY(y - 2);
                    selectedR.setWidth(current.getWidth() * 16 + 2);
                    selectedR.setHeight(current.getHigh() * 16 + 2);


                if(
                        !(currentMap.occupyGrid.isOccupied((int)(x)/16-4,(int)(y)/16-4,current.getWidth(),current.getHigh())
                            ||
                                !currentMap.levelGrid.canPlace((int)(x)/16-4,(int)(y)/16-4,current.getWidth(),current.getHigh()))
                    //check if can build
                )
                    selectedR.setStroke(Color.GREEN);
                else {
                    selectedR.setStroke(Color.RED);
                }
                }
            }
        }

    }
    //MOUSE CLICKED
    @FXML
    private void mousePressed(MouseEvent event) {
        double x = (int) (event.getX()) - ((int) (event.getX()) % 16);
        double y = (int) (event.getY()) - ((int) (event.getY()) % 16);

        //if levelEditing
        if(levelEditing) {
            int v=0;
            if (event.getButton() == MouseButton.SECONDARY) {
                //Dec level
                v=-1;
            }
            else {
                //In level
                v=1;

            }
            currentMap.levelGrid.editLevel((int)(x)/16-4,(int)(y)/16-4,v,(int)levelSlider.getValue());
            currentMap.levelGrid.update();
        }
        //not mouse editing
        else {
            //deselect
            if (event.getButton() == MouseButton.SECONDARY&&current!=null) {
                if (isBuilding) {
                    anchor.getChildren().remove(current.texture);
                }
                int x2 = (int) (current.pos.getPosX()) - ((int) (current.pos.getPosX()) % 16);
                int y2 = (int) (current.pos.getPosY()) - ((int) (current.pos.getPosY()) % 16);
                if(!current.floating)
                    if(!isBuilding)
                currentMap.occupyGrid.addToGrid(x2/16 - 4, y2/16 - 4,current.getWidth(),current.getHigh());
                current = null;
                swapBot(true);
                isBuilding = false;
                anchor.getChildren().remove(selectedR);
                selectedR = null;
                anchor.getChildren().remove(selectedStr);
                selectedStr = null;
                return;
            }




                //GET OBJECT BY COORDS
            if (current == null) {
                current = currentMap.getStructure(x, y);
                if (current instanceof EnemyStructure && !mapEditor) {
                    current = null;
                }
                if (current != null)
                {
                //Updating info about struct
                    TextField[] tf= new TextField[]{
                            fName,fHP,fCost,fExtra
                    };
                    for (int i =0; i <4 ;i ++) {
                        tf[i].setText(current.text[i]);
                    }
                    fDesc.setText(current.text[4]);


                int x2 = (int) (current.pos.getPosX()) - ((int) (current.pos.getPosX()) % 16);
                int y2 = (int) (current.pos.getPosY()) - ((int) (current.pos.getPosY()) % 16);
                swapBot(current == null);
                if (selectedStr == null) {
                    selectedStr = new Rectangle(x2  - 2, y2 - 2, current.getWidth() * 16+2, current.getHigh() * 16+2);
                    selectedStr.setStroke(Color.ANTIQUEWHITE);
                    selectedStr.setStrokeWidth(4);
                    selectedStr.setFill(null);
                    anchor.getChildren().add(selectedStr);
                }
                //removing current from grid
                if(!current.floating)
                currentMap.occupyGrid.removeFromGrid(x2 / 16 - 4, y2 / 16 - 4, current.getWidth(), current.getHigh());

            }

            }
            else {


                if (isBuilding) {
                    // placing new
                    if (
                            !(currentMap.occupyGrid.isOccupied((int) (x) / 16 - 4, (int) (y) / 16 - 4, current.getWidth(), current.getHigh()))
                            &&
                                    currentMap.levelGrid.canPlace((int) (x) / 16 - 4, (int) (y) / 16 - 4, current.getWidth(), current.getHigh())
                        //check if can build
                    ) {
                        currentMap.placeObject(current,mapEditor);
                        currentMap.occupyGrid.addToGrid((int) (x) / 16 - 4, (int) (y) / 16 - 4, current.getWidth(), current.getHigh());
                        isBuilding = false;
                        current = null;
                        anchor.getChildren().remove(selectedR);
                        selectedR = null;
                    } else {
                        //  System.out.println("BLOCK");
                    }
                }
                //MOVING
                //OBJECT
                //move selected object if can move
                else {

                    if (
                            !(currentMap.occupyGrid.isOccupied((int) (x) / 16 - 4, (int) (y) / 16 - 4, current.getWidth(), current.getHigh()))
                                    &&
                                    currentMap.levelGrid.canPlace((int) (x) / 16 - 4, (int) (y) / 16 - 4, current.getWidth(), current.getHigh())
                    )
                    if(current.canmove||mapEditor) {

                        if(mapEditor){
                            current.setPos(new Point(x,y));
                        }
                        else{
                            if(!current.reachedPosition){
                                currentMap.occupyGrid.removeFromGrid((int)(current.pos.getPosX())/16-4,(int)(current.pos.getPosY())/16-4,current.getWidth(),current.getHigh());
                            }
                            if(current.floating){
                                double x2 = (int) (current.targetPos.getPosX()) - ((int) (current.targetPos.getPosX()) % 16);
                                double y2 = (int)(current.targetPos.getPosY()) - ((int) (current.targetPos.getPosY()) % 16);
                                currentMap.occupyGrid.removeFromGrid((int)(x2)/16-4,(int)(y2)/16-4,current.getWidth(),current.getHigh());

                            }
                            current.setTarget(new Point(x, y));
                            if(!current.floating) {


                                currentMap.calc.addFloating(current);
                            }

                        }

                           currentMap.occupyGrid.addToGrid((int)(x)/16-4,(int)(y)/16-4,current.getWidth(),current.getHigh());


                        current = null;
                        anchor.getChildren().remove(selectedR);
                        selectedR = null;
                        swapBot(current==null);
                        anchor.getChildren().remove(selectedStr);
                        selectedStr = null;


                    }

                }
            }
        }
    }
    private void swapBot(boolean havecurrent) {
        buildB.setVisible(havecurrent);
        selectB.setVisible(!havecurrent);
    }
    public void setCurrentMap(Map m) {
        currentMap=m;

    }


    public int counter=0;
    public Runnable a = new Runnable() {
        @Override
        public void run() {
            energyBar.setProgress(currentMap.base.eValue / currentMap.base.eStorageMax);
            collectionBar.setProgress(currentMap.base.eGenRate >= currentMap.base.eAskRate ? currentMap.base.eGenRate / currentMap.base.eAskRate : currentMap.base.eAskRate / currentMap.base.eGenRate);
            depletionBar.setProgress(currentMap.base.eGenRate >= currentMap.base.eAskRate ? currentMap.base.eAskRate / currentMap.base.eGenRate : currentMap.base.eGenRate / currentMap.base.eAskRate);
            saturationBar.setProgress(currentMap.base.eGenRate >= 0.5 - currentMap.base.eAskRate ? currentMap.base.eAskRate / currentMap.base.eGenRate /2 : 0.5 + currentMap.base.eGenRate / currentMap.base.eAskRate/2);
            PB_Enr.setText(currentMap.base.eValue + "/" + currentMap.base.eStorageMax);
            PB_Col.setText("" + currentMap.base.eGenRate);
            PB_Dep.setText("" + currentMap.base.eAskRate);
            PB_Dif.setText("" + (currentMap.base.eGenRate - currentMap.base.eAskRate < 0 ? 0 : currentMap.base.eGenRate - currentMap.base.eAskRate));
            PB_Timer.setText("" + currentMap.time / 60 + ":" + currentMap.time % 60);
            if (!currentMap.toDeleteList.isEmpty()) {
                  for(Sprite obj:currentMap.toDeleteList) {
                      anchor.getChildren().remove(obj.texture);
                  }
                  currentMap.toDeleteList.clear();
            }
            if(!currentMap.toAddList.isEmpty()){
                for(Sprite obj:currentMap.toAddList) {
                    anchor.getChildren().add(obj.texture);
                }
                currentMap.toAddList.clear();

            }
            if ( counter > 4*5) {
                  currentMap.creeperGrid.updateRect();
            }

            if(!currentMap.pairs.isEmpty()){
                for(Pair p: currentMap.pairs) {
                    if(!p.alreadyPainted){
                        p.setLine();
                        anchor.getChildren().add(p.l);
                    }
                    else{
                        p.update();
                    }
                }
            }
            if(!currentMap.toDeletePairs.isEmpty()){
                for (Pair p:currentMap.toDeletePairs){
                    anchor.getChildren().remove(p.l);
                }
            }

            //End
        }
    };
    public TimerTask task = new TimerTask() {
        @Override
        public void run() {
            counter++;
            for(int v =0; v < currentMap.calc.period; v++)
            Platform.runLater(currentMap.calc);
            Platform.runLater(a);

            if(counter>20)
                counter=0;
        }
    };
    public Timer t=new Timer();
    public void setMapPainter() {
        this.mapPainter = new MapPainter(anchor, resourceLoader, currentMap, bgL, gridL, conL);
        mapEditY.setVisible(!mapEditor);
        mapEditN.setVisible(mapEditor);

        if (!mapEditor) {

            t.scheduleAtFixedRate(task,10L,25L);

        }

    }

    public void setKeyBindings() {
        scene.setOnKeyPressed(new KeyBinding(scrollPane,currentMap));
    }

    boolean checkBorders(double x, double y) {
        int modX;
        int modY;
        if(current==null) {
            modX=0;
            modY=0;
        }
        else {
            modX=(current.getWidth()-1);
            modY=(current.getHigh()-1);
        }
        if (x>=64&&x<=currentMap.getMapW()*16+16*modX&&y>=64&&y<=currentMap.getMapH()*16+16*modY) {
            return true;
        }
        else return false;
    }




}
