package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Reactor extends FriendlyStructure {
    public double eGenRate=1;

    public Reactor(boolean creative, Map map) {
        super(creative, map);
    }

    public static Reactor makeEmpty(ResourceLoader resourceLoader,boolean creative, Map map) {

        Reactor obj = new Reactor(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }
}
