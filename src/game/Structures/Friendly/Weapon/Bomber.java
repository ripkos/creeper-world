package game.Structures.Friendly.Weapon;

import game.Structures.FriendlyWeapon;
import game.map.Map;
import resource.ResourceLoader;

public class Bomber extends FriendlyWeapon {
    public Bomber(boolean creative, Map map) {
        super(creative,map);
    }

    public static Bomber makeEmpty(ResourceLoader res,boolean creative, Map map) {

        Bomber obj = new Bomber(creative,map);
        obj.updateTexture(res);
        return obj;
    }
}
