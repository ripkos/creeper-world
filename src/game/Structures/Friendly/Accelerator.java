package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Accelerator extends FriendlyStructure {
    public Accelerator(boolean creative, Map map) {
        super(creative,map);
    }

    public static Accelerator makeEmpty(ResourceLoader resourceLoader, boolean creative, Map map) {

        Accelerator obj = new Accelerator(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }
}
