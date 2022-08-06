package game.Structures.Friendly.Weapon.WeaponProjectile;

import game.Grids.CreepLayout;
import game.Structures.Friendly.Weapon.Mortar;
import game.Structures.FriendlyWeapon;
import game.lib.Point;
import resource.Sprite;

public class AbstractProjectile extends Sprite {
    int damage;
    int area;
    CreepLayout crp;

    public AbstractProjectile(int damage, int area, CreepLayout crp, FriendlyWeapon wpn, Point targetPoint) {
        this.damage = -damage;
        this.area = area;
        this.crp = crp;
        this.high=1;
        this.width=1;
        this.updateTexture(wpn,true);
        this.pos=new Point(wpn.pos);
        this.texture.setX(pos.getPosX());
        this.texture.setY(pos.getPosY());
        this.targetPos=targetPoint;
        //System.out.println("CREATED");
        this.speed=3;
        if(wpn instanceof Mortar) {
            this.speed=2;
        }
    }

    public void doDamage() {
        crp.addCreep(damage,(int)(pos.getPosX())/16-4-area/2,(int)(pos.getPosY())/16-4-area/2,area,area);
    }
}
