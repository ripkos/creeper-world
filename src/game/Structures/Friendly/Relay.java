package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Relay extends FriendlyStructure {
    public Relay(boolean creative, Map map) {
        super(creative,map);
        this.connectionRadius=24*16;
    }

    public static Relay makeEmpty(ResourceLoader resourceLoader,boolean creative,Map map) {
        Relay obj = new Relay(creative,  map);
        obj.updateTexture(resourceLoader);
        return obj;
    }


}
