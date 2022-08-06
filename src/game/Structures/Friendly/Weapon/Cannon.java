package game.Structures.Friendly.Weapon;

import game.Structures.FriendlyWeapon;
import game.map.Map;
import resource.ResourceLoader;

public class Cannon extends FriendlyWeapon {


    public Cannon(boolean creative, Map map) {
        super(creative,map);
        text[0]="Cannon";

        text[4]="Can not shoot at elevation higher than current";
        this.maxAmmo=20;
        this.ammo=20;
        this.fireRate=1;
        this.damage=200;
        this.area=3;
        this.fireRange=10;
        this.canFireUp=false;
    }

    public static Cannon makeEmpty(ResourceLoader resourceLoader, boolean creative, Map map) {

        Cannon obj = new Cannon(creative,map);
        obj.updateTexture(resourceLoader);
        return obj;
    }
}
