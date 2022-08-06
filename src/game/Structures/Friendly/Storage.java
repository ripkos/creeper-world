package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Storage extends FriendlyStructure {
    boolean alreadyCounted=true;
    public Storage(boolean creative, Map map) {
        super(creative,map);
    }

    public static Storage makeEmpty(ResourceLoader resourceLoader,boolean creative,Map map) {
        Storage obj = new Storage(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }

    @Override
    public void connect(Map map) {
        super.connect(map);
        if(!alreadyCounted&&this.base!=null)
        {this.base.incStorage(20);
        alreadyCounted=true;}
    }
    @Override
    public void disconnect(Map map) {
        if(alreadyCounted&&this.base!=null) {
            this.base.incStorage(-20);
            alreadyCounted = false;
            super.disconnect(map);
        }
    }
}
