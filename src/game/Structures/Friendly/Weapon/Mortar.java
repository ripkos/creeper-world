package game.Structures.Friendly.Weapon;



import game.Structures.FriendlyWeapon;
import game.map.Map;
import resource.ResourceLoader;

public class Mortar extends FriendlyWeapon {


    public Mortar(boolean creative, Map map) {
        super(creative,map);
        this.maxAmmo=10;
        this.ammo=10;
        this.fireRate=6;
        this.damage=5000;
        this.area=7;
        this.fireRange=20;

    }


    public static Mortar makeEmpty(ResourceLoader resourceLoader,boolean creative,Map map) {

        Mortar obj = new Mortar(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }
}
