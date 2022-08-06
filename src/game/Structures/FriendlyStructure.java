package game.Structures;


import game.Structures.Friendly.Base;
import game.lib.Connectable;
import game.lib.Point;
import game.map.Map;
import resource.Sprite;

import java.util.ArrayList;

public abstract class FriendlyStructure extends AbstractStructure implements Connectable {
    protected Base base;
    protected ArrayList<Connectable> network;
    protected int connectionRadius=12*16;
    protected int cWaitQueue=0;
    protected boolean bidirectional=true;
    public int currentConstr=0;
    public int constrCost=10;

    public boolean finished;

    public FriendlyStructure(boolean creative, Map map) {
        super();
        this.network=new ArrayList<>();

        finished=creative;
        if(!creative) {
           // connect(map);
        }
        else {
           // disconnect(map);
        }
        this.isImmortal=false;
        this.text=new String[]{
                "Name",
                ("HP:"+hp+"/"+hpMAX),
                ("Cost:"+constrCost+", Radius:"+connectionRadius),
                "Extra",
                "Desc"
        };
    }

    @Override
    public void setBase(Base b){
        this.base=b;
    }

    @Override
    public Base getBase(){
        return this.base;
    }

    @Override
    public boolean hasBase(){
        return this.base!=null;
    }

    @Override
    public ArrayList<Connectable> getNetwork(){
        return this.network;
    }

    @Override
    public void checkBase(Connectable current,ArrayList<Connectable> checkedList){
        if(!current.getNetwork().isEmpty()&&!checkedList.contains(this)){
            for(Connectable con:current.getNetwork()){
                if(con instanceof Base){
                    for (Connectable c:checkedList){
                        if(!c.hasBase())
                            c.setBase((Base)con);

                    }
                    return;
                }
                else{
                    if(!checkedList.contains(con)) {
                        checkedList.add(con);
                        checkBase(con, checkedList);
                    }
                }
            }
        }

    }
    @Override
    public int getSizeH(){
        return this.getHigh();
    }
    @Override
    public int getSizeW(){
        return this.getWidth();
    }
    @Override
    public boolean isBiDirect() {
        return this.bidirectional;
    }
    @Override
    public Point getPos(){
        return new Point(this.pos);
    }
    @Override
    public void connect(Map map){
        if(map.spriteList.size()>2)
        for (Sprite obj:map.spriteList)
        {
        if(obj instanceof  Connectable && obj!=this) {
            if (this.canConnectTo((Connectable) obj)) {
                this.addVertex((Connectable) obj);
               // if(((Connectable) obj).isBiDirect())
                ((Connectable) obj).addVertex(this);
                map.addEdge(this,((Connectable) obj));
            }
            }
        }
        if(base==null){
            ArrayList<Connectable> checkedList = new ArrayList<Connectable>();
            checkBase(this,checkedList);
        }
    }

    @Override
    public void disconnect(Map map) {
       if(!network.isEmpty())
        for(Connectable a:network){
            a.getNetwork().remove(this);
            map.removeEdge(this,a);
        }
       this.base=null;
    }

    @Override
    public boolean canConnectTo(Connectable con){
        return Point.getDistance(this.pos,((Connectable) con).getPos())<=this.connectionRadius&&Point.getDistance(this.pos,((Connectable) con).getPos())<=((Connectable) con).getConnectionRadius();
    }

    @Override
    public void addVertex(Connectable con){
        if(!network.contains(con))
        this.network.add(con);
    }

    @Override
    public void removeVertex(Connectable con){
        if(network.contains(con))
            this.network.remove(con);
    }

    public void askSupply(int i) {
        if(base!=null) {
            //TBD
        }
    }
    @Override
    public int getConnectionRadius() {
        return this.connectionRadius;
    }

}
