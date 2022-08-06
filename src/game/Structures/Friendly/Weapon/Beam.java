package game.Structures.Friendly.Weapon;

import game.Structures.FriendlyWeapon;
import game.map.Map;
import resource.ResourceLoader;

public class Beam extends FriendlyWeapon {


    public Beam(boolean creative, Map map) {
        super(creative,map);
    }
    public static Beam makeEmpty(ResourceLoader resourceLoader, boolean creative, Map map) {
        Beam obj = new Beam(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }

}
