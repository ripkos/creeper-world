package game.lib;

import game.Structures.Friendly.Base;
import game.map.Map;

import java.util.ArrayList;

public interface Connectable {
    void connect(Map map);
    void disconnect(Map map);
    int getConnectionRadius();
    void addVertex(Connectable con);
    void removeVertex(Connectable con);
    boolean canConnectTo(Connectable con);
    void checkBase(Connectable current,ArrayList<Connectable> checkedList);
    void setBase(Base base);
    Base getBase();
    boolean hasBase();
    ArrayList<Connectable> getNetwork();
    Point getPos();
    boolean isBiDirect();
    int getSizeW();
    int getSizeH();
}
