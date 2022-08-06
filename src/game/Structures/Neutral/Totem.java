package game.Structures.Neutral;

import game.Structures.Friendly.Base;
import game.Structures.NeutralStructure;
import game.lib.Connectable;
import resource.ResourceLoader;

import java.util.ArrayList;

public class Totem extends NeutralStructure {
    protected Base base;
    protected ArrayList<Connectable> network;
    protected int connectionRadius=10*16;
    int waitQueue=0;

    public Totem() {
        super();
    }

    public static Totem makeEmpty(ResourceLoader resourceLoader) {
        Totem obj = new Totem();
        obj.updateTexture(resourceLoader);
        return obj;
    }


    public void askSupply(int i) {
        if(base!=null) {
            //TBD
        }
    }

}
