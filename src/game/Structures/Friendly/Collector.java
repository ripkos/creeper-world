package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Collector extends FriendlyStructure {
    private int energyRadius=3;
    boolean counted=false;

    public Collector(boolean creative, Map map) {
        super(creative,map);
    }

    public static Collector makeEmpty(ResourceLoader resourceLoader,boolean creative,Map map) {

        Collector obj = new Collector(creative, map);
        obj.updateTexture(resourceLoader);
        return obj;
    }

    @Override
    public void connect(Map map) {
        super.connect(map);
        if(!counted&&this.base!=null){
            map.energyGrid.addEnergy(1,energyRadius,(int)(this.pos.getPosX()/16),(int)(this.pos.getPosY()/16),map.levelGrid.grid);
            counted=true;
        }
    }
    @Override
    public void disconnect(Map map) {
        super.disconnect(map);
        if(counted&&this.base!=null){
            map.energyGrid.addEnergy(-1,energyRadius,(int)(this.pos.getPosX()/16),(int)(this.pos.getPosY()/16),map.levelGrid.grid);
            counted=false;
        }
    }
}
