package game.map;

import game.Grids.CreepLayout;
import game.Grids.EnergyLayout;
import game.Grids.LevelLayout;
import game.Grids.OccupyGrid;
import game.Structures.AbstractStructure;
import game.Structures.Friendly.Base;
import game.lib.CalcThread;
import game.lib.Connectable;
import game.lib.Pair;
import game.lib.Point;
import resource.ResourceLoader;
import resource.Sprite;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {

    //Public data
    public double pGenRate=1;
    public double pAskRate=0;
    public ArrayList<Sprite> toDeleteList;
    public ArrayList<Pair> toDeletePairs;
    public ArrayList<Sprite> toAddList;
    public int time=0;
    public ArrayList<Pair> pairs;
    //public boolean paused=true;


    //Map info
   public String mName;
    public String mDesc;
    public String mID;
    public String saveName;



    int mapW,mapH;

    //Map>GameController
   // boolean started;

    public CreepLayout creeperGrid;

    public EnergyLayout energyGrid;
    public LevelLayout levelGrid;
    public OccupyGrid occupyGrid;
    //No network, each object have list of connected elements
    //Network /* list */ networkList;
   public ArrayList<Sprite> spriteList;

   public Base base;
    public CalcThread calc;

    public Map(ResourceLoader r) {
        calc = new CalcThread(this, r);
    }
    private Map(String mName, String mDesc, String mID, ResourceLoader r) {
        this(r);
        this.mName=mName;
        this.mDesc=mDesc;
        this.mID=mID;
        int x1=(int)((Math.random()*100)%10);
        int x2=(int)((Math.random()*100)%10);
        int x3=(int)((Math.random()*100)%10);
        this.saveName=mID+x1+x2+x3;
        this.pairs=new ArrayList<>();
        this.toDeletePairs=new ArrayList<>();

    }

    private Map(int h, int w,String mName, String mDesc, String mID, ResourceLoader r) {
        this(mName,mDesc,mID,r);
        //paused=true;
        mapH=h;
        mapW=w;
       // started=false;
        levelGrid=new LevelLayout(mapW,mapH);
        creeperGrid=new CreepLayout(mapW,mapH);
        occupyGrid=new OccupyGrid(mapW,mapH);
        energyGrid=new EnergyLayout(mapW,mapH);
        spriteList=new ArrayList<>();
        toDeleteList=new ArrayList<>();
        toAddList=new ArrayList<>();

    }
    public static Map createEmptyMap(int h, int w,String mName, String mDesc, String mID,ResourceLoader r) {
        if (h<54||h>125) {
            h=54;
        }
        if(w<113||w>250) {
            w=113;
        }
        return new Map(h,w,mName,mDesc,mID,r);
    }

    public int getMapW() {
        return mapW;
    }

    public int getMapH() {
        return mapH;
    }

    public void placeObject(Sprite str,boolean creative) {
        int x1=(int)((Math.random()*100)%10);
        int x2=(int)((Math.random()*100)%10);
        int x3=(int)((Math.random()*100)%10);
        this.saveName=mID+x1+x2+x3;
        spriteList.add(str);
        if(str instanceof Base)
            base=(Base)str;
        if(str instanceof Connectable){
            ((Connectable)str).connect(this);
        }
        calc.checkAdd(str);

    }

    public void removeObject(Sprite str) {
        if(str instanceof Connectable){
            ((Connectable)str).disconnect(this);
        }
        calc.checkRemove(str);
        spriteList.remove(str);
        //if(str instanceof Base)
            //gameOver();
        removeFromGrid(str);

    }
    public void removeObject(Base str, boolean creative) {
        if(!creative)
            removeObject(str);
        else {
           // calc.checkRemove(str);
            spriteList.remove(str);
        }
        removeFromGrid(str);
        //if(str instanceof Base)

    }

    private void removeFromGrid(Sprite current) {
        int x2 = (int) (current.pos.getPosX()) - ((int) (current.pos.getPosX()) % 16);
        int y2 = (int) (current.pos.getPosY()) - ((int) (current.pos.getPosY()) % 16);
        occupyGrid.removeFromGrid(x2 / 16 - 4, y2 / 16 - 4, current.getWidth(), current.getHigh());
    }

    public AbstractStructure getStructure(double x, double y) {
        double dis=0;
        AbstractStructure str = null;
        for (Sprite obj: spriteList
             ) {
            if (obj instanceof  AbstractStructure) {
                if(str==null) {
                    str=(AbstractStructure)obj;
                    dis=Point.getDistance(str.pos,x,y);
                }
                else {
                    if(Point.getDistance(obj.pos,x,y)<dis) {
                        str=(AbstractStructure)obj;
                        dis=Point.getDistance(str.pos,x,y);
                    }
                }
            }
        }
        return str;
    }

    public void addEdge(Connectable a, Connectable b) {
    Pair p=new Pair(a,b);
    //p.setLine();
    if(!pairs.contains(p))
    pairs.add(p);
    }
    public void removeEdge(Connectable a, Connectable b){
        ArrayList<Pair> toRemove=new ArrayList<>();
        Pair p=new Pair(a,b);
        if(!pairs.isEmpty())
        for (Pair p2:pairs){
            if(p.equals(p2)){
                toRemove.add(p2);
            }
        }
        if(!toRemove.isEmpty())
        for(Pair p2:toRemove){
            pairs.remove(p2);
            toDeletePairs.add(p2);

        }

    }
}


